package com.adminportal.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.adminportal.core.repository.CategoryRepository;
import com.adminportal.domain.PriceGroup;
import com.adminportal.domain.enun.CategoryInactivation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adminportal.core.IFacade;
import com.adminportal.core.application.Result;
import com.adminportal.domain.Book;
import com.adminportal.domain.Category;


@Controller
@RequestMapping("/book")
public class BookController {


	@Autowired
	private IFacade facade;
	@Autowired
	private Result  resultBook;
	@Autowired
	private CategoryRepository categoryRepository;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBook(Model model) throws SQLException, ClassNotFoundException {
		Book book = new Book();
		List<PriceGroup> priceGroups = (List<PriceGroup>) this.facade.findAll(new PriceGroup()).getEntities();
		model.addAttribute("priceGroups", priceGroups);
		List<Category> categoryList =  (List<Category>) this.facade.findAll(new Category()).getEntities();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("book", book);
		return "addBook";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addBookPost(@ModelAttribute("book") Book book,
							  BindingResult bindingResult,
			                  Model model) throws SQLException, ClassNotFoundException{

		Result resultBook = facade.save(book);
		if (bindingResult.hasErrors()) {
			model.addAttribute("Msg", "fields with * must be filled");
			return "redirect:/book/add";
		}
        if (resultBook.getMsg() != null) {
			model.addAttribute("Msg",resultBook.getMsg());
            return "addBook";
		}

		MultipartFile bookImage = book.getBookImage();
		try {
			byte[] bytes = bookImage.getBytes();
			String name = book.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:bookList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/bookInfo")
	public String bookInfo(@RequestParam("id") Long id, Model model,  RedirectAttributes attr) throws SQLException, ClassNotFoundException {
	 	Book book = new Book();
		book.setId(id);
		  resultBook = this.facade.findById(book);
		  if(resultBook.equals(null)){
			  throw new ClassNotFoundException("Could not find book with  id " + id);
		  }

		model.addAttribute("Msg", resultBook.getMsg());
		model.addAttribute("book", resultBook.getEntity());

		return "bookInfo";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateBook")
	public String updateBook(@RequestParam("id") Long id, Model model,  RedirectAttributes attr) throws SQLException, ClassNotFoundException {
		Book book = new Book();
		book.setId(id);
		resultBook = this.facade.findById(book);
		if(resultBook.getEntity() != null){
			//aqui necessario strategy e preparar a view
			model.addAttribute("Msg", resultBook.getMsg());
		}

		model.addAttribute("book", resultBook.getEntity());

		return "updateBook";
	}
	
	@RequestMapping(value="/updateBook", method=RequestMethod.POST)
	public String updateBookPost(@ModelAttribute("book") Book book ) throws SQLException, ClassNotFoundException {
		
		resultBook = this.facade.update(book);

		MultipartFile bookImage = book.getBookImage();
		
		if(!bookImage.isEmpty()) {
			try {
				byte[] bytes = bookImage.getBytes();
				String name = book.getId() + ".png";
				
				Files.delete(Paths.get("src/main/resources/static/image/book/"+ name));
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/book/bookInfo?id="+ book.getId();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/bookList")
	public String bookList(Model model, Book bookCategory) throws SQLException, ClassNotFoundException {
	
		List<Book> bookList = (List<Book>) facade.findAll(new Book()).getEntities();
		model.addAttribute("bookList", bookList);		
		return "bookList";
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String remove(
			@ModelAttribute("id") String id, Model model
			) throws SQLException, ClassNotFoundException {
		Long ID = Long.parseLong(id.substring(8));
		Book book = new Book();
		book.setId(ID);
		 resultBook = this.facade.findById(book);
		if(resultBook.equals(null)){
			throw new ClassNotFoundException("Impossible find the book");
		}
		facade.delete(book);
	//	bookService.removeOne(Long.parseLong(id.substring(8)));
		List<Book> bookList = (List<Book>) facade.findAll(new Book()).getEntities();
		model.addAttribute("bookList", bookList);
		
		return "redirect:/book/bookList";
	}

	@ModelAttribute("sts")
	public CategoryInactivation[] getCategoryInactivation() {

		return CategoryInactivation.values();
	}


}
