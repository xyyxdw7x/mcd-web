package com.asiainfo.biapp.framework.jdbc;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * 
 * @author hjn
 * @param <T>
 *
 */
public final class PageList<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6892696919293275778L;

	private List<T> datas;

	private Page page;

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
