package es.source.code.model;

public class LoginParam {
    //用户名
    private String userName;
    //密码
    private String passWord;

    public LoginParam(String userName, String passWord) {
        super();
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
