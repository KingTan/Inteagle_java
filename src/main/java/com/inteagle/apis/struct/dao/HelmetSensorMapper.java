package com.inteagle.apis.struct.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inteagle.apis.struct.entity.HelmetSensorDataStruct;
import com.inteagle.common.base.dao.BaseMapper;

@Repository
public interface HelmetSensorMapper extends BaseMapper<HelmetSensorDataStruct>{
	
	/**
	 * @description 添加设备 电视情况数据
	 * @author IVAn
	 * @date 2019年8月8日 下午8:03:15
	 * @param helmetSensorDataStruct
	 * @return
	 */
	int insertSensor(HelmetSensorDataStruct helmetSensorDataStruct);
	
	
	/**
	 * @description 根据 设备ID 查询该设备的所有电池情况
	 * @author IVAn
	 * @date 2019年8月8日 下午8:04:12
	 * @param deviceId
	 * @return
	 */
	List<HelmetSensorDataStruct> selectAllHelmetSensorDataStructListById(String deviceId);
	
	
	
}
