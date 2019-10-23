package com.inteagle.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.inteagle.common.annotation.NotNull;
import com.inteagle.common.exception.BusinessException;

/**
 * 参数工具类
 * 
 * @author IVAn
 * @CreateDate 2018年7月12日 下午2:41:29
 */
public class ParamUtil {

	// 手机号正则
	private static final String PHONE_REG = "^(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])\\d{8}$";

	// 密码正则
	private static final String PASSWORD_REG = "^\\w{6,18}$";

	// 密码错误消息
	private static final String PASSWORD_MSG = "密码长度在6~18之间，只能包含字母、数字和下划线";

	private ParamUtil() {
		throw new RuntimeException("new ParamUtil instance error");
	}

	/**
	 * 检验多个整数数组参数是否小于等于零
	 * 
	 * @param msg：错误信息
	 * @param lines：整数数组
	 * @author IVAn
	 * @time 2018年10月15日 下午8:07:30
	 *
	 */
	public static void validateLine(String msg, int... lines) {
		if (lines.length <= 0) {
			BusinessException.throwException(msg);
		}
		for (int line : lines) {
			if (line <= 0) {
				BusinessException.throwException(msg);
			}
		}
	}

	/**
	 * 检验参数是否为空，如果是则报错
	 * 
	 * @param param：参数
	 * @param msg：错误信息
	 * @author IVAn
	 * @createDate 2018年7月12日 下午1:58:38
	 */
	public static void validateParam(Object param, String msg) {
		if (isNullForParam(param)) {
			BusinessException.throwException(msg);
		}
	}

	/**
	 * 检验集合是否为空，如果是则报错
	 * 
	 * @param collection：参数集合
	 * @param msg：错误信息数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午1:59:19
	 */
	public static void validateParam(Collection<?> collection, String msg) {
		if (isNullForCollection(collection)) {
			BusinessException.throwException(msg);
		}
	}

	/**
	 * 检验多个参数是否为空，如果是则报错
	 * 
	 * @param msg：错误信息
	 * @param params：多个参数
	 * @author IVAn
	 * @createDate 2018年7月12日 下午1:59:19
	 */
	public static void validateParams(String msg, Object... params) {
		if (isNullForArray(params)) {
			BusinessException.throwException(msg);
		}
	}

	/**
	 * 检验多个参数是否为空，如果是则报错
	 * 
	 * @param params：参数数组
	 * @param msgArray：错误信息数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午1:59:19
	 */
	public static void validateParams(Object[] params, String[] msgArray) {
		if (isNullForLength(params) || isNullForLength(msgArray) || params.length != msgArray.length) {
			BusinessException.throwException("校验失败");
		}
		for (int i = 0; i < params.length; i++) {
			validateParam(params[i], msgArray[i]);
		}
	}

