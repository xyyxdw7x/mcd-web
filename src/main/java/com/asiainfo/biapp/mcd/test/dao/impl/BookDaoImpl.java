package com.asiainfo.biapp.mcd.test.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.asiainfo.biapp.framework.jdbc.JdbcDaoBase;
import com.asiainfo.biapp.mcd.test.dao.IBookDao;
import com.asiainfo.biapp.mcd.test.vo.Book;
import com.asiainfo.biapp.framework.jdbc.VoPropertyRowMapper;

/**
 * book dao impl
 * @author hjn
 *
 */
@Repository("bookDao")
public class BookDaoImpl extends JdbcDaoBase implements IBookDao {

	@Override
	public void save(Book book) {

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

}
