package com.bookstore.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class PaymentCoupon extends Payment {

    private BigDecimal promotionalCouponValue;

    private BigDecimal exchangeCouponValue;

    @OneToOne
    private ExchangeCoupon exchangeCoupon;

    @OneToOne
    private PromotionalCoupon promotionalCoupon;

    public BigDecimal getPromotionalCouponValue() {
        return promotionalCouponValue;
    }

    public void setPromotionalCouponValue(BigDecimal promotionalCouponValue) {
        this.promotionalCouponValue = promotionalCouponValue;
    }

    public BigDecimal getExchangeCouponValue() {
        return exchangeCouponValue;
    }

    public void setExchangeCouponValue(BigDecimal exchangeCouponValue) {
        this.exchangeCouponValue = exchangeCouponValue;
    }
}