	/**
	 * 校验对象和属性是否为空
	 * 
	 * @param paramObj：参数
	 * @author IVAn
	 * @createDate 2018年8月16日 下午7:38:07
	 */
	public static void validateParamField(Object paramObj) {
		validateParam(paramObj, "参数错误");
		List<Field> fieldList = BeanUtil.getFieldList(paramObj.getClass());
		if (notNullForCollection(fieldList)) {
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(NotNull.class)) {
					Object fieldValue = BeanUtil.invokeGet(paramObj, field.getName());
					if (isNullForParam(fieldValue)) {
						NotNull notNull = field.getAnnotation(NotNull.class);
						BusinessException.throwException(notNull.value());
					}
				}
			}
		}
	}

	/**
	 * 校验一个集合中的字段是否为空
	 * 
	 * @param collection
	 * @author IVAn
	 * @time 2018年11月17日 上午9:36:25
	 */
	public static void validateListField(Collection<?> collection) {
		validateParam(collection, "参数错误");
		Iterator<?> iterator = collection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			validateParamField(next);
		}
	}

	/**
	 * 判断一个参数是否为空
	 * 
	 * @param param：参数数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:06:03
	 */
	public static boolean isNullForParam(Object param) {
		return (param == null || "".equals(param));
	}

	/**
	 * 判断一个参数是否不为空
	 * 
	 * @param param：参数数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:06:27
	 */
	public static boolean notNullForParam(Object param) {
		return !isNullForParam(param);
	}

	/**
	 * 判断多个参数是否其中一个为空
	 * 
	 * @param params：参数数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:08:08
	 */
	public static boolean isNullForParams(Object... params) {
		return isNullForArray(params);
	}

	/**
	 * 判断多个参数是否全都不为空
	 * 
	 * @param params：参数数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:08:33
	 */
	public static boolean notNullForParams(Object... params) {
		return !isNullForParams(params);
	}

	/**
	 * 判断参数数组是否其中一个为空
	 * 
	 * @param params：参数数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:18:36
	 */
	public static boolean isNullForArray(Object[] params) {
		if (isNullForLength(params)) {
			return true;
		}
		return isNullForContent(params);
	}

	/**
	 * 判断参数数组是否全都不为空
	 * 
	 * @param params：参数数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:18:51
	 */
	public static boolean notNullForArray(Object[] params) {
		return !isNullForArray(params);
	}

	/**
	 * 判断数组的长度是否为空
	 * 
	 * @param params：参数数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:21:09
	 */
	private static boolean isNullForLength(Object[] params) {
		return (params == null || params.length <= 0);
	}

	/**
	 * 判断数组的长度是否不为空
	 * 
	 * @param params：参数数组
	 * @author IVAn
	 * @createDate 2018年8月16日 下午7:30:08
	 */
	@SuppressWarnings("unused")
	private static boolean notNullForLength(Object[] params) {
		return !isNullForLength(params);
	}

	/**
	 * 判断数组内容是否为空
	 * 
	 * @param params：参数数组
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:21:24
	 */
	private static boolean isNullForContent(Object[] params) {
		for (Object param : params) {
			if (isNullForParam(param)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断一个集合是否为空
	 * 
	 * @param collection
	 * @return boolean
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:35:50
	 */
	public static boolean isNullForCollection(Collection<?> collection) {
		return (collection == null || collection.isEmpty() || collection.size() <= 0);
	}

	/**
	 * 判断一个集合是否不为空
	 * 
	 * @param collection
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:35:50
	 */
	public static boolean notNullForCollection(Collection<?> collection) {
		return !isNullForCollection(collection);
	}

	/**
	 * 判断一个Map是否为空
	 * 
	 * @param map
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:35:50
	 */
	public static boolean isNullForMap(Map<?, ?> map) {
		return (map == null || map.isEmpty() || map.size() <= 0);
	}

	/**
	 * 判断一个Map是否不为空
	 * 
	 * @param map
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:35:50
	 */
	public static boolean notNullForMap(Map<?, ?> map) {
		return !isNullForMap(map);
	}

	/**
	 * 判断是否为手机号
	 * 
	 * @param telephone
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:56:16
	 */
	public static boolean isPhoneNumber(String telephone) {
		validateParam(telephone, "手机号不能为空");
		return telephone.matches(PHONE_REG);
	}

	/**
	 * 判断是否不为手机号
	 * 
	 * @param telephone
	 * @return
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:56:34
	 */
	public static boolean notPhoneNumber(String telephone) {
		return !isPhoneNumber(telephone);
	}

	/**
	 * 手机号码正则校验
	 * 
	 * @param telephone：手机号码
	 * @return boolean：校验结果
	 * @author IVAn
	 * @createDate 2018年3月22日 上午10:45:08
	 */
	public static void validataTelephone(String telephone) {
		if (notPhoneNumber(telephone)) {
			BusinessException.throwException("手机号格式错误");
		}
	}

	/**
	 * 判断是否为密码
	 * 
	 * @param password
	 * @author IVAn
	 * @createDate 2018年7月12日 下午2:59:50
	 */
	public static boolean isPassword(String password) {
		validateParam(password, "密码不能为空");
		return password.matches(PASSWORD_REG);
	}

	/**
	 * 判断是否不为密码
	 * 
	 * @param password
	 * @return
	 * @author IVAn
	 * @createDate 2018年7月12日 下午3:00:31
	 */
	public static boolean notPassword(String password) {
		return !isPassword(password);
	}

	/**
	 * 密码正则校验
	 * 
	 * @param password：密码
	 * @return boolean：校验结果
	 * @author IVAn
	 * @createDate 2018年5月23日 上午10:12:03
	 */
	public static void validatePassword(String password) {
		if (notPassword(password)) {
			BusinessException.throwException(PASSWORD_MSG);
		}
	}

	/**
	 * 屏蔽电话号码中的中间四位
	 * 
	 * @param telephone：手机号码
	 * @return String：屏蔽后的字符串
	 * @author IVAn
	 * @createDate 2018年5月4日 20:12:46
	 */
	public static String getShieldTelephone(String telephone) {
		return telephone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

}
