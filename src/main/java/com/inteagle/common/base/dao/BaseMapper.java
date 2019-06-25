package com.inteagle.common.base.dao;

import com.inteagle.common.base.entity.BaseEntity;

/**
 * 
 * @ClassName: BaseMapper
 * @Description: TODO(Mapper基类)
 * @author IVAn
 * @date 2019年6月25日
 *
 * @param <T>
 */
public interface BaseMapper<T extends BaseEntity> {

	/**
	 * 
	 * @Title: deleteByPrimaryKey @Description: TODO(根据entityId删除记录) @param @param
	 *         entityId @param @return 参数 @return int 返回类型 @throws
	 */
	int deleteByPrimaryKey(String entityId);

	/**
	 * 
	 * @Title: deleteByPrimaryKey @Description: TODO(新增entity) @param @param
	 *         entityId @param @return 参数 @return int 返回类型 @throws
	 */
	int insert(T entity);

	/**
	 * 
	 * @Title: deleteByPrimaryKey @Description: TODO(动态新增entity) @param @param
	 *         entityId @param @return 参数 @return int 返回类型 @throws
	 */
	int insertSelective(T entity);

	/**
	 * 
	 * @Title: deleteByPrimaryKey @Description: TODO(根据entityId查询) @param @param
	 *         entityId @param @return 参数 @return int 返回类型 @throws
	 */
	T selectByPrimaryKey(String entityId);

	/**
	 * 
	 * @Title: deleteByPrimaryKey @Description: TODO(动态更新entity) @param @param
	 *         entityId @param @return 参数 @return int 返回类型 @throws
	 */
	int updateByPrimaryKeySelective(T entity);

	/**
	 * 
	 * @Title: deleteByPrimaryKey @Description: TODO(更新entity) @param @param
	 *         entityId @param @return 参数 @return int 返回类型 @throws
	 */
	int updateByPrimaryKey(T entity);

}
