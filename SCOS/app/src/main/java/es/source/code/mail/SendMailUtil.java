package es.source.code.mail;

import android.os.Message;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.logging.Handler;

public class SendMailUtil {

    //qq
    private static final String HOST = "smtp.qq.com";
    private static final String PORT = "587";
    private static final String FROM_ADD = "1055056138@qq.com";
    private static final String FROM_PSW = "cjuyepihkydnbbaa";

//    //163
//    private static final String HOST = "smtp.163.com";
//    private static final String PORT = "465"; //或者465  994
//    private static final String FROM_ADD = "teprinciple@163.com";
//    private static final String FROM_PSW = "teprinciple163";
////    private static final String TO_ADD = "2584770373@qq.com";


    public static void send(final File file,String toAdd){
        final MailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo,file);
            }
        }).start();
    }

    public static void send(String toAdd){
        final MailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    @NonNull
    private static MailInfo creatMail(String toAdd) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(HOST);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_ADD); // 你的邮箱地址
        mailInfo.setPassword(FROM_PSW);// 您的邮箱密码
        mailInfo.setFromAddress(FROM_ADD); // 发送的邮箱
        mailInfo.setToAddress(toAdd); // 发到哪个邮箱去
        mailInfo.setSubject("求助"); // 邮件主题
        mailInfo.setContent("软件不会用"); // 邮件文本
        return mailInfo;
    }

}
