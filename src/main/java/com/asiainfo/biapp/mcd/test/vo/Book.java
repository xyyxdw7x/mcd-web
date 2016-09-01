
package com.asiainfo.biapp.mcd.test.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Book
 * @author 
 */
@Table(name="TEST_BOOK")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private String id;
	
	@Column(name="NAME")
	private String title;

	@Column(name="PUBLICATION")
	private Date publication;
	
	@Column(name="PRICE")
	private Number price;
	
	@Column(name="DISCOUNT")
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
	
	public void setTitle(String title) {
		this.title = title;
	}

	
	public Date getPublication() {
		return publication;
	}
	
	public void setPublication(Date publication) {
		this.publication = publication;
	}
	
	public Number getPrice() {
		return price;
	}
	
	public void setPrice(Number price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}
	
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