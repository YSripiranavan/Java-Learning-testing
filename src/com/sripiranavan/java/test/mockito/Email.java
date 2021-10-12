package com.sripiranavan.java.test.mockito;

public class Email {
	private String msgContent;
	private String addressee;

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgContent() {
		return msgContent;
	}

}
