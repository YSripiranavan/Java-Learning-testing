package com.sripiranavan.java.test.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculatorTest {

	@Test
	void test() {
		var calculator = new Calculator();
		Integer result = calculator.add(3, 4);
		assertEquals(7, result);
	}

}
