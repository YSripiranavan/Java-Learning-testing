package com.sripiranavan.java.test.junit;

public class Account {
	private double moneyAmount;

	public Account() {
	}

	public Account(double moneyAmount) {
		super();
		this.moneyAmount = moneyAmount;
	}

	public double getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

}
