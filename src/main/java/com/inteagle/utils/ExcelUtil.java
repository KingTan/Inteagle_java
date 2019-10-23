package com.inteagle.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import com.bimface.sdk.utils.StringUtils;
import com.inteagle.common.exception.BusinessException;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

/**
 * 
 * @ClassName: ExcelUtil
 * @Description: TODO(Excel工具类)
 * @author IVAn
 * @date 2019年9月9日上午11:49:28
 *
 */
public class ExcelUtil {

	/**
	 * @description 复杂导出Excel，包括文件名以及表名。创建表头
	 * @author IVAn
	 * @date 2019年9月9日 上午11:49:54
	 * @param list
	 * @param title
	 * @param sheetName
	 * @param pojoClass
	 * @param fileName
	 * @param isCreateHeader
	 * @param response
	 */
	public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
			boolean isCreateHeader, HttpServletResponse response) {
		ExportParams exportParams = new ExportParams(title, sheetName);
		exportParams.setCreateHeadRows(isCreateHeader);
		defaultExport(list, pojoClass, fileName, response, exportParams);
	}

	/**
	 * @description 复杂导出Excel，包括文件名以及表名,不创建表头
	 * @author IVAn
	 * @date 2019年9月9日 上午11:51:12
	 * @param list
	 * @param title
	 * @param sheetName
	 * @param pojoClass
	 * @param fileName
	 * @param response
	 */
	public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
			HttpServletResponse response) {
		defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
	}

	/**
	 * @description Map集合导出
	 * @author IVAn
	 * @date 2019年9月9日 上午11:51:29
	 * @param list
	 * @param fileName
	 * @param response
	 */
	public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
		defaultExport(list, fileName, response);
	}

	/**
	 * @description 默认导出的方法
	 * @author IVAn
	 * @date 2019年9月9日 上午11:51:52
	 * @param list
	 * @param pojoClass
	 * @param fileName
	 * @param response
	 * @param exportParams
	 */
	private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
			ExportParams exportParams) {
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
		if (workbook != null)
			;
		downLoadExcel(fileName, response, workbook);
	}

	/**
	 * @description Excel导出
	 * @author IVAn
	 * @date 2019年9月9日 上午11:52:16
	 * @param fileName
	 * @param response
	 * @param workbook
	 */
	private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * @description 默认导出的方法
	 * @author IVAn
	 * @date 2019年9月9日 上午11:51:52
	 * @param list
	 * @param pojoClass
	 * @param fileName
	 * @param response
	 * @param exportParams
	 */
	private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
		Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
		if (workbook != null)
			;
		downLoadExcel(fileName, response, workbook);
	}

	/**
	 * @description 根据文件路径 导入excel文件
	 * @author IVAn
	 * @date 2019年9月9日 上午11:53:35
	 * @param <T>
	 * @param filePath
	 * @param titleRows
	 * @param headerRows
	 * @param pojoClass
	 * @return
	 */
	public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
		if (StringUtils.isNullOrEmpty(filePath)) {
			return null;
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(titleRows);
		params.setHeadRows(headerRows);
		List<T> list = null;
		try {
			list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
		} catch (NoSuchElementException e) {
			throw new BusinessException("模板不能为空");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return list;
	}

	public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
			Class<T> pojoClass) {
		if (file == null) {
			return null;
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(titleRows);
		params.setHeadRows(headerRows);
		List<T> list = null;
		try {
			list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
		} catch (NoSuchElementException e) {
			throw new BusinessException("excel文件不能为空");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return list;
	}

}
