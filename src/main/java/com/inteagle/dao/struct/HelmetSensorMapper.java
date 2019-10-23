package com.inteagle.dao.struct;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.inteagle.dao.base.BaseMapper;
import com.inteagle.modal.struct.HelmetSensorDataStruct;

@Repository
public interface HelmetSensorMapper extends BaseMapper<HelmetSensorDataStruct>{
	
	
	/**
	 * @description 删除所有电池数据 
	 * @author IVAn
	 * @date 2019年10月4日 上午10:31:55
	 * @return
	 */
	int delAllSensorData();
	
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
	List<HelmetSensorDataStruct> selectAllHelmetSensorDataStructListById(@Param("deviceId")String deviceId);
	
	
	
}
