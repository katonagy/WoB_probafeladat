import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.sun.corba.se.pept.encoding.InputObject;

import model.DBParameters;
import model.FTPParameters;
import model.InputException;
import model.InputFileLine;

public class main {

	private static DBParameters mariaDB;
	private static FTPParameters ftp;

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

	public static void main(String[] args) {
		readParameters("Connection.properties");
		List<InputFileLine> lines = readInputFile("inputFile.csv");		
		
		for (InputFileLine inputFileLine : lines) {
			try {
				
				System.out.println(inputFileLine);
				inputFileLine.isValid();
				
			} catch (InputException e) {
				System.out.println(e.getMessage());
			}
		}			

	}

}
