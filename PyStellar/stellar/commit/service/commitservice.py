import requests

token = 'xoxb-992233197952-1249394514658-FBiWQecqbunwQPFJZ8uZS80B'
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

    def commitlist(self):
        commitmessage = {}
        attachments = []
        commit = {}
        commi = []

        data = self.historyrequest()

        if data['ok']:
            messagedata = data['messages']
            for i in messagedata:
                if "attachments" in i:
                    attachments.append(i['attachments'])
        else:
            print('request 실패')
            result_json = {'result': 'False'}
            return result_json
        for i in attachments:
            for j in i:
                if 'author_name' in j:
                    # print('------author_name------', j['author_name'])
                    commi.append(j['author_name'])
                    commitList = set(commi)
        print('------commitList------', commitList)
        print('---------len------', len(attachments))
        return commitList
