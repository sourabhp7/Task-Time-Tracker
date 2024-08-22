package task.time.tracker.service;

import java.io.File;

public interface EmailService {
	
	void sendEmail(String to,String subject,String message);
	
	void sendEmail(String[] to,String subject,String message);
	
	void sendEmailWithHtml(String to,String subject,String htmlContent);
	
	void sendEmailWithFile(String to,String subject,String message,File file);

}
