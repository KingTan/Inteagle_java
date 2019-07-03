package com.inteagle.common.struct;


import struct.StructClass;
import struct.StructField;

@StructClass
public class IdInfoStruct {

	@StructField(order = 0)
	public short id;
	
	@StructField(order = 1)
	public float x;
	
	@StructField(order = 2)
	public float y;
	
	@StructField(order = 3)
	public long  t;

}
