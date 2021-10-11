package com.sripiranavan.java.test.hamcrest;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class HamcrestDemo {
	@Test
	void firstHamcrestTest() {
		String actualString = "some value";
		String expectedString = "some value";
		assertThat(actualString, equalTo(expectedString));
	}

	@Test
	void secondHamcrestTest() {
		var list = new ArrayList<String>(Arrays.asList("a", "b", "c"));

		assertThat(list, hasItem(anyOf(equalTo("a"), equalTo("b"), equalTo("c"))));

		assertTrue(list.contains("a") || list.contains("b") || list.contains("c"));
	}
}
