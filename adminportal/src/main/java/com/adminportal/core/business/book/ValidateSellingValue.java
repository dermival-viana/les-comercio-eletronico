package com.adminportal.core.business.book;


import com.adminportal.core.IStrategy;
import com.adminportal.domain.Book;
import com.adminportal.domain.DomainEntity;

import java.sql.SQLException;

public class ValidateSellingValue implements IStrategy {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public String process(DomainEntity entity) throws SQLException {
        Book book = (Book) entity;

        if (book.getOurPrice() == 0.0 || book.getPriceGroup() == null || book.getListPrice() == 0.0) {
            return "The values for sale are required!";
        }

        double margin = (book.getPriceGroup().getPercentage() * book.getListPrice()) / 100;

        if (book.getOurPrice() < (margin + book.getListPrice())){
            return String
                    .format("The sale value is below the margin value% .2f",
                            margin + book.getListPrice());
        }else if(book.getOurPrice() >  (margin  + book.getListPrice())){
            return String
                        .format("Selling value is above profit margin % .2f",
                          margin +book.getListPrice());
        }

        return null;
       }

}

