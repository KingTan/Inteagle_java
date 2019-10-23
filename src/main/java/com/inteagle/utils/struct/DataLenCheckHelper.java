package com.inteagle.utils.struct;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.inteagle.common.annotation.DataLen;

/**
 * 
* @ClassName: DataLenCheckHelper
* @Description: TODO(检验数组长度的注解)
* @author IVAn
* @date 2019年7月3日上午10:09:02
*
 */
public class DataLenCheckHelper {

	/**
	 * 校验数据属性至
	 * 
	 * @param obj
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void checkAttributeValueLen(Object obj) throws Exception {
		if (null != obj) {
			// 得到class
			Class cls = obj.getClass();
			System.out.println("校验对象中参数的数据长度是否符合要求,校验对象:" + cls.getName());
			// 得到所有属性
			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {// 遍历
				try {
					// 得到属性
					Field field = fields[i];
					Annotation[] anns = field.getAnnotations();
					DataLen dataLen = null;
					for (Annotation ann : anns) {
						if (ann instanceof DataLen)
							dataLen = (DataLen) ann;
					}

					// 判断该属性是否有校验数据长度的注解
					if (null != dataLen) {
						// 打开私有访问
						field.setAccessible(true);
						// 获取属性
						String name = field.getName();
						// 获取属性值
						Object value = field.get(obj);
						// 指定的长度
						int len = dataLen.value();
						// 数据的长度
						int vaLen = 0;
						String data = null;
						// 一个个赋值
						if (null != value && value instanceof String) {
							data = (String) value;
							vaLen = data.length();
						}

						if (vaLen != len) {
							System.out.print("对象:" + cls.getName() + "中存在不符合条件的参数,参数名:" + name + "参数值:" + data
									+ "指定的数据长度:" + len + "实际长度:" + vaLen + "不符合条件");
							throw new Exception();
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			System.out.println("参数校验完成，全部符合要求");
		}
	}

}
