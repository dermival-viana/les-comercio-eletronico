package com.bookstore.web.controller.controller;



import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.bookstore.core.aplication.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.core.IFacade;

import com.bookstore.core.service.UserService;
import com.bookstore.domain.Book;
import com.bookstore.domain.CartItem;
import com.bookstore.domain.ShoppingCart;
import com.bookstore.domain.User;
import com.bookstore.web.controller.controller.helper.ShoppingCartHelperController;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

	@Autowired
	private UserService userService;

	@Autowired
	private ShoppingCartHelperController shoppingCartHelperController;

	@Autowired
	private IFacade facade;

	@SuppressWarnings("unchecked")
	@RequestMapping("/cart")
	public String shoppingCart(Model model, Principal principal) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();

		List<CartItem> cartItemList = (List<CartItem>) facade.findAll(new CartItem()).getEntities();
		cartItemList = cartItemList.stream().filter(cti -> cti.getShoppingCart()==shoppingCart).collect(Collectors.toList());

		shoppingCartHelperController.updateShoppingCart(shoppingCart);

		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);

		return "shoppingCart";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/addItem")
	public String addItem(
			@ModelAttribute("book") Book bookId,
			@ModelAttribute("qty") String qty,
			Model model, Principal principal
	) throws SQLException, ClassNotFoundException {
		User user = userService.findByUsername(principal.getName());
		List<Book> books = (List<Book>) facade.findAll(new Book()).getEntities();
		books = books.stream().filter(b -> b.getId() == bookId.getId()).collect(Collectors.toList());

		Book book = books.get(0);

		if (Integer.parseInt(qty) > book.getInStockNumber()) {
			model.addAttribute("notEnoughStock", true);
			return "forward:/bookDetail?id="+book.getId();
		}

		CartItem cartItem = shoppingCartHelperController.addBookToCartItem(book, user, Integer.parseInt(qty));
		model.addAttribute("addBookSuccess", true);

		return "forward:/bookDetail?id="+book.getId();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/updateCartItem")
	public String updateShoppingCart(
			@ModelAttribute("id") Long cartItemId,
			@ModelAttribute("qty") int qty, Model model
	) throws SQLException, ClassNotFoundException {

		CartItem cartItem = new CartItem();
		cartItem.setId(cartItemId);
		Result resultCartItem = this.facade.findById(cartItem);
		if(resultCartItem.getMsg()!= null){
			model.addAttribute("Msg", resultCartItem.getMsg());
		}
		cartItem = (CartItem) resultCartItem.getEntity();
		cartItem.setQty(qty);

		shoppingCartHelperController.updateCartItem(cartItem);

		return "forward:/shoppingCart/cart";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/removeItem")
	public String removeItem(@RequestParam("id") Long id, Model model) throws SQLException, ClassNotFoundException {
		CartItem cartItem = new CartItem();
		cartItem.setId(id);
		Result resultCartItem = this.facade.findById(cartItem);
		if(resultCartItem.getMsg()!= null){
			model.addAttribute("Msg", resultCartItem.getMsg());
		}
		cartItem = (CartItem) resultCartItem.getEntity();
		shoppingCartHelperController.removeCartItem(cartItem);

		return "forward:/shoppingCart/cart";
	}
}
