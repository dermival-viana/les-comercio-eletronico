package com.adminportal.web.controller;

import com.adminportal.core.IFacade;
import com.adminportal.core.application.Result;
import com.adminportal.core.dao.BookDAO;
import com.adminportal.domain.Exchange;
import com.adminportal.domain.Order;
import com.adminportal.domain.PromotionalCoupon;
import com.adminportal.domain.enun.StatusExchange;
import com.adminportal.domain.enun.StatusOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class CouponController {

    @Autowired
    private IFacade facade;
    @Autowired
    private Result result;

    @Autowired
    private BookDAO bookDAO;


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exchangeList", method = RequestMethod.GET)
    public String exchangeList(Principal principal, Model model) throws SQLException, ClassNotFoundException {

        List<Exchange> exchangeList = (List<Exchange>) facade.findAll(new Exchange()).getEntities();
        model.addAttribute("exchangeList", exchangeList);
        model.addAttribute("statusExchange", StatusExchange.values());
        return "exchangeList";

    }


    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/aprovedExchange", method = RequestMethod.POST)
    public String updateExchange(@RequestParam(value = "idExchange", required = false) List<Long> exchangeIds, String statusExchange, Model model) throws SQLException, ClassNotFoundException {
        Exchange exchange;
        List<Exchange> queryResult = (List<Exchange>) facade.findAll(new Exchange()).getEntities();

        for (Long idExchange : exchangeIds) {
            exchange = queryResult.stream().filter(e -> e.getId() == idExchange).collect(Collectors.toList()).get(0);

            long statusExchangeCode = Long.valueOf(statusExchange);
            StatusExchange statusExchangeObject = StatusExchange.EM_TROCA;

            for (StatusExchange status : StatusExchange.values()) {
                if (status.getCode() == statusExchangeCode)
                    statusExchangeObject = status;
            }

            System.out.println("Id: " + idExchange + " Status: " + statusExchangeObject.name());

            exchange.setStatusExchange(statusExchangeObject);
            facade.update(exchange);

/*
			if (statusExchangeObject == StatusExchange.TROCA_AUTORIZADA) {
				//after exchange returns items to stock
				for(CartItem item : exchange.getCartItemList()){
					Book book = item.getBook();
					Integer qtdInStock = book.getInStockNumber() + item.getQty();
					book.setInStockNumber(qtdInStock);
					bookDAO.update(book);
				}
				String codeCoupon = generateCouponHelper.generateCoupon();
				ExchangeCoupon exchangeCoupon = new ExchangeCoupon();
				exchangeCoupon.setUser(exchange.getUser());
				exchangeCoupon.setCode(codeCoupon);
				exchangeCoupon.setActive(true);
				exchangeCoupon.setValue(exchange.getValueExchange());
				facade.save(exchangeCoupon);
			}*/

        }
        model.addAttribute("statusExchange", StatusExchange.values());

        return "redirect:exchangeList";

    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public String orderList(Model model) throws SQLException, ClassNotFoundException {

        List<Order> orderList = (List<Order>) facade.findAll(new Order()).getEntities();

        model.addAttribute("orderList", orderList);

        model.addAttribute("statusOrder", StatusOrder.values());
        return "orderList";

    }

    @RequestMapping(value = "/orderUpdate", method = RequestMethod.POST)
    public String orderChangeStatus(@RequestParam(value = "idOrder", required = false) List<Long> orderIds, String statusOrder, Model model) throws SQLException, ClassNotFoundException {
        Order order;
        List<Order> queryResult = (List<Order>) facade.findAll(new Order()).getEntities();

        for (Long idOrder : orderIds) {
            order = queryResult.stream().filter(o -> o.getId() == idOrder).collect(Collectors.toList()).get(0);

            long statusOrderCode = Long.valueOf(statusOrder);
            StatusOrder statusOrderObject = StatusOrder.EM_PROCESSAMENTO;

            for (StatusOrder status : StatusOrder.values()) {
                if (status.getCode() == statusOrderCode)
                    statusOrderObject = status;
            }

            System.out.println("Id: " + idOrder + " Status: " + statusOrderObject.name());

            order.setStatusOrder(statusOrderObject);
            facade.update(order);
        }

        model.addAttribute("statusOrder", StatusOrder.values());

        return "redirect:orderList";

    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createCouponPromotional(Model model) throws SQLException {
        PromotionalCoupon pCoupon = new PromotionalCoupon();
        model.addAttribute("pCoupon", pCoupon);
        return "createCouponAdmin";
    }

    @RequestMapping(value = "/createCouponAdmin", method = RequestMethod.POST)
    public String createCouponPromotionalPost(PromotionalCoupon pCoupon, Model model) throws SQLException, ClassNotFoundException {

        String codeCoupon = generateCoupon();
        if (codeCoupon != null) {
            pCoupon.setCode(codeCoupon);
            facade.save(pCoupon);
        }

        Result resultCoupon = facade.findById(pCoupon);
        pCoupon = (PromotionalCoupon) resultCoupon.getEntity();
        model.addAttribute("pCoupon", pCoupon);
        return "createCouponAdmin";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {

            @Override
            public String getAsText() {
                if (null != getValue()) {
                    return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDate) getValue());
                }
                return null;
            }

            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (null != text && !text.isEmpty()) {
                    setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
            }
        });
    }

    public String generateCoupon() {

        String CODECOUPON = "ABCEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();

        while (sb.length()<18) {
            int index= (int) (rnd.nextFloat()*CODECOUPON.length());
            sb.append(CODECOUPON.charAt(index));
        }
        String code = sb.toString();

        return code;
    }
}


