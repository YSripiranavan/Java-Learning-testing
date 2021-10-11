package com.sripiranavan.java.test.junit;

import org.junit.jupiter.api.Test;

class TestCodeCoverageDemoTest {

	private TestCodeCoverageDemo testInstance = new TestCodeCoverageDemo();
	@Test
	void testStringNull() {
		testInstance.testCodeCoverage(null);
	}
	
	@Test
	void testString() {
		testInstance.testCodeCoverage("test");
	}

}
