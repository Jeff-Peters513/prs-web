package com.prs.business;

import javax.persistence.*;

@Entity
public class LineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "requestId")
	private Request request;
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;
	private int quantity;

	// empty constructor
	public LineItem() {
		super();
	}

	// fully loaded constructor
	public LineItem(int id, Request request, Product product, int quantity) {
		super();
		this.id = id;
		this.request = request;
		this.product = product;
		this.quantity = quantity;
	}

	// getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

//	Commented out until the correct Annotation discovered	
//	public double getLineItemSubTotal() {
//		return quantity * product.getPrice();
//	}

	

	// IDE generated toString()
	@Override
	public String toString() {
		return "LineItem [id=" + id + ", request=" + request + ", product=" + product + ", quantity=" + quantity
				+  "]";
	}



}
