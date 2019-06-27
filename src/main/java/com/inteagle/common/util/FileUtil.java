package com.inteagle.common.util;

import com.inteagle.common.exception.BusinessException;

/**
 * 文件工具类
 * 
 * @author IVAn
 * @CreateDate 2018年8月15日 下午7:24:07
 */
public class FileUtil {

	// 图片格式
	private static final String[] PICTURE_PATTERNS = { ".png", ".jpg", ".jpge", ".bmp", ".gif" };

	// 音频格式
	private static final String[] VOICE_PATTERNS = { ".mp3", ".mov", ".aac", ".amr", ".wav", ".wma" };

	// 视频格式
	private static final String[] VIDEO_PATTERNS = { ".mp4", ".avi", ".wmv", ".mkv", ".rmvb", ".3gp", ".flv" };

	private FileUtil() {
		throw new RuntimeException("new ParamUtil instance error");
	}

	/**
	 * 获取文件前缀名
	 * 
	 * @param fileName:文件名
	 * @return String:前缀名
	 * @author IVAn
	 * @createDate 2018年5月7日 下午1:37:14
	 */
	public static String getFilePrefix(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param fileName:文件名
	 * @return String:后缀名
	 * @author IVAn
	 * @createDate 2018年5月7日 下午1:37:14
	 */
	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 是否为照片
	 * 
	 * @param fileName
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:08:19
	 */
	public static boolean isPicture(String fileName) {
		return endWithPattern(fileName, PICTURE_PATTERNS);
	}

	/**
	 * 是否不为照片
	 * 
	 * @param fileName
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:08:19
	 */
	public static boolean notPicture(String fileName) {
		return !isPicture(fileName);
	}

	/**
	 * 检验图片
	 * 
	 * @param fileName
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:39:18
	 */
	public static void validatePicture(String fileName) {
		if (notPicture(fileName)) {
			BusinessException.throwException("图片格式错误");
		}
	}

	/**
	 * 是否为音频
	 * 
	 * @param fileName
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:08:19
	 */
	public static boolean isVoice(String fileName) {
		return endWithPattern(fileName, VOICE_PATTERNS);
	}

	/**
	 * 是否不为音频
	 * 
	 * @param fileName
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:08:19
	 */
	public static boolean notVoice(String fileName) {
		return !isVoice(fileName);
	}

	/**
	 * 检验音频
	 * 
	 * @param fileName
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:39:18
	 */
	public static void validateVoice(String fileName) {
		if (notVoice(fileName)) {
			BusinessException.throwException("音频格式错误");
		}
	}

	/**
	 * 是否为视频
	 * 
	 * @param fileName
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:08:19
	 */
	public static boolean isVideo(String fileName) {
		return endWithPattern(fileName, VIDEO_PATTERNS);
	}

	/**
	 * 是否不为视频
	 * 
	 * @param fileName
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:08:19
	 */
	public static boolean notVideo(String fileName) {
		return !isVideo(fileName);
	}

	/**
	 * 检验视频
	 * 
	 * @param fileName
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:39:18
	 */
	public static void validateVideo(String fileName) {
		if (notVideo(fileName)) {
			BusinessException.throwException("视频格式错误");
		}
	}

	/**
	 * 对格式进行匹配
	 * 
	 * @param fileName：文件名
	 * @param Patterns：格式
	 * @author IVAn
	 * @createDate 2018年7月12日 下午4:09:04
	 */
	private static boolean endWithPattern(String fileName, String[] Patterns) {
		ParamUtil.validateParam(fileName, "文件名不能为空");
		String suffix = FileUtil.getFileSuffix(fileName);
		for (String pattern : Patterns) {
			if (suffix.equalsIgnoreCase(pattern)) {
				return true;
			}
		}
		return false;
	}
}

