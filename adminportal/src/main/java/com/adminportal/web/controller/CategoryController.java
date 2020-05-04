package com.adminportal.web.controller;

import com.adminportal.core.IFacade;
import com.adminportal.core.application.Result;
import com.adminportal.core.repository.CategoryRepository;
import com.adminportal.domain.Book;
import com.adminportal.domain.Category;
import com.adminportal.domain.PriceGroup;
import com.adminportal.domain.enun.CategoryInactivation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;


@Controller
@RequestMapping("/category")
public class CategoryController {


	@Autowired
	private IFacade facade;
	@Autowired
	private Result  result;
	@Autowired
	private CategoryRepository categoryRepository;


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addCategory(Model model) throws SQLException, ClassNotFoundException {
		Category category = new Category();
		model.addAttribute("category", category);
		return "addCategory";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addCategoryPost(@ModelAttribute("category") Category category,
							  BindingResult bindingResult,
			                  Model model) throws SQLException, ClassNotFoundException{

		 result = this.facade.save(category);
        if (result.getMsg() != null) {
			model.addAttribute("Msg",result.getMsg());
            return "addCategory";
		}

		return "redirect:categoryList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateCategory")
	public String updateCategory(@RequestParam("id") Long id, Model model,  RedirectAttributes attr) throws SQLException, ClassNotFoundException {
		Category category = new Category();
		category.setId(id);
		result = this.facade.findById(category);
		if(result.getMsg() != null){
			model.addAttribute("Msg", result.getMsg());
		}
		model.addAttribute("category", result.getEntity());

		return "updateCategory";
	}
	
	@RequestMapping(value="/updateCategory", method=RequestMethod.POST)
	public String updateCategoryPost(@ModelAttribute("category") Category category,
									 Model model) throws SQLException, ClassNotFoundException {

		Result resultCategory = this.facade.update(category);
		if(resultCategory.equals(null)){
			throw new ClassNotFoundException("Update was not performed");
		}

		return "redirect:/category/categoryList?id="+category.getId();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/categoryList")
	public String categoryList(Model model) throws SQLException, ClassNotFoundException {
	
		List<Category> categoryList = (List<Category>) facade.findAll(new Category()).getEntities();
		model.addAttribute("categoryList", categoryList);
		return "categoryList";
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public String remove(
			@ModelAttribute("id") String id, Model model
			) throws SQLException, ClassNotFoundException {
		Long ID = Long.parseLong(id.substring(8));
		Category category = new Category();
		category.setId(ID);
		 result = this.facade.findById(category);
		if(result.getEntity().equals(null)){
			throw new ClassNotFoundException("Impossible find the category");
		}
		result = facade.delete(category);
		model.addAttribute("categoryList", result.getEntities());
		
		return "redirect:/category/categoryList";
	}


}
