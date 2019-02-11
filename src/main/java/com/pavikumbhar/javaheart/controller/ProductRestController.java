package com.pavikumbhar.javaheart.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pavikumbhar.javaheart.bean.MessageRestBean;
import com.pavikumbhar.javaheart.model.Category;
import com.pavikumbhar.javaheart.model.Product;
import com.pavikumbhar.javaheart.service.CategoryService;
import com.pavikumbhar.javaheart.service.ProductService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/api")
public class ProductRestController {
	private ProductService productService;

	
	private CategoryService categoryService;

	@RequestMapping(value = "/categories/{categoryId}/products/{productId}")
	@ResponseBody
	public Product getProduct(@PathVariable("productId") Long productId) {
		Product product = productService.findProduct(productId);
		return product;
	}

	@RequestMapping(value = "/categories/{categoryId}/products")
	@ResponseBody
	public List<Product> getProducts(@PathVariable("categoryId") Long categoryId) {
		Category category = categoryService.findCategoryEagerly(categoryId);
		return category.getProducts();
	}

	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public ResponseEntity<MessageRestBean> handleDatabaseErrors() {

		return new ResponseEntity<MessageRestBean>(new MessageRestBean("Database Error", "DBERR"),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
