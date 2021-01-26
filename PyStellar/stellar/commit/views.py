from drf_yasg import openapi

from drf_yasg.utils import swagger_auto_schema
from rest_framework.generics import CreateAPIView, RetrieveAPIView, ListAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.views import APIView

from commit.commitSerializer import commitHistory
from commit.service.commitservice import commithistory


class commitMessageHistory(ListAPIView):
    """
    '''
        Slack에서 CommitMessage 받아오기

    '''
    """
    permission_classes = [AllowAny, ]

    # response = {
    #     "200": openapi.Response(
    #         description="JSON",
    #         required=[],
    #         schema=openapi.TYPE_OBJECT,
    #         property={
    #             "commitList": openapi.Response(type=openapi.TYPE_OBJECT, description='String')
    #         }
    #     )
    # }

    @swagger_auto_schema(
        query_serializer=commitHistory,
        tags=['Slack Commit', ],
        responses={"200": 'success'},
        operation_summary="Slack에서 Message History를 받아온다.",
        produces='application/json',
        operation_description=
        """
        CommitList
        ---
                    SlackHistory 명단
                    '''
                        #json
                        {
                            'latest': 1610582399,
                            'oldest': 1610496000
                        }
                        '''        
            """

    )
    # def get(self, request):
    def get(self, request, *args, **kwargs):
        print('------request----------', request.GET)
        latest = request.GET['latest']
        oldest = request.GET['oldest']
        r = commithistory(latest, oldest)
        data = r.historyrequest()
        attachments = r.messagelist(data)
        commitlist = r.commituserlist(attachments)

        print('----response-----', commitlist)
        result = {'result': commitlist}
        print('----result-----', result)
        return Response(attachments)


class CommitMessage(CreateAPIView):
    """
    커밋 내역 디비 저장
    ---
        ```
        {
            "latest": "1610582399",
            "oldest": "1610496000"
        }
        '''
    """
    permission_classes = [AllowAny, ]
    request_dict = openapi.Schema(type=openapi.TYPE_OBJECT,
                                  required=['latest', 'oldest'],
                                  properties={
                                      'latest': openapi.Schema(type=openapi.TYPE_STRING, description='마지막시간'),
                                      'oldest': openapi.Schema(type=openapi.TYPE_STRING, description='최초시간')
                                  })
    response_dict = {
        "200": openapi.Schema(type=openapi.TYPE_OBJECT,
                              required=[],
                              properties={
                                  'result': openapi.Schema(type=openapi.TYPE_STRING, description='성공')
                              })
    }
    @swagger_auto_schema(
        tags=['Slack Commit', ],
        operation_summary="Slack에서 받아온 Message History를 DB에 저장",
        request_body=request_dict,
        responses=response_dict,
        produces='application/json',
    )
    def post(self, request, *args, **kwargs):
        print('------request.data-----', request.data)
        latest = request.data['latest']
        oldest = request.data['oldest']
        r = commithistory(latest, oldest)
        data = r.historyrequest()
        attachments = r.messagelist(data)
        result = r.insertcommit(attachments)
        return Response(result)

class commitcheck(ListAPIView):
    """
    커밋 체크 디비 저장
    """
    permission_classes = [AllowAny, ]

    @swagger_auto_schema(
        query_serializer=commitHistory,
        tags=['Psg User', ],
        responses={"200": openapi.Schema(type=openapi.TYPE_STRING,
                                         description='성공')},
        operation_summary="commit 체크",
        produces='application/json',
        operation_description=
        """
        CommitCheck
        ---
                    CommitCheck 명단
                    '''
                        #json
                        {
                            'latest': 1610582399,
                            'oldest': 1610496000
                        }
                        '''        
            """

    )
    def get(self, request, *args, **kwargs):
        latest = request.GET['latest']
        oldest = request.GET['oldest']
        r = commithistory(latest, oldest)
        data = r.historyrequest()
        attachments = r.messagelist(data)
        commitlist = r.commituserlist(attachments)
        uncommitlist = r.commitcheck(commitlist)
        return Response(uncommitlist)
