package edu.westga.cs3110.unicoder.model;

/**
 * Models a single Codepoint for a Hexadecimal string.
 * 
 * @author Owner
 *
 */
public class Codepoint {
	private String hexString;
	private int decodedHex;

	/**
	 * One-parameter constructor.
	 * 
	 * @precondition !hexString.isBlank() && hexString != null
	 * @postcondition getHexString() == hexString
	 * @param hexString the hexadecimal string
	 */
	public Codepoint(String hexString) {
		if (hexString == null) {
			throw new IllegalArgumentException("hexString cannot be null.");
		}
		if (hexString.isBlank()) {
			throw new IllegalArgumentException("hexString cannot be blank.");
		}

		this.hexString = hexString;
		this.decodedHex = Integer.parseUnsignedInt(this.hexString, 16);
	}

	public String getHexString() {
		return this.hexString;
	}

	public String toUTF32() {
		return "";
	}

	public String toUTF16() {
		return "";
	}

	public String toUTF8() {
		String hexStringUTF8 = "";
		if (this.checkIfHexStringIsOneByteUTF8()) {
			hexStringUTF8 = this.encodeUTF8OneByte();
		} else if (this.checkIfHexStringIsTwoBytesUTF8()) {
			hexStringUTF8 = this.encodeUTF8TwoBytes();
		} else if (this.checkIfHexStringIsThreeBytesUTF8()) {
			hexStringUTF8 = this.encodeUTF8ThreeBytes();
		} else {
			hexStringUTF8 = this.encodeUTF8FourBytes();
		}

		return hexStringUTF8;
	}

//	private int decodeHexString() {
//		String decodeableHexString = "0x" + this.hexString;
//		return Integer.decode(decodeableHexString);
//		
//	}

	private boolean checkIfHexStringIsOneByteUTF8() {
		return this.decodedHex >= 0 && this.decodedHex <= 0x007f;
	}

	private boolean checkIfHexStringIsTwoBytesUTF8() {
		return this.decodedHex >= 0x0080 && this.decodedHex <= 0x07ff;
	}

	private boolean checkIfHexStringIsThreeBytesUTF8() {
		return this.decodedHex >= 0x0800 && this.decodedHex <= 0xffff;
	}

//	private boolean checkIfHexStringIsFourBytesUTF8() {
//		return this.decodedHex >= 0x10000 && this.decodedHex <= 0x10ffff;
//	}

	private String encodeUTF8OneByte() {
		int oneByteEncoding = this.decodedHex & 0b000000000000111111111;
		String encodedString = String.format("%X", oneByteEncoding);
		
		return encodedString;
	}

	private String encodeUTF8TwoBytes() {
		int upperFiveBits = this.decodedHex >> 6;
		int lowerSixBits = this.decodedHex & 0b00000111111;
		
		int upperTwoByteEncoding = 0b11000000 | upperFiveBits;
		int lowerTwoByteEncoding = 0b10000000 | lowerSixBits;
		
		int fullTwoByteEncoding = (upperTwoByteEncoding << 8) | lowerTwoByteEncoding;
		String encodedString = String.format("%X", fullTwoByteEncoding);
		
		return encodedString;
	}

	private String encodeUTF8ThreeBytes() {
		int upperFourBits = this.decodedHex >> 12;
		int middleSixBits = this.decodedHex >> 6;
		middleSixBits = this.decodedHex & 0b0000000000111111;
		int lowerSixBits = this.decodedHex & 0b0000000000111111;
		
		int upperThreeByteEncoding = 0b11100000 | upperFourBits;
		int middleThreeByteEncoding = 0b10000000 | middleSixBits;
		int lowerThreeBitsEncoding = 0b10000000 | lowerSixBits;
		
		int fullThreeByteEncoding = (upperThreeByteEncoding << 8) | middleThreeByteEncoding;
		fullThreeByteEncoding = (fullThreeByteEncoding << 8) | lowerThreeBitsEncoding;
		
		String encodedString = String.format("%X", fullThreeByteEncoding);
		
		return encodedString;
	}

	private String encodeUTF8FourBytes() {
		int firstQuarterOfBits = this.decodedHex >> 18;
		int secondQuarterOfBits = this.decodedHex >> 12;
		secondQuarterOfBits = secondQuarterOfBits & 0b000000000000000111111;
		int thirdQuarterOfBits = this.decodedHex >> 6;
		thirdQuarterOfBits = thirdQuarterOfBits & 0b000000000000000111111;
		int fourthQuarterOfBits = this.decodedHex & 0b000000000000000111111;
		
		int firstQuarterFourByteEncoding = 0b11110000 | firstQuarterOfBits;
		int secondQuarterFourByteEncoding = 0b10000000 | secondQuarterOfBits;
		int thirdQuarterFourByteEncoding = 0b10000000 | thirdQuarterOfBits;
		int fourthQuarterFourByteEncoding = 0b10000000 | fourthQuarterOfBits;
		
		long fullFourByteEncoding = (firstQuarterFourByteEncoding << 8) | secondQuarterFourByteEncoding;
		fullFourByteEncoding = (fullFourByteEncoding << 8) | thirdQuarterFourByteEncoding;
		fullFourByteEncoding = (fullFourByteEncoding << 8) | fourthQuarterFourByteEncoding;
		
		String encodedString = String.format("%X", fullFourByteEncoding);
		
		return encodedString;
	}
}
