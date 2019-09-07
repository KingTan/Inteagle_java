package com.inteagle.common.file.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.inteagle.common.base.entity.BaseEntity;
import com.inteagle.common.constant.Constant;
import com.inteagle.common.exception.BusinessException;
import com.inteagle.common.redis.RedisService;
import com.inteagle.common.util.ParamUtil;
import net.coobird.thumbnailator.Thumbnails;

/**
 * 
 * @ClassName: FileService
 * @Description: TODO(文件上传业务类)
 * @author IVAn
 * @date 2019年9月7日上午10:21:51
 *
 */
@Service
public class FileService {

	// 文件后缀名
	private String lastName;

	/**
	 * @description 保存图片并压缩
	 * @author IVAn
	 * @date 2019年9月7日 上午10:48:27
	 * @param file
	 * @param pathType
	 * @param type
	 * @return
	 */
	public Map<String, String> saveThumbnail(MultipartFile file, String pathType, Integer type) {

		ParamUtil.validateParam(pathType, "上传路径代号不能为空");

		String path = Constant.basePath + PropertiesUtil.getProperty(pathType);
		if (!file.isEmpty()) {
			try {
				File filepath = new File(path);
				if (!filepath.exists())
					filepath.mkdirs();

				String realName = file.getOriginalFilename();
				String[] split = realName.split("\\.");
				String fileName;
				String fileName2;

				String uuid = BaseEntity.getUUID();
				if (split.length > 1) {
					fileName = uuid + "." + split[1];
					fileName2 = uuid + "_thumbnail" + "." + split[1];

				} else {
					fileName = uuid + lastName;
					fileName2 = uuid + "_thumbnail" + lastName;
				}
				// 文件保存路径
				String savePath = path + fileName;
				String savePath2 = path + fileName2;

				// 压缩图片
				Thumbnails.of(file.getInputStream()).scale(0.7f).outputQuality(0.1f).toFile(savePath2);

				String URL = changeUrl(savePath);
				String URL2 = changeUrl(savePath2);

				// 源文件存储
				file.transferTo(new File(savePath));
				Map<String, String> map = new HashMap<>();

				// 1-不返回磁盘路径 2-返回磁盘路径
				if (type == null || type == 1) {
					map.put("file", URL);
					map.put("thumbnail", URL2);
					return map;
				} else if (type == 2) {
					map.put("file", URL);
					map.put("thumbnail", URL2);
					map.put("path", savePath2);
					return map;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @description 单文件上传
	 * @author IVAn
	 * @date 2019年9月7日 上午10:36:54
	 * @param files
	 * @param pathType
	 * @param lastName
	 * @return
	 */
	public Object filesUpload(MultipartFile files, String pathType, String lastName) {
		String path = Constant.basePath + PropertiesUtil.getProperty(pathType);
		String fileName = null;
		this.lastName = lastName;
		if (files != null) {
			// 保存文件
			fileName = this.saveFile(files, path, 1);
			if (fileName == null) {
				throw new BusinessException("文件上传失败");
			}
		}
		return fileName;
	}

	/**
	 * @description 保存文件
	 * @author IVAn
	 * @date 2019年9月7日 上午10:32:04
	 * @param file
	 * @param path
	 * @param type
	 * @return
	 */
	private String saveFile(MultipartFile file, String path, Integer type) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				File filepath = new File(path);
				if (!filepath.exists())
					filepath.mkdirs();

				String realName = file.getOriginalFilename();
				String[] split = realName.split("\\.");
				String fileName = null;

				if (split.length > 1) {
					if(this.lastName != null) {
						fileName =  this.lastName+ "." + realName.split("\\.")[1];
						this.lastName = null;
					}else{
						fileName = BaseEntity.getUUID() + "." + realName.split("\\.")[1];
					}
				} else {
					fileName = BaseEntity.getUUID();
				}

				// 文件保存路径
				String savePath = path + fileName;

				// 转存文件
				file.transferTo(new File(savePath));

				String URL = changeUrl(savePath);
				if (type == 1) {
					return URL;
				} else if (type == 2) {
					return savePath + "&" + URL;
				} else if (type == 3) {
					return URL + "," + realName;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @description 多文件上传
	 * @author IVAn
	 * @date 2019年9月7日 上午10:32:20
	 * @param files
	 * @param pathType
	 * @param typeNumber
	 * @return
	 */
	public List<String> filesUpload(MultipartFile[] files, String pathType, Integer typeNumber) {
		String path = Constant.basePath + PropertiesUtil.getProperty(pathType);
		List<String> fileNames = new ArrayList<>();
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				// 保存文件
				String fileName = this.saveFile(file, path, typeNumber == null ? 1 : typeNumber);
				fileNames.add(fileName);
				if (fileName == null) {
					throw new BusinessException("文件上传失败");
				}
			}
		}
		return fileNames;
	}

	/**
	 * @description 修改文件上传路径
	 * @author IVAn
	 * @date 2019年9月7日 上午10:31:14
	 * @param savePath
	 * @return
	 */
	private String changeUrl(String savePath) {
		String result = savePath.replace(Constant.basePath, "");
		return Constant.baseUrl + result;
	}

}
