package com.inteagle.common.util;

import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.common.struct.CameraStruct;
import com.inteagle.common.struct.IdPositionStruct;
import com.sun.jna.*;

/**
 * 
 * @ClassName: ConcatHardWare
 * @Description: TODO(处理硬件数据)
 * @author IVAn
 * @date 2019年8月1日下午2:27:59
 *
 */
public class ConcatHardWare {

//	public interface CLibrary extends Library {
//		CLibrary INSTANCE = (CLibrary) Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), CLibrary.class);
//
//		void printf(String format, Object... args);
//	}
	@SuppressWarnings("deprecation")
	public interface Clibrary extends Library {
		String os = System.getProperty("os.name"); // 获取当前操作系统的类型
		int beginIndex = os != null && os.startsWith("Windows") ? 1 : 0;// windows操作系统为1 否则为0

		Clibrary INSTANTCE = (Clibrary) Native.synchronizedLibrary(
				(Clibrary) Native.loadLibrary(Clibrary.class.getResource("/src/main/resources/libestimate_position")
						.getPath().substring(beginIndex), Clibrary.class));
		
//		Clibrary INSTANTCE=(Clibrary) Native.loadLibrary("F://mqtt_files/libestimate_position.so", Clibrary.class);
		
		Boolean get_position(CameraStruct cameratruct, IdPositionStruct idPositionStruct);

	}

	public static void main(String[] args) {

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

		Boolean get_position = Clibrary.INSTANTCE.get_position(cameraStruct, idPositionStruct);

		System.out.println("get_position----------" + get_position);

	}

}
