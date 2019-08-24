package com.inteagle.apis.struct.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.inteagle.apis.struct.entity.DeviceActionStruct;
import com.inteagle.common.base.dao.BaseMapper;

@Repository
public interface DeviceActionMapper extends BaseMapper<DeviceActionStruct> {

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
