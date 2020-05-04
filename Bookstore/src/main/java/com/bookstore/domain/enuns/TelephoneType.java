package com.bookstore.domain.enuns;

public enum TelephoneType {
    RESIDENTIAL("RESIDENTIAL"),
    COMMERCIAL("COMMERCIAL"),
    CELLPHONE("CELLPHONE");
    private String value;

    TelephoneType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value ;
    }
}
