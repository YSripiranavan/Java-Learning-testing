package com.sripiranavan.java.test.mockito;

public class Validator {
	public void validate(Email email) {
		if (email.getAddressee() == null) {
			throw new IllegalArgumentException();
		}
	}
}
