package es.source.code.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 此部分参考网上源码
 * 原作者地址
 * https://www.jianshu.com/p/f940ebcae899
 */
public class MyAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public MyAuthenticator() {
	}

	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
