package esd.scos.utils;

public class RegexUtil {
    //��֤��¼���͵�¼�����Ƿ���Ϲ���
    public static boolean checkUsernamePassword(String username,String passwd){
        String regex = "[A-Za-z0-9]+";
        if(username.matches(regex) && passwd.matches(regex)){
            return true;
        }
        return false;
    }
}
