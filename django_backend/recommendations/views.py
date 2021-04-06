from django.shortcuts import render

from .models import Review, DjangoRecomm, DjangoUser

from .algorithm.recommender import ItemBased

from rest_framework.decorators import api_view

# 비슷한 식당을 불러와 줌
@api_view(['GET'])
def similarStore(request):
    review_data = Review.objects.all()
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



# 신규 유저 등장 시 라벨 값 반환하여 DjangoUser에 insert 시키기

# 관리자가 업데이트 클릭 시 진행하는 모든 로직만들기
# 유저 클러스터 업데이트
    # review_cnt 업데이트도 꼭 하기
# 유사한 식당 djangoRecomm도 업데이트하기
    # similarStore 이용