from psguser.dao.psguser_dao import PsgUserList
from psguser.resources.psguser_sql import psgusersql


class PsgUserListService:

    def psguserlistservice(self):
        sql = psgusersql()
        psguserdao = PsgUserList()
        return_json = psguserdao.psguserdao(sql)
        return return_json
