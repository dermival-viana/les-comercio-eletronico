package com.adminportal.domain;



import java.util.Date;

import javax.persistence.Entity;

import com.adminportal.domain.enun.StatePayment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;



@Entity
public class PaymentTicket extends Payment {
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date finalDate;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date datePayment;

	public PaymentTicket() {
	}

	public PaymentTicket(long id, StatePayment statusPayment, Order order, Date finalDate, Date datePayment) {
		this.datePayment = datePayment;
		this.finalDate = finalDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	public Date getDatePayment() {
		return datePayment;
	}

	public void setDatePayment(Date datePayment) {
		this.datePayment = datePayment;
	}
}
