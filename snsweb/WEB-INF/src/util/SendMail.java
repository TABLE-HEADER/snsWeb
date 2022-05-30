package util;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {

	public void sendMail(String email, String title, String text) throws MailTransmissionFailureException {

		try {

			// **** 注意！ ****
			// 以下に送信元メールアドレス、アカウントパスワードを入力すること。
			String mail = "xxxxxxxx.gmail.com";
			String pass = "yyyyyyyy";
			// ****************

			Properties props = System.getProperties();

			// SMTPサーバのアドレスを指定（今回はGmailのSMTPサーバを利用）
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.debug", "true");
			//props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); //Could not convert socket to TLSが出た場合に追加

			Session session = Session.getInstance(
				props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						// この部分は随時変更すること。
						return new PasswordAuthentication(mail, pass);
					}
				}
			);

			MimeMessage mimeMessage = new MimeMessage(session);

			// 送信元メールアドレスと送信者名を指定
			// この部分は随時変更すること。
			mimeMessage.setFrom(new InternetAddress(mail, "神田英会話スクール", "iso-2022-jp"));

			// 送信先メールアドレスを指定
			mimeMessage.setRecipients(Message.RecipientType.TO, email);

			// メールのタイトルを指定
			mimeMessage.setSubject(title, "iso-2022-jp");

			// メールの内容を指定
			mimeMessage.setText(text, "iso-2022-jp");

			// メールの形式を指定
			mimeMessage.setHeader("Content-Type", "text/plain; charset=iso-2022-jp");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 送信します
			Transport.send(mimeMessage);

//			// 送信成功
//			System.out.println("送信に成功しました。");

		} catch (Exception e) {
			// e.printStackTrace();
			throw new MailTransmissionFailureException("");
		}
	}

}
