package com.inteagle.common.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.inteagle.common.exception.BusinessException;
import com.inteagle.config.app.AppConfig;
import com.inteagle.modal.sms.IdentifyingCode;

/**
 * 
 * @ClassName: RedisService
 * @Description: TODO(redis业务类)
 * @author IVAn
 * @date 2019年6月27日下午6:30:33
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RedisService {

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @description 保存验证码
	 * @author IVAn
	 * @date 2019年8月28日 下午2:30:01
	 * @param telephone
	 * @param code
	 * @param codeType
	 */
	public void saveIdentifyingCode(String telephone, String code, String codeType) {
		IdentifyingCode identifyingCode = new IdentifyingCode(telephone, code, codeType);
		set(identifyingCode.getCacheKey(), JSON.toJSONString(identifyingCode), Long.parseLong("10"));
	}

	/**
	 * @description 效验验证码
	 * @author IVAn
	 * @date 2019年8月28日 下午2:30:12
	 * @param telephone 手机号
	 * @param code      验证码
	 * @param codeType  验证码类型 register-注册 login-登录
	 */
	public void validateIdentifyingCode(String telephone, String code, String codeType) {
		Object redis_object = new Object();
		try {
			redis_object = get(telephone + "-" + codeType);
			if (redis_object == null) {
				BusinessException.throwException("该手机号未接收验证码");
			}
		} catch (Exception e) {
			BusinessException.throwException("该手机号未接收验证码");
		}
		IdentifyingCode identifyingCode = JSON.parseObject(redis_object.toString(), IdentifyingCode.class);
		if (identifyingCode == null) {
			BusinessException.throwException("请重新获取验证码");
		}
		if (!telephone.equals(identifyingCode.getTelephone())) {
			BusinessException.throwException("手机号码不一致");
		}
		if (!code.equals(identifyingCode.getCode())) {
			BusinessException.throwException("验证码错误");
		}
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
	public <T> T get_Object(String key, Class<T> clas) {
		return (T) redisTemplate.opsForValue().get(getProjectKey(key));
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 获取带前缀的key名称
	 * 
	 * @param key：键
	 * @return projectKey
	 * @author peng.xy
	 * @time 2018年10月24日 下午4:40:14
	 */
	private String getProjectKey(String key) {
		if (key == null) {
			throw new BusinessException("rediskey不能为空");
		}
		return AppConfig.project + "-" + key;
	}

	/**
	 * 写入缓存设置时效时间(分钟)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
	public void setHours(String key, Object value, int hours) {
		redisTemplate.opsForValue().set(getProjectKey(key), value, hours, TimeUnit.HOURS);
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
	public void setDay(String key, Object value, int day) {
		redisTemplate.opsForValue().set(getProjectKey(key), value, day, TimeUnit.DAYS);
	}

	/**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 哈希 添加
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hmSet(String key, Object hashKey, Object value) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
	}

	/**
	 * 哈希获取数据
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hmGet(String key, Object hashKey) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		return hash.get(key, hashKey);
	}

	/**
	 * 列表添加
	 * 
	 * @param k
	 * @param v
	 */
	public void lPush(String k, Object v) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.rightPush(k, v);
	}

	/**
	 * 列表获取
	 * 
	 * @param k
	 * @param l
	 * @param l1
	 * @return
	 */
	public List<Object> lRange(String k, long l, long l1) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.range(k, l, l1);
	}

	/**
	 * 集合添加
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		set.add(key, value);
	}

	/**
	 * 集合获取
	 * 
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.members(key);
	}

	/**
	 * 有序集合添加
	 * 
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public void zAdd(String key, Object value, double scoure) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		zset.add(key, value, scoure);
	}

	/**
	 * 有序集合获取
	 * 
	 * @param key
	 * @param scoure
	 * @param scoure1
	 * @return
	 */
	public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.rangeByScore(key, scoure, scoure1);
	}

}
