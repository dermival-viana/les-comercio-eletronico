package com.bookstore.web.controller.controller;



import java.math.BigDecimal;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bookstore.core.aplication.Result;
import com.bookstore.core.dao.impl.ExchangeDAO;
import com.bookstore.domain.*;
import com.bookstore.domain.enuns.StatusExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.core.IFacade;
import com.bookstore.core.aplication.dto.ExchangeDTO;
import com.bookstore.core.repository.BookRepository;
import com.bookstore.core.repository.CartItemRepository;
import com.bookstore.core.repository.ExchangeRepository;
import com.bookstore.core.repository.OrderRepository;
import com.bookstore.core.service.UserService;


@Controller
public class ExchangeController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private IFacade facade;

	@Autowired
	private ExchangeDAO exchangeDAO;
	@SuppressWarnings("unchecked")
	@RequestMapping("/exchangeDetails")
	public String exchangeDetail(@RequestParam("id") Long orderId, 
			                     @ModelAttribute("exchangeDTO") ExchangeDTO exchangeDTO,
			                     Principal principal, Model model) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());

		List<Order> orders = (List<Order>) facade.findAll(new Order()).getEntities();
		orders = orders.stream().filter(o -> o.getId()==orderId).collect(Collectors.toList());
		Order order = orders.get(0);

		//List<Exchange> exchangeList =  exchangeDAO.findByOrder(order);
		List<Exchange> exchangeList = (List<Exchange>) facade.findAll(new Exchange()).getEntities();
		exchangeList = exchangeList.stream().filter(e -> e.getOrder()==order).collect(Collectors.toList());


		if (order.getUser().getId() != user.getId()) {
			return "badRequestPage";
		} else {

			List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
			cartItemList = cartItemList.stream().filter(cti -> cti.getOrder()==order).collect(Collectors.toList());

			 for(CartItem item: cartItemList){
			 	for(Exchange exchange: exchangeList){
			 		exchange.getCartItemList().forEach(e-> {
						if(e.getBook().getId() == item.getBook().getId()){
							Integer qtd = item.getQty() - e.getQty();
							item.setQty(qtd);
						}
					});
				}
			 }
			 System.err.println(exchangeList.size());


			model.addAttribute("cartItemList", cartItemList);
			model.addAttribute("user", user);
			model.addAttribute("order", order);
			model.addAttribute("exchangeDTO",exchangeDTO);
			return "myExchange";
		}
	}



	@RequestMapping("/saveExchange") //troca
	public String enchangeDetail1(
			@ModelAttribute("exchangeDTO") ExchangeDTO exchangeDTO,
			Model model, Principal principal) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());
		Exchange exchange = new Exchange();
		Order order = new Order();
		order.setId(exchangeDTO.getOrderId());
		//Order order = orderRepository.findOne(exchangeDTO.getOrderId());
		Result resultOrder = this.facade.findById(order);
		if(resultOrder.getMsg()!=null){
			model.addAttribute("Msg", resultOrder.getMsg());
		}
		order = (Order) resultOrder.getEntity();
		BigDecimal exchangeTotal = new BigDecimal(0);

		List<CartItem> cartItemS = new ArrayList<>();
		for(int i =0 ;i < exchangeDTO.getBookId().length; i++) {
			CartItem cartItem = new CartItem();
			Book book = new Book();
			book.setId(exchangeDTO.getBookId()[i]);
			Result resultBook = this.facade.findById(book);
			book = (Book) resultBook.getEntity();
			cartItem.setBook(book);
			int qty = cartItem.getQty() + exchangeDTO.getQty()[i];
			cartItem.setQty(qty);
			if(cartItem.getQty()!=0) {
				cartItem = cartItemRepository.save(cartItem);
				exchangeTotal = exchangeTotal.add(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(cartItem.getQty())));
				cartItemS.add(cartItem);
				model.addAttribute("cartItems", cartItemS);

			}

		}

		exchange.setValueExchange(exchangeTotal);
		exchange.setCartItemList(cartItemS);
		exchange.setUser(user);
		exchange.setOrder(order);
		exchange.setStatusExchange(StatusExchange.TROCA_EM_ANALISE);
		exchange.setExchangeDate(Calendar.getInstance().getTime());
		exchange.setJustification(exchangeDTO.getJustification());
		facade.save(exchange);
		
		model.addAttribute("exchange", exchange);
		model.addAttribute("order", order);
		model.addAttribute("user", user);
		
			return "exchange";
		
	}

	@RequestMapping(value = "/exchangeDetail", method = RequestMethod.GET)
	public String exchangeDetail( Principal principal, Model model) throws SQLException, ClassNotFoundException {
		List<Exchange> exchangeList = (List<Exchange>) facade.findAll(new Exchange()).getEntities();

		model.addAttribute("exchangeList", exchangeList);
		model.addAttribute("classActiveExchanges", true);

		return "myProfile";
	}

}
