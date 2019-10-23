package com.inteagle.service.struct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inteagle.dao.struct.DeviceActionMapper;
import com.inteagle.modal.struct.DeviceActionStruct;
import com.inteagle.service.base.AbstractService;

@Service
public class DeviceActionService extends AbstractService<DeviceActionStruct, DeviceActionMapper> {

	@Autowired
	private DeviceActionMapper deviceActionMapper;
	
	/**
	 * @description 删除所有数据 
	 * @author IVAn
	 * @date 2019年10月4日 上午10:28:30
	 * @return
	 */
	public int delAllData() {
		return deviceActionMapper.delAllData();
	}
	
	/**
	 * @description 添加设备 设备行为数据
	 * @author IVAn
	 * @date 2019年8月8日 下午8:03:15
	 * @param helmetSensorDataStruct
	 * @return
	 */
	public int insert(DeviceActionStruct deviceActionStruct) {
		return deviceActionMapper.insert(deviceActionStruct);
	}

	/**
	 * @description 查看全部数据
	 * @author IVAn
	 * @date 2019年8月8日 下午8:03:15
	 * @param helmetSensorDataStruct
	 * @return
	 */
	public List<DeviceActionStruct> getDeviceActionList() {
		return deviceActionMapper.getDeviceActionList();
	}

}
