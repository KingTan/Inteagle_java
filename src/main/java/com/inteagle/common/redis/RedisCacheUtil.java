package com.inteagle.common.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import com.inteagle.common.exception.BusinessException;
import com.inteagle.modal.sms.IdentifyingCode;

/**
 * 
 * @ClassName: RedisCacheUtil
 * @Description: TODO(redis缓存工具类)
 * @author IVAn
 * @date 2019年8月1日下午4:47:43
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RedisCacheUtil {

	private static RedisCacheUtil redisCacheUtil;
	@Autowired
	private RedisTemplate redisTemplate;

	// 项目启动时 注入到Spring容器
	@PostConstruct
	public void init() {
		redisCacheUtil = this;
		redisCacheUtil.redisTemplate = this.redisTemplate;
	}

	/**
	 * 保存验证码
	 * 
	 * @param request
	 * @param telephone：手机号码
	 * @param codeType：1注册，2修改密码，3修改手机号码，4绑定手机号码
	 * @author peng.xy
	 * @time 2018年11月1日 下午8:55:47
	 */
	public static void saveIdentifyingCode(String telephone, String code, String codeType) {
		IdentifyingCode IdentifyingCode = new IdentifyingCode(telephone, code, codeType);
		setMinutes(IdentifyingCode.getCacheKey(), IdentifyingCode, 10);
	}

	/**
	 * 保存键值对到缓存中
	 * 
	 * @param key   键
	 * @param value 值
	 * @author peng.xy
	 * @time 2018年10月24日 下午4:59:10
	 */
	public static void set(String key, Object value) {
		redisCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value);
	}

	/**
	 * 按秒保存键值对到缓存中
	 * 
	 * @param key     键
	 * @param value   值
	 * @param seconds 秒
	 * @author peng.xy
	 * @time 2018年10月24日 下午4:59:10
	 */
	public static void setSeconds(String key, Object value, int seconds) {
		redisCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 按时保存键值对到缓存中
	 * 
	 * @param key     键
	 * @param value   值
	 * @param minutes 分
	 * @author peng.xy
	 * @time 2018年10月24日 下午4:59:10
	 */
	public static void setMinutes(String key, Object value, int minutes) {
		redisCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value, minutes, TimeUnit.MINUTES);
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
	public static void setHours(String key, Object value, int hours) {
		redisCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value, hours, TimeUnit.HOURS);
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
	public static void setDay(String key, Object value, int day) {
		redisCacheUtil.redisTemplate.opsForValue().set(getProjectKey(key), value, day, TimeUnit.DAYS);
	}

	/**
	 * 取出缓存中存储的对象
	 * 
	 * @param key 键
	 * @return Object
	 * @author peng.xy
	 * @time 2018年10月24日 下午5:06:57
	 */
	public static Object get(String key) {
		return redisCacheUtil.redisTemplate.opsForValue().get(getProjectKey(key));
	}

	/**
	 * 取出缓存中存储的对象
	 * 
	 * @param key  键
	 * @param clas 值的类型
	 * @return T
	 * @author peng.xy
	 * @param <T>
	 * @time 2018年10月24日 下午5:06:57
	 */
	public static <T> T get(String key, Class<T> clas) {
		return (T) redisCacheUtil.redisTemplate.opsForValue().get(getProjectKey(key));
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

	public static void deteleKey(String key) {
		redisCacheUtil.redisTemplate.delete(key);
	}

}
