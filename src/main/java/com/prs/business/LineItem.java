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
	private int quanitity;
	private double price;
//	Foreign Key (ProductID) references product(ID),
//	Foreign Key (RequestID) references request(ID),
//	CONSTRAINT req_pdt unique (RequestID, ProductID)

	// empty constructor
	public LineItem() {
		super();
	}

	// fully loaded constructor
	public LineItem(int id, Request request, Product product, int quanitity, double price) {
		super();
		this.id = id;
		this.request = request;
		this.product = product;
		this.quanitity = quanitity;
		this.price = price;
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

	public int getQuanitity() {
		return quanitity;
	}

	public void setQuanitity(int quanitity) {
		this.quanitity = quanitity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// IDE generated toString()
	@Override
	public String toString() {
		return "LineItem [id=" + id + ", request=" + request + ", product=" + product + ", quanitity=" + quanitity
				+ ", price=" + price + "]";
	}

}
