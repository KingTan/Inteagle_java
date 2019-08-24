package com.inteagle.common.weather.account;

import com.inteagle.common.base.entity.BaseEntity;

/**
 * 
 * @ClassName: WeatherApiAccount
 * @Description: TODO(天气api账户实体类)
 * @author IVAn
 * @date 2019年8月24日下午3:02:27
 *
 */
public class WeatherApiAccount extends BaseEntity {

	/**
	 * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -8174598153213401291L;

	// 请求地址
	public static final String HOST = "https://weather01.market.alicloudapi.com";

	// app_code
	public static final String APP_CODE = "5619664a9da343f0a87eea51ad4dad0d";

	// app_key
	public static final String APP_KEY = "24981964";

	// app_secret
	public static final String APP_SECRET = "dd55ecf7f0c795b9d89cf32e30da8386";

}
