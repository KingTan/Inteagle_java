package com.inteagle.common.struct;

import java.nio.ByteOrder;
import java.util.Arrays;

import struct.*;

public class Demo2 {

	@StructClass
	public class AString {

		@StructField(order = 0)
		public int length;

		@StructField(order = 1)
		public char[] chars;

		public AString(String content) {
			this.length = content.length();
			this.chars = content.toCharArray();
		}
	}

	public void TestAString() {
		// 构造 str 对象时，执行相应构造方法后，长度字段为4。
		AString str = new AString("0102");
		try {
			byte[] b = JavaStruct.pack(str, ByteOrder.BIG_ENDIAN);
			for (int i = 0; i < b.length; i++) {
				System.out.printf("b[%d]: %d\n", i, b[i]);
			}
			
//			byte[] b= {0,0,0,4,98,17,114,49,78,45,86,-3};
			
			AString str2 = new AString("");
			JavaStruct.unpack(str2, b, ByteOrder.BIG_ENDIAN);
			System.out.println("str2: " + str2.length);
			System.out.println("str2: " + Arrays.toString(str2.chars));
		} catch (StructException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Demo2 t = new Demo2();
		t.TestAString();
	}

}
