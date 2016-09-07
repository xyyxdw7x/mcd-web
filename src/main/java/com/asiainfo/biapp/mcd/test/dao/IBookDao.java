package com.asiainfo.biapp.mcd.test.dao;

import java.util.List;

import com.asiainfo.biapp.mcd.test.vo.Book;

/**
 * book dao
 * @author hjn
 *
 */
public interface IBookDao {

	public void save(Book book);
	
	public List<Book> queryBookByName(String name);
	
	public Book getBook(String bookId) throws Exception;
	
	public void updateBook(Book book) throws Exception;
	
	public void updateDeleteBook(Book book) throws Exception;
	
	public int queryDatInMem() throws Exception;
	
}
