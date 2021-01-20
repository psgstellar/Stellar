
def psgusersql():

    sql = """
SELECT user_info.id,user_info.slack_name, user_info.slack_nickname, user_info.kakao_name,
user_info.kakao_nickname, user_info.content, user_git.git_name
 FROM user_info, user_git WHERE user_info.id = user_git.id 
          """

    return sql

def psgusergitsql():

    sql = """
            select id,
                   git_name
            from user_git
    """