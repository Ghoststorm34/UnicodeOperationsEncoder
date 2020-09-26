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

	private boolean checkIfHexStringIsFourBytesUTF8() {
		return this.decodedHex >= 0x10000 && this.decodedHex <= 0x10ffff;
	}

	private String encodeUTF8OneByte() {
		return "";
	}

	private String encodeUTF8TwoBytes() {
		return "";
	}

	private String encodeUTF8ThreeBytes() {
		return "";
	}

	private String encodeUTF8FourBytes() {
		return "";
	}
}
