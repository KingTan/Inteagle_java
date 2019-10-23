package com.inteagle.modal.excel;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 
 * @ClassName: ExportFoundation
 * @Description: TODO(导出基坑数据实体类)
 * @author IVAn
 * @date 2019年9月9日上午11:12:36
 *
 */
public class ExportFoundation implements Serializable {

	/**
	 * @Fields field:field:{todo}(序列化)
	 */
	private static final long serialVersionUID = 1L;

	@Excel(name = "设备ID", width = 30, orderNum = "0", isImportField = "true_st")
	private String deviceId;

	@Excel(name = "深度", width = 30, orderNum = "1", isImportField = "true_st")
	private int deepLength;

	@Excel(name = "x轴位移量", width = 30, orderNum = "2", isImportField = "true_st")
	private int xRate;

	@Excel(name = "y轴位移量", width = 30, orderNum = "3", isImportField = "true_st")
	private int yRate;

	@Excel(name = "记录时间", width = 40, orderNum = "4", exportFormat = "yyyy-MM-dd", isImportField = "true_st")
	private Date recordDate;

	public String getDeviceId() {
		return deviceId;
	}

	public int getDeepLength() {
		return deepLength;
	}

	public int getxRate() {
		return xRate;
	}

	public int getyRate() {
		return yRate;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setDeepLength(int deepLength) {
		this.deepLength = deepLength;
	}

	public void setxRate(int xRate) {
		this.xRate = xRate;
	}

	public void setyRate(int yRate) {
		this.yRate = yRate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

}
