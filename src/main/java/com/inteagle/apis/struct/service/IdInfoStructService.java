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

	// 保存数据
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
