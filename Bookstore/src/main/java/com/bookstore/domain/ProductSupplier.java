package com.bookstore.domain;

import javax.persistence.Entity;


@Entity
public class ProductSupplier extends DomainEntity {

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}