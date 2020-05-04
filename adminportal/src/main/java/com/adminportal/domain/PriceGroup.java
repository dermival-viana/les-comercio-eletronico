package com.adminportal.domain;

import javax.persistence.Entity;

@Entity
public class PriceGroup extends DomainEntity {

	private static final long serialVersionUID = 1L;
	
    private String name;
	
	private double percentage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
}
