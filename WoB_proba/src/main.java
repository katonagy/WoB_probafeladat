import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.text.SimpleDateFormat;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.sun.corba.se.pept.encoding.InputObject;

import database.DBManager;
import model.DBParameters;
import model.FTPParameters;
import model.InputException;
import model.InputFileLine;
import model.Order;
import model.OrderItem;
import model.ResponseFileLine;

public class main {

	private static DBParameters mariaDB;
	private static FTPParameters ftp;
	private static List<InputFileLine> inputFilelines;
	private static List<ResponseFileLine> responseFileLines;
	
	public static void main(String[] args) {
		readParameters("Connection.properties");
		inputFilelines = readInputFile("inputFile.csv");		
		responseFileLines = new ArrayList<>();
		
		DBManager.getConnection(mariaDB.getIp(), mariaDB.getPort(), mariaDB.getUserName(), mariaDB.getPassword());
		
		for (InputFileLine inputFileLine : inputFilelines) {
			try {
				inputFileLine.isValid();
				
				OrderItem orderItem = parseIntoOrderItem(inputFileLine);
				Order order = parseIntoOrder(inputFileLine);
				
				DBManager.insertOrderIntoTable(order);		
				DBManager.insertOrderItemIntoTable(orderItem);		
				
				addResponseFileLine(inputFileLine, "", Status.OK);
				
				
			} catch (InputException e) {			
				addResponseFileLine(inputFileLine, e.getMessage(), Status.ERROR);
						
			} catch (SQLException e) {
				addResponseFileLine(inputFileLine, e.getMessage(), Status.ERROR);
			}
		}			

		DBManager.closeConnection();		
		
		createResponseFile(responseFileLines);
		
	}
	
	private static void addResponseFileLine(InputFileLine inputFileLine, String message, Status status) {
		ResponseFileLine line = new ResponseFileLine();
		line.setLineNumber(Integer.parseInt(inputFileLine.getLineNumber()));
		line.setMessage(message);
		line.setStatus(status.name());
		
		responseFileLines.add(line);		
	}

	private static OrderItem parseIntoOrderItem(InputFileLine inputFileLine) {
		OrderItem item = new OrderItem();
		
		item.setOrderId(Integer.parseInt(inputFileLine.getOrderId()));
		item.setOrderItemId(Integer.parseInt(inputFileLine.getOrderItemId()));
		item.setSalePrice(Double.parseDouble(inputFileLine.getSalePrice()));
		item.setShippingPrice(Double.parseDouble(inputFileLine.getShippingPrice()));
		item.setSKU(inputFileLine.getSKU());
		item.setStatus(inputFileLine.getStatus());
		item.setTotalItemPrice(Double.parseDouble(inputFileLine.getSalePrice()) 
				+ Double.parseDouble(inputFileLine.getShippingPrice()));
		
		return item;
	}

	private static Order parseIntoOrder(InputFileLine inputFileLine) {
		Order item = new Order();
		
		item.setAddress(inputFileLine.getAddress());
		item.setBuyerEmail(inputFileLine.getBuyerEmail());
		item.setBuyerName(inputFileLine.getBuyerName());
		item.setOrderId(Integer.parseInt(inputFileLine.getOrderId()));
		item.setOrderTotalValue(calculateTotalItemPrice(inputFileLine.getOrderId()));
		item.setPostcode(Integer.parseInt(inputFileLine.getPostcode()));
		
		if(inputFileLine.getOrderDate().isEmpty()){
			Calendar cal = Calendar.getInstance();
			item.setOrderDate(new Date(cal.getTimeInMillis()));
		}else{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsed;
			try {
				parsed = format.parse(inputFileLine.getOrderDate());
				item.setOrderDate(new Date(parsed.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}			
		}
		
		return item;
		
	}

	private static double calculateTotalItemPrice(String orderId) {
		double counter = 0;
		
		for(InputFileLine line : inputFilelines){
			
			try {
				
				if(line.isValid() && line.getOrderId().equals(orderId))
					counter += (Double.parseDouble(line.getSalePrice()) + Double.parseDouble(line.getShippingPrice()));
				
			} catch (InputException e) {
				e.printStackTrace();
			}
			
		}
		
		return counter;
	}

	private enum Status{
		ERROR, OK
	}
	
	private static void readParameters(String fileName) {
		mariaDB = new DBParameters();
		ftp = new FTPParameters();

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
			while (scanner.hasNextLine()) {

				String[] line = scanner.nextLine().split("=");
				String key = line[0];
				String value = line[1];

				switch (key) {
				case "DBIP":
					mariaDB.setIp(value);
					break;
				case "DBport":
					mariaDB.setPort(value);
					break;
				case "DBusername":
					mariaDB.setUserName(value);
					break;
				case "DBpassword":
					mariaDB.setPassword(value);
					break;
				case "FTPIP":
					ftp.setIp(value);
					break;
				case "FTPuser":
					ftp.setUserName(value);
					break;
				case "FTPpass":
					ftp.setPassword(value);
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}

	}

	private static List<InputFileLine> readInputFile(String fileName) {
		Scanner scanner = null;
		List<InputFileLine> listOfLines = new ArrayList<>();

		try {
			scanner = new Scanner(new File(fileName));
			scanner.nextLine();

			while (scanner.hasNextLine()) {

				String[] line = scanner.nextLine().split(";");

				InputFileLine item = new InputFileLine();
				item.setLineNumber(line[0]);
				item.setOrderItemId(line[1]);
				item.setOrderId(line[2]);
				item.setBuyerName(line[3]);
				item.setBuyerEmail(line[4]);
				item.setAddress(line[5]);
				item.setPostcode(line[6]);
				item.setSalePrice(line[7]);
				item.setShippingPrice(line[8]);
				item.setSKU(line[9]);
				item.setStatus(line[10]);
				
				if(line.length == 11)
					item.setOrderDate("");
				else
					item.setOrderDate(line[11]);

				listOfLines.add(item);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}

		return listOfLines;

	}	
	
	private static void createResponseFile(List<ResponseFileLine> list) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("responseFile.csv"));
			bw.write(String.format("LineNumber;Status;Message%n"));
			for (ResponseFileLine item : list) {
				bw.write(String.format("%d;%s;%s\n", item.getLineNumber(), item.getMessage(), item.getStatus() ));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e){
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
