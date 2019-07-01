package com.inteagle.common.struct;

import java.io.Serializable;

import struct.*;

/**
 * 
 * @ClassName: CallBackInstruBase
 * @Description: TODO(接收数据原型类)
 * @author IVAn
 * @date 2019年7月1日上午10:13:30
 *
 */
@StructClass
public class CallBackInstruBase implements Serializable {

	/**
	 * @Fields field:field:{todo}(序列化ID)
	 */
	private static final long serialVersionUID = 1L;

	@StructField(order = 0)
	public String sof;

	@StructField(order = 1)
	public String len;

	@StructField(order = 2)
	public String cmd;

	@StructField(order = 3)
	public String data;

	@StructField(order = 4)
	public String crc;

	@StructField(order = 5)
	public String eof;

	public String getSof() {
		return sof;
	}

	public void setSof(String sof) {
		this.sof = sof;
	}

	public String getLen() {
		return len;
	}

	public void setLen(String len) {
		this.len = len;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCrc() {
		return crc;
	}

	public void setCrc(String crc) {
		this.crc = crc;
	}

	public String getEof() {
		return eof;
	}

	public void setEof(String eof) {
		this.eof = eof;
	}

}
