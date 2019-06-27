package com.inteagle.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
* @ClassName: IgnoreXml
* @Description: TODO(xml忽略)
* @author IVAn
* @date 2019年6月27日
*
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreXml {

}
