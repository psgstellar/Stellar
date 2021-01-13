from drf_yasg import openapi

from drf_yasg.utils import swagger_auto_schema
from rest_framework.generics import CreateAPIView, RetrieveAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.views import APIView

from commit.commitSerializer import commitHistory
from commit.service.commitservice import commithistory


class commitMessage(RetrieveAPIView):
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
        # manual_parameters=[openapi.Parameter('latest', openapi.IN_HEADER, description='마지막시간',
        #                                      type=openapi.TYPE_STRING),
        #                    openapi.Parameter('oldest', openapi.IN_HEADER, description='최초시간',
        #                                      type=openapi.TYPE_STRING)],
        query_serializer=commitHistory,
        tags=['Slack Commit', ],
        responses={ "200": 'success' },
        operation_summary = "Slack에서 Message History를 받아온다.",
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
        response = r.historyrequest()
        print('----response-----', response)
        result = {'result': response}
        print('----result-----', result)
        return Response(result)
