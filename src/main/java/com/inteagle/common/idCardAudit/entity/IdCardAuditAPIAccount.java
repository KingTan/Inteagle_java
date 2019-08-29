package com.inteagle.common.idCardAudit.entity;

import com.inteagle.common.base.entity.BaseEntity;

/**
 * 
 * @ClassName: IdCardAuditAPIAccount
 * @Description: TODO(身份证实名认证API账户类)
 * @author IVAn
 * @date 2019年8月29日上午10:17:00
 *
 */
public class IdCardAuditAPIAccount extends BaseEntity{

	/**
	* @Fields field:field:{todo}(用一句话描述这个变量表示什么)
	*/
	private static final long serialVersionUID = 1L;

	// 请求地址
	public static final String HOST = "http://idcard3.market.alicloudapi.com";

	// app_code
	public static final String APP_CODE = "5619664a9da343f0a87eea51ad4dad0d";

	// app_key
	public static final String APP_KEY = "24981964";

	// app_secret
	public static final String APP_SECRET = "dd55ecf7f0c795b9d89cf32e30da8386";

}
