package com.pavikumbhar.javaheart.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.pavikumbhar.javaheart.model.Purchase;
import com.pavikumbhar.javaheart.model.PurchaseItem;


public class OrderRestBean {

    private Long orderId;
	
    private Calendar orderDate;

    private String customerId;
    
    private String customerName;

    private List<OrderItemRestBean> orderItems = new ArrayList<OrderItemRestBean>();
      
    public OrderRestBean() {
    	
    }
    
    public OrderRestBean(Purchase purchase) {
		orderId = purchase.getId();
		orderDate = purchase.getOrderDate();
		setCustomerId(purchase.getOrderedBy().getName());
		customerName = purchase.getOrderedBy().getFirstName() + " " + purchase.getOrderedBy().getLastName();
		orderItems = new ArrayList<OrderItemRestBean>();
		for (PurchaseItem item : purchase.getPurchaseItems()) {
			OrderItemRestBean orderItem = new OrderItemRestBean();
			orderItem.setId(item.getId());
			orderItem.setProductId(item.getProduct().getId());
			orderItem.setProductName(item.getProduct().getName());
			orderItem.setCount(item.getQuantity());
			orderItems.add(orderItem);
		}
		
	}


	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public Calendar getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}


	
	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public List<OrderItemRestBean> getOrderItems() {
		return orderItems;
	}


	public void setOrderItems(List<OrderItemRestBean> orderItems) {
		this.orderItems = orderItems;
	}


	
    
}
