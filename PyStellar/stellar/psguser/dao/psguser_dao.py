import pymysql

from stellar.settings import MYSQLUSER, MYSQLNAME, MYSQLPASSWORD, MYSQLHOST, MYSQLPORT


class PsgUserList:


    def psguserdao(self, sql):
        return_json = {}
        userlist = []

        try:
            print('------try---------', sql)
            connection = pymysql.connect(
                user=MYSQLUSER,
                password=MYSQLPASSWORD,
                host=MYSQLHOST,
                db=MYSQLNAME,
                charset='utf8'
            )
        except Exception as e:
            print('-----Exception-----', e)
        else:
            print('--------Success------')
            cursor = connection.cursor()
            cursor.execute(sql)
            rows = cursor.fetchall()
            for i in rows:
                return_json = {
                    'id': i[0],
                    'slack_name': i[1],
                    'slack_nickname': i[2],
                    'kakao_name': i[3],
                    'kakao_nickname': i[4],
                    'content': i[5]
                }
                userlist.append(return_json)
        return userlist
