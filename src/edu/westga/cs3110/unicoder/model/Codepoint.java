package edu.westga.cs3110.unicoder.model;

/**
 * Models a single Codepoint for a Hexadecimal string.
 * 
 * @author Owner
 *
 */
public class Codepoint {
	private static final String UTF_16_NOT_ALLOWED = "Hex string is within the range that cannot be translated to UTF-16. Please try again with a new code point.";
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

	/**
	 * Gets the string representation of the hexadecimal codepoint.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the string representation of the hexadecimal codepoint
	 */
	public String getHexString() {
		return this.hexString;
	}

	/**
	 * Computes the codepoint into its four byte UTF-32 value.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the UTF-32 value.
	 */
	public String toUTF32() {
		return this.hexString;
	}

	/**
	 * Computes the codepoint into its two or four byte UTF-16 value.
	 * 
	 * @precondition getHexString() cannot be between 0xd800 and 0xdffff
	 * @postcondition none
	 * @return the UTF-16 value
	 */
	public String toUTF16() {
		if (this.checkIfUTF16TwoBytesForbiddenZone()) {
			throw new IllegalArgumentException(Codepoint.UTF_16_NOT_ALLOWED);
		}
		String encodedString = "";

		if (this.checkIfUTF16TwoBytes()) {
			encodedString = this.encodeUTF16TwoBytes();
		} else {
			encodedString = this.encodeUTF16FourBytes();
		}

		return encodedString;
	}
/**
 * Computes the codepoint into its one, two, three, or four byte UTF-8 value.
 * 
 * @precondition none
 * @postcondition none
 * @return the UTF-8 value
 */
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

	private boolean checkIfHexStringIsOneByteUTF8() {
		return this.decodedHex >= 0 && this.decodedHex <= 0x007f;
	}

	private boolean checkIfHexStringIsTwoBytesUTF8() {
		return this.decodedHex >= 0x0080 && this.decodedHex <= 0x07ff;
	}

	private boolean checkIfHexStringIsThreeBytesUTF8() {
		return this.decodedHex >= 0x0800 && this.decodedHex <= 0xffff;
	}

	private boolean checkIfUTF16TwoBytes() {
		return (this.decodedHex >= 0x0000 && this.decodedHex <= 0xD7FF)
				|| (this.decodedHex >= 0xE000 && this.decodedHex <= 0xFFFF);
	}

	private boolean checkIfUTF16TwoBytesForbiddenZone() {
		return this.decodedHex >= 0xd800 && this.decodedHex <= 0xdfff;
	}

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
		middleSixBits = middleSixBits & 0b0000000000111111;
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

	private String encodeUTF16TwoBytes() {
		return this.hexString;
	}

	private String encodeUTF16FourBytes() {
		int workableHex = this.decodedHex - 0x10000;
		
		int firstHalfOfBits = workableHex >>> 10;
		int secondHalfOfBits = workableHex & 0b00000000001111111111;
		
		int highSurrogate = 0xD800 + firstHalfOfBits;
		int lowSurrogate = 0xDC00 + secondHalfOfBits;
		
		int fullFourByteEncoding = highSurrogate << 16 | lowSurrogate;
		
		String encodedString = String.format("%X", fullFourByteEncoding);
		
		return encodedString;
	}

}
