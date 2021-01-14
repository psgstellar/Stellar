from django.urls import path
from . import views

app_name = 'psguser'

urlpatterns = [

    path('user/', views.userList.as_view(), name='userlist')
]
