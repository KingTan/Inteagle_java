package com.inteagle.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.inteagle.common.exception.BusinessException;

import sun.misc.BASE64Encoder;

/**
 * MD5加密工具类
 * 
 * @author IVAn
 * @time 2018年11月1日 下午2:05:52
 */
@SuppressWarnings("restriction")
public class Md5Util {

	/**
	 * 获取md5加密
	 * 
	 * @param str
	 * @return
	 * @author IVAn
	 * @time 2018年11月1日 下午2:18:54
	 */
	public static String getMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException("加密失败");
		}
	}
	
	/**
	 * 
	 * @Title: encoderByMd5   
	 * @Description: 加密字符串
	 * @param: @param str
	 * @param: @return      
	 * @return: String      
	 * @Author: yu.gui.ming
	 */
	public static String encoderByMd5(String str){
        //确定计算方法
        MessageDigest md5= null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr= null;
        try {
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newstr;
    }
	
	/**
	   * 解密比较字符串
	 * @Title: checkoutStr   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param oldstr
	 * @param: @param newstr
	 * @param: @return      
	 * @return: boolean      
	 * @Author: yu.gui.ming
	 */
    @SuppressWarnings("unused")
	public static boolean checkoutStr(String oldstr,String newstr){
        if(oldstr != null && oldstr != "" && newstr != null && newstr != ""){
           if(Md5Util.encoderByMd5(oldstr).equals(newstr)){
              return true;
           }else {
               return false;
           }
        }
        return false;
    }

}
