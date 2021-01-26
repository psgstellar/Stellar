import requests

from commit.dao.commit_dao import CommitDao
from commit.resources.commit_sql import insert_commit
from psguser.service.psguser_service import PsgUserListService

token = 'xoxb-992233197952-1661853654679-wdyMzsIpoLudwtWSPryzKBcD'
channel = 'CUW4CLH4Z'
url = 'https://slack.com/api/conversations.history'


class commithistory:

    def __init__(self, latest, oldest):
        self.latest = latest
        self.oldest = oldest

    def historyrequest(self):
        latest = self.latest
        oldest = self.oldest
        print('------time-----', latest)

        r = requests.get(f"{url}?token={token}&channel={channel}&latest={latest}&oldest={oldest}&pretty=1")

        data = r.json()
        return data

    def messagelist(self, data):
        attachments = []

        if data['ok']:
            message = data['messages']
            for i in message:
                if "attachments" in i:
                    attachments.append(i['attachments'])
        else:
            print('request 실패')
            result_json = {'result': 'False'}
            return result_json
        return attachments

    def commituserlist(self, attachments):
        commitlist = []
        for i in attachments:
            for j in i:
                if 'author_name' in j:
                    # print('------author_name------', j['author_name'])
                    commitlist.append(j['author_name'])
                    commitList = set(commitlist)
        print('------commitList------', commitList)
        print('---------len------', len(attachments))
        return commitList

    def insertcommit(self, attachments):

        insert_commit_json = CommitDao()
        return_json = insert_commit_json.insertcommit(attachments)

        return return_json

    def commitcheck(self, commitList):
        restlist = PsgUserListService()
        restuser = restlist.psgusergitlistservice()
        unchecklist = []
        print('-------commitlist-------', commitList)
        print('-------restuser-------', restuser)
        for i in commitList:
            print('-----i-----', i)
            for j in restuser:
                print('-----j-----', restuser)
                for k, v in j.items():
                    print('-------items--------', j.items())
                    if k == 'github_name':
                        print('----k----', v)
                        if i == v:
                            print('-----v-----', v)
                            restuser.remove(j)
        print('-----restuser-----', restuser)
        return restuser

