
package com.asiainfo.biapp.mcd.test.vo;

import java.io.Serializable;
import java.util.Date;

import com.asiainfo.biapp.framework.jdbc.annotation.Column;

/**
 * Book
 * @author 
 */
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;

	private Date publication;
	
	private Number price;
	
	private int discount;
	
	public Book(){
		
	}
	
	public Book(String title, Date publication, Number price, int discount){
		this.title = title;
		this.publication = publication;
		this.price = price;
		this.discount = discount;
	}
	
	public String getTitle() {
		return title;
	}
	
	@Column(name="BOOK_TITLE")
	public void setTitle(String title) {
		this.title = title;
	}

	
	public Date getPublication() {
		return publication;
	}
	
	@Column(name="BOOK_PUBLICATION")
	public void setPublication(Date publication) {
		this.publication = publication;
	}
	
	public Number getPrice() {
		return price;
	}
	
	@Column(name="BOOK_PRICE")
	public void setPrice(Number price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}
	
	@Column(name="BOOK_DISCOUNT")
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
}