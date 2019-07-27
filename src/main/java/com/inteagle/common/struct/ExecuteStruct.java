package com.inteagle.common.struct;

import java.nio.ByteOrder;

import com.inteagle.apis.struct.entity.IdInfoStruct;

import struct.*;

/**
 * 
 * @ClassName: ExecuteStruct
 * @Description: TODO(验证javaStruct工具类)
 * @author IVAn
 * @date 2019年7月5日上午11:43:25
 *
 */
public class ExecuteStruct {

	public void TestAString() throws StructException {

		try {
			// 0064420000004204000000000000000001930000
//			00ff43494CCC41D59999000000000000000f
//			1564219947548
			String hexStr = "1564219947548";

			String str = "{";

			byte[] b = ByteHexUtil.hexStr2Bytes(hexStr);
			for (int i = 0; i < b.length; i++) {
				System.out.printf("b[%d]: %d\n", i, b[i]);
				str += b[i] + ",";
			}

			str += "}";

			System.out.println("str------" + str);

//			byte[] b2 = { 0, -1, 67, 73, 76, -52, 65, -43, -103, -103, 0, 0, 0, 0, 0, 0, 0, 15 };

//			IdInfoStruct demo1 = new IdInfoStruct();
//			demo1.id = 255;
//			demo1.x = 201.29999f;
//			demo1.y = 26.699999f;
//			demo1.t = 15;
//
//			byte[] b_demo = JavaStruct.pack(demo1);
//			
//			String str_d = "{";
//
//			for (int i = 0; i < b_demo.length; i++) {
//				System.out.printf("b_demo[%d]: %d\n", i, b_demo[i]);
//				str_d += b_demo[i] + ",";
//			}
//
//			str_d += "}";
//
//			System.out.println("str_d------" + str_d);

			// javaStruct接收转换
//			IdInfoStruct demo = new IdInfoStruct();
//			JavaStruct.unpack(demo, b, ByteOrder.BIG_ENDIAN);
//			System.out.println("id----" + demo.id);
//			System.out.println("x-----" + demo.x);
//			System.out.println("y-----" + demo.y);
//			System.out.println("t-----" + demo.t);
//			System.out.println("camera_id-----" + demo.camera_id);

//			StructDemo sto = new StructDemo();
//			sto.a = 1.2f;
//			sto.b = 2.1f;
//
//			byte[] f = JavaStruct.pack(sto);
//			for (int i = 0; i < f.length; i++) {
//				System.out.printf("f[%d]: %d\n", i, f[i]);
//			}

//			byte[] b3 = { 0, 0, 0, 1, 0, 0, 0, 2 };

//			byte[] b4 = { -1, -3, -1, -6, -3, -6, -3, -1, -1, -1, -1, -1, -2, -1, -2, -2, -1, -38 };

//			StructDemo demo = new StructDemo();
//			JavaStruct.unpack(demo, f, ByteOrder.BIG_ENDIAN);
//
//			System.out.println("A----" + demo.a);
//			System.out.println("B-----" + demo.b);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
