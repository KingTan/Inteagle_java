package com.inteagle.apis.struct.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.common.base.dao.BaseMapper;

@Repository
public interface IdInfoStructMapper extends BaseMapper<IdInfoStruct> {
	
	//新增数据
	int insert(IdInfoStruct idInfoStruct);

	// 查询所有数据集合
	List<IdInfoStruct> getAllIdInfoStructList();

}
