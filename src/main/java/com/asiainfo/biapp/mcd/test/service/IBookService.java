package com.asiainfo.biapp.mcd.test.service;

import java.util.List;

import com.asiainfo.biapp.mcd.test.vo.Book;


/**
 * book service
 * @author hjn
 *
 */
public interface IBookService {

	List<Book> findBooks(String name) throws Exception;
}
