package com.bookstore.domain;

import com.bookstore.domain.enuns.TelephoneType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Telephone {

    private String ddd;
    private String number;

    @Enumerated(EnumType.STRING)
    private TelephoneType telephoneType;

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public TelephoneType getTelephoneType() {
        return telephoneType;
    }

    public void setTelephoneType(TelephoneType telephoneType) {
        this.telephoneType = telephoneType;
    }
}
