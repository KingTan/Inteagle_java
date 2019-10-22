package com.inteagle.common.constant;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @ClassName: Constant
 * @Description: TODO(常量类)
 * @author IVAn
 * @date 2019年8月27日上午11:15:40
 *
 */
public class Constant {

	// 项目ip
	public static String server;

	// 项目名
	public static String project;

	// 小程序信息
	public static String appid;

	public static String secret;

	// 项目盘符
	public static String drive;

	// 项目文件夹
	public static String folder;

	// 基础访问路径
	public static String baseUrl;

	// 基础文件路径
	public static String basePath;

	// 调试过滤器开关
	public static boolean debugFilterFlag;

	// 异常控制台打印开关
	public static boolean exceptionFlag;

	// 真实验证码开关
	public static boolean IdentifyingCodeFlag;

	// 真实验证码有效时间
	public static int identityCodeMinutes;

	static {
		SAXReader saxReader = new SAXReader();
		String path = Constant.class.getClassLoader().getResource("constant.xml").getPath();
		try {
			Document document = saxReader.read(new File(path));
			Element root = document.getRootElement();
			// 获取配置文件
			server = root.elementText("server");
			project = root.elementText("project");
			drive = root.elementText("drive");
			folder = root.elementText("folder");
			appid = root.elementText("appid");
			secret = root.elementText("secret");
			baseUrl = server + folder + "/";
			basePath = drive + folder + File.separator;
			debugFilterFlag = Boolean.parseBoolean(root.elementText("debugFilterFlag"));
			exceptionFlag = Boolean.parseBoolean(root.elementText("exceptionFlag"));
			IdentifyingCodeFlag = Boolean.parseBoolean(root.elementText("IdentifyingCodeFlag"));
			identityCodeMinutes = Integer.parseInt(root.elementText("identityCodeMinutes"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
