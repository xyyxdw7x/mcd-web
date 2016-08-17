package com.asiainfo.biapp.framework.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * 
 * spring mvc对csv的支持
 * @author hanjn
 *
 */
public abstract class AbstractCsvView extends AbstractView {

	/** The content type for an Excel response */
	private static final String CONTENT_TYPE = "text/csv";
	
	/** down file name */
	private String fileName;
	
	/**
	 * Default Constructor.
	 * Sets the content type of the view to "application/vnd.ms-excel".
	 */
	public AbstractCsvView() {
		setContentType(CONTENT_TYPE);
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	
	@Override
	protected void prepareResponse(HttpServletRequest request,
			HttpServletResponse response) {
		super.prepareResponse(request, response);
		if(generatesDownloadContent()){
			 String headerKey = "Content-Disposition";
			 String headerValue = String.format("attachment; filename=\"%s\"",fileName);
			 response.setHeader(headerKey, headerValue);
		}
	}

	@Override
	protected Map<String, Object> createMergedOutputModel(Map<String, ?> model,
			HttpServletRequest request, HttpServletResponse response) {
		return super.createMergedOutputModel(model, request, response);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CSVWriter writer;
		writer=new CSVWriter(response.getWriter());
		buildCsvDocument(model, writer, request, response);
		writer.flush();
		writer.close();
	}
	
	/**
	 * 子类需要实现该方法
	 * @param model
	 * @param writer
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void buildCsvDocument(
			Map<String, Object> model, CSVWriter writer, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
