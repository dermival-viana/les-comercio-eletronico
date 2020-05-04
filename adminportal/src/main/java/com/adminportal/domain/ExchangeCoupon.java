package com.adminportal.domain;

import java.math.BigDecimal;

import javax.persistence.*;


@Entity
public class ExchangeCoupon extends Coupon {
    private static final long serialVersionUID = 1L;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean active = true;

    /*==============================*/

    public ExchangeCoupon() {
        super();

    }

    public ExchangeCoupon(User user, boolean active) {
        this.user = user;
        this.active = active;
    }

    public ExchangeCoupon(String code, BigDecimal value, User user, boolean active) {
        super(code, value);
        this.user = user;
        this.active = active;
    }

    /*==============================*/

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /*==============================*/
}
