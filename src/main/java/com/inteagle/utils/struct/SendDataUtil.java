package com.inteagle.utils.struct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.inteagle.modal.struct.IdInfoStruct;

import struct.JavaStruct;
import struct.StructException;

/**
 * 
 * @ClassName: SendDataUtil
 * @Description: TODO(发送下行数据的工具类)
 * @author IVAn
 * @date 2019年8月1日上午11:30:35
 *
 */
public class SendDataUtil {

	/**
	 * @description 发送电机启动的数据
	 * @author IVAn
	 * @date 2019年9月9日 上午10:28:02
	 * @return
	 */
	public static String sendElecticStart() {
		String sof = "a5a5";
		sof = ByteHexUtil.changeType(sof);
		// CMD 16进制
		// Integer.toHexString(0712)
		String cmd = "02be";
		cmd = ByteHexUtil.changeType(cmd);
		String msg = "";
		try {
			MotorStartStruct motorStartStruct = new MotorStartStruct();
			short fre = 1;
			motorStartStruct.setFre(fre);
			short duty = 1;
			motorStartStruct.setDuty(duty);
			short steps = 3;
			motorStartStruct.setSteps(steps);
			short step_hold = 3;
			motorStartStruct.setSteps_hold(step_hold);
			short hold_time = 3;
			motorStartStruct.setHold_time(hold_time);
			short dir = 1;
			motorStartStruct.setDir(dir);

			// 转成字节数组
			byte[] start_byte = JavaStruct.pack(motorStartStruct);
			String data = ByteHexUtil.bytes2HexStr(start_byte);

			// 10进制转成16进制
			String len_hex = ByteHexUtil.intToHex(data.length());
			System.out.println("len_hex----" + len_hex);

			String len = ByteHexUtil.addZeroForNum(len_hex, 4);
			System.out.println("len----" + len);

			len = ByteHexUtil.changeType(len);
			// 按照协议 截取到crc的16进制值
			String crc = len + cmd + data;
			// System.out.println("crc-------" + crc);

			// 16进制转成字节数组
			byte[] crc_byte = ByteHexUtil.hex2Byte(crc);

			// 传入字节数组 求出crc的校验值字节
			byte crc_after = CRC8.calcCrc8(crc_byte);

			// 将校验值字节放入数组中 转成16进制数据
			byte[] crc_after_array = { crc_after };
			crc = ByteHexUtil.byte2HexStr(crc_after_array);

			String eof = "5a5a";
			eof = ByteHexUtil.changeType(eof);

			msg = sof + len + cmd + data + crc + eof;
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @description 发送人员定位数据
	 * @author IVAn
	 * @date 2019年9月11日 上午10:22:26
	 * @return
	 */
	public static String sendHelmetPositionData() {

		try {
			String sof = "a5a5";
			String len = "0008";
			String cmd = "0133";

			IdInfoStruct inInfo = new IdInfoStruct();
			inInfo.setCamera_id(Short.parseShort("1"));
			inInfo.setId(Short.parseShort("1"));
			inInfo.setT(5L);
			inInfo.setX(4.6f);
			inInfo.setY(3.2f);

			// 转成字节数组
			byte[] time_byte;
			time_byte = JavaStruct.pack(inInfo);

			String data = ByteHexUtil.bytes2HexStr(time_byte);
			System.out.println("data------" + data);

			// 按照协议 截取到crc的16进制值
			String crc = len + cmd + data;
			// System.out.println("crc-------" + crc);

			// 16进制转成字节数组
			byte[] crc_byte = ByteHexUtil.hex2Byte(crc);

			// 传入字节数组 求出crc的校验值字节
			byte crc_after = CRC8.calcCrc8(crc_byte);
			// System.out.println("crc_after-校验值-----" + crc_after);

			// 将校验值字节放入数组中 转成16进制数据
			byte[] crc_after_array = { crc_after };
			crc = ByteHexUtil.byte2HexStr(crc_after_array);
			// System.out.println("crc_last------" + crc);

			String eof = "5a5a";
			eof = ByteHexUtil.changeType(eof);

			String msg = sof + len + cmd + data + crc + eof;
			System.out.println("msg-----" + msg);
			return msg;
		} catch (StructException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return "";

	}

	/**
	 * @description 发送时间同步的数据
	 * @author IVAn
	 * @date 2019年9月7日 下午6:39:09
	 * @return
	 */
	public static String sendTimeSyncData() {

		try {
			String sof = "a5a5";
			sof = ByteHexUtil.changeType(sof);

			String len = "0008";
			len = ByteHexUtil.changeType(len);

			String cmd = "2006";
			cmd = ByteHexUtil.changeType(cmd);

			// 初始时间结构体对象
			TimeSyncData timeSyncData = new TimeSyncData();

			long curretnTimeStamp = Calendar.getInstance().getTimeInMillis();

			System.out.println("curretnTimeStamp-------" + curretnTimeStamp);

			int t_l = (int) curretnTimeStamp;
			int t_h = (int) (curretnTimeStamp >> 32);

			System.out.println("t_h-------" + t_h);
			System.out.println("t_l-------" + t_l);

			Long change_val = getLongFromInt(t_l, t_h);
			System.out.println("change_val-------" + change_val);

			timeSyncData.setT_h(t_h);
			timeSyncData.setT_l(t_l);

			// 转成字节数组
			byte[] time_byte;
			time_byte = JavaStruct.pack(timeSyncData);

			String data = ByteHexUtil.bytes2HexStr(time_byte);

			// 按照协议 截取到crc的16进制值
			String crc = len + cmd + data;

			// 16进制转成字节数组
			byte[] crc_byte = ByteHexUtil.hex2Byte(crc);

			// 传入字节数组 求出crc的校验值字节
			byte crc_after = CRC8.calcCrc8(crc_byte);

			// 将校验值字节放入数组中 转成16进制数据
			byte[] crc_after_array = { crc_after };
			crc = ByteHexUtil.byte2HexStr(crc_after_array);

			String eof = "5a5a";
			eof = ByteHexUtil.changeType(eof);

			String msg = sof + len + cmd + data + crc + eof;
			System.out.println("msg-----" + msg);
			return msg;
		} catch (StructException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return "";
	}

	// long转为两个int
	public static List<Integer> changLong(long num) {
		int intNum1 = (int) num;
		int intNum2 = (int) (num >> 32);
		List<Integer> list = new ArrayList<Integer>();
		list.add(intNum1);
		list.add(intNum2);
		System.out.println("intNum1-----" + intNum1);
		System.out.println("intNum2-----" + intNum2);
		return list;
	}

	// 两个int合并成long
	public static long getLongFromInt(int num1, int num2) {
		// long l1 = (long)(((long)num2&0xffffffff)<<32);
		// long l2 = (long)num1&0x00000000ffffffffL;
		// 简写
		long l1 = (num2 & 0x00000000ffffffffL) << 32;
		long l2 = num1 & 0x00000000ffffffffL;

		long num = l1 | l2;
		return num;
	}

}
