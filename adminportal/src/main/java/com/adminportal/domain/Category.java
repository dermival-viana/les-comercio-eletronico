package com.adminportal.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@Entity
public class Category extends DomainEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	
	@ManyToMany(mappedBy="category")
	private List<Book> book = new ArrayList<>();
	
	
	public Category() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}
}
