package com.inteagle.test;

import java.util.Date;

public class TemplatureReadTest {
	public static void main(String[] args) {
		byte[] data = { 0x11, 0x23, 0x67, 0x00 };
		TemplatureRead tmp = new TemplatureRead(data);
		System.out.println(tmp.value());
	}

}
