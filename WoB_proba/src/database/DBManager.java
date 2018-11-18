package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.InputException;
import model.Order;
import model.OrderItem;

public class DBManager {

	private static Connection conn = null;

	public static Connection getConnection(String IP, String port, String username, String password) {
		if (conn == null) {
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://" + IP + ":" + port, username, password);
				System.out.println("Connected to database successfully...");
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				System.out.println("Disconnected from DB!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
	
	public static void insertOrderIntoTable(Order order) throws SQLException {

		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO PROBA.ORDER"
				+ "(OrderId, BuyerName, BuyerEmail, OrderDate, OrderTotalValue, Address, PostCode) VALUES"
				+ "(?,?,?,?,?,?,?)";

		try {
			preparedStatement = conn.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, order.getOrderId());
			preparedStatement.setString(2, order.getBuyerName());
			preparedStatement.setString(3, order.getBuyerEmail());
			preparedStatement.setDate(4, order.getOrderDate());
			preparedStatement.setDouble(5, order.getOrderTotalValue());
			preparedStatement.setString(6, order.getAddress());
			preparedStatement.setInt(7, order.getPostcode());

			// execute insert SQL statement
			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into Order table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e.getMessage());
		} catch (NullPointerException e){
			System.out.println(e.getMessage());
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}

	}
	
	public static void insertOrderItemIntoTable(OrderItem orderItem) throws SQLException {

		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO PROBA.ORDER_ITEM"
				+ "(OrderItemId, OrderId, SalePrice, ShippingPrice, TotalItemPrice, SKU, Status) VALUES"
				+ "(?,?,?,?,?,?,?)";

		try {
			preparedStatement = conn.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, orderItem.getOrderItemId());
			preparedStatement.setInt(2, orderItem.getOrderId());
			preparedStatement.setDouble(3, orderItem.getSalePrice());
			preparedStatement.setDouble(4, orderItem.getShippingPrice());
			preparedStatement.setDouble(5, orderItem.getTotalItemPrice());
			preparedStatement.setString(6, orderItem.getSKU());
			preparedStatement.setString(7, orderItem.getStatus());

			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into OrderItem table!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}

	}

	
	
	
}
