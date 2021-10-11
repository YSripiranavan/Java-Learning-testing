package com.sripiranavan.java.test.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.DisabledForJreRange;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

@Tag("production")
/*
 * There are different test method order implementation. They are: - DisplayName
 * - MethodName - OrderAnnotation - Random
 * 
 */
@TestMethodOrder(OrderAnnotation.class)
public class JUnitApiAdvancedExamples {

	@Test
	@Tag("production")
	void someTestForProdEnv() {
	}

	@Test
	@Order(1)
	void order1() {
//		perform assertions against null values
	}

	@Test
	@Order(2)
	void order2() {
//		perform assertions against empty values
	}

	@Test
	@Order(3)
	void order3() {
//		perform assertions against valid values
	}
	// ===================== Test Conditions Examples

	/*
	 * There are such groups of conditions: - Operating System conditions - Java
	 * Runtime Environments conditions - System Property Conditions - Environment
	 * Variable Conditions - Custom conditions
	 * 
	 */

	@Test
	@EnabledOnOs(OS.MAC)
	void onlyOnMacOs() {

	}

	@Test
	@EnabledOnOs({ OS.MAC, OS.LINUX })
	void onLinuxOrMac() {

	}

	@Test
	@DisabledOnOs(OS.WINDOWS)
	void notOnWindows() {

	}

	@Test
	@EnabledOnJre(JRE.JAVA_8)
	void onlyOnJava8() {

	}

	@Test
	@EnabledOnJre({ JRE.JAVA_8, JRE.JAVA_9 })
	void onJava8Or9() {

	}

	@Test
	@EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_11)
	void fromJava8to11() {

	}

	@Test
	@EnabledForJreRange(min = JRE.JAVA_11)
	void formJava11toCurrentJavaFeatureNumber() {

	}

	@Test
	@EnabledForJreRange(max = JRE.JAVA_15)
	void fromJava8to15() {

	}

	@Test
	@DisabledOnJre(JRE.JAVA_12)
	void notOnJava12() {

	}

	@Test
	@DisabledForJreRange(min = JRE.JAVA_9, max = JRE.JAVA_12)
	void notFromJava9to12() {

	}

	@Test
	@DisabledForJreRange(min = JRE.JAVA_13)
	void notFromJava13toCurrentJavaFeatureNumber() {

	}

	@Test
	@DisabledForJreRange(max = JRE.JAVA_13)
	void notFromJava8to13() {

	}

	@Test
	@EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
	void onlyOn64BitArchitectures() {

	}

	@Test
	@DisabledIfSystemProperty(named = "ci-server", matches = "true")
	void notOnCiServer() {

	}

	@Test
	@EnabledIfEnvironmentVariable(named = "ENV", matches = "staging-server")
	void onlyOnStagingServer() {

	}

	@Test
	@DisabledIfEnvironmentVariable(named = "ENV", matches = ".*development.*")
	void notOnDevelopmentWorkStation() {

	}

	@Test
	@EnabledIf("customCondition")
	void enabled() {

	}

	@Test
	@DisabledIf("customCondition")
	void disabled() {

	}

	boolean customCondition() {
		return true;
	}

//	@RepeatedTest(10)
	@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetition}")
	void repetedTest() {

	}

	@Test
	void tempDirExample(@TempDir Path tempDir) throws IOException {
		Path path = tempDir.resolve("test");
		Files.write(path, "some text".getBytes());

		assertEquals("some text", Files.readAllLines(path).stream().collect(Collectors.joining()));
	}
}
