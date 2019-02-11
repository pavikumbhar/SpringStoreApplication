package com.pavikumbhar.javaheart.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pavikumbhar.javaheart.bean.CategoryList;
import com.pavikumbhar.javaheart.model.Category;
import com.pavikumbhar.javaheart.service.CategoryService;

@Controller
@RequestMapping("/api/categories")
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/{id}")
	@ResponseBody
	public Category getCategory(@PathVariable("id") Long categoryId) {
		Category category = categoryService.findCategoryEagerly(categoryId);
		return category;
	}

	//@RequestMapping("/{id}")
	@ResponseBody
	public CategoryList getCategories(@PathVariable("id") Long categoryId) {
		Category category = categoryService.findCategoryEagerly(categoryId);
		return new CategoryList(Collections.singletonList(category));
	}

	@RequestMapping
	@ResponseBody
	public CategoryList getCategories(@RequestParam("start") int start, @RequestParam("size") int size) {
		List<Category> categoryEntries = categoryService.findCategoryEntries(start, size);
		return new CategoryList(categoryEntries);

	}
}
