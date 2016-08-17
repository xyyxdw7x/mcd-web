package com.asiainfo.biapp.mcd.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.mcd.test.dao.IBookDao;
import com.asiainfo.biapp.mcd.test.service.IBookService;
import com.asiainfo.biapp.mcd.test.vo.Book;


@Repository("bookService")
public class BookServiceImpl implements IBookService {

	@Resource(name="bookDao")
	private IBookDao bookDao;
	
	public List<Book> findBooks(String name) throws Exception {
		List<Book> books=bookDao.queryBookByName(name);
		return books;
	}

	public IBookDao getBookDao() {
		return bookDao;
	}

	public void setBookDao(IBookDao bookDao) {
		this.bookDao = bookDao;
	}
}
