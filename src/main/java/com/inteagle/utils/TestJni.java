package com.inteagle.utils;

import com.inteagle.modal.struct.IdInfoStruct;
import com.inteagle.utils.struct.CameraStruct;
import com.inteagle.utils.struct.IdPositionStruct;

public class TestJni {

	static {
		System.load("/src/main/resources/libestimate_position");
	}

	private native Boolean get_position(CameraStruct cameratruct, IdPositionStruct idPositionStruct);

	public static void main(String[] args) {
		
		System.out.println("begin-------------------");
		
		IdInfoStruct idInfoStruct = new IdInfoStruct();
		idInfoStruct.setX(6.6f);
		idInfoStruct.setY(7.7f);
		idInfoStruct.setT(8);
		idInfoStruct.setCamera_id(Short.parseShort("1"));

		CameraStruct cameraStruct = new CameraStruct();
		cameraStruct.setRotation(1.2f);
		cameraStruct.setTranslation(2.4f);
		cameraStruct.setDistortion(3.6f);
		cameraStruct.setK(4.8f);
		cameraStruct.setIdInfoStruct(idInfoStruct);

		IdPositionStruct idPositionStruct = new IdPositionStruct();
		idPositionStruct.setId(Short.parseShort("1"));
		idPositionStruct.setPosition(5.5f);

		TestJni hj = new TestJni();
		Boolean get_position = hj.get_position(cameraStruct, idPositionStruct);

		System.out.println("get_position----------" + get_position);

	}

}
