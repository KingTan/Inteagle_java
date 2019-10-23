package com.inteagle.utils.struct;

import struct.StructClass;
import struct.StructField;

@StructClass
public class StructDemo {
	
	@StructField(order = 0)
	public float a;
	
	@StructField(order = 1)
	public float b;

//	public int getA() {
//		return a;
//	}
//
//	public void setA(int a) {
//		this.a = a;
//	}
//
//	public int getB() {
//		return b;
//	}
//
//	public void setB(int b) {
//		this.b = b;
//	}

}
