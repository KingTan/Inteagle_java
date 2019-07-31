package com.inteagle.common.struct;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.apis.struct.service.IdInfoStructService;
import com.inteagle.common.websocket.server.WebSocketServer;

import lombok.extern.slf4j.Slf4j;
import struct.JavaStruct;
import struct.StructException;

/**
 * @author IVAn
 * @ClassName: AnalysisUtil
 * @Description: TODO(按照协议 解析工具类)
 * @date 2019年7月2日下午4:12:41
 */
@Slf4j
@Component
public class AnalysisUtil {

    @Autowired
    private IdInfoStructService idInfoStructService;

    private static AnalysisUtil analysisUtil;

    // 项目启动时 注入到Spring容器
    @PostConstruct
    public void init() {
        analysisUtil = this;
        analysisUtil.idInfoStructService = this.idInfoStructService;
    }

    // 命令类型
    public static final Map<Integer, String> CMD_TYPE = new HashMap<Integer, String>();

    // 公共命令
    public static final Map<Integer, String> CMD = new HashMap<Integer, String>();

    static {
        CMD_TYPE.put(0, "CMD_TYPE_SET");
        CMD_TYPE.put(1, "CMD_TYPE_GET");
        CMD_TYPE.put(2, "CMD_TYPE_PUT");
        CMD_TYPE.put(3, "CMD_TYPE_ACK");

        CMD.put(1, "CMD_COMMON_NONE");
        CMD.put(2, "CMD_COMMON_HEARTBEAT");
        CMD.put(3, "CMD_COMMON_GET_DEVICE_INFO");
        CMD.put(4, "CMD_COMMON_REBOOT");
        CMD.put(5, "CMD_COMMON_SLEEP");
        CMD.put(6, "CMD_COMMON_TIME_SYNC");
        CMD.put(7, "CMD_COMMON_LAST");

        CMD.put(50, "CMD_COMMON_RESERVED");
        CMD.put(51, "CMD_BR_GET_ROUTERS"); // 获取路由表，有子网的所有节点ID和IPV6地址
        CMD.put(52, "CMD_BR_SET_NEW_NODE");
        CMD.put(53, "CMD_BR_SET_DIO_PERIOD");

        CMD.put(100, "CMD_BR_RESERVED");
        CMD.put(101, "CMD_CAMERA_GET_VISIBLE");
        CMD.put(102, "CMD_CAMERA_GET_VISIBLE_ALL");
        CMD.put(103, "CMD_CAMERA_GET_FULL_IMG");
        CMD.put(104, "CMD_CAMERA_GET_IMU");// 惯性测量单元
        CMD.put(105, "CMD_CAMERA_RECONFIG");
        CMD.put(106, "CMD_CAMERA_SET_MODE");

        CMD.put(120, "CMD_CAMERA_RESERVED");
        CMD.put(121, "CMD_CAMERA_C_GET_AUX_NUM");// 获取辅助摄像机数量

        // 辅助摄像头命令 151-199
        CMD.put(150, "CMD_CAMERA_C_RESERVED");
        CMD.put(151, "CMD_CAMERA_A_GET_IMG");

        // 安全帽命令 201-249
        CMD.put(150, "CMD_CAMERA_A_RESERVED");
        CMD.put(150, "CMD_HELMET_GET_BLINK_FRE");
        CMD.put(150, "CMD_HELMET_GET_BLINK_PERIOD");
        CMD.put(150, "CMD_HELMET_RESERVED");
        
        // 安全帽信息
        CMD.put(307,"CMD_CAMERA_ID_INFO");
        
        // 基坑设备命令 251-299
        CMD.put(251, "CMD_FRAME_SET_THRESHOLD");
        CMD.put(300, "CMD_FRAME_RESERVED");
        
        
    }

