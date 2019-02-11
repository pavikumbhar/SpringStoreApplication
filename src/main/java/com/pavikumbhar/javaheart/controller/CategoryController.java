package com.pavikumbhar.javaheart.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pavikumbhar.javaheart.model.Category;
import com.pavikumbhar.javaheart.service.CategoryService;

import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

	
	private CategoryService categoryService;

	

	@RequestMapping(params = "create")
	public String createForm(Model model) {
		model.addAttribute("category", new Category());
		return "category/create";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("category") @Valid Category category,
			BindingResult result) {
		if (result.hasErrors()) {
			return "category/register";
		}
		categoryService.saveCategory(category);
		return "redirect:/categories/" + category.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String updateCategory(@ModelAttribute("category") @Valid Category category,
			BindingResult result) {
		if (result.hasErrors()) {
			return "category/register";
		}
		categoryService.updateCategory(category);
		return "redirect:/categories/" + category.getId();
	}
	@RequestMapping(value = "/{id}")
	public ModelAndView getCategory(@PathVariable("id") Long categoryId) {
		Category category = categoryService.findCategoryEagerly(categoryId);
		return new ModelAndView("category/view", "category", category);
		
	}
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String editCategory(@PathVariable("id") Long categoryId,
			Map<String, Object> model) {
		Category category = categoryService.findCategory(categoryId);
		model.put("category", category);
		return "category/edit";
	}

}
