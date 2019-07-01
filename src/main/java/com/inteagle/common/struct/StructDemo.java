package com.inteagle.common.struct;

import struct.*;

@StructClass
public class StructDemo {

	@StructField(order = 0)
	public int a;
	
	@StructField(order = 1)
	public int b;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

}
