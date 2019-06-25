package com.inteagle.common.base.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.inteagle.common.base.dao.BaseMapper;
import com.inteagle.common.base.entity.BaseEntity;

/**
 * 
 * @ClassName: AbstractService
 * @Description: TODO(service抽象类)
 * @author IVAn
 * @date 2019年6月25日
 *
 * @param <T>
 * @param <D>
 */
public abstract class AbstractService<T extends BaseEntity, D extends BaseMapper<T>> implements BaseService<T> {

	/**
	 * 自动注入主表mapper
	 */
	@Autowired(required = true)
	protected D mapper;

	/**
	 * 根据entityId删除记录
	 */
	public int deleteByPrimaryKey(String entityId) {
		return mapper.deleteByPrimaryKey(entityId);
	}

	/**
	 * 新增entity
	 */
	public int insert(T entity) {
		return mapper.insert(entity);
	}

	/**
	 * 动态新增entity
	 */
	public int insertSelective(T entity) {
		return mapper.insertSelective(entity);
	}

	/**
	 * 根据entityId查询
	 */
	public T selectByPrimaryKey(String entityId) {
		return mapper.selectByPrimaryKey(entityId);
	}

	/**
	 * 动态更新entity
	 */
	public int updateByPrimaryKeySelective(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	/**
	 * 更新entity
	 */
	public int updateByPrimaryKey(T entity) {
		return mapper.updateByPrimaryKey(entity);
	}

	/**
	 * 根据主键判断是否存在
	 */
	public boolean existsByPrimaryKey(String entityId) {
		return mapper.selectByPrimaryKey(entityId) != null;
	}

}
