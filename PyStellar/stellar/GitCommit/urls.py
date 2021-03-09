from django.urls import path
from . import views

app_name = 'GitCommit'

urlpatterns = [

    path('gitcommithistory', views.GitCommitCheck.as_view()),

]
