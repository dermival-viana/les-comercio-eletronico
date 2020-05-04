package com.bookstore.web.controller.controller;



import java.security.Principal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.bookstore.core.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.core.IFacade;
import com.bookstore.core.aplication.Result;
import com.bookstore.core.service.UserService;
import com.bookstore.domain.Book;
import com.bookstore.domain.Category;
import com.bookstore.domain.Order;
import com.bookstore.domain.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private IFacade facade;

	@Autowired
	private BookRepository bookRepository;

	@SuppressWarnings("unchecked")
	@RequestMapping("/searchByCategory")
	public String searchByCategory(
			@RequestParam("id") Long categoryId,
			Model model, Principal principal) throws SQLException, ClassNotFoundException {
		if(principal!=null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		/* "View Helper" */
		Category category = new Category();
	    category.setId(categoryId);
	    Set<Category> categories = new HashSet<Category>();
	    categories.add(category);
	    Book book = new Book();
	    book.setCategory(categories);

	    Result result = this.facade.findAll(book);

	    /* "View Helper" */
		if (result.getEntities().isEmpty()) {
			model.addAttribute("emptyList", true);
			return "bookshelf";
		}
		model.addAttribute("bookList", result.getEntities());
		
		return "bookshelf";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/searchBook")
	public String searchBook(
			@ModelAttribute("keyword") String keyword,
			Principal principal, Model model
			) throws SQLException, ClassNotFoundException {
		if(principal!=null) {
			String username = principal.getName();
			User user = userService.findByUsername(username);
			model.addAttribute("user", user);
		}
		
		//List<Book> bookList = bookService.blurrySearch(keyword);
		List<Book> bookList = (List<Book>) facade.findAll(new Book()).getEntities();
		bookList = bookList.stream().filter(b -> b.getTitle()==keyword).collect(Collectors.toList());
		
		if (bookList.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "bookshelf";
		}
		
		model.addAttribute("bookList", bookList);
		
		return "bookshelf";
	}
}
