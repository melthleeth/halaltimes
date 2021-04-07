from django.urls import path
from . import views

app_name = "recommendations"

urlpatterns = [
  path('',views.adminUpdate, name='adminUpdate')
  path('/new_user',views.newUser, name='newUser')
]