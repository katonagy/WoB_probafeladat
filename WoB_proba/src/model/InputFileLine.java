package model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class InputFileLine {
	
	private String lineNumber;
	private String orderItemId;
	private String orderId;
	private String buyerName;
	private String buyerEmail;
	private String address;
	private String postcode;
	private String salePrice;
	private String shippingPrice;
	private String SKU;
	private String status;
	private String orderDate;
	
	private enum Status{
		OUT_OF_STOCK,
		IN_STOCK
	}

	public InputFileLine() {}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(String shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "InputFileLine [lineNumber=" + lineNumber + ", orderItemId=" + orderItemId + ", orderId=" + orderId
				+ ", buyerName=" + buyerName + ", buyerEmail=" + buyerEmail + ", address=" + address + ", postcode="
				+ postcode + ", salePrice=" + salePrice + ", shippingPrice=" + shippingPrice + ", SKU=" + SKU
				+ ", status=" + status + ", orderDate=" + orderDate + "]";
	}

	public boolean isValid() throws InputException {
		
		if(this.lineNumber == null || this.lineNumber.isEmpty())
			throw new InputException("Line number should not be empty");
		
		if(this.orderItemId == null || this.orderItemId.isEmpty())
			throw new InputException("Order item id should not be empty");
		
		if(this.orderId == null || this.orderId.isEmpty())
			throw new InputException("Order id should not be empty");
		
		if(this.buyerName == null || this.buyerName.isEmpty())
			throw new InputException("Buyer name should not be empty");
		
		if(this.buyerEmail == null || this.buyerEmail.isEmpty())
			throw new InputException("Buyer email should not be empty");
		
		if(this.address == null || this.address.isEmpty())
			throw new InputException("Address should not be empty");
			
		if(this.postcode == null || this.postcode.isEmpty())
			throw new InputException("Postcode should not be empty");
		
		if(this.salePrice== null || this.salePrice.isEmpty())
			throw new InputException("Sale price should not be empty");
		
		if(this.shippingPrice == null || this.shippingPrice.isEmpty())
			throw new InputException("Shipping price should not be empty");
		
		if(this.SKU == null || this.SKU.isEmpty())
			throw new InputException("SKU should not be empty");
		
		if(this.status == null || this.status.isEmpty())
			throw new InputException("Status should not be empty");
		
		if(!isValidEmailAddress(this.buyerEmail))
			throw new InputException("Not valid email");
		
		if(!isValidDateFormat("yyyy-MM-dd",this.orderDate,Locale.ENGLISH))
			throw new InputException("Not valid date format");
	
		if(!isValidInteger(this.postcode))
			throw new InputException("Not valid postcode");
		
		if(!isValidPrice(this.salePrice) || Double.parseDouble(this.salePrice) < 1.00 )
			throw new InputException("Not valid sale price");
		
		if(!isValidPrice(this.shippingPrice) || Double.parseDouble(this.shippingPrice) < 0.00 )
			throw new InputException("Not valid shipping price");
		
		if(!isValidStatus(this.status))
			throw new InputException("Not valid status");		
		
		return true;
	
	}
	
	private boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
	
	private boolean isValidDateFormat(String format, String value, Locale locale) {
	    LocalDateTime ldt = null;
	    DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

	    if(value.equals(""))
	    	return true;
	    
	    try {
	        ldt = LocalDateTime.parse(value, fomatter);
	        String result = ldt.format(fomatter);
	        return result.equals(value);
	    } catch (DateTimeParseException e) {
	        try {
	            LocalDate ld = LocalDate.parse(value, fomatter);
	            String result = ld.format(fomatter);
	            return result.equals(value);
	        } catch (DateTimeParseException exp) {
	            try {
	                LocalTime lt = LocalTime.parse(value, fomatter);
	                String result = lt.format(fomatter);
	                return result.equals(value);
	            } catch (DateTimeParseException e2) {
//	                System.out.println(e.getMessage());
	            }
	        }
	    }

	    return false;
	}
	
	private boolean isValidInteger(String postcode){
		try{
			Integer.parseInt(postcode);
		}catch(NumberFormatException e){
			return false;
		}
		
		return true;
	}
	
	private boolean isValidPrice(String price){
		try{
			Double.parseDouble(price);
		}catch(NumberFormatException e){
			return false;
		}
		
		return true;
	}
	
	private boolean isValidStatus(String status){
		if(status.equals(Status.IN_STOCK.name()) || status.equals(Status.OUT_OF_STOCK.name()))
			return true;
		
		return false;
	}
	
	public OrderItem parseIntoOrderItem() {
		OrderItem item = new OrderItem();
		
		item.setOrderId(Integer.parseInt(this.orderId));
		item.setOrderItemId(Integer.parseInt(this.orderItemId));
		item.setSalePrice(Double.parseDouble(this.salePrice));
		item.setShippingPrice(Double.parseDouble(this.shippingPrice));
		item.setSKU(this.SKU);
		item.setStatus(this.status);
		item.setTotalItemPrice(Double.parseDouble(this.salePrice) 
				+ Double.parseDouble(this.shippingPrice));
		
		return item;
	}

	public Order parseIntoOrder(List<InputFileLine> list) {
		Order item = new Order();
		
		item.setAddress(this.address);
		item.setBuyerEmail(this.buyerEmail);
		item.setBuyerName(this.buyerName);
		item.setOrderId(Integer.parseInt(this.orderId));
		item.setOrderTotalValue(calculateTotalItemPrice(this.orderId, this.orderItemId, list));
		item.setPostcode(Integer.parseInt(this.postcode));
		
		if(this.orderDate.isEmpty()){
			Calendar cal = Calendar.getInstance();
			item.setOrderDate(new Date(cal.getTimeInMillis()));
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsed;
			try {
				parsed = format.parse(this.orderDate);
				item.setOrderDate(new Date(parsed.getTime()));
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}			
		}
		
		return item;
		
	}

	private double calculateTotalItemPrice(String orderId, String orderItemId,  List<InputFileLine> inputFilelines) {
		double counter = 0;
		
		for(InputFileLine line : inputFilelines){
		
			try {
				
				if(line.isValid() && line.getOrderId().equals(orderId)){
					counter += Double.parseDouble(line.getSalePrice()) + Double.parseDouble(line.getShippingPrice());
					
				}
				
			} catch (InputException e) {
//				System.out.println(e.getMessage());
			}
		
		}
		
		return counter;
	}

	
}
