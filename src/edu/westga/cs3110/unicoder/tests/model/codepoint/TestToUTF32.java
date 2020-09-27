package edu.westga.cs3110.unicoder.tests.model.codepoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import edu.westga.cs3110.unicoder.model.Codepoint;

class TestToUTF32 {

	@ParameterizedTest
	@CsvSource({ "0000, 0000, At lower bound", "0001, 0001, One above lower bound", "10FFFE, 10FFFE, One below upper bound", "10FFFF, 10FFFF, At upper bound" })
	void testHexStringAtAllBounds(String hexString, String expected, String message) {
		Codepoint testingPoint = new Codepoint(hexString);
		String result = testingPoint.toUTF32();

		assertEquals(expected, result, message);
	}

}
