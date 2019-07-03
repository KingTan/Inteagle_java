package com.inteagle.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
* @ClassName: DataLen
* @Description: TODO(校验数据长度的注解)
* @author IVAn
* @date 2019年7月3日上午10:08:41
*
 */
@Target({ ElementType.FIELD, ElementType.TYPE })
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DataLen
{
    int value();
}
