package com.adminportal.domain.enun;

public enum CategoryInactivation {

    OUTOFMARKET("OUT OF MARKET"),
    RETUNRMARKET("RETURN MARKET"),
    NOTINSTOCK("NOT IN STOCK"),
    RETURNINSTOCK("RETURN IN STOCK");


    private String category;


    CategoryInactivation(String category){
        this.category = category;
    }


    public String value() {
        return this.category;
    }
}

