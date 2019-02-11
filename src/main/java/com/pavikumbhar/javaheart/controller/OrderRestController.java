package com.pavikumbhar.javaheart.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pavikumbhar.javaheart.bean.OrderItemRestBean;
import com.pavikumbhar.javaheart.bean.OrderRestBean;
import com.pavikumbhar.javaheart.model.Customer;
import com.pavikumbhar.javaheart.model.Product;
import com.pavikumbhar.javaheart.model.Purchase;
import com.pavikumbhar.javaheart.model.PurchaseItem;
import com.pavikumbhar.javaheart.service.CustomerService;
import com.pavikumbhar.javaheart.service.ProductService;
import com.pavikumbhar.javaheart.service.PurchaseService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/api/orders")
public class OrderRestController {

	private PurchaseService purchaseService;

	private ProductService productService;

	private CustomerService customerService;

	@RequestMapping(value = "/{id}")
	@ResponseBody
	public OrderRestBean getOrder(@PathVariable("id") Long orderId) {
		Purchase purchase = purchaseService.findPurchase(orderId);
		return new OrderRestBean(purchase);

	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<String> savePurchase(@RequestBody OrderRestBean order) {

		Purchase purchase = new Purchase();
		Calendar orderDate = Calendar.getInstance();
		purchase.setOrderDate(orderDate);
		Customer customer = customerService.findCustomer(order.getCustomerId());
		purchase.setOrderedBy(customer);
		List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
		long itemId = 1;
		for (OrderItemRestBean orderItem : order.getOrderItems()) {
			PurchaseItem item = new PurchaseItem();
			item.setId(itemId++);
			Product product = productService.findProduct(orderItem.getProductId());
			item.setProduct(product);
			item.setOrderDate(orderDate);
			item.setPurchase(purchase);
			item.setQuantity(orderItem.getCount());
			purchaseItems.add(item);
		}

		purchase.setPurchaseItems(purchaseItems);

		purchaseService.savePurchase(purchase);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(OrderRestController.class).slash(purchase.getId()).toUri());
		return new ResponseEntity<String>("Order created. Order ID: " + purchase.getId(), headers, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public OrderRestBean updatePurchase(@PathVariable("id") Long orderId, @RequestBody OrderRestBean order) {

		Purchase purchase = new Purchase();
		purchase.setId(orderId);
		Calendar orderDate = Calendar.getInstance();
		purchase.setOrderDate(orderDate);
		Customer customer = customerService.findCustomer(order.getCustomerId());
		purchase.setOrderedBy(customer);
		List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
		long itemId = 1;
		for (OrderItemRestBean orderItem : order.getOrderItems()) {
			PurchaseItem item = new PurchaseItem();
			item.setId(itemId++);
			Product product = productService.findProduct(orderItem.getProductId());
			item.setProduct(product);
			item.setOrderDate(orderDate);
			item.setPurchase(purchase);
			item.setQuantity(orderItem.getCount());
			purchaseItems.add(item);
		}

		purchase.setPurchaseItems(purchaseItems);

		purchaseService.updatePurchase(purchase);
		return new OrderRestBean(purchase);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deletePurchase(@PathVariable("id") Long orderId) {
		Purchase purchase = new Purchase();
		purchase.setId(orderId);
		purchaseService.deletePurchase(purchase);
		return "Order " + orderId + " deleted";
	}
}
