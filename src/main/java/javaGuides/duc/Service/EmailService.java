package javaGuides.duc.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	public String sendEmail(String token, String email) throws Exception {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("vuvanduc0501@gmail.com");
			msg.setTo(email);
			msg.setSubject("Forgot Password from Server");
			msg.setText("Token forgot password:" + token);
			mailSender.send(msg);
			return "send mail successfully";
		} catch (Exception e) {
			throw new Exception("can't send email");
		}

	}

}