    // 解析 paylaod 数据
    // byte[] playload
    @SuppressWarnings("unlikely-arg-type")
	public static void validate(String hexStr) throws UnsupportedEncodingException {
        System.out.println("hexStr-----" + hexStr);
        try {
            // SOF头
            String sof_hex = hexStr.substring(0, 4);
//			System.out.println("sof_hex----" + sof_hex);
            // EOF尾
            String eof_hex = hexStr.substring(hexStr.length() - 4);
//			System.out.println("eof_hex----" + eof_hex);

            // 验证头尾
            if (sof_hex.equals("a5a5") && eof_hex.equals("5a5a")) {

                // | SOF | len | cmd | data | crc | EOF |
                // Data:|0xa5 0xa5 | 0x00 0x00 | 0x20 0x02 | 0x02 0x03 | 0x7d | 0x5a 0x5a |
                // len: data
                // crc: len + cmd + data
                // CRC校验类型：CRC8/MAXIM X8+X5+X4+1

                log.info("数据完整,SOF EOF验证成功");
                // 数据长度 16进制
                String len_hex = hexStr.substring(4, 8);
                // System.out.println("长度len_hex------" + len_hex);

                // 数据长度 10进制
                // long len_num = Long.parseLong(len_hex, 16);
                // System.out.println("长度len_num------" + len_num);

                //CMD 16进制
                String cmd_hex = hexStr.substring(8, 12);
                System.out.println("cmd_hex-----" + cmd_hex);

                // CMD 10进制
                long cmd_ten = Long.parseLong(cmd_hex, 16) & 0x0fff;
                System.out.println("cmd_ten-----" + cmd_ten);

                // data+crc+eof
                String data_crc_eof = hexStr.substring(12);

                // CRC 16进制
                String crc_hex = data_crc_eof.substring((data_crc_eof.length() - 6), (data_crc_eof.length() - 4));

                System.out.println("crc_hex-------" + crc_hex);

                // CRC 10进制
                long crc_ten = Long.parseLong(crc_hex, 16);

                System.out.println("crc_ten-------" + crc_ten);

                // CRC 检验数据
                String crc_examine = hexStr.substring(4, (hexStr.length() - 6));

                // CRC 校验数据的字节数组
                byte[] b = ByteHexUtil.hexStr2Bytes(crc_examine);

                // CRC 校验
                byte crc = CRC8.calcCrc8(b);

                System.out.println("crc--校验值-----" + crc);

                // crc_ten == crc
                // 判断CRC 10进制 和校验值
                if (crc_hex.equals((Integer.toHexString(0x00ff & crc)).toString())) {
                    log.info("crc校验成功....");

                    // 数据体
                    String data_str = data_crc_eof.substring(0, (data_crc_eof.length() - 6));

                    System.out.println("data_str-----" + data_str);

                    // JavaStruct解析数据、保存到数据库
                    getDataJson(data_str, cmd_ten);

                } else {
                    log.error("CRC 校验失败.....");
                }

            } else {
                log.error("sof eof验证失败...");
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.toString());

            // TODO Auto-generated catch block
            log.info("解析数据失败...");
        }

    }

