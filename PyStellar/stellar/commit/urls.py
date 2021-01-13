from django.urls import path
from . import views

app_name = 'commit'

urlpatterns = [

    path('history/', views.commitMessage.as_view(), name='commitList')
]
