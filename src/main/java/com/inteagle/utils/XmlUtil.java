package com.inteagle.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.inteagle.common.exception.XmlException;
import com.thoughtworks.xstream.XStream;


/**
 * xml工具类
 * 
 * @author IVAn
 * @CreateDate 2019年6月27日
 */
public class XmlUtil {

	private XmlUtil() {
		throw new RuntimeException("new XmlUtil instance error");
	}

	/**
	 * 解析xml字符串
	 * 
	 * @param xmlStr
	 * @return Document
	 * @author IVAn
	 * @createDate 2018年8月15日 下午2:42:02
	 */
	public static Document parseXmlStr(String xmlStr) {
		try {
			return DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			XmlException.throwException(e);
		}
		return null;
	}

	/**
	 * 解析xml文件
	 * 
	 * @param xmlFile
	 * @return Document
	 * @author IVAn
	 * @createDate 2018年8月15日 下午2:49:50
	 */
	public static Document parseFile(File xmlFile) {
		SAXReader reader = new SAXReader();
		try {
			return reader.read(xmlFile);
		} catch (DocumentException e) {
			XmlException.throwException(e);
		}
		return null;
	}

	/**
	 * 解析xml文件地址
	 * 
	 * @param filePath
	 * @return Document
	 * @author IVAn
	 * @createDate 2018年8月15日 下午2:49:50
	 */
	public static Document parseFilePath(String filePath) {
		return parseFile(new File(filePath));
	}

	/**
	 * 将xml字符串转换为Map
	 * 
	 * @param xmlStr
	 * @return Map<String, String>
	 * @author IVAn
	 * @createDate 2018年8月15日 下午6:00:18
	 */
	public static Map<String, String> xmlToMap(String xmlStr) {
		Map<String, String> map = new HashMap<>();
		Document document = parseXmlStr(xmlStr);
		Element root = document.getRootElement();
		@SuppressWarnings("rawtypes")
		Iterator iterator = root.elementIterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			map.put(element.getName(), element.getText());
		}
		return map;
	}

	/**
	 * 将javaBean转换为Xml字符串
	 * 
	 * @param bean：java对象
	 * @return String
	 * @author IVAn
	 * @createDate 2018年8月14日 下午8:42:16
	 */
	public static String beanToXml(Object bean) {
		XStream xstream = new XStream();
		xstream.alias("xml", bean.getClass());
		return xstream.toXML(bean);
	}

	public static String beanToXmlSimple(Object bean) {
		return BeanUtil.beanToXml("xml", bean);
	}

}
