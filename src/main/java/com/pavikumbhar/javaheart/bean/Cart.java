package com.pavikumbhar.javaheart.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.pavikumbhar.javaheart.model.Product;



@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION, 
	proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Product, Integer> contents = new HashMap<Product,Integer>();

	public Map<Product, Integer> getContents() {
		return contents;
	}
	
	public Set<Product> getProducts() {
		return contents.keySet();
	}
	
	public void addProduct(Product product, int count) {
		
		if (contents.containsKey(product)) {
			contents.put(product, contents.get(product) + count);
		} 
		else {
			contents.put(product, count);
		}
	}
	
	
	public void removeProduct(Product product) {
		contents.remove(product);
	}
	
	public void clearCart() {
		contents.clear();
	}
	
	@Override
	public String toString() {
		return contents.toString();
	}
	
	public double getTotalCost() {
		double totalCost = 0;
		for (Product product : contents.keySet()) {
			totalCost += product.getPrice();
		}
		return totalCost;
	}
	
}
