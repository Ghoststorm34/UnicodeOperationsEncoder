package edu.westga.cs3110.unicoder.tests.model.codepoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import edu.westga.cs3110.unicoder.model.Codepoint;

class TestToUTF8 {

	@ParameterizedTest
	@CsvSource({ "0000, 00, At lower bound", "0001, 01, One above lower bound", "007E, 7E, One below upper bound", "007F, 7F, At upper bound" })
	void testHexStringAtOneByte(String hexString, String expected, String message) {
		Codepoint testingPoint = new Codepoint(hexString);
		String result = testingPoint.toUTF8();

		assertEquals(expected, result, message);
	}

	@ParameterizedTest
	@CsvSource({ "0080, C280, At lower bound", "0081, C281, One above lower bound", "07FE, DFBE, One below upper bound", "07FF, DFBF, At upper bound" })
	void testHexStringAtTwoBytes(String hexString, String expected, String message) {
		Codepoint testingPoint = new Codepoint(hexString);
		String result = testingPoint.toUTF8();

		assertEquals(expected, result, message);
	}
	
	@ParameterizedTest
	@CsvSource({ "0800, E0A080, At lower bound", "0801, E0A081, One above lower bound", "FFFE, EFBFBE, One below upper bound", "FFFF, EFBFBF, At upper bound" })
	void testHexStringAtThreeBytes(String hexString, String expected, String message) {
		Codepoint testingPoint = new Codepoint(hexString);
		String result = testingPoint.toUTF8();

		assertEquals(expected, result, message);
	}
	
	@ParameterizedTest
	@CsvSource({ "10000, F0908080, At lower bound", "10001, F0908081, One above lower bound", "10FFFE, F48FBFBE, One below upper bound", "10FFFF, F48FBFBF, At upper bound" })
	void testHexStringAtFourBytes(String hexString, String expected, String message) {
		Codepoint testingPoint = new Codepoint(hexString);
		String result = testingPoint.toUTF8();

		assertEquals(expected, result, message);
	}
}

