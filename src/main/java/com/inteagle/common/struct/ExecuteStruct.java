package com.inteagle.common.struct;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.Arrays;

import struct.*;

public class ExecuteStruct {

	@StructClass
	public class AString {

		@StructField(order = 0)
		@ArrayLengthMarker(fieldName = "chars")
		public int length;

		@StructField(order = 1)
		public char[] chars;

		public AString(String content) {
			this.length = content.length();
			this.chars = content.toCharArray();
		}
	}

	public void TestAString() throws StructException {

		// 构造 str 对象时，执行相应构造方法后，长度字段为4。
		AString str = new AString("");

		String textStr = "0102";

		try {
			String encodeStr = new String(ByteHexUtil.hexStr2Bytes(textStr), "utf8");
			System.out.println("encodeStr------" + encodeStr);

			byte[] b = ByteHexUtil.hexStr2Bytes(encodeStr);

			byte[] b2 = new byte[3];

			for (int i = 0; i < b.length; i++) {
				System.out.printf("b[%d]: %d\n", i, b[i]);
				b2[i] = b[i];
			}

			b2[2] = -3;

			for (int i = 0; i < b2.length; i++) {
				System.out.printf("b2[%d]: %d\n", i, b2[i]);
			}

			byte[] b3 = { 0, 0, 0, 1, 0, 0, 0, 2, -3 };
			 

			StructDemo demo = new StructDemo();
			JavaStruct.unpack(demo, b3, ByteOrder.BIG_ENDIAN);

			System.out.println("A----" + demo.getA());
			System.out.println("B-----" + demo.getB());

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//		{
//			int(8) a=1; 000000001;0x01
//			int(8) b=2; 0x02
//		}
//		
//		{
//			int a;
//			int b;
//		}

	}

	public static void main(String args[]) {
		ExecuteStruct t = new ExecuteStruct();
		try {
			t.TestAString();
		} catch (StructException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
