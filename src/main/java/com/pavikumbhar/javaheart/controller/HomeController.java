package com.pavikumbhar.javaheart.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pavikumbhar.javaheart.model.Category;
import com.pavikumbhar.javaheart.service.CategoryService;
import com.pavikumbhar.javaheart.service.ProductService;

import lombok.AllArgsConstructor;

/**
 * 
 * @author pavi kumbhar
 * Handles requests for the application home page.
 */
@Controller
@AllArgsConstructor
public class HomeController {
	
	private CategoryService categoryService;
	
	private ProductService productService;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale) {
		return new ModelAndView("home", "featuredProducts",productService.getFeaturedProducts()) ;
	}
	
	@ModelAttribute("allCategories")
	public List<Category> fetchAllCategories() {
		return categoryService.getAllCategories();
	}

}
