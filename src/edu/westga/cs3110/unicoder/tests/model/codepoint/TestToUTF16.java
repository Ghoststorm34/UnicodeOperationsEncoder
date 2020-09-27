package edu.westga.cs3110.unicoder.tests.model.codepoint;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import edu.westga.cs3110.unicoder.model.Codepoint;

class TestToUTF16 {
	@ParameterizedTest
	@CsvSource({ "D800", "D801", "DFFE", "DFFF"})
	void testHexStringAtForbiddenZone(String hexString) {
		Codepoint testingPoint = new Codepoint(hexString);

		assertThrows(IllegalArgumentException.class, () -> testingPoint.toUTF16());
	}

	@ParameterizedTest
	@CsvSource({ "0000, 0000, At first lower bound", "0001, 0001, One above first lower bound",
			"D7FE, D7FE, One below first upper bound", "D7FF, D7FF, At first upper bound",
			"E000, E000, At second lower bound", "E001, E001, One above second lower bound",
			"FFFE, FFFE, One below second upper bound", "FFFF, FFFF, At second upper bound" })
	void testHexStringAtTwoBytesValidData(String hexString, String expected, String message) {
		Codepoint testingPoint = new Codepoint(hexString);
		String result = testingPoint.toUTF16();

		assertEquals(expected, result, message);
	}

	@ParameterizedTest
	@CsvSource({ "10000, D800DC00, At lower bound", "10001, D800DC01, One above lower bound",
			"10FFFE, DBFFDFFE, One below upper bound", "10FFFF, DBFFDFFF, At upper bound"})
	void testHexStringAtFourBytesValidData(String hexString, String expected, String message) {
		Codepoint testingPoint = new Codepoint(hexString);
		String result = testingPoint.toUTF16();

		assertEquals(expected, result, message);
	}
}
