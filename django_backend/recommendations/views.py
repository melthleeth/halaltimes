from django.shortcuts import render
from djanago.db.models import Count

from .models import Review, DjangoRecomm, DjangoUser

from .algorithm.recommender import ItemBased

from rest_framework.decorators import api_view

# 파이썬 패키지



'''
나는야 관리자~ 업데이트를 하지
'''
@api_view(["GET"])
def adminUpdate():
    userUpdate()
    userClusterd()
    clusterModel()
    similarStore()


def userUpdate():
    # is_skeleton 아닌 값만 DjangoUser DB에서 삭제

    # User에서 데이터 가져와서 DjangoUser 채움 

    # cf
    # user_review_cnt = Review.objects.values(
    #     'id_user'
    # )
   
    # review_cnt = user_review_cnt.filter(
    #     id_user=line.id_user,
    #     active=line.active
    # ).values(
    #     'id_user'
    # ).annotate(
    #     reviews=Count('id_user')
    # )

def userClusterd(request):
    # user Update 함
    
    # update 된 DjangoUser DB 사용
    user_data = DjangoUser.objects.values(
        'id_user','born_year','gender'
    )
    data = []
    for line in user_data:
        age = line.age

        if line.gender:
            gender_m, gender_f = 1, 0
        else:
            gender_m, gender_f = 0, 1

        reviews = line.review_cnt

        data.append([age, gender_m, gender_f, reviews])
    
    # data 값 넣고 돌려서 label 값 부여하기 output : dataframe
    # 순서대로 DB에 label 값 update


    # label 업데이트 완성 되면 similarStore 실행하여 DjangoRecomm 채움


def clusterModel():
    # 클러스터 작업을 진행 한 후 모델을 저장
    # userClusterd 함께 넣을지 고민


@api_view(['POST'])
def similarStore(request):
    review_data = Review.objects.values(
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

    # DjangoRecomm DB 날리는 작업 실행
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



'''
나는 자동화 작업이지롱 
관리자는 필요 없어~ >_<
'''
def userLabel(age, gender):
    if gender:
        new_user = [age, 1, 0, 0]
    else:
        new_user = [age, 0, 1, 0]

    # 모델 저장한 거 사용
    label = 0
    return label


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
    user.gender = gender
    user.is_skeleton = False
    user.review_cnt = 0
    user.label = label

    user.save()