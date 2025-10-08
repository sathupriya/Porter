package com.example.Porter.Email;

import java.io.File;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.Porter.Exception.ResourceNotFoundException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	@Override
	public boolean sendEmail(String to, String subject, String body) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom("sathiyapriyac85@gmail.com");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean sendEmailWithAttachment(String to, String subject, String body, String attachment) {

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom("sathiyapriyac85@gmail.com");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			FileSystemResource file = new FileSystemResource(new File(attachment));
			helper.addAttachment(file.getFilename(), file);
			mailSender.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean emailVerify(String email, String otp, String cusName) {
		try {
			ClassPathResource resource = new ClassPathResource("emailTemplates/emailVerifyMail.html");
			String htmlContent = new String(resource.getInputStream().readAllBytes());
			
			htmlContent = htmlContent.replace("{customerName}", cusName);
			htmlContent = htmlContent.replace("{otp}", otp);
			return sendEmail(email, "Email Verification OTP", htmlContent);
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());
		}

	}

	@Override
	public boolean forgotMail(String email, String otp, String cusName) {
		try {
			ClassPathResource resource = new ClassPathResource("emailTemplates/forgotPasswordMail.html");
			String htmlContent = new String(resource.getInputStream().readAllBytes());
			htmlContent = htmlContent.replace("{customerName}", cusName);
			htmlContent = htmlContent.replace("{otp}", otp);
			return sendEmail(email, "Email Verification OTP", htmlContent);
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());
		}

	}

}
