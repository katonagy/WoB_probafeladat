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

import ftp.FTPUpload;
import database.DBManager;
import model.DBParameters;
import model.FTPParameters;
import model.InputException;
import model.InputFileLine;
import model.Order;
import model.OrderItem;
import model.ResponseFileLine;
import model.ResponseFileLineList;
import model.Status;

public class main {

	private static DBParameters mariaDB;
	private static FTPParameters ftp;
	
	public static void main(String[] args) {
		readParameters("Connection.properties");
		List<InputFileLine> inputFilelines = readInputFile("inputFile.csv");		
		ResponseFileLineList responseFileList = new ResponseFileLineList();
		OrderItem orderItem = null;
		Order order = null;
		
		DBManager.getConnection(mariaDB.getIp(), mariaDB.getPort(), mariaDB.getUserName(), mariaDB.getPassword());
		
		for (InputFileLine inputFileLine : inputFilelines) {
			try {
				inputFileLine.isValid();
				
				orderItem = inputFileLine.parseIntoOrderItem();
				order = inputFileLine.parseIntoOrder(inputFilelines);
				
			} catch (InputException e) {			
				responseFileList.addResponseFileLine(inputFileLine, e.getMessage(), Status.ERROR);
				System.out.println(e.getMessage());
				continue;
						
			}
			
			try {
				DBManager.insertOrderIntoTable(order);
			} catch (SQLException e) {
				if(!e.getMessage().contains("Duplicate")){
					responseFileList.addResponseFileLine(inputFileLine, e.getMessage(), Status.ERROR);
					System.out.println(e.getMessage());
					continue;
				}
			}					
			
			try {
				DBManager.insertOrderItemIntoTable(orderItem);
			} catch (SQLException e) {
				if(e.getMessage().contains("Duplicate"))
					responseFileList.addResponseFileLine(inputFileLine, "Duplicated OrderItem", Status.ERROR);					
				else
					responseFileList.addResponseFileLine(inputFileLine, e.getMessage(), Status.ERROR);
				
				continue;
			}
			
			responseFileList.addResponseFileLine(inputFileLine, "", Status.OK);
			
		}			

		DBManager.closeConnection();		
		
		createResponseFile(responseFileList.getList());
		
		FTPUpload.upload(ftp.getIp(), ftp.getUserName(), ftp.getPassword(), "responseFile.csv");
		
	}
	
	public static void readParameters(String fileName) {
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

	public static List<InputFileLine> readInputFile(String fileName) {
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
		} catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}

		return listOfLines;

	}	
	
	public static void createResponseFile(List<ResponseFileLine> list) {
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
