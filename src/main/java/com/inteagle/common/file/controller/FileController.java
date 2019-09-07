package com.inteagle.common.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.inteagle.common.entity.JsonResult;
import com.inteagle.common.file.service.FileService;

/**
 * 
 * @ClassName: FileController
 * @Description: TODO(文件上传控制器)
 * @author IVAn
 * @date 2019年9月7日上午10:43:51
 *
 */
@Controller
@RequestMapping("/upload")
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * @description 上传图片并压缩
	 * @author IVAn
	 * @date 2019年9月7日 上午10:49:35
	 * @param file
	 * @param pathType
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/savePicAndCompress")
	public JsonResult<Object> savePicture(MultipartFile file, String pathType, Integer type) {
		return new JsonResult<>(fileService.saveThumbnail(file, pathType, type));
	}

	/**
	 * @description 单文件上传
	 * @author IVAn
	 * @date 2019年9月7日 上午10:47:12
	 * @param file
	 * @param pathType
	 * @param lastName
	 * @return
	 */
	@RequestMapping("/fileUpload")
	@ResponseBody
	public JsonResult<Object> filesUpload(MultipartFile file, @RequestParam String pathType, String lastName) {
		return new JsonResult<>(fileService.filesUpload(file, pathType, lastName));
	}

	/**
	 * @description 多文件上传
	 * @author IVAn
	 * @date 2019年9月7日 上午10:46:47
	 * @param files
	 * @param pathType
	 * @param typeNumber
	 * @return
	 */
	@RequestMapping("/filesUpload")
	@ResponseBody
	public JsonResult<Object> filesUpload(@RequestParam("files") MultipartFile[] files, @RequestParam String pathType,
			Integer typeNumber) {
		return new JsonResult<>(fileService.filesUpload(files, pathType, typeNumber));
	}

}
