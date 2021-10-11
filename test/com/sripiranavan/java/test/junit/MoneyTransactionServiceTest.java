package com.sripiranavan.java.test.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sripiranavan.java.test.exception.NotEnoughMoneyException;

class MoneyTransactionServiceTest {
	private static final String MONEY_AMOUNT_EXCEPTION_MSG = "Money amount shoud be greater than 0";
	private static final String ACCOUNT_EXCEPTION_MSG = "Account's shoudn't be null";
	private static final double RANDOM_MONEY_AMOUNT = 100.0;
	private static final double ZERO_MONEY_AMOUNT = 0.0;
	private static final double MORE_THAN_RANDOM_MONEY_AMOUNT = 200.0;
	private static final double NEGATIVE_MONEY_AMOUNT = -1;

	private MoneyTransactionService testInstance;

	@BeforeEach
	void setUp() {
		testInstance = new MoneyTransactionService();
	}

	@BeforeAll
	static void beforeAll() {
		// this method will be executed before all tests
	}

	@AfterEach
	void tearDown() {
		// this method will be executed after each test method
	}

	@AfterAll
	static void afterAll() {

	}

	@Test
	void shouldTransferMoneyFromOneAccountToAnother() {
//		Given
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
//		When
		testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
//		Then
		assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount());
		assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount());

	}

	@Test
	void shouldThrowExceptionIfAccountFromIsNull() {
//		Given
		Account account1 = null;
		Account account2 = new Account(RANDOM_MONEY_AMOUNT);
//		When
		var exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
//		Then
		assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
	}

	@Test
	void shouldThrowExceptionIfAccountToIsNull() {
//		Given
		Account account1 = new Account(RANDOM_MONEY_AMOUNT);
		Account account2 = null;
//		When
		var exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
//		Then
		assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
	}

	@Test
	void shouldThrowExceptionNotEnoughMoneyExceptionWhenTransferMoney() {
//		Given
		Account account1 = new Account(RANDOM_MONEY_AMOUNT);
		Account account2 = new Account(ZERO_MONEY_AMOUNT);
//		When & Then
		assertThrows(NotEnoughMoneyException.class,
				() -> testInstance.transferMoney(account1, account2, MORE_THAN_RANDOM_MONEY_AMOUNT));
	}

	@Test
	void shouldThrowExceptionWhenTransferNegativeAmount() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(RANDOM_MONEY_AMOUNT);

		var exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(account1, account2, NEGATIVE_MONEY_AMOUNT));

		assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenTransferZeroAmount() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(RANDOM_MONEY_AMOUNT);

		var exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(account1, account2, ZERO_MONEY_AMOUNT));

		assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
	}

}
