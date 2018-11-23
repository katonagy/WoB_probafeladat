package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.InputException;
import model.InputFileLine;
import model.Order;
import model.OrderItem;

public class InputFileLineTest {

	@Test
	public void TestEmail() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Not valid email", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptyLineNumber() {
		InputFileLine item = new InputFileLine();
		
//		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Line number should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptyOrderItemId() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
//		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Order item id should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptyOrderId() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
//		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Order id should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptyBuyerName() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
//		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Buyer name should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptyBuyerEmail() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
//		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Buyer email should not be empty", e.getMessage());
		}		
	}

	@Test
	public void TestEmptyAddress() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
//		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Address should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptyPostcode() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
//		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Postcode should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptySalePrice() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
//		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Sale price should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptyShippingPrice() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
//		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Shipping price should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptySKU() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
//		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("SKU should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestEmptyStatus() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
//		item.setStatus("IN_STOCK");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Status should not be empty", e.getMessage());
		}		
	}
	
	@Test
	public void TestSalePricePositiveValue() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("-5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		item.setOrderDate("2018-11-22");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Not valid sale price", e.getMessage());
		}		
	}
	
	@Test
	public void TestShippingPricePositiveValue() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("-500.00");
		item.setSKU("AJKDF");
		item.setStatus("IN_STOCK");
		item.setOrderDate("2018-11-22");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Not valid shipping price", e.getMessage());
		}		
	}
	
	@Test
	public void TestStatusValue() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("OUT");
		item.setOrderDate("2018-11-22");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Not valid status", e.getMessage());
		}		
	}
	
	@Test
	public void TestDateValue() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("OUT");
		item.setOrderDate("2018.11.22");
		
		try{
			item.isValid();
		} catch (InputException e) {
			assertEquals("Not valid date format", e.getMessage());
		}		
	}
	
	@Test
	public void TestcreateOrder() {
		InputFileLine item = new InputFileLine();
		List<InputFileLine> list = new ArrayList<>();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("OUT");
		item.setOrderDate("2018-11-22");
		list.add(item);
		
		Order order =  item.parseIntoOrder(list);
		
		assertNotNull(order);
				
	}
	
	@Test
	public void TestCreateOrderItem() {
		InputFileLine item = new InputFileLine();
		
		item.setLineNumber("1");
		item.setOrderItemId("1");
		item.setOrderId("1");
		item.setBuyerName("Kiss Tibor");
		item.setBuyerEmail("kiss.tibor@ds.com");
		item.setAddress("Pécs, Tettye urca 45.");
		item.setPostcode("7626");
		item.setSalePrice("5000.00");
		item.setShippingPrice("500.00");
		item.setSKU("AJKDF");
		item.setStatus("OUT");
		item.setOrderDate("2018-11-22");
		
		OrderItem orderItem =  item.parseIntoOrderItem();
		
		assertNotNull(orderItem);
				
	}
	
	
}
