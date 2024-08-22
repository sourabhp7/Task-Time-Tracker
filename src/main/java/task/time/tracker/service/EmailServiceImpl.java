package task.time.tracker.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailServiceImpl implements EmailService {
	

	@Autowired
	private JavaMailSender MailSender;
	
	@Override
	public void sendEmail(String to, String subject, String message) {
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		simpleMailMessage.setFrom("sourabh");
		MailSender.send(simpleMailMessage);
		System.out.println("Mail has been sent");
		
	}

	@Override
	public void sendEmail(String[] to, String subject, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEmailWithHtml(String to, String subject, String htmlContent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendEmailWithFile(String to, String subject, String message, File file) {
		// TODO Auto-generated method stub
		
	}

}
