from django.shortcuts import render

from .models import Review

from .algorithm.recommender import ItemBased

from rest_framework.decorators import api_view

# 비슷한 식당을 불러와 줌
@api_view(['GET'])
def similarStore(request):
    review_data = Review.objects.all()
    data = {}
    user = review_data.id_user
    item = review_data.id_store
    score = review_data.score

    data[user][item] = float(score)

    ibcf = ItemBased()
    ibcf.loadData(data)

    model = ibcf.buildModel(nNeighbors=15)
    for user in data.keys():
        recommendation = ibcf.Recommendation(user, model=model)
    #   print(user)
    #   print(recommendation)
    #   print("--------------------"+"\n")
        # 


def transposePrefs(prefs):
    transposed = {}
    for obj in prefs:
        for subj in prefs[obj]:
            transposed.setdefault(subj, {})
            transposed[subj][obj] = prefs[obj][subj]
    return transposed


