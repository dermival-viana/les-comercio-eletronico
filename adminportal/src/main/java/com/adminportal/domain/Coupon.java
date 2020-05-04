package com.adminportal.domain;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author marco
 */

@MappedSuperclass
public class Coupon extends DomainEntity {

    private static final long serialVersionUID = 1L;
    private String code;
    private BigDecimal value;


    public Coupon() {
        super();

    }


    public Coupon(String code, BigDecimal value) {
        super();
        this.code = code;
        this.value = value;
    }


    public BigDecimal getValue() {
        return value;
    }


    public void setValue(BigDecimal value) {
        this.value = value;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


}
