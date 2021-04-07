from django.shortcuts import render
from django.db.models import Count

from .models import Review, User, DjangoRecomm, DjangoUser

from .algorithm.recommender import ItemBased

from rest_framework.decorators import api_view


import pandas as pd
import pickle
from sklearn.cluster import KMeans
from sklearn.neighbors import KNeighborsClassifier


@api_view(["GET"])
def adminUpdate():
    userUpdate()
    userClusterd(n=30)
    clusterModel(n=30)
    similarStore()


def userUpdate():
    # is_skeleton 아닌 값만 DjangoUser DB에서 삭제
    DjangoUser.objects.filter(is_skeleton=False).delete()

    halaltime_users_all = User.objects.filter(
        active = 1
    ).values(
        'id_user', 'born_year', 'gender'
    )

    halaltime_reviews_all = Review.objects.filter(
        active = 1
    ).values(
        'id_user'
    )
    # User에서 데이터 가져와서 DjangoUser 채움 
    for line in halaltime_users_all:
        id_user = line.id_user
        born_year = line.born_year
        age = 2021 - int(born_year[:4]) + 1
        if line.gender:
            gender_m, gender_f = 1, 0
        else:
            gender_m, gender_f = 0, 1

        halaltime_reviews_all.filter(
            id_user = id_user
        ).annotate(
            review_cnt = Count('id_user')
        )

        DjangoUser.objects.create(
            id_user = id_user,
            age=age,
            gender_m=gender_m,
            gender_f=gender_f,
            review_cnt=review_cnt
            )
    

def userClusterd(n):
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
        age = line.age
        gender_m = line.gender_m
        gender_f = line.gender_f
        reviews = line.review_cnt
        data.append([age, gender_m, gender_f, reviews])

    df = pd.DataFrame(data, columns=["age", "gender_m", "gender_f", "reviews"])

    kmeans = KMeans(n_clusters=n).fit(df)

    for idx in range(len(kmeans.labels_)):
        user = user_data.filter(id_django_user=idx)
        user.label = kmeans.labels_[idx]
        user.save()


def clusterModel(n):
    user_data = DjangoUser.objects.values(
        'age','gender_m', 'gender_f','reviews_cnt','label'
    )
    data = user_data.values(
        'age','gender_m', 'gender_f','reviews_cnt'
    )
    classifier = KNeighborsClassifier(n_neighbors=n)
    classifier.fit(data, user_data.label)

    filename = 'finalized_model.sav'
    pickle.dump(classifier, open(filename, 'wb'))


@api_view(['POST'])
def similarStore():
    review_data = Review.objects.filter(
        active = 1
    ).values(
        'id_user', 'id_store','score'
    )
    data = {}
    for line in review_data:
        line_user = line.id_user
        user_label = DjangoUser.objects.filter(id_user=line_user)

        user = user_label.label
        item = line.id_store
        score = line.score

        data[user][item] = float(score)

    ibcf = ItemBased()
    ibcf.loadData(data)
    model = ibcf.buildModel(nNeighbors=15)

    DjangoRecomm.objects.all().delete()
    for user in data.keys():
        recommendation = ibcf.Recommendation(user, model=model)
        for store in recommendation:
            DjangoRecomm.objects.create(id_store=store, label=user)


def transposePrefs(prefs):
    transposed = {}
    for obj in prefs:
        for subj in prefs[obj]:
            transposed.setdefault(subj, {})
            transposed[subj][obj] = prefs[obj][subj]
    return transposed


def userLabel(age, gender):
    if gender:
        new_user = [age, 1, 0, 0]
    else:
        new_user = [age, 0, 1, 0]

    model = pickle.load(open(filename, 'rb'))
    label = model.predict(new_user)

    return label[0]


@api_view(['POST'])
def newUser(request):
    id_user = request.GET.get("id_user")
    born_year = request.GET.get("born_year")
    gender = request.GET.get("gender")
    age = 2021 - int(born_year[:4]) + 1

    label = userLabel(age, gender)

    user = DjangoUser()
    user.id_user = id_user
    user.age = age
    if gender:
        user.gender_m, user.gender_f = 1, 0
    else:
        user.gender_m, user.gender_f = 0, 1
    user.is_skeleton = False
    user.review_cnt = 0
    user.label = label

    user.save()
