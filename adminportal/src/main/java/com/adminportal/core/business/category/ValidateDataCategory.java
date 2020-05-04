package com.adminportal.core.business.category;

import com.adminportal.core.IStrategy;
import com.adminportal.domain.Book;
import com.adminportal.domain.Category;
import com.adminportal.domain.DomainEntity;

import java.sql.SQLException;

public class ValidateDataCategory implements IStrategy {
    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Override
    public String process(DomainEntity entity) throws SQLException {
        Category c = (Category) entity;
        StringBuilder msg = new StringBuilder();


        if (c.getName().equals("") || c.getName().equals(null)) {
            msg.append("Category name is required!");
        }


        return msg.toString();
    }
}
