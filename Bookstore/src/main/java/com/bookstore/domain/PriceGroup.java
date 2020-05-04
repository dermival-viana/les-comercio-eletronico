package com.bookstore.domain;

import javax.persistence.Entity;

@Entity
public class PriceGroup extends DomainEntity {

	private static final long serialVersionUID = 1L;
	
    private String name;
	
	private Double percentage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
}
