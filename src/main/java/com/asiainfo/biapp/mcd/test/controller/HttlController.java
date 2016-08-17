package com.asiainfo.biapp.mcd.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.test.service.IBookService;
import com.asiainfo.biapp.mcd.test.vo.Book;

@RequestMapping("/test/httl")
public class HttlController extends BaseMultiActionController {

	//@Autowired
	@Resource(name="bookService")
	private IBookService bookService;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		return null;
	}

	
	@RequestMapping("/findBooks")
	public ModelAndView findBooks(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId=this.getUserId(request, response);
		System.out.println("userId="+userId);
		String name=request.getParameter("name");
		ModelAndView model = new ModelAndView("test/books");
		HashMap<String,Object> modelMap=new HashMap<String,Object>();
		modelMap.put("user", "张三");
		modelMap.put("pwd", "张三1233123212sadadas");
		if(name==null){
			name="";
		}
		List<Book> books=bookService.findBooks(name);
		modelMap.put("books", books);
		
		model.addAllObjects(modelMap);
		model.addObject("books", books);
		return model;
	}
	
	@RequestMapping("/hello")
	public ModelAndView hello(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.getWriter().write("hello spring mvc，this is text plan");
		return null;
	} 
	
	@RequestMapping("/getBook")
	@ResponseBody
	public Book getBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Book user=new Book();
		user.setTitle("sanyisss13123 ceshi策划四输入大大缩短");
		user.setPrice(1.23);
		return user;
	} 
	
	@RequestMapping("/findBooks2")
	public ModelAndView findBooks2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView("test/books");
		HashMap<String,Object> modelMap=new HashMap<String,Object>();
		modelMap.put("user", "张三");
		modelMap.put("pwd", "张三1233123212sadadas");
		
		String name=request.getParameter("name");
		if(name==null){
			name="";
		}
		
		List<Book> books=bookService.findBooks(name);
		modelMap.put("books", books);
		
		model.addAllObjects(modelMap);
		model.addObject("books", books);
		Map<String,Object> user=new HashMap<String,Object>();
		user.put("name", "张三");
		user.put("role", "admin");
		model.addObject("user", user);
		return model;
	} 
	

	public IBookService getBookService() {
		return bookService;
	}

	public void setBookService(IBookService bookService) {
		this.bookService = bookService;
	} 
}
