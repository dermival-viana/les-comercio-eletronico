package com.bookstore.domain;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

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
