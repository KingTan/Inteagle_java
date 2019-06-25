package com.inteagle.common.base.service;

import com.inteagle.common.base.entity.BaseEntity;

/**
 * 
 * @ClassName: BaseService
 * @Description: TODO(service公众接口)
 * @author IVAn
 * @date 2019年6月25日
 *
 * @param <T>
 */
public interface BaseService<T extends BaseEntity> {

	/**
	 * 
	 * @Title: deleteByPrimaryKey @Description: TODO(根据entityId删除记录) @param @param
	 *         entityId @param @return 参数 @return int 返回类型 @throws
	 */
	int deleteByPrimaryKey(String entityId);

	/**
	 * 
	 * @Title: insert @Description: TODO(新增entity) @param @param
	 *         entity @param @return 参数 @return int 返回类型 @throws
	 */
	int insert(T entity);

	/**
	 * 
	 * @Title: insertSelective @Description: TODO(动态新增entity) @param @param
	 *         entity @param @return 参数 @return int 返回类型 @throws
	 */
	int insertSelective(T entity);

	/**
	 * 
	 * @Title: selectByPrimaryKey @Description: TODO(根据entityId查询) @param @param
	 *         entityId @param @return 参数 @return T 返回类型 @throws
	 */
	T selectByPrimaryKey(String entityId);

	/**
	 * 
	 * @Title: updateByPrimaryKeySelective @Description:
	 *         TODO(动态更新entity) @param @param entity @param @return 参数 @return int
	 *         返回类型 @throws
	 */
	int updateByPrimaryKeySelective(T entity);

	/**
	 * 
	 * @Title: selectByPrimaryKey @Description: TODO( 更新entity) @param @param
	 *         entityId @param @return 参数 @return T 返回类型 @throws
	 */
	int updateByPrimaryKey(T entity);
}
