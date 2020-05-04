package com.bookstore.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Category extends DomainEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	

	private String name;
	
	@ManyToMany(mappedBy="category")
	private Set<Book> book = new HashSet<>();
	
	
	public Category() {
	}
	
	
	public Category(Long id) {
		super(id);
		
	}


	public Category(Long id,String name) {
		super(id);
		this.name = name;
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<Book> getBook() {
		return book;
	}


	public void setBook(Set<Book> book) {
		this.book = book;
	}



	
}
