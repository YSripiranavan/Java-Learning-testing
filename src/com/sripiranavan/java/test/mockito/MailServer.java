package com.sripiranavan.java.test.mockito;

public class MailServer {
	private Validator emailValidator;

	public void send(Email email) {
		emailValidator.validate(email);
	}

	public Validator getValidator() {
		return this.emailValidator;
	}
}
