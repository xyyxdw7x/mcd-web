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
}
