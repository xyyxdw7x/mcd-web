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
	
	
	public int saveBook(Book book) throws Exception{
		int suc=0;
		bookDao.save(book);
		return suc;
	}
	
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

	@Override
	public Book getBook(String bookId) throws Exception {
		Book book=bookDao.getBook(bookId);
		return book;
	}

	@Override
	public int updateBook(Book book) throws Exception {
		bookDao.updateBook(book);
		return 1;
	}
	
	@Override
	public int delBook(Book book) throws Exception{
		bookDao.updateDeleteBook(book);
		return 1;
	}
}
