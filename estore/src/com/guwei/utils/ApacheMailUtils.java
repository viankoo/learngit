package com.guwei.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class ApacheMailUtils {
	public static boolean sendMail(String snedto, String subject, String msg) {
		try {
			Email email = new HtmlEmail();
			// 发送邮件服务器主机名
			email.setHostName("smtp.qq.com");
			email.setSmtpPort(465);
			// 邮箱账号和授权码
			email.setAuthenticator(new DefaultAuthenticator("858592926",
					"xgsiomgrrixpbeia"));
			email.setSSLOnConnect(true);
			email.setFrom("858592926@qq.com");
			email.setSubject(subject);
			email.setContent(msg, "text/html;charset=utf-8");
			email.addTo(snedto);
			email.send();
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		}
	}
}
