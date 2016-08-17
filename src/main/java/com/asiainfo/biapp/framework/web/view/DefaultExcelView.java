package com.asiainfo.biapp.framework.web.view;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DefaultExcelView extends AbstractExcel2007View {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list=(List<Map<String, Object>>) model.get("datas");
		
		//SXSSFWorkbook
		HSSFSheet sheet = workbook.createSheet("flex_my_favorites");
		
        sheet.setDefaultColumnWidth(90);    
        HSSFCell cell = getCell(sheet, 0, 0);    
        setText(cell, "Spring2 Excel test");    
        //dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));    
        cell = getCell(sheet, 1, 0);    
        cell.setCellValue("日期：21008-10-23");    
        //cell.setCellStyle(dateStyle);    
        getCell(sheet, 2, 0).setCellValue("测试1");    
        getCell(sheet, 2, 1).setCellValue("测试2");  
        HSSFRow sheetRow = sheet.createRow(3);    
        for (short i = 0; i < 10; i++) {    
        	sheetRow.cellIterator();
            //sheetRow.createCell(i).setCellValue(i * 10);    
        }
        
        HSSFRow row;
        int rownum=4;
        Map<String, Object> dataMap;
        for (int i = 0; i < list.size(); i++) {
            row=sheet.createRow(rownum);
            dataMap=list.get(i);
            String value1=(String)dataMap.get("ID");
            String value2=(String)dataMap.get("NAME");
            String value3=(String)dataMap.get("NAME_PINYIN");
            String value4=(String)dataMap.get("URL");
            String value5=(String)dataMap.get("ICON");
            
            row.createCell(1).setCellValue(value1);
            row.createCell(2).setCellValue(value2);
            row.createCell(3).setCellValue(value3);
            row.createCell(4).setCellValue(value4);
            row.createCell(5).setCellValue(value5);
            
            rownum=rownum+1;
        }
        
        OutputStream ouputStream = response.getOutputStream();     
        workbook.write(ouputStream);     
        ouputStream.flush();     
        ouputStream.close();     
        
	}

	@Override
	public String getFileName() {
		return "1 23测试 继续122bbddA ";
	}
	
	
	
	

}
