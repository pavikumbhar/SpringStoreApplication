package com.pavikumbhar.javaheart.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pavikumbhar.javaheart.model.Category;
import com.pavikumbhar.javaheart.model.Product;
import com.pavikumbhar.javaheart.service.CategoryService;
import com.pavikumbhar.javaheart.service.ProductService;
import com.pavikumbhar.javaheart.validator.ProductSearchValidator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/products")
public class SearchController {

	
	private ProductService productService;
	
	private CategoryService categoryService;
	
	private ProductSearchValidator productSearchValidator;

	@RequestMapping(value = "/search")
	public String searchForm(Model model) {
		model.addAttribute("product", new Product());

		return "product/search";
	}

	@RequestMapping
	public String processSearch(@Valid Product product, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "product/search";
		}

		List<Product> results = productService.findProducts(product);
		if (results.size() < 1) {
			bindingResult.rejectValue("name", "notFound", "Sorry, no results found");
			return "product/search";
		}
		if (results.size() == 1) {
			product = results.get(0);
			return "redirect:/products/" + product.getId();
		} else {
			model.addAttribute("results", results);
			return "product/search";
		}

	}

	@ModelAttribute("categories")
	public List<Category> fetchAllCategories() {
		return categoryService.getAllCategories();
	}

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(productSearchValidator);
		binder.setAllowedFields("name", "category");
	}

}
