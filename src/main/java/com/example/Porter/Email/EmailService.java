package com.example.Porter.Email;

public interface EmailService {

	boolean sendEmail(String to, String subject,String body);
	
	boolean sendEmailWithAttachment(String to, String subject,String body,String attachment);

    boolean emailVerify (String email,  String otp, String cusName);
    
    boolean forgotMail(String email,  String otp, String cusName);




}
