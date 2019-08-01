package com.inteagle.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String redisTest() {
		
		// 将指定的值插入列表尾部并返回长度
		ListCacheUtil.rightPush("camera_person_id_list", "1");
		
		
		
		
//		StringBuffer sb = new StringBuffer();
//		redisService.set("str", "str");
//		redisService.set("str", "str2");
//		sb.append("str=").append(redisService.get("str").toString()).append(",");
//		redisService.hmSet("hmset", "key", "val");
//		
//		sb.append("hmset=").append(redisService.hmGet("hmset", "key")).append(",");
//		redisService.lPush("list", "val");
//		
//		sb.append("list=").append(redisService.lRange("list", 0, 1).toString()).append(",");
//		redisService.add("set", "val");
//		
//		sb.append("set=").append(redisService.setMembers("set").toString()).append(",");
//		redisService.zAdd("zset", "val1", 1);
//		redisService.zAdd("zset", "val2", 2);
//		
//		sb.append("zset=").append(redisService.rangeByScore("zset", 1, 2)).append(",");
		
		return "1";
	}

}
