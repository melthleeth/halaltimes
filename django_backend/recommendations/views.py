from django.shortcuts import render
from django.db.models import Count

from .models import Review, User, Store, DjangoRecomm, DjangoUser, DjangoReview

from .algorithm.recommender import ItemBased

from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

import pandas as pd
import pickle
from sklearn.cluster import KMeans
from sklearn.neighbors import KNeighborsClassifier
import json

filename = 'finalized_model.sav'

@api_view(["GET"])
def adminUpdate(request):
    try:
        userUpdate()
        reviewUpdate()
        userClusterd(n=30)
        clusterModel(n=30)
        similarStore()
        msg = "success"
    except:
        msg = "fail"

    return Response(msg)


def userUpdate():
    print('')
    DjangoUser.objects.filter(is_skeleton=0).delete()
    halaltime_users_all = User.objects.filter(
        active = 1
    ).values(
        'id_user', 'born_year', 'gender'
    )

    halaltime_reviews_all = Review.objects.filter(
        active = 1
    ).values(
        'id_user', 'id_store'
    )

    for line in halaltime_users_all:
        id_user = line["id_user"]
        born_year = line["born_year"]
        age = 2021 - int(born_year[:4]) + 1
        if line["gender"]:
            gender_m, gender_f = 0, 1
        else:
            gender_m, gender_f = 1, 0

        review_cnt = halaltime_reviews_all.filter(
            id_user = id_user
        ).count()
        DjangoUser.objects.create(
            id_user = id_user,
            age=age,
            gender_m=gender_m,
            gender_f=gender_f,
            review_cnt= review_cnt
        )
    

def reviewUpdate():
    print("reviewUpdate")
    DjangoReview.objects.filter(is_skeleton=False).delete()
    halaltime_reviews_all = Review.objects.filter(
        active = 1
    ).values(
        'id_user', 'id_store', 'score'
    )

    for line in halaltime_reviews_all:
        store = Store.objects.only('id_store').get(id_store=line["id_store"])
        id_user = line['id_user']
        score = line['score']

        DjangoReview.objects.create(
            id_store=store,
            id_user=id_user,
            score=score,
        )


def userClusterd(n):
    print("userClusterd")
    user_data = DjangoUser.objects.values(
        'id_django_user',
        'id_user',
        'age',
        'gender_m',
        'gender_f',
        'review_cnt',
        'label'
    )

    data = []
    for line in user_data:
        age = line["age"]
        gender_m = line["gender_m"]
        gender_f = line["gender_f"]
        reviews = line["review_cnt"]
        data.append([age, gender_m, gender_f, reviews])

    df = pd.DataFrame(data, columns=["age", "gender_m", "gender_f", "reviews"])

    kmeans = KMeans(n_clusters=n).fit(df)
    n=0
    for user in  DjangoUser.objects.all():
        user.label = kmeans.labels_[n]
        user.save()
        n+=1


def clusterModel(n):
    print("clusterModel")
    user_data = DjangoUser.objects.values(
        'age','gender_m', 'gender_f','review_cnt','label'
    )

    user_df = pd.DataFrame(list(user_data))
    classifier = KNeighborsClassifier(n_neighbors=n)
    classifier.fit(user_df[["age","gender_m","gender_f","review_cnt"]], user_df["label"])
    pickle.dump(classifier, open(filename, 'wb'))


def similarStore():
    print("similarStore")
    review_data = DjangoReview.objects.values(
        'id_user', 'id_store','score'
    )
    data = {}

    for line in review_data:
        user_label = DjangoUser.objects.only('label').get(id_user=line['id_user'])
        user = user_label.label
        item = line['id_store']
        score = line['score']

        data.setdefault(user, {})
        data[user][item] = float(score)

    ibcf = ItemBased()
    ibcf.loadData(data)
    model = ibcf.buildModel(nNeighbors=15)

    DjangoRecomm.objects.all().delete()
    for user in data.keys():
        recommendation = ibcf.Recommendation(user, model=model)
        for store in recommendation:
            id_store = Store.objects.only('id_store').get(id_store=store)
            DjangoRecomm.objects.create(id_store=id_store, label=user)


def transposePrefs(prefs):
    transposed = {}
    for obj in prefs:
        for subj in prefs[obj]:
            transposed.setdefault(subj, {})
            transposed[subj][obj] = prefs[obj][subj]
    return transposed


def userLabel(age, gender):
    if gender:
        new_user = [[age, 1, 0, 0]]
    else:
        new_user = [[age, 0, 1, 0]]

    model = pickle.load(open(filename, 'rb'))
    label = model.predict(new_user)

    return label[0]


@api_view(['POST'])
def newUser(request):
    born_year = request.POST['born_year']
    gender = request.POST['gender']
    age = 2021 - int(born_year[:4]) + 1

    try:
        label = userLabel(age, gender)

        user = DjangoUser()

        user.age = age
        if gender:
            user.gender_m, user.gender_f = 0, 1
        else:
            user.gender_m, user.gender_f = 1, 0
        user.is_skeleton = False
        user.review_cnt = 0
        user.label = label

        user.save()
        msg = "success"

    except:
        msg = "fail"

    return Response(msg)