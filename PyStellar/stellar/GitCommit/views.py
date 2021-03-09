from drf_yasg import openapi
from drf_yasg.utils import swagger_auto_schema
from rest_framework.generics import ListAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response

from GitCommit.serializers import GitCommitCheckSerializer
from GitCommit.service.git_commit_service import GitCommitCheckService


class GitCommitCheck(ListAPIView):
    """ Github에서 커밋 기록을 가져온다. """
    permission_classes = [AllowAny, ]

    @swagger_auto_schema(
        query_serializer=GitCommitCheckSerializer,
        tags=['Git Commit', ],
        responses={"200": openapi.Schema(type=openapi.TYPE_STRING,
                                         description='성공')},
        operation_summary="git commit 체크",
        produces='application/json',
        operation_description=
        """
        GitCommitCheck
        ---
                        Github에서 직접 커밋 기록 가져옴

            """

    )
    def get(self, request, *args, **kwargs):

        r = GitCommitCheckService()
        data = r.git_public_request(request)
        return Response(data)