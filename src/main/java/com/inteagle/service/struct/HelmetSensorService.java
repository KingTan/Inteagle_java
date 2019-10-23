package com.inteagle.service.struct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inteagle.dao.struct.HelmetSensorMapper;
import com.inteagle.modal.struct.HelmetSensorDataStruct;
import com.inteagle.service.base.AbstractService;

@Service
public class HelmetSensorService extends AbstractService<HelmetSensorDataStruct, HelmetSensorMapper> {

	@Autowired
	private HelmetSensorMapper helmetSensorMapper;
	
	
	/**
	 * @description 删除所有电池数据 
	 * @author IVAn
	 * @date 2019年10月4日 上午10:33:47
	 * @return
	 */
	public int delAllSensorData(){
		return helmetSensorMapper.delAllSensorData();
	}
	
	/**
	 * @description 添加设备 电视情况数据
	 * @author IVAn
	 * @date 2019年8月8日 下午8:03:15
	 * @param helmetSensorDataStruct
	 * @return
	 */
	public int insertSensor(HelmetSensorDataStruct helmetSensorDataStruct) {
		return helmetSensorMapper.insertSensor(helmetSensorDataStruct);
	}

	/**
	 * @description 根据 设备ID 查询该设备的所有电池情况
	 * @author IVAn
	 * @date 2019年8月8日 下午8:04:12
	 * @param deviceId
	 * @return
	 */
	public List<HelmetSensorDataStruct> selectAllHelmetSensorDataStructListById(String deviceId) {
		return helmetSensorMapper.selectAllHelmetSensorDataStructListById(deviceId);
	}
}
