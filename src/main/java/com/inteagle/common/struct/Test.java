package com.inteagle.common.struct;

import struct.*;

public class Test {

	@StructClass
	public class Foo {

		@StructField(order = 0)
		public byte b;

		@StructField(order = 1)
		public int i;

	}

	public void TestFoo() {
		try {
			// Pack the class as a byte buffer
			Foo f = new Foo();
			f.b = (byte) 1;
			f.i = 2;

			// 大端排序
			byte[] b = JavaStruct.pack(f);

			// 小端排序
			// byte[] b = JavaStruct.pack(f, ByteOrder.LITTLE_ENDIAN);

			for (int i = 0; i < b.length; i++) {
				System.out.printf("b[%d]: %d\n", i, b[i]);
			}

			// Unpack it into an object
			Foo f2 = new Foo();
			JavaStruct.unpack(f2, b);
			System.out.println("f2.b: " + f2.b);
			System.out.println("f2.i: " + f2.i);

		} catch (StructException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Test t = new Test();
		t.TestFoo();
	}

}
