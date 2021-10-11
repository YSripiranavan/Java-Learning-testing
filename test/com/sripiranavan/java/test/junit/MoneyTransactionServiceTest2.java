package com.sripiranavan.java.test.junit;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.sripiranavan.java.test.exception.NotEnoughMoneyException;

@DisplayName("Money Transaction Service Test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MoneyTransactionServiceTest2 {
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

	@Test
	@DisplayName("Verify money transaction from one account to another")
	void shouldTransferMoneyFromOneAccountToAnother() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);

		testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
	}

	@Test
	void should_Throw_Exception_If_Account_From_Is_Null() {
		Account account1 = null;
		Account account2 = new Account(RANDOM_MONEY_AMOUNT);

		var exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));

		assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
	}

	@Test
	void should_Throw_Execption_If_Account_To_Is_Null() {
		Account account1 = new Account(RANDOM_MONEY_AMOUNT);
		Account account2 = null;

		var exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));

		assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
	}

	@Test
	void should_Throw_Exception_Not_Enough_Money_Exception_When_Transfer_Money() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);

		assertThrows(NotEnoughMoneyException.class,
				() -> testInstance.transferMoney(account1, account2, MORE_THAN_RANDOM_MONEY_AMOUNT));
	}

	@Test
	void Should_Throw_Exception_When_Transfer_Negative_Amount() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);

		var exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(account1, account2, NEGATIVE_MONEY_AMOUNT));

		assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
	}

	@Test
	void Should_Throw_Exception_When_Transfer_Zero_Money_Amount() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);

		var exception = assertThrows(IllegalArgumentException.class,
				() -> testInstance.transferMoney(account1, account2, ZERO_MONEY_AMOUNT));

		assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
	}

	@Test
	void Grouped_Assertion_Examples() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);

		testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);

		assertAll("money transaction", () -> assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount()),
				() -> assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount()));
	}

	@Test
	void Dependent_Assertion_Example() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		assertAll("Money Transactions", () -> {
//			When
			boolean isTransactionSuccesed = testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
			assertTrue(isTransactionSuccesed);
//			Executed only if the previous assertion is valid
			assertAll("Money ammount is changed on the accounts",
					() -> assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount()),
					() -> assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount()));
		});
	}

	@Test
	void Test_With_Timeout_Example() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);

		assertTimeout(Duration.ofSeconds(1), () -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
	}

	@Test
	@Timeout(2)
	void Timeout_Not_Exceeded_With_Result() {
		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);
		boolean actualResult = assertTimeout(Duration.ofSeconds(1),
				() -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
		assertTrue(actualResult);
	}

	@Test
	void Test_Only_On_Piranavan_Workstation() {
		assumeTrue("truee".equals(System.getenv("IS_SRIPIRANAVAN_LAPTOP")),
				() -> "Aborting this test, because it is not running on " + "laptop of Sripiranavan");

		var account1 = new Account(RANDOM_MONEY_AMOUNT);
		var account2 = new Account(ZERO_MONEY_AMOUNT);

		boolean actualResult = assertTimeout(Duration.ofSeconds(1),
				() -> testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
		assertTrue(actualResult);
	}

	@ParameterizedTest
	@ValueSource(ints = { 100, 200, 50, -10 })
	void Parameterized_Test_Example(int moneyAmount) {
		assumeTrue(moneyAmount > 0, () -> "Money amount can't be negative");
		var account1 = new Account(moneyAmount);
		var account2 = new Account(ZERO_MONEY_AMOUNT);

		assertTrue(testInstance.transferMoney(account1, account2, moneyAmount));
	}

	@ParameterizedTest
	@NullSource
	@EmptySource
	@NullAndEmptySource
	void Null_And_Empty_Sources(String text) {
		assertTrue(text == null || text.trim().isEmpty());
	}

	@ParameterizedTest
	@MethodSource("sourceMethod")
	void Test_Method_Source(String text) {
		assertNotNull(text);
	}

	static Stream<String> sourceMethod() {
		return Stream.of("Jhon", "Walter", "Piranavan");
	}

	@ParameterizedTest
	@CsvSource({ "apple,1", "banana,2", "'lemon,lime',0xF1" })
	void Test_With_Csv_Source(String fruit, int rank) {
		assertNotNull(fruit);
		assertNotEquals(0, rank);
	}
}
