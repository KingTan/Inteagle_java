package com.inteagle.apis.struct.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inteagle.apis.struct.dao.IdInfoStructMapper;
import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.common.base.service.AbstractService;

@Service
public class IdInfoStructService extends AbstractService<IdInfoStruct, IdInfoStructMapper> {
	@Autowired
	private IdInfoStructMapper idInfoStructMapper;

	/**
	 * @description 删除所有安全帽位置数据
	 * @author IVAn
	 * @date 2019年10月4日 上午10:46:11
	 * @return
	 */
	public int delAllIdInfoData() {
		return idInfoStructMapper.delAllIdInfoData();
	}

	/**
	 * @description 查询对应安全帽指定时间段之内的数据
	 * @author IVAn
	 * @date 2019年9月20日 上午10:55:39
	 * @param id
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<IdInfoStruct> getListByIdAndTimePeriod(String id, String beginTime, String endTime) {

		return idInfoStructMapper.getListByIdAndTimePeriod(id, beginTime, endTime);
	}

	/**
	 * @description 查询对应安全帽ID的数据集合
	 * @author IVAn
	 * @date 2019年9月20日 上午10:47:22
	 * @param helmetId
	 * @return
	 */
	public List<IdInfoStruct> getListById(String helmetId) {
		return idInfoStructMapper.getListById(helmetId);
	}

	/**
	 * @description 保存位置数据
	 * @author IVAn
	 * @date 2019年9月11日 上午11:45:08
	 * @return
	 */
	public int insert(IdInfoStruct idInfoStruct) {
		return idInfoStructMapper.insert(idInfoStruct);
	}

	/**
	 * @description 查询数据列表
	 * @author IVAn
	 * @date 2019年9月11日 上午11:45:08
	 * @return
	 */
	public List<IdInfoStruct> getIdInfoStructList() {
		return idInfoStructMapper.getAllIdInfoStructList();
	}

}
