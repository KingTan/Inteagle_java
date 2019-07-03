package com.inteagle.apis.struct.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inteagle.apis.struct.dao.IdInfoStructMapper;
import com.inteagle.apis.struct.entity.IdInfoStruct;
import com.inteagle.common.base.service.AbstractService;

@Service
public class IdInfoStructService extends AbstractService<IdInfoStruct, IdInfoStructMapper> {
	@Autowired
	private IdInfoStructMapper idInfoStructMapper;

	// 保存数据
	public int insert(IdInfoStruct idInfoStruct) {
		return idInfoStructMapper.insert(idInfoStruct);
	}

}
