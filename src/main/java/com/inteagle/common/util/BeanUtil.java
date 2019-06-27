package com.inteagle.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.inteagle.common.annotation.IgnoreKey;
import com.inteagle.common.annotation.IgnoreXml;
import com.inteagle.common.exception.BeanException;


/**
 * 通用工具方法类
 * 
 * @author IVAn
 * @CreateDate 2019年6月27日
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BeanUtil {

	private static final String GET = "get";

	private static final String SET = "set";

	private BeanUtil() {
		throw new RuntimeException("new BeanUtil instance error");
	}

	/**
	 * 将oldObj不为空的属性复制到newObj为空的属性上
	 * 
	 * @param newObj
	 * @param oldObj
	 * @author IVAn
	 * @time 2018年10月24日 下午8:06:13
	 */
	public static void copyProperty(Object newObj, Object oldObj) {
		List<Field> fieldList = getFieldList(newObj.getClass());
		for (Field field : fieldList) {
			if (!Modifier.isStatic(field.getModifiers())) {
				Object fromFieldValue = invokeGet(newObj, field.getName());
				if (fromFieldValue == null) {
					Field oldObjField = getField(oldObj.getClass(), field.getName());
					if (oldObjField != null) {
						Object toFieldValue = invokeGet(oldObj, field.getName());
						if (toFieldValue != null) {
							invokeSet(newObj, field.getName(), field.getType(), toFieldValue);
						}
					}
				}
			}
		}
	}

	/**
	 * 将javaBean转换为Map
	 * 
	 * @param bean：java对象
	 * @return Map<String, Object>
	 * @author IVAn
	 * @createDate 2018年8月14日 下午8:20:53
	 */
	public static Map<String, Object> beanToMap(Object bean) {
		Map<String, Object> map = new HashMap<>();
		List<Field> fieldList = getFieldList(bean.getClass());
		for (Field field : fieldList) {
			if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(IgnoreKey.class)) {
				String fieldName = field.getName();
				Object fieldValue = invokeGet(bean, fieldName);
				if (ParamUtil.notNullForParam(fieldValue)) {
					map.put(fieldName, fieldValue);
				}
			}
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
	public static String beanToXml(String rootName, Object bean) {
		StringBuilder xml = new StringBuilder();
		xml.append("<" + rootName + " version=\"1.0\" encoding=\"utf-8\">");
		List<Field> fieldList = getFieldList(bean.getClass());
		for (Field field : fieldList) {
			if (!Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(IgnoreXml.class)) {
				String fieldName = field.getName();
				Object fieldValue = invokeGet(bean, fieldName);
				if (ParamUtil.notNullForParam(fieldValue)) {
					xml.append("<" + fieldName + ">");
					xml.append("<![CDATA[" + fieldValue + "]]>");
					xml.append("</" + fieldName + ">");
				}
			}
		}
		xml.append("</" + rootName + ">");
		return xml.toString();
	}

	/**
	 * 将一个Collection集合转化为Map，并指定一个字段为key值
	 * 
	 * @param collection：要转换的集合
	 * @param keyFieldName：指定为key值的字段字符串
	 * @param fieldClass：字段的class
	 * @return Map<K, V>：转换后的map
	 * @author IVAn
	 * @CreateDate 2017年11月7日 14:40:41
	 */
	public static <K, V> Map<K, V> getFeildKeyMap(Collection<V> collection, String keyFieldName, Class<K> fieldClass) {
		Map<K, V> map = new HashMap<K, V>();
		Iterator<V> iterator = collection.iterator();
		while (iterator.hasNext()) {
			V entity = iterator.next();
			K key = invokeGet(entity, keyFieldName, fieldClass);
			map.put(key, entity);
		}
		return map;
	}

	/**
	 * 传入一个collection集合，获取指定字段的List
	 * 
	 * @param collection：数据集合
	 * @param fieldName：字段名称
	 * @param fieldClass：字段的class
	 * @return List<E>：返回的字段list
	 * @author IVAn
	 * @CreateDate 2017年11月7日 18:29:13
	 */
	public static <E, T> List<T> getPropertyList(Collection<E> collection, String fieldName, Class<T> fieldClass) {
		List<T> list = new ArrayList<T>();
		Iterator<E> iterator = collection.iterator();
		while (iterator.hasNext()) {
			E entity = iterator.next();
			T value = invokeGet(entity, fieldName, fieldClass);
			if (ParamUtil.notNullForParam(value)) {
				list.add(value);
			}
		}
		return list;
	}

	/**
	 * 传入一个储存map的集合，获取所有指定key的value
	 * 
	 * @param collection：数据集合
	 * @param key：map的key
	 * @param keyClass：key的class
	 * @return List：所有指定key的value
	 * @author IVAn
	 * @CreateDate 2018年3月21日 14:40:18
	 */
	public static <V> List<V> getValueList(Collection collection, Object key, Class<V> valueClass) {
		List<V> valueList = new ArrayList<V>();
		Iterator<Map> iterator = collection.iterator();
		while (iterator.hasNext()) {
			Map map = iterator.next();
			V value = (V) map.get(key);
			if (ParamUtil.notNullForParam(value)) {
				valueList.add(value);
			}
		}
		return valueList;
	}

	/**
	 * 传入一个List<Map>集合，并根据同一个的key进行分组
	 * 
	 * @param collection：需要分组的集合
	 * @param key：进行分组的key
	 * @param keyClass：key的class
	 * @return List：所有指定key的value
	 * @author IVAn
	 * @CreateDate 2018年3月21日 15:19:53
	 */
	public static Map<Object, List<Map>> getGroupMap(Collection collection, Object key) {
		Map<Object, List<Map>> gruopMap = new HashMap<Object, List<Map>>();
		Iterator<Map> iterator = collection.iterator();
		while (iterator.hasNext()) {
			Map itempMap = iterator.next();
			Object itemValue = itempMap.get(key);
			List<Map> groupList = gruopMap.get(itemValue);
			if (groupList == null) {
				groupList = new ArrayList<Map>();
			}
			groupList.add(itempMap);
			gruopMap.put(itemValue, groupList);
		}
		return gruopMap;
	}

	/**
	 * 传入一个List集合，实现两个类型之间字段的赋值，返回一个新类型的list集合
	 * 
	 * @param oldObjCollection：要转换的集合
	 * @param newObjClass：新对象的类型
	 * @param oldFieldArray：原对象的字段数组
	 * @return newFieldArray：转换后对象的数组
	 * @author IVAn
	 * @CreateDate 2017年11月15日 18:31:00
	 */
	public static <E, T> List<T> getCopyList(List<E> oldObjCollection, Class<T> newObjClass, String[] oldFieldArray,
			String[] newFieldArray) {
		List<T> newObjList = new ArrayList<T>();
		try {
			if (oldFieldArray.length != newFieldArray.length) {
				BeanException.throwException("the old field array is not equal in length to the new field array");
			}
			int length = oldFieldArray.length;
			Iterator<E> iterator = oldObjCollection.iterator();
			while (iterator.hasNext()) {
				E oldObj = iterator.next();
				T newObj = newObjClass.newInstance();
				for (int j = 0; j < length; j++) {
					String oldFieldName = oldFieldArray[j];
					Field oldField = oldObj.getClass().getDeclaredField(oldFieldName);
					String newFieldName = newFieldArray[j];
					Field newField = newObj.getClass().getDeclaredField(newFieldName);
					if (!oldField.getType().equals(newField.getType())) {
						BeanException.throwException(oldFieldName + " and " + newFieldName + " field type mismatch");
					}
					invokeSet(newObj, newFieldName, newField.getType(), invokeGet(oldObj, oldFieldName));
				}
				newObjList.add(newObj);
			}
		} catch (Exception e) {
			BeanException.throwException("field copy failed, error message : " + e.getMessage());
		}
		return newObjList;
	}

	/**
	 * 对两个集合的字段进行匹配，符合匹配条件
	 * 
	 * @param parentCollection：父集合
	 * @param childCollection：子集合
	 * @param matchingField：匹配的字段名称
	 * @param tagertField：目标字段名称
	 * @param isSingle：匹配成功是否删除
	 * @author IVAn
	 * @createDate 2018年6月15日 下午5:32:32
	 */
	public static <E, T> void matchingField(Collection<E> parentCollection, Collection<T> childCollection,
			String matchingField, String tagertField, boolean isSingle) {
		matchingField(parentCollection, childCollection, matchingField, matchingField, tagertField, isSingle);
	}

	/**
	 * 对两个集合的字段进行匹配，符合匹配条件添加到list集合
	 * 
	 * @param parentCollection：父集合
	 * @param childCollection：子集合
	 * @param parentField：父类字段名称
	 * @param childField：子类字段名称
	 * @param tagertField：目标字段名称
	 * @param isSingle：匹配成功是否删除
	 * @author IVAn
	 * @createDate 2018年6月15日 下午5:32:32
	 */
	public static <E, T> void matchingField(Collection<E> parentCollection, Collection<T> childCollection,
			String parentField, String childField, String tagertField, boolean isSingle) {
		Iterator<E> parentIterator = parentCollection.iterator();
		while (parentIterator.hasNext()) {
			E parent = parentIterator.next();
			Object parentFieldValue = invokeGet(parent, parentField);
			Iterator<T> childIterator = childCollection.iterator();
			while (childIterator.hasNext()) {
				T child = childIterator.next();
				Object childFieldValue = invokeGet(child, childField);
				if (parentFieldValue.equals(childFieldValue)) {
					invokeSet(parent, tagertField, child.getClass(), child);
					if (isSingle) {
						childIterator.remove();
					}
				}
			}
		}
	}

	/**
	 * 对两个集合的字段进行匹配，符合匹配条件添加到list集合
	 * 
	 * @param parentCollection：父集合
	 * @param childCollection：子集合
	 * @param matchingField：匹配的字段名称
	 * @param tagertField：目标字段名称
	 * @param isSingle：匹配成功是否删除
	 * @author IVAn
	 * @createDate 2018年6月15日 下午5:32:32
	 */
	public static <E, T> void matchingFieldList(Collection<E> parentCollection, Collection<T> childCollection,
			String matchingField, String tagertField, boolean isSingle) {
		matchingFieldList(parentCollection, childCollection, matchingField, matchingField, tagertField, isSingle);
	}

	/**
	 * 对两个集合的字段进行匹配，符合匹配条件添加到list集合
	 * 
	 * @param parentCollection：父集合
	 * @param childCollection：子集合
	 * @param parentField：父类字段名称
	 * @param childField：子类字段名称
	 * @param tagertField：目标字段名称
	 * @param isSingle：匹配成功是否删除
	 * @author IVAn
	 * @createDate 2018年6月15日 下午5:32:32
	 */
	public static <E, T> void matchingFieldList(Collection<E> parentCollection, Collection<T> childCollection,
			String parentField, String childField, String tagertField, boolean isSingle) {
		Iterator<E> parentIterator = parentCollection.iterator();
		while (parentIterator.hasNext()) {
			E parent = parentIterator.next();
			Object parentFieldValue = invokeGet(parent, parentField);
			List<T> tagertList = new ArrayList<T>();
			Iterator<T> childIterator = childCollection.iterator();
			while (childIterator.hasNext()) {
				T child = childIterator.next();
				Object childFieldValue = invokeGet(child, childField);
				if (parentFieldValue.equals(childFieldValue)) {
					tagertList.add(child);
					if (isSingle) {
						childIterator.remove();
					}
				}
			}
			invokeSet(parent, tagertField, List.class, tagertList);
		}
	}

	/**
	 * 调用get方法
	 * 
	 * @param obj：调用对象
	 * @param fieldName：字段名称
	 * @return value
	 * @author IVAn
	 * @createDate 2018年6月16日 上午11:56:58
	 */
	public static Object invokeGet(Object obj, String fieldName) {
		Object value = null;
		try {
			Method fieldGetMethod = getMethod(fieldName, obj.getClass());
			if (ParamUtil.isNullForParam(fieldGetMethod)) {
				BeanException.throwException("not find getMethod in " + fieldName);
			}
			value = fieldGetMethod.invoke(obj);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			BeanException.throwException("invoke to get method failed, error message : " + e.getMessage());
		}
		return value;
	}

	/**
	 * 调用get方法
	 * 
	 * @param obj：调用对象
	 * @param fieldName：字段名称
	 * @param fieldClass：字段class
	 * @return value
	 * @author IVAn
	 * @createDate 2018年6月16日 上午11:56:58
	 */
	public static <T> T invokeGet(Object obj, String fieldName, Class<T> fieldClass) {
		T value = null;
		try {
			Method fieldGetMethod = getMethod(fieldName, obj.getClass());
			if (ParamUtil.isNullForParam(fieldGetMethod)) {
				BeanException.throwException("not find getMethod in " + fieldName);
			}
			value = (T) fieldGetMethod.invoke(obj);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			BeanException.throwException("invoke to get method failed, error message : " + e.getMessage());
		}
		return value;
	}

	/**
	 * 调用set方法
	 * 
	 * @param obj：调用set方法
	 * @param fieldName：字段名称
	 * @param paramClass：字段class
	 * @param value：set方法的参数
	 * @author IVAn
	 * @createDate 2018年6月16日 下午12:06:07
	 */
	public static <T> void invokeSet(Object obj, String fieldName, Class<?> paramType, Object value) {
		try {
			Method fieldSetMethod = setMethod(fieldName, obj.getClass(), paramType);
			if (ParamUtil.isNullForParam(fieldSetMethod)) {
				BeanException.throwException("not find setMethod in " + fieldName);
			}
			fieldSetMethod.invoke(obj, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			BeanException.throwException("invoke to set method failed, error message : " + e.getMessage());
		}
	}

	/**
	 * 获取自身包括父类的字段
	 * 
	 * @param clas
	 * @return List<Field>
	 * @author IVAn
	 * @time 2018年10月24日 下午8:59:26
	 */
	public static List<Field> getFieldList(Class<?> clas) {
		if (clas != null && !clas.equals(Object.class)) {
			ArrayList<Field> fieldList = new ArrayList<Field>();
			Field[] fields = clas.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (Field field : fields) {
					if (!Modifier.isStatic(field.getModifiers())) {
						fieldList.add(field);
					}
				}
			}
			Class<?> superClass = clas.getSuperclass();
			if (superClass != null && !superClass.equals(Object.class)) {
				List<Field> superFieldList = getFieldList(superClass);
				if (superFieldList != null && !superFieldList.isEmpty()) {
					fieldList.addAll(superFieldList);
				}
			}
			return fieldList;
		}
		return new ArrayList<Field>();
	}

	/**
	 * 获取指定的字段
	 * 
	 * @param clas
	 * @param fieldName
	 * @return Field
	 * @author IVAn
	 * @time 2018年11月7日 下午5:48:30
	 */
	public static Field getField(Class<?> clas, String fieldName) {
		Field field = null;
		if (clas != null && !clas.equals(Object.class)) {
			try {
				field = clas.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				field = getField(clas.getSuperclass(), fieldName);
			} catch (SecurityException e) {
				throw e;
			}
		}
		return field;
	}

	/**
	 * 传入字段名和Class，获取get方法
	 * 
	 * @param fieldName：字段名称
	 * @param objClas：对象类型
	 * @return fieldGetMethod：字段get方法
	 * @author IVAn
	 * @CreateDate 2017年11月21日 11:56:45
	 */
	private static Method getMethod(String fieldName, Class<?> objClas) {
		Method fieldGetMethod = null;
		try {
			String fieldGetMethodName = getMethodName(GET, fieldName);
			fieldGetMethod = objClas.getDeclaredMethod(fieldGetMethodName);
		} catch (NoSuchMethodException e) {
			Class<?> superclass = objClas.getSuperclass();
			if (superclass != null && !objClas.equals(Object.class)) {
				return getMethod(fieldName, superclass);
			}
		} catch (SecurityException e) {
			throw e;
		}
		return fieldGetMethod;
	}

	/**
	 * 传入字段名和Class，获取set方法
	 * 
	 * @param fieldName：字段名称
	 * @param objClas：对象类型
	 * @return fieldSetMethod：字段set方法
	 * @author IVAn
	 * @CreateDate 2017年11月21日 11:56:45
	 */
	private static Method setMethod(String fieldName, Class<?> objClas, Class<?>... parameterTypes) {
		Method fieldSetMethod = null;
		try {
			String fieldSetMethodName = getMethodName(SET, fieldName);
			fieldSetMethod = objClas.getDeclaredMethod(fieldSetMethodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			Class<?> superclass = objClas.getSuperclass();
			if (superclass != null && !objClas.equals(Object.class)) {
				return setMethod(fieldName, superclass, parameterTypes);
			}
		} catch (SecurityException e) {
			throw e;
		}
		return fieldSetMethod;
	}

	/**
	 * 传入字段名，获取字段的get/set方法名称
	 * 
	 * @param fieldName：字段名称
	 * @return String：get方法名称
	 * @author IVAn
	 * @CreateDate 2017年11月7日 18:33:17
	 */
	private static String getMethodName(String methodType, String fieldName) {
		return methodType + firstWordToUpperCase(fieldName);
	}

	/**
	 * 传入字符串，将首字母转化为大写
	 * 
	 * @param str：原字符串
	 * @return String：转化后的字符串
	 * @author IVAn
	 * @CreateDate 2017年11月7日 15:58:35
	 */
	private static String firstWordToUpperCase(String str) {
		if (Character.isUpperCase(str.charAt(0))) {
			return str;
		}
		byte[] bytes = str.getBytes();
		bytes[0] -= 32;
		return new String(bytes);
	}
}

