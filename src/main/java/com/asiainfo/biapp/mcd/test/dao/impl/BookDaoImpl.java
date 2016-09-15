package com.asiainfo.biapp.mcd.test.dao.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.framework.aop.BeanSelfAware;
import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.test.dao.IBookDao;
import com.asiainfo.biapp.mcd.test.vo.Book;
import com.esotericsoftware.minlog.Log;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;

/**
 * book dao impl
 * @author hjn
 *
 */
@Repository("bookDao")
public class BookDaoImpl extends JdbcDaoBase implements IBookDao,Serializable,BeanSelfAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 自身的代理对象
	 */
	//private IBookDao selfProxy;

	@Override
	public void save(Book book) {
		try {
			this.getJdbcTemplateTool().save(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Book> queryBookByName(String name) {
		String sql="select * from mcd_test_book where book_title like ?";
		String sql2="select * from mcd_test_book ";
		Object[] args=new Object[]{"%"+name+"%"};
		int[] argTypes=new int[]{Types.VARCHAR};
		List<Map<String,Object>> list2=this.getJdbcTemplate().queryForList(sql2);
		System.out.println(list2.size());
		List<Book> list = this.getJdbcTemplate().query(sql,args,argTypes,new VoPropertyRowMapper<Book>(Book.class));
		return list;
	}

	@Override
	public Book getBook(String bookId) throws Exception {
		Book book=this.getJdbcTemplateTool().get(Book.class, bookId);
		Log.info("getBook from database");
		//int size=selfProxy.queryDatInMem();
		return book;
	}

	@Override
	public void updateBook(Book book) throws Exception {
		this.getJdbcTemplateTool().update(book);
	}
	
	@Override
	public void updateDeleteBook(Book book) throws Exception{
		this.getJdbcTemplateTool().delete(book);
	}

	@Override
	public int queryDatInMem() throws Exception {
		String sql="select * from RED_WHITE_LIST";
		List<Map<String,Object>> list=this.getJdbcTemplate().queryForList(sql);
		return list.size();
	}
	@Override
	public void setSelfProxy(Object proxyObj) {
		//this.selfProxy=(IBookDao) proxyObj;
	}
}
