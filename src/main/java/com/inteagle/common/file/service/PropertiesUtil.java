package com.inteagle.common.file.service;

import org.springframework.stereotype.Component;

import com.inteagle.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @ClassName: PropertiesUtil
 * @Description: TODO(读取property文件内容)
 * @author IVAn
 * @date 2019年9月7日上午11:12:43
 *
 */
@Component
@Slf4j
public class PropertiesUtil {

	private static Properties props;

	static {
		loadProps();
	}

	synchronized static private void loadProps() {
		props = new Properties();
		InputStream in = null;
		try {
			// 第一种，通过类加载器进行获取properties文件流
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream("file-name.properties");
			props.load(in);
		} catch (FileNotFoundException e) {
			log.error("file-name.properties文件未找到");
		} catch (IOException e) {
			log.error("出现IOException");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				log.error("jdbc.properties文件流关闭出现异常");
			}
		}
	}

	public static String getProperty(String key) {
		if (null == props) {
			loadProps();
		}
		String result = props.getProperty(key);
		if (result == null) {
			throw new BusinessException("上传路径代号错误");
		}
		return result;
	}

	public static String getProperty(String key, String defaultValue) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key, defaultValue);
	}

}
