<%@page language="java" contentType="application/x-msdownload" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.net.URLEncoder,java.util.*,com.asiainfo.biframe.utils.config.Configure,com.asiainfo.biapp.mcd.util.Encoding" %>
<%
 
	String filename =request.getSession().getServletContext().getRealPath("/mcd/pages/custom") + "/20160329.txt";
	String displayname = "客户群导入模板.txt";
	filename=Encoding.decodeURL(filename,"UTF-8");
	filename=filename.replace("\\","/");
	response.reset();
	response.setContentType("application/x-download");
	//String downloadfile ="/oradata/dp_dir1/"+filename;
	String downloadfile =filename;
	downloadfile=downloadfile.replace("\\","/");
	String dfilename=displayname;
	/* if(filename!=null&&filename!=""){
		dfilename=filename.substring(filename.lastIndexOf("/")+1,filename.length());
	} */
	response.setHeader("Content-disposition", "attachment; filename="+ new String(dfilename.getBytes("gb2312"),"iso8859-1")); //设置下载文件的名称
	java.io.OutputStream outp = null;
	java.io.FileInputStream in = null;
	try {
		outp = response.getOutputStream();
		in = new java.io.FileInputStream(downloadfile);
		byte[] b = new byte[1024];
		int i = 0;
		while ((i = in.read(b)) > 0) {
			outp.write(b, 0, i);
		}
		outp.flush();
		outp.close();
	} catch (Exception e) {
		System.out.println("Error!");
		e.printStackTrace();
	} finally {
		if (in != null) {
			in.close();
			in = null;
		}
		if (outp != null) {
			outp.close();
			outp = null;
		}
	}
	//下载时抛出异常
	 	out.clear();
	 	out = pageContext.pushBody();
%>