from django.shortcuts import render

from recommendations.models import User_Reviews

from .algorithm.recommender import ItemBased

from rest_framework.decorators import api_view

# User_Reviews : class 이름 == table 이름
@api_view(['GET'])
def loadData(request):
    data = User_Reviews.objects.all()
    print("TEST: Done load data")

    ibcf = ItemBased()
    ibcf.loadData(data)
    model = ibcf.buildModel(nNeighbors=20)
    
    num = 0
    for user in data.keys():
      if num == 5:
        break
      recommendation = ibcf.Recommendation(user, model=model)
      print(user)
      print(recommendation)
      print("--------------------"+"\n")
      num += 1


    # DB 저장하고 끝내기 해야하지롱 return 값 뭐하고 할지 모르겠어요
    return True

def transposePrefs(prefs):
    transposed = {}
    for obj in prefs:
        for subj in prefs[obj]:
            transposed.setdefault(subj, {})
            transposed[subj][obj] = prefs[obj][subj]
    return transposed


