package com.bookstore.domain;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class ExchangeCoupon extends Coupon {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean active = true;

    /*==============================*/

    public ExchangeCoupon() {
        super();

    }

    public ExchangeCoupon(User user) {
        super();
        this.user = user;
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
