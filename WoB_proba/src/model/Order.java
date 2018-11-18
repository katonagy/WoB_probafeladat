package model;

import java.sql.Date;

public class Order {

	private int orderId;
	private String buyerName;
	private String buyerEmail;
	private Date orderDate;
	private double orderTotalValue;
	private String address;
	private int postcode;
	
	public Order(){}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getOrderTotalValue() {
		return orderTotalValue;
	}
	public void setOrderTotalValue(double orderTotalValue) {
		this.orderTotalValue = orderTotalValue;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPostcode() {
		return postcode;
	}
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}
	
	
	
}
