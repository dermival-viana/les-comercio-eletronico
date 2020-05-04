package com.adminportal.core.application.dto;

import com.adminportal.domain.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportsSalesDTO {

    List<ReportSalesDTO> reports;

    public List<ReportSalesDTO> getReports() {
        return reports;
    }

    public void setReports(List<ReportSalesDTO> reports) {
        this.reports = reports;
    }



    public ReportsSalesDTO(){
        this.reports = new ArrayList<>();
    }

    public void addBookToRightDate(Date date, Book book){
        for(ReportSalesDTO reportSalesDTO : this.reports){
            if(reportSalesDTO.getDateOrder().equals(date)){
                reportSalesDTO.addDataBook(book);
                return;
            }
        }
        ReportSalesDTO report = new ReportSalesDTO(date);
        report.addDataBook(book);

        this.reports.add(report);
    }
}
