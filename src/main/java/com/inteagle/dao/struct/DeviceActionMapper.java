package com.inteagle.dao.struct;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inteagle.dao.base.BaseMapper;
import com.inteagle.modal.struct.DeviceActionStruct;

@Repository
public interface DeviceActionMapper extends BaseMapper<DeviceActionStruct> {

	/**
	 * @description 删除所有数据
	 * @author IVAn
	 * @date 2019年10月4日 上午10:22:20
	 * @return
	 */
	int delAllData();

	/**
	 * @description 添加设备 设备行为数据
	 * @author IVAn
	 * @date 2019年8月8日 下午8:03:15
	 * @param helmetSensorDataStruct
	 * @return
	 */
	int insert(DeviceActionStruct deviceActionStruct);

	/**
	 * @description 查看全部数据
	 * @author IVAn
	 * @date 2019年8月8日 下午8:03:15
	 * @param helmetSensorDataStruct
	 * @return
	 */
	List<DeviceActionStruct> getDeviceActionList();

}
