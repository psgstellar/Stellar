from psguser.dao.psguser_dao import PsgUserList
from psguser.resources.psguser_sql import psgusersql, psgusergitsql


class PsgUserListService:

    def psguserlistservice(self):
        sqluser = psgusersql()
        psguserdao = PsgUserList()
        return_json = psguserdao.psguserlistdao(sqluser)
        return return_json

    def psgusergitlistservice(self):
        sqlgit = psgusergitsql()
        psguserdao = PsgUserList()
        return_git_json = psguserdao.usergitdao(sqlgit)
        return return_git_json

