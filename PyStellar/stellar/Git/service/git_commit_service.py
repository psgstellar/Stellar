import requests


class GitCommitCheckService:
    """Github Public 저장소 커밋 기록 가져오기"""

    @classmethod
    def git_public_request(cls, request):
        """Commit 기록 요청"""
        print('-------request-------', request)
        owner = request['github_name']
        repo = request['github_repo']
        if request.get('since') and request.get('until'):
            # date_params = {'since': request['since'],
            #                'until': request['until']}
            since = request['since']
            until = request['until']
            r = requests.get(f'https://api.github.com/repos/{owner}/{repo}/commits?my_client_id={owner}?since={since}?until={until}')
        elif request.get('since'):
            # date_params = {'since': request['since']}
            since = request['since']
            r = requests.get(f'https://api.github.com/repos/{owner}/{repo}/commits?my_client_id={owner}?since={since}')

        elif request.get('until'):
            # date_params = {'until': request['until']}
            until = request['until']
            r = requests.get(f'https://api.github.com/repos/{owner}/{repo}/commits?my_client_id={owner}?until={until}')

        else:
            r = requests.get(f'https://api.github.com/repos/{owner}/{repo}/commits?my_client_id={owner}')
        data = r.json()
        commit_list = []
        if len(data) == 0:
            commit_json = {'message': '오늘 커밋 없음'}
            commit_list.append(commit_json)
        elif data['message'] == 'Not Found':
            commit_json = {'message': data}
            commit_list.append(commit_json)
        else:
            for i in data:
                for k, v in i.items():
                    if k == 'commit':
                        commit_json = {}
                        commit_json.update({'author_name': v['author']['name'],
                                            'message': v['message'],
                                            'url': v['url']})
                commit_list.append(commit_json)
        return commit_list
