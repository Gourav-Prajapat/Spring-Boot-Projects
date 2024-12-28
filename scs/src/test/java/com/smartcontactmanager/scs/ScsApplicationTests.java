package com.smartcontactmanager.scs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smartcontactmanager.services.EmailService;

@SpringBootTest
class ScsApplicationTests {

	@Test
	void contextLoads() {
	}

	/* 
	@Autowired
	private EmailService service;

	
	//mailstrap test case
	@Test
	void sendEmailTest(){
		service.sendEmail(
			"gprajapati644@gmail.com",
			"Testing",
			"this is scm project email service"
		);
	}
	
	*/ 

	@Autowired
	private EmailService emailservice;

	@Test
	void sendEmailTest(){
		emailservice.sendEmail(
			"gourav24507@gmail.com",
			"testing email",
			"this email is from SCM Project"
		);
	}

	@Test
	void sendEmailTest2(){
		String[] list = {"gourav24507@gmail.com","gouravp9111124507@gmail.com"};
		emailservice.sendEmail(list, "email testing", "this is a bulk email");
	}

	@Test
	void sendEmailWithHtml(){
		String htmlContent ="<h1 style='color:purple; border:1px solid'>Welcome to the Gourav SCM Project</h1>";
		emailservice.sendEmailWithHtml("gourav24507@gmail.com", "Email from Gourav", htmlContent);
	}

	/* 
	@Test
	void sendEmailWithFile(){
		emailservice.sendEmailWithFile("gourav24507@gmail.com",
		"testing email with Attachment", "this email is from SCM Project with an Attachment",
		new File("D:\\Gourav Programs\\java practice\\scs\\src\\main\\resources\\static\\images\\phone-book.png"));
	}

	@Test
	void sendEmailWithFileStream(){
		File file = new File("D:\\Gourav Programs\\java practice\\scs\\src\\main\\resources\\static\\images\\phone-book.png");
		try (InputStream is = new FileInputStream(file)) {
			try {
				emailservice.sendEmailWithFile("gourav24507@gmail.com",
				"Email with file", "this is email contains file which comes from input stream", is, file.getName());
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	*/
}
