
package com.asiainfo.biapp.mcd.test.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.asiainfo.biapp.framework.jdbc.annotation.Column;

/**
 * Book
 * @author 
 */
@Table(name="TEST_BOOK")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@javax.persistence.Column(name="ID")
	private String id;
	
	@javax.persistence.Column(name="NAME")
	private String title;

	@javax.persistence.Column(name="PUBLICATION")
	private Date publication;
	
	@javax.persistence.Column(name="PRICE")
	private Number price;
	
	@javax.persistence.Column(name="DISCOUNT")
	private int discount;
	
	@Transient
	private String author;
	
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}