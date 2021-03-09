import requests


class GitCommitCheckService:
    """Github Public 저장소 커밋 기록 가져오기"""

    @classmethod
    def git_public_request(cls, request):
        """Commit 기록 요청"""
        owner = request.GET['owner']
        repo = request.GET['repo']
        if request.GET.get('since') and request.GET.get('until'):
            date_params = {'since': request.GET['since'],
                           'until': request.GET['until']}
            r = requests.get(f'https://api.github.com/repos/{owner}/{repo}/commits', params=date_params)
        elif request.GET.get('since'):
            date_params = {'since': request.GET['since']}
            r = requests.get(f'https://api.github.com/repos/{owner}/{repo}/commits', params=date_params)

        elif request.GET.get('until'):
            date_params = {'until': request.GET['until']}
            r = requests.get(f'https://api.github.com/repos/{owner}/{repo}/commits', params=date_params)

        else:
            r = requests.get(f'https://api.github.com/repos/{owner}/{repo}/commits')
        data = r.json()
        commit_list = []

        for i in data:
            for k, v in i.items():
                if k == 'commit':
                    commit_json = {}
                    commit_json.update({'author_name': v['author']['name'],
                                        'message': v['message'],
                                        'url': v['url']})
            commit_list.append(commit_json)
        return commit_list
