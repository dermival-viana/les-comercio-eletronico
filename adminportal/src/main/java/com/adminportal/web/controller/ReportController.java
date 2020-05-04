package com.adminportal.web.controller;

import com.adminportal.core.IFacade;
import com.adminportal.core.application.dto.ReportChartDTO;
import com.adminportal.core.application.dto.ReportsSalesDTO;
import com.adminportal.core.repository.OrderRepository;
import com.adminportal.domain.Book;
import com.adminportal.domain.CartItem;
import com.adminportal.domain.Order;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("reports")
public class ReportController {
    @Autowired
    private IFacade facade;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/sales")
    public String reportSales(Model model) throws SQLException {
        /*List<Order> orderList = (List<Order>) facade.findAll(new Order()).getEntities();
       // ReportsSalesDTO chartData = new ReportsSalesDTO();

        for (Order order: orderList){
            for (CartItem item: order.getCartItemList()){
                item.getBook().setInStockNumber(item.getQty());
               // chartData.addBookToRightDate(order.getOrderDate(), item.getBook());

            }

        }


           model.addAttribute("labels", listOrdeItem.stream().map(ReportChart::getTitle).collect(Collectors.toList()));
            model.addAttribute("data", listOrdeItem.stream().map(ReportChart::getTotal).collect(Collectors.toList()));
*/
        return "reportSalesPage";
    }

    @GetMapping("/stock")
    public String reportStock(Model model) throws SQLException, ClassNotFoundException {
        List<Book> obj = (List<Book>) facade.findAll(new Book()).getEntities();
        model.addAttribute("labels", obj.stream().map(Book::getTitle).collect(Collectors.toList()));
        model.addAttribute("data", obj.stream().map(Book::getInStockNumber).collect(Collectors.toList()));
        return "reportStockPage";
    }


    @PostMapping("/sales")
    @ResponseBody
    public ResponseEntity<?> reportSales(@RequestParam("start") String start,
                                      @RequestParam("end") String end) throws SQLException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dateStart = new Date(sdf.parse(start).getTime());
        Date dateEnd = new Date(sdf.parse(end).getTime());
        List<Order> orders =orderRepository.filterYear(dateStart,dateEnd);
        List<ReportChartDTO> reportChartDTOS = new ArrayList<>();
        Map<String,Integer> bookIntegerMap = new HashMap<>();
        for(Order order: orders){
            for(CartItem cartItem: order.getCartItemList()){
                if(bookIntegerMap.get(cartItem.getBook().getTitle())==null){
                    bookIntegerMap.put(cartItem.getBook().getTitle(),0);
                }
               bookIntegerMap.put(cartItem.getBook().getTitle(),bookIntegerMap.get(cartItem.getBook().getTitle()) + cartItem.getQty());
            }
        }
       bookIntegerMap.forEach((key, value)->{
           reportChartDTOS.add(new ReportChartDTO(key,value));
       });
        boolean flag = false;
        ReportChartDTO aux ;
        while(flag != true){
            flag = true;
            for(int i = 0; i<reportChartDTOS.size()-1;i++){
                if((int)reportChartDTOS.get(i).getValue() < (int)reportChartDTOS.get(i+1).getValue()){
                    aux = reportChartDTOS.get(i);
                    reportChartDTOS.set(i,reportChartDTOS.get(i+1));
                    reportChartDTOS.set(i+1,aux);
                    flag = false;
                }
            }
        }
        List<ReportChartDTO> booksMoreSales = (reportChartDTOS.size()>=3)? reportChartDTOS.subList(0,3): reportChartDTOS;
        List<Map<String,Integer>> bookMoreSalesMonth = new ArrayList<>();

        for(int i = 0; i <= 12; i++){
            Map<String, Integer> reportMap = new HashMap<>();
            for(Order order: orders){
                if(order.getOrderDate().getMonth()== i){
                    for(CartItem cartItem:order.getCartItemList()){
                        for(ReportChartDTO reportChartDTO:booksMoreSales) {
                            if (cartItem.getBook().getTitle().equals(reportChartDTO.getKey())){
                                if(reportMap.get(cartItem.getBook().getTitle())==null){
                                    reportMap.put(cartItem.getBook().getTitle(),0);
                                }
                                reportMap.put(cartItem.getBook().getTitle(),reportMap.get(cartItem.getBook().getTitle())+cartItem.getQty());
                            }
                        }
                    }
                }
            }

            bookMoreSalesMonth.add(reportMap);
        }

        List<List<ReportChartDTO>> list = new ArrayList<>();

        bookMoreSalesMonth.forEach((key)->{
            List<ReportChartDTO> reportChartDtoList = new ArrayList<>();
            key.forEach((key2, value2) -> {
                reportChartDtoList.add(new ReportChartDTO(key2, value2));
            });
            list.add(reportChartDtoList);
        });

        return ResponseEntity.ok().body(list);
    }

//    @PostMapping("/sales")
//    @ResponseBody
//    public String reportSales(@RequestParam("start") String start,
//                              @RequestParam("end") String end) throws SQLException, ParseException {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        Date dateStart = new Date(sdf.parse(start).getTime());
//        Date dateEnd = new Date(sdf.parse(end).getTime());
//        List<Order> orders = orderRepository.filterYear(dateStart, dateEnd);
//        List<ReportChartDTO> reportChartDTOS = new ArrayList<>();
//        Map<String, Integer> bookIntegerMap = new HashMap<>();
//        for (Order order : orders) {
//            for (CartItem cartItem : order.getCartItemList()) {
//                if (bookIntegerMap.get(cartItem.getBook().getTitle()) == null) {
//                    bookIntegerMap.put(cartItem.getBook().getTitle(), 0);
//                }
//                bookIntegerMap.put(cartItem.getBook().getTitle(), bookIntegerMap.get(cartItem.getBook().getTitle()) + cartItem.getQty());
//            }
//        }
//        bookIntegerMap.forEach((key, value) -> {
//            reportChartDTOS.add(new ReportChartDTO(key, value));
//        });
//        JsonArray jsonTitle = new JsonArray();
//        JsonArray jsonQty = new JsonArray();
//        JsonObject json = new JsonObject();
//        reportChartDTOS.forEach(data -> {
//            jsonTitle.add(data.getKey());
//            jsonQty.add(data.getValue());
//        });
//        json.add("title", jsonTitle);
//        json.add("qty", jsonQty);
//
//        return json.toString();
//    }


}
