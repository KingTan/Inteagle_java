package com.inteagle.common.redis;

import java.util.Collection;
import java.util.List;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.inteagle.common.util.SpringContextUtil;

/**
 * 
 * @ClassName: ListCacheUtil
 * @Description: TODO(集合缓存工具类)
 * @author IVAn
 * @date 2019年8月1日下午4:49:20
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ListCacheUtil {

	private static RedisTemplate<String, Object> redisTemplate = SpringContextUtil.getBean("redisTemplate",
			RedisTemplate.class);

	/**
	 * 设置指定位置的值
	 * 
	 * @param key   键
	 * @param index 位置
	 * @param value 值
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:52:37
	 */
	public static void set(String key, long index, Object value) {
		redisTemplate.opsForList().set(getProjectKey(key), index, value);
	}

	/**
	 * 删除指定的key列表中指定的值
	 * 
	 * @param key   键
	 * @param value 值
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:56:35
	 */
	public static void remove(String key, Object value) {
		redisTemplate.opsForList().remove(getProjectKey(key), 0, value);
	}

	/**
	 * 从指定位置开始删除列表中的值， count> 0：删除等于从头到尾移动的值的元素 count <0：删除等于从尾到头移动的值的元素 count =
	 * 0：删除等于value的所有元素
	 * 
	 * @param key   键
	 * @param count 位置
	 * @param value 值
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:57:46
	 */
	public static void remove(String key, long count, Object value) {
		redisTemplate.opsForList().remove(getProjectKey(key), count, value);
	}

	/**
	 * 返回存储在键中的列表的指定元素
	 * 
	 * @param key   键
	 * @param start 开始位置
	 * @param end   结束位置
	 * @return List<Object>
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:25:17
	 */
	public static List<Object> range(String key, long start, long end) {
		return redisTemplate.opsForList().range(getProjectKey(key), start, end);
	}

	/**
	 * 根据下标获取列表中的值
	 * 
	 * @param key   键
	 * @param index 下标
	 * @return Object
	 * @author peng.xy
	 * @time 2018年10月29日 下午2:08:20
	 */
	public static Object index(String key, long index) {
		return redisTemplate.opsForList().index(getProjectKey(key), index);
	}

	/**
	 * 裁剪列表指定位置
	 * 
	 * @param key   键
	 * @param start 开始位置
	 * @param end   结束位置
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:26:32
	 */
	public static void trim(String key, long start, long end) {
		redisTemplate.opsForList().trim(getProjectKey(key), start, end);
	}

	/**
	 * 返回存储在键中的列表的长度
	 * 
	 * @param key 键
	 * @return Long 长度
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:28:17
	 */
	public static Long size(String key) {
		return redisTemplate.opsForList().size(getProjectKey(key));
	}

	/**
	 * 将指定的值插入列表头部并返回长度
	 * 
	 * @param key   键
	 * @param value 值
	 * @return Long 长度
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:30:59
	 */
	public static Long leftPush(String key, Object value) {
		return redisTemplate.opsForList().leftPush(getProjectKey(key), value);
	}

	/**
	 * 批量插入列表头部并返回长度
	 * 
	 * @param key    键
	 * @param values 值
	 * @return Long 长度
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:30:59
	 */
	public static Long leftPushAll(String key, Object... values) {
		return redisTemplate.opsForList().leftPushAll(getProjectKey(key), values);
	}

	/**
	 * 插入一个集合到列表头部并返回长度
	 * 
	 * @param key    键
	 * @param values 值
	 * @return Long 长度
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:33:52
	 */
	public static Long leftPushAll(String key, Collection<Object> values) {
		return redisTemplate.opsForList().leftPushAll(getProjectKey(key), values);
	}

	/**
	 * 只有存在该key时才进行插入列表头部
	 * 
	 * @param key   键
	 * @param value 值
	 * @return Long 长度
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:35:37
	 */
	public static Long leftPushIfPresent(String key, Object value) {
		return redisTemplate.opsForList().leftPushIfPresent(getProjectKey(key), value);
	}

	/**
	 * 将指定的值插入列表尾部并返回长度
	 * 
	 * @param key   键
	 * @param value 值
	 * @return Long 长度
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:44:10
	 */
	public static Long rightPush(String key, Object value) {
		return redisTemplate.opsForList().rightPush(getProjectKey(key), value);
	}

	/**
	 * 批量插入列表尾部并返回长度
	 * 
	 * @param key    键
	 * @param values 值
	 * @return Long 长度
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:47:40
	 */
	public static Long rightPushAll(String key, Object... values) {
		return redisTemplate.opsForList().rightPushAll(getProjectKey(key), values);
	}

	/**
	 * 插入一个集合到列表尾部并返回长度
	 * 
	 * @param key    键
	 * @param values 值
	 * @return Long 长度
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:47:40
	 */
	public static Long rightPushAll(String key, Collection<Object> values) {
		return redisTemplate.opsForList().rightPushAll(getProjectKey(key), values);
	}

	/**
	 * 只有key存在时才插入到列表尾部
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 长度
	 * @author peng.xy
	 * @time 2018年10月24日 上午9:50:09
	 */
	public static Long rightPushIfPresent(String key, Object value) {
		return redisTemplate.opsForList().rightPushIfPresent(getProjectKey(key), value);
	}

	/**
	 * 弹出最左边的元素，弹出之后该值在列表中删除
	 * 
	 * @param key 键
	 * @return Object
	 * @author peng.xy
	 * @time 2018年10月29日 下午2:10:25
	 */
	public static Object leftPop(String key) {
		return redisTemplate.opsForList().leftPop(getProjectKey(key));
	}

	/**
	 * 弹出最右边的元素，弹出之后该值在列表中将删除
	 * 
	 * @param key 键
	 * @return Object
	 * @author peng.xy
	 * @time 2018年10月29日 下午2:11:55
	 */
	public static Object rightPop(String key) {
		return redisTemplate.opsForList().rightPop(getProjectKey(key));
	}

	/**
	 * 功能描述: 获取list所有属性
	 *
	 * @param: key
	 * @author: YooB
	 * @date: 2018/11/20 0020 14:43
	 */
	public static List<Object> listFindAll(String key) {
		// 获取带有头部的key
		key = getProjectKey(key);
		ListOperations listOperations = redisTemplate.opsForList();
		if (!redisTemplate.hasKey(key)) {
			return null;
		}
		return listOperations.range(key, 0, listOperations.size(key));
	}

	/**
	 * 获取带前缀的key名称
	 * 
	 * @param key：键
	 * @return projectKey
	 * @author peng.xy
	 * @time 2018年10月24日 下午4:40:14
	 */
	public static String getProjectKey(String key) {
		return "Inteagle-" + key;
	}

}
