package com.inteagle.test;

public class TemplatureRead {

	float templature = 0;
	byte[] byteArray;

	public TemplatureRead(byte[] data) {
		byteArray = data;
	}

	private int byteToInt(byte b) {
		return b & 0xff;
	}

	public float value() {
		byte sign = (byte) ((byteArray[0] & 0xf0) >> 4);
		byte hundreds = (byte) (byteArray[0] & 0x0f);
		byte tens = (byte) ((byteArray[1] & 0xf0) >> 4);
		byte uints = (byte) (byteArray[1] & 0x0f);
		byte dotUints = (byte) ((byteArray[2] & 0xf0) >> 4);
		byte dotTens = (byte) (byteArray[2] & 0x0f);
		byte dotHundreds = (byte) (byteArray[3] & 0x0f);

		templature = (float) ((1 - Math.pow(2, byteToInt(sign))) * ((byteToInt(hundreds) * 100.0)
				+ (byteToInt(tens) * 10.0) + (byteToInt(uints)) + (byteToInt(dotUints) * 0.1)
				+ (byteToInt(dotTens) * 0.01) + (byteToInt(dotHundreds) * 0.001)));

		return templature;
	}
}