    /**
     * @param data
     * @description 解析数据体
     * @author IVAn
     * @date 2019年7月3日 上午10:52:49
     */
    public static void getDataJson(String data, long cmd) {

        int cmd_num = (int) cmd;
        System.out.println("cmd-num--------" + cmd_num);

        String cmd_value = CMD.get(cmd_num);

        switch (cmd_value) {
            case "CMD_COMMON_TIME_SYNC":
                try {
                    // 16进制的data转成字节数组
                    byte[] b = ByteHexUtil.hexStr2Bytes(data);
                    TimeSyncData timeSyncData = new TimeSyncData();
                    // 解析成javaStruct
                    JavaStruct.unpack(timeSyncData, b, ByteOrder.BIG_ENDIAN);
                    System.out.println("getT_h-------" + timeSyncData.getT_h());
                    System.out.println("getT_l-------" + timeSyncData.getT_l());

                } catch (StructException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "CMD_CAMERA_ID_INFO":
            	// 方法2 JavaStruct解析
				try {
					IdInfoStruct struct = new IdInfoStruct();
					byte[] b = ByteHexUtil.hexStr2Bytes(data);

					// 解析成javaStruct
					JavaStruct.unpack(struct, b, ByteOrder.BIG_ENDIAN);

					System.out.println("id----" + struct.id);
					System.out.println("x-----" + struct.x);
					System.out.println("y-----" + struct.y);
					System.out.println("t-----" + struct.t);
					System.out.println("camera_id-----" + struct.camera_id);
					try {
						// 保存到数据库
//						  int result = analysisUtil.idInfoStructService.insert(struct);
//						  if (result > 0) { log.info("数据保存成功..."); }

						// 发送socket消息
						WebSocketServer.sendInfo(JSONObject.toJSONString(struct), "ivan");

					} catch (Exception e) {
						log.error(e.toString());
					}

				} catch (StructException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
            case "2":
                break;
            default:
                break;
        }

//		System.out.println("data--length------" + data.length());
//		if (data.length() > 0) {
//			if (data.length() == 44) {
//			} else if (data.length() == 40) {
//
//				// 方法2 JavaStruct解析
//				try {
//					IdInfoStruct struct = new IdInfoStruct();
//					byte[] b = ByteHexUtil.hexStr2Bytes(data);
//
//					// 解析成javaStruct
//					JavaStruct.unpack(struct, b, ByteOrder.BIG_ENDIAN);
//
//					System.out.println("id----" + struct.id);
//					System.out.println("x-----" + struct.x);
//					System.out.println("y-----" + struct.y);
//					System.out.println("t-----" + struct.t);
//					System.out.println("camera_id-----" + struct.camera_id);
//					try {
//						// 保存到数据库
////						  int result = analysisUtil.idInfoStructService.insert(struct);
////						  if (result > 0) { log.info("数据保存成功..."); }
//
//						// 发送socket消息
//						WebSocketServer.sendInfo(JSONObject.toJSONString(struct), "ivan");
//
//					} catch (Exception e) {
//						log.error(e.toString());
//					}
//
//				} catch (StructException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				/*
//				 * //方法1 分段截取 按照协议转进制解析 // 16进制 转 10进制 int id =
//				 * ByteHexUtil.hexStringToAlgorism(data.substring(0, 4));
//				 * System.out.println("id-----" + id); // hex 转成 float Long x_long =
//				 * parseLong(data.substring(4, 12), 16); float x_rate =
//				 * Float.intBitsToFloat(x_long.intValue()); System.out.println("x_rate------" +
//				 * x_rate); Long y_long = parseLong(data.substring(12, 20), 16); float y_rate =
//				 * Float.intBitsToFloat(y_long.intValue()); System.out.println("y_rate-----" +
//				 * y_rate); int t_rate = ByteHexUtil.hexStringToAlgorism(data.substring(20,
//				 * 36)); System.out.println("t_rate-----" + t_rate);
//				 */
//			} else if (data.length() == 16) {
//				try {
//					TimeSyncData timeSyncData = new TimeSyncData();
//
//					byte[] b = ByteHexUtil.hexStr2Bytes(data);
//
//					// 解析成javaStruct
//					JavaStruct.unpack(timeSyncData, b, ByteOrder.BIG_ENDIAN);
//
//					System.out.println("getT_h-------" + timeSyncData.getT_h());
//					System.out.println("getT_l-------" + timeSyncData.getT_l());
//
//				} catch (StructException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//
//		}

    }

    public static void main(String[] args) {
        // a5a5000820060000000100000001065a5a
        // a5a500122133009542db000042f700000000000000000040115a5a

        try {

            String sof = "a5a5";
            sof = ByteHexUtil.changeType(sof);

            String len = "0008";
            len = ByteHexUtil.changeType(len);

            String cmd = "2006";
            cmd = ByteHexUtil.changeType(cmd);

            //初始时间结构体对象
            TimeSyncData timeSyncData = new TimeSyncData();
//            timeSyncData.setT_h(1564543);
//            timeSyncData.setT_l(919221);

            Long curretnTimeStamp = Calendar.getInstance().getTimeInMillis();

            System.out.println("curretnTimeStamp------" + curretnTimeStamp);

            int int_timestamp=curretnTimeStamp.intValue();
            System.out.println("int_timestamp------" + int_timestamp);

//            String t_h = "";
//            String t_l = "";

//            byte[] long2Bytes = long2Bytes(curretnTimeStamp);
//            System.out.printf("long转行成bytes: ");
//
//            for (int ix = 0; ix < long2Bytes.length; ++ix) {
//                System.out.print(long2Bytes[ix] + " ");
//                if (ix < long2Bytes.length / 2) {
//                    t_h = t_h + long2Bytes[ix];
//                } else {
//                    t_l = t_l + long2Bytes[ix];
//                }
//            }
//
//            System.out.println("t_h-------" + t_h);
//            System.out.println("t_l-------" + t_l);
//
//            System.out.println("t_h----int---" + Integer.parseInt(t_h));
//            System.out.println("t_l----int---" + Integer.parseInt(t_l));

            timeSyncData.setT_h(11864);
            timeSyncData.setT_l(70138);

            //转成字节数组
            byte[] time_byte = JavaStruct.pack(timeSyncData);
            String data = ByteHexUtil.bytes2HexStr(time_byte);
            //System.out.println("data------"+data);

            //按照协议 截取到crc的16进制值
            String crc = len + cmd + data;
            //System.out.println("crc-------" + crc);

            //16进制转成字节数组
            byte[] crc_byte = ByteHexUtil.hex2Byte(crc);

            //传入字节数组 求出crc的校验值字节
            byte crc_after = CRC8.calcCrc8(crc_byte);
            //System.out.println("crc_after-校验值-----" + crc_after);

            // 将校验值字节放入数组中 转成16进制数据
            byte[] crc_after_array = {crc_after};
            crc = ByteHexUtil.byte2HexStr(crc_after_array);
            //System.out.println("crc_last------" + crc);

            String eof = "5a5a";
            eof = ByteHexUtil.changeType(eof);

            String msg = sof + len + cmd + data + crc + eof;
            System.out.println("msg-----" + msg);

            //"a5a5 0008 2006 000003e8000007d0 11 5a5a"
            validate(msg);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * Long 转成 byte数组
     *
     * @param
     * @return
     */
    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

    /**
     * byte数组转成long
     *
     * @param byteNum
     * @return
     */
    public static long bytes2Long(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    /**
     * @param s
     * @param radix
     * @return
     * @throws NumberFormatException
     * @description 16进制转成10进制
     * @author IVAn
     * @date 2019年7月9日 下午2:45:12
     */
    public static long parseLong(String s, int radix) throws NumberFormatException {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix + " less than Character.MIN_RADIX");
        }
        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix + " greater than Character.MAX_RADIX");
        }

        long result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        long limit = -Long.MAX_VALUE;
        long multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+')
                    throw NumberFormatException.forInputString(s);

                if (len == 1) // Cannot have lone "+" or "-"
                    throw NumberFormatException.forInputString(s);
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s.charAt(i++), radix);
                if (digit < 0) {
                    throw NumberFormatException.forInputString(s);
                }
                if (result < multmin) {
                    throw NumberFormatException.forInputString(s);
                }
                result *= radix;
                if (result < limit + digit) {
                    throw NumberFormatException.forInputString(s);
                }
                result -= digit;
            }
        } else {
            throw NumberFormatException.forInputString(s);
        }
        return negative ? result : -result;
    }

}
