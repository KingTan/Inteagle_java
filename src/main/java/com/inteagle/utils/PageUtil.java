package com.inteagle.utils;


import java.util.List;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inteagle.common.exception.BusinessException;
import com.inteagle.modal.base.JsonResult;


/**
 * 
 * 分页工具类
 * 
 * @author IVAn
 * @CreateDate 2018年5月3日 10:21:42
 */
public class PageUtil {
	
	private PageUtil() {
		throw new RuntimeException("new PageUtil instance error");
	}
	
	/**
	 * 设置分页属性
	 * 
	 * @param pageIndex:页码
	 * @param pageSize:一页大小
	 * @author IVAn
	 * @createDate 2018年5月3日 下午7:56:39
	 */
	public static void setPage(Integer pageIndex, Integer pageSize) {
		if (pageIndex == null || pageSize == null) {
			PageHelper.startPage(1, Integer.MAX_VALUE);
		} else {
			if (pageIndex <= 0 || pageSize <= 0) {
				BusinessException.throwException("分页信息错误");
			}
			PageHelper.startPage(pageIndex, pageSize);
		}
	}

	/**
	 * 设置分页信息对象
	 * 
	 * @param list:分页数据
	 * @return PageInfo:分页信息
	 * @author IVAn
	 * @createDate 2018年5月3日 下午8:44:47
	 */
	public static <T> PageInfo<T> getPageInfo(List<T> list) {
		return new PageInfo<T>(list);
	}

	/**
	 * 获取分页jsonResult对象
	 * 
	 * @param list:分页数据
	 * @return JsonResult
	 * @author IVAn
	 * @createDate 2018年5月17日 下午4:50:11
	 */
	public static <T> JsonResult<PageInfo<T>> getPageJsonResult(List<T> list) {
		return new JsonResult<PageInfo<T>>(new PageInfo<T>(list));
	}
}

