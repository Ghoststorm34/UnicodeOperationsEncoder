package edu.westga.cs3110.unicoder.tests.model.codepoint;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs3110.unicoder.model.Codepoint;

class TestConstructor {

	@Test
	void testNullHexString() {
		assertThrows(IllegalArgumentException.class, () -> new Codepoint(null));
	}

	@Test
	void testEmptyHexString() {
		assertThrows(IllegalArgumentException.class, () -> new Codepoint(""));
	}
	
	@Test
	void testValidConstructor() {
		Codepoint validData = new Codepoint("FFFF");
		
		assertEquals("FFFF", validData.getHexString());
	}
}
