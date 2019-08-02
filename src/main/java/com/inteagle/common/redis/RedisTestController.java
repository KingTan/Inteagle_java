package com.inteagle.common.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @ClassName: RedisTestController
 * @Description: TODO(redis测试类)
 * @author IVAn
 * @date 2019年6月27日
 *
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisTestController {

	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "/test", method = { RequestMethod.GET, RequestMethod.POST })
	public String redisTest(String value) {
		redisService.set("personId", 256);

		int id = (int) redisService.get("personId");
		System.err.println("id---------" + id);

		return "1";
	}

}
