package com.asiainfo.biapp.framework.web.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import net.sf.json.*;

public class DefaultPdfView extends AbstractPdf5View {


	private static Logger log = Logger.getLogger(DefaultPdfView.class);
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		if(session.getAttribute("userInfo")!=null){
		}else{
			session.setAttribute("userInfo", "12wedc");
		}
		
		String header="测试中文Asb  sss123 ss8*7";
		Paragraph headerParagraph = new Paragraph(new Chunk(header,getChineseFont(24)));
		document.add(headerParagraph);
		
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> dataList=(List<Map<String,Object>>) model.get("datas");
		
		if(dataList==null||dataList.size()==0){
			log.info(" kpi size=0");
			document.add(new Paragraph("无数据"));
		}else{
			log.info(" kpi size ="+dataList);
			//如果第一条数据的hit_count结果为null 则标识该用户不使用该接口 按照getAllKpiTypeMap获取全部指标
			if(dataList.get(0).get("HIT_COUNT")==null){
				document.add(new Paragraph("无数据"));
			}else{
				log.info("sort before");
				//Collections.sort(dataList, new KpiHitComparator<Map<String,Object>>());
				log.info("sort after");
				//JSONArray jsonArr=new JSONArray(dataList);
				for (Map<String, Object> map : dataList) {
					map.remove("CREATE_TIME");
					map.remove("TOP_INVALID_DATE");
					map.remove("HIT_COUNT");
					map.remove("KPI_DISPLAY_ORDER");
					map.put("id",map.get("KPI_ID"));
					map.put("name",map.get("KPI_NAME"));
					map.put("sameKindId",map.get("SAME_KIND_KPI_ID"));
					map.remove("KPI_ID");
					map.remove("KPI_NAME");
					map.remove("SAME_KIND_KPI_ID");
				}
				JsonConfig jsonConfig = new JsonConfig();
				//jsonConfig.setExcludes(new String[]{"CREATE_TIME","TOP_INVALID_DATE","HIT_COUNT","KPI_DISPLAY_ORDER"});
//				jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
//					@Override
//					public boolean apply(Object obj, String key, Object value) {
//						if(key.equals("KPI_NAME")||key.equals("SAME_KIND_KPI_ID")||key.equals("KPI_ID")||key.equals("CREATE_TIME")||key.equals("TOP_INVALID_DATE")||key.equals("HIT_COUNT")||key.equals("KPI_DISPLAY_ORDER")){
//							Map<String,Object> map=(Map<String, Object>) obj;
//							if(key.equals("KPI_NAME")){
//								map.put("name", value);
//							}else if(key.equals("KPI_ID")){
//								map.put("id", value);
//							}else if(key.equals("SAME_KIND_KPI_ID")){
//								map.put("sameKindId", value);
//							}
//							return true;
//						}
//						return false;
//					}
//				});
				//jsonConfig.setJsonPropertyFilter(propertyFilter);
				JSONArray jsonArr=JSONArray.fromObject(dataList, jsonConfig);
				String ss=jsonArr.toString();
				
				//ss=ss.replaceAll("\"KPI_NAME\"", "\"name\"");
				//ss=ss.replaceAll("\"KPI_ID\"", "\"id\"");
				//ss=ss.replaceAll("\"SAME_KIND_KPI_ID\"", "\"sameKindId\"");
				//String str=jsonArr.toString();
				Paragraph par=new Paragraph(ss);
				par.setFont(getChineseFont(12));
				document.add(par);
			}
		}
	}
	 
	private static final Font getChineseFont(float size) {  
		Font FontChinese = null;  
	    try {  
	        BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
	        FontChinese = new Font(bfChinese, size, Font.NORMAL);  
	    } catch (DocumentException de) {  
	        //System.err.println(de.getMessage());  
	    } catch (IOException e) {  
	        //System.err.println(e.getMessage());  
	    }  
	    return FontChinese;  
	} 
	@Override
	public String getFileName() {
		return "1 23测试 继续122bbddA ";
	}

}
