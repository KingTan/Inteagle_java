package com.inteagle.bimface.controller;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.FileBean;
import com.bimface.sdk.BimfaceClient;
import com.bimface.sdk.bean.request.FileUploadRequest;
import com.inteagle.bimface.config.Config;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/bim")
@Slf4j
public class BimController {

	// 初始化BimfaceClient
	protected static BimfaceClient bimfaceClient = new BimfaceClient(Config.APP_KEY, Config.APP_SECRET);

	/**
	 * @description 获取viewToken
	 * @author IVAn
	 * @date 2019年7月5日 下午3:46:11
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/getViewToken")
	public void getViewToken(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取参数值
		String fileId = request.getParameter("fileId");

		// 获取view token
		String viewToken = null;
		try {
			viewToken = bimfaceClient.getViewTokenByFileId(Long.valueOf(fileId));
		} catch (NumberFormatException e) {
			log.error(e.toString());
		} catch (BimfaceException e) {
			log.error(e.toString());
		}

		// JSON序列化
		response.getWriter().write(JSONObject.toJSONString(viewToken));
	}

	/**
	 * @description 上传文件 并转换文件
	 * @author IVAn
	 * @date 2019年7月5日 下午3:44:18
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/uploadFile")
	public void uploadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file) throws IOException {
		// 上传文件
		FileBean fileBean = null;
		try {
			FileUploadRequest fileUploadRequest = new FileUploadRequest();
			fileUploadRequest.setContentLength(file.getSize());
			fileUploadRequest.setName(file.getOriginalFilename());
			fileUploadRequest.setInputStream(file.getInputStream());

			fileBean = bimfaceClient.upload(fileUploadRequest);

		} catch (BimfaceException e) {
			log.error(e.toString());
		}

		// 获取fileId
		Long fileId = fileBean.getFileId();

		// 发起文件转换
		FileTranslateBean translateBean = null;
		try {
			translateBean = bimfaceClient.translate(fileId);
		} catch (BimfaceException e) {
		}

		response.getWriter().write(JSONObject.toJSONString(translateBean));
	}

	/**
	 * @description 获取文件转换状态
	 * @author IVAn
	 * @date 2019年7月5日 下午3:41:16
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getPullStatus")
	public void getPullStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 文件ID
		String fileId = request.getParameter("fileId");

		// 调用BIMFACE-SDK获取文件转换状态
		FileTranslateBean translateBean = null;
		try {
			translateBean = bimfaceClient.getTranslate(Long.valueOf(fileId));
		} catch (NumberFormatException e) {
			log.error(e.toString());
		} catch (BimfaceException e) {
			e.printStackTrace();
		}

		// JSON序列化
		response.getWriter().write(JSONObject.toJSONString(translateBean));

	}

}
