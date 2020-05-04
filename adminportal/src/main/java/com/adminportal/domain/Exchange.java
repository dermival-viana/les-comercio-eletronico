package com.adminportal.domain;


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.adminportal.domain.enun.StatusExchange;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Exchange extends DomainEntity {

    private static final long serialVersionUID = 1L;

    private Date exchangeDate;
    @Enumerated(EnumType.STRING)
    private StatusExchange statusExchange;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private BigDecimal valueExchange;

    @Column(columnDefinition = "text")
    private String justification;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "EXCHANGE_ITEM", joinColumns = {
            @JoinColumn(name = "exchange_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "cartItem_id", referencedColumnName = "id")})
    private List<CartItem> cartItemList;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @Transient
    private Collection<Long> ids;

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public StatusExchange getStatusExchange() {
        return statusExchange;
    }

    public void setStatusExchange(StatusExchange statusExchange) {
        this.statusExchange = statusExchange;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }


    public BigDecimal getValueExchange() {
        return valueExchange;
    }

    public void setValueExchange(BigDecimal valueExchange) {
        this.valueExchange = valueExchange;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Collection<Long> getIds() {
        return ids;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIds(Collection<Long> ids) {
        this.ids = ids;

    }


}

