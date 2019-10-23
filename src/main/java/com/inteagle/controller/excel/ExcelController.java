package com.inteagle.controller.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inteagle.modal.excel.ExportFoundation;
import com.inteagle.utils.ExcelUtil;

@Controller
@RequestMapping("/excel")
public class ExcelController {

	/**
	 * @description 导出基坑excel数据
	 * @author IVAn
	 * @date 2019年9月9日 下午2:29:28
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/exportFoundation")
	public void exportFoundationData(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ExportFoundation foundation_one = new ExportFoundation();
		foundation_one.setDeviceId("001");
		foundation_one.setDeepLength(2);
		foundation_one.setxRate(4);
		foundation_one.setyRate(6);
		foundation_one.setRecordDate(new Date());

		ExportFoundation foundation_two = new ExportFoundation();
		foundation_two.setDeviceId("002");
		foundation_two.setDeepLength(4);
		foundation_two.setxRate(6);
		foundation_two.setyRate(8);
		foundation_two.setRecordDate(new Date());

		List<ExportFoundation> ExportFoundationList = new ArrayList<ExportFoundation>();
		ExportFoundationList.add(foundation_one);
		ExportFoundationList.add(foundation_two);

		String table_header = "深层水平位移";
		String table_sheetName = "深层水平位移";
		String table_name = "基坑设备数据";
		table_name = table_name.concat(".xls");

		ExcelUtil.exportExcel(ExportFoundationList, table_header, table_sheetName, ExportFoundation.class, table_name,
				true, response);
	}

}
