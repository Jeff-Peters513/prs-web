package com.prs.business;

import javax.persistence.*;

@Entity
public class LineItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int requestId;
	private int productId;
	private int quanitity;
	private double price;
//	Foreign Key (ProductID) references product(ID),
//	Foreign Key (RequestID) references request(ID),
//	CONSTRAINT req_pdt unique (RequestID, ProductID)
		
	//empty constructor
	public LineItem() {
		super();
	}
	
	//fully loaded constructor
	public LineItem(int id, int requestId, int productId, int quanitity, double price) {
		super();
		this.id = id;
		this.requestId = requestId;
		this.productId = productId;
		this.quanitity = quanitity;
		this.price = price;
	}

	//getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	//IDE generated toString()
	@Override
	public String toString() {
		return "LineItem [id=" + id + ", requestId=" + requestId + ", productId=" + productId + ", quanitity="
				+ quanitity + ", price=" + price + "]";
	}
	
	
	
	
}
