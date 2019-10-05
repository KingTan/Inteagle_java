package com.inteagle.apis.struct.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.common.base.dao.BaseMapper;

@Repository
public interface IdInfoStructMapper extends BaseMapper<IdInfoStruct> {
	
	
	/**
	 * @description 删除所有安全帽位置数据 
	 * @author IVAn
	 * @date 2019年10月4日 上午10:44:32
	 * @return
	 */
	int delAllIdInfoData();
	
	/**
	 * @description 查询对应安全帽ID 指定时间段内的数据
	 * @author IVAn
	 * @date 2019年9月20日 上午10:49:54
	 * @param id
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<IdInfoStruct> getListByIdAndTimePeriod(@Param("id") String id, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);

	/**
	 * @description 查询对应安全帽ID的数据集合
	 * @author IVAn
	 * @date 2019年9月20日 上午10:44:14
	 * @param id
	 * @return
	 */
	List<IdInfoStruct> getListById(String id);

	/**
	 * @description 查询所有数据集合
	 * @author IVAn
	 * @date 2019年9月20日 上午10:44:14
	 * @param id
	 * @return
	 */
	List<IdInfoStruct> getAllIdInfoStructList();

	/**
	 * @description 新增位置数据
	 * @author IVAn
	 * @date 2019年9月20日 上午10:44:14
	 * @param id
	 * @return
	 */
	int insert(IdInfoStruct idInfoStruct);

}
