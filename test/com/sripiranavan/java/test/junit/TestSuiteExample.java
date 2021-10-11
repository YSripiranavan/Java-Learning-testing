package com.sripiranavan.java.test.junit;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
//By default, it will only include test classes whose names either begin with Test or end with Test or Tests.
@SelectPackages("com.sripiranavan.com.java.test.junit")
//@SelectPackages({"com.sripiranavan.com.java.test.junit","com.sripiranavan.sripiranavan.java.test.exception"})

//@ExcludePackages("com.sripiranavan.com.java.test.junit")
//@IncludePackages("com.sripiranavan.com.java.test.exception")

@IncludeTags("production")
//@ExcludeTags("development")

@SelectClasses(JUnitApiAdvancedExamples.class)
//@SelectClasses({CalculatorTest.class,MoneyTransactionServiceTest.class})
public class TestSuiteExample {

}
