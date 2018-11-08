package esd.scos.utils;

public class RegexUtil {
    //验证登录名和登录密码是否符合规则
    public static boolean checkUsernamePassword(String username,String passwd){
        String regex = "[A-Za-z0-9]+";
        if(username.matches(regex) && passwd.matches(regex)){
            return true;
        }
        return false;
    }
}
