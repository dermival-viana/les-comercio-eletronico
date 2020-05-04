package com.adminportal.core.application.dto;

import com.adminportal.domain.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportSalesDTO {
    private Date dateOrder;
    private List<Book> bookList;

    public ReportSalesDTO(Date dateOrder){
        this.dateOrder = dateOrder;
        this.bookList = new ArrayList<>();
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void addDataBook(Book book){
        for(int i =0; i < this.bookList.size(); i++){
            if(this.bookList.get(i).getTitle().equals(book.getTitle())){
                this.bookList.get(i).setInStockNumber(this.bookList.get(i).getInStockNumber() + book.getInStockNumber());
                return;
            }
        }

        this.bookList.add(book);
    }
}
