package com.asiainfo.biapp.mcd.test.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiainfo.biapp.framework.web.controller.BaseMultiActionController;
import com.asiainfo.biapp.mcd.test.service.IBookService;
import com.asiainfo.biapp.mcd.test.vo.Book;

@RequestMapping("/test/httl")
public class HttlController extends BaseMultiActionController {

	//@Autowired
	@Resource(name="bookService")
	private IBookService bookService;
	
	@RequestMapping("/findBooks")
	public ModelAndView findBooks(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name=request.getParameter("name");
		ModelAndView model = new ModelAndView("test/books");
		HashMap<String,Object> modelMap=new HashMap<String,Object>();
		modelMap.put("user", "张三");
		modelMap.put("pwd", "张三1233123dadas");
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
		String id=request.getParameter("id");
		Book book=bookService.getBook(id);
		return book;
	} 
	
	@RequestMapping("/updateBook")
	@ResponseBody
	public Book updateBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		Book book=bookService.getBook(id);
		book.setTitle("修改的");
		bookService.updateBook(book);
		return book;
	} 
	
	@RequestMapping("/delBook")
	@ResponseBody
	public Book delBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		Book book=bookService.getBook(id);
		bookService.delBook(book);
		return book;
	} 
	
	
	@RequestMapping("/saveBook")
	@ResponseBody
	public Book saveBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		Book book=new Book();
		book.setId(id);
		book.setTitle("sanyi缩短");
		book.setPrice(1.23);
		book.setPublication(new Date());
		book.setDiscount(8);
		bookService.saveBook(book);
		return book;
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
