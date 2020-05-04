package com.adminportal.domain;

import javax.persistence.Column;
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