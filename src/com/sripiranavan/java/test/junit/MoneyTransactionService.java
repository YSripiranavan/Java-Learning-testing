package com.sripiranavan.java.test.junit;

import com.sripiranavan.java.test.exception.NotEnoughMoneyException;

public class MoneyTransactionService {
	public boolean transferMoney(Account accountFrom, Account accountTo, double moneyAmountToSend) {
		if (accountFrom == null || accountTo == null) {
			throw new IllegalArgumentException("Account's shoudn't be null");
		}
		if (moneyAmountToSend <= 0) {
			throw new IllegalArgumentException("Money amount shoud be greater than 0");
		}
		if (accountFrom.getMoneyAmount() < moneyAmountToSend) {
			throw new NotEnoughMoneyException();
		}
		accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - moneyAmountToSend);
		accountTo.setMoneyAmount(accountTo.getMoneyAmount() + moneyAmountToSend);
		return true;
	}
}
