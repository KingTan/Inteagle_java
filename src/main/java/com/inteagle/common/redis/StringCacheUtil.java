package com.inteagle.common.redis;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import com.inteagle.common.exception.BusinessException;
import com.inteagle.common.util.ParamUtil;

/**
 * 
 * @ClassName: StringCacheUtil
 * @Description: TODO(字符串缓存工具类)
 * @author IVAn
 * @date 2019年8月1日下午4:46:13
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class StringCacheUtil {

	private static StringCacheUtil stringCacheUtil;
	@Autowired
	private RedisTemplate redisTemplate;

	// 项目启动时 注入到Spring容器
	@PostConstruct
	public void init() {
		stringCacheUtil = this;
		stringCacheUtil.redisTemplate = this.redisTemplate;
	}

	/**
	 * 保存键值对
	 *
	 * @param key  键
	 * @param valu 值
	 * @author peng.xy
	 * @time 2018年10月23日 下午8:17:05
	 */
	public static boolean set(String key, String value) {
		if (ParamUtil.notNullForParams(key, value)) {
			stringCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value);
			return true;
		}
		return false;
	}

	/**
	 * 按秒保存键值对
	 *
	 * @param key     键
	 * @param valu    值
	 * @param seconds 秒
	 * @author peng.xy
	 * @time 2018年10月23日 下午8:17:05
	 */
	public static boolean setSeconds(String key, String value, int seconds) {
		if (ParamUtil.notNullForParams(key, value)) {
			stringCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value, seconds, TimeUnit.SECONDS);
			return true;
		}
		return false;
	}

	/**
	 * 按分保存键值对
	 *
	 * @param key     键
	 * @param valu    值
	 * @param minutes 分
	 * @author peng.xy
	 * @time 2018年10月23日 下午8:17:05
	 */
	public static boolean setMinutes(String key, String value, int minutes) {
		if (ParamUtil.notNullForParams(key, value)) {
			stringCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value, minutes, TimeUnit.MINUTES);
			return true;
		}
		return false;
	}

	/**
	 * 按小时保存键值对
	 *
	 * @param key   键
	 * @param valu  值
	 * @param hours 时
	 * @author peng.xy
	 * @time 2018年10月23日 下午8:17:05
	 */
	public static boolean setHours(String key, String value, int hours) {
		if (ParamUtil.notNullForParams(key, value)) {
			stringCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value, hours, TimeUnit.HOURS);
			return true;
		}
		return false;
	}

	/**
	 * 按天保存键值对
	 *
	 * @param key  键
	 * @param valu 值
	 * @param day  天
	 * @author peng.xy
	 * @time 2018年10月23日 下午8:17:05
	 */
	public static boolean setDay(String key, String value, int day) {
		if (ParamUtil.notNullForParams(key, value)) {
			stringCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value, day, TimeUnit.DAYS);
			return true;
		}
		return false;
	}

	/**
	 * 追加字符串
	 *
	 * @param key  键
	 * @param valu 值
	 * @author peng.xy
	 * @time 2018年10月23日 下午8:25:40
	 */
	public static boolean append(String key, String value) {
		if (ParamUtil.notNullForParams(key, value)) {
			stringCacheUtil.redisTemplate.opsForValue().append(getProjectKey(key), value);
			return true;
		}
		return false;
	}

	/**
	 * 获取键值对
	 *
	 * @param key 键
	 * @return String 值
	 * @author peng.xy
	 * @time 2018年10月23日 下午8:23:22
	 */
	public static String get(String key) {
		if (ParamUtil.notNullForParams(key)) {
			return (String) stringCacheUtil.redisTemplate.opsForValue().get(getProjectKey(key));
		}
		return null;
	}

	/**
	 * 截取key对应的字符串
	 *
	 * @param key   键
	 * @param start 起始位置
	 * @param end   结束位置
	 * @author peng.xy
	 * @time 2018年10月23日 下午8:29:35
	 */
	public static String get(String key, long start, long end) {
		if (ParamUtil.notNullForParams(key)) {
			return stringCacheUtil.redisTemplate.opsForValue().get(getProjectKey(key), start, end);
		}
		return null;
	}

	/**
	 * 获取长度
	 *
	 * @param key 键
	 * @return Long 长度
	 * @author peng.xy
	 * @time 2018年10月23日 下午8:32:24
	 */
	public static Long size(String key) {
		if (ParamUtil.notNullForParams(key)) {
			return stringCacheUtil.redisTemplate.opsForValue().size(getProjectKey(key));
		}
		return null;
	}

	/**
	 * 功能描述: 删除字符串缓存
	 *
	 * @author: YooB
	 * @date: 2018/10/24 0024 20:09
	 */
	public static void delete(String key) {
		if (ParamUtil.notNullForParams(key)) {
			stringCacheUtil.redisTemplate.delete(getProjectKey(key));
		}
	}

	/**
	 * 获取带前缀的key名称
	 *
	 * @param key：键
	 * @return projectKey
	 * @author peng.xy
	 * @time 2018年10月24日 下午4:40:14
	 */
	private static String getProjectKey(String key) {
		if (key == null) {
			throw new BusinessException("rediskey不能为空");
		}
		return "Inteagle-" + key;
	}
}
