import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.DBParameters;
import model.FTPParameters;

public class main {

	private static DBParameters mariaDB;
	private static FTPParameters ftp;
	
	private static void readParameters(String fileName){
		mariaDB = new DBParameters();
		ftp = new FTPParameters();
		
		Scanner scanner = null;
		try {
			 scanner = new Scanner(new File(fileName));	
			 while(scanner.hasNextLine()){
				 
				 String[] line = scanner.nextLine().split("=");
				 String key = line[0];
				 String value = line[1];
				 
				 switch(key){
				 	case "DBIP": mariaDB.setIp(value); 
					 	break;
				 	case "DBport": mariaDB.setPort(value);
				 		break;
				 	case "DBusername": mariaDB.setUserName(value);
				 		break;
				 	case "DBpassword": mariaDB.setPassword(value);
				 		break;
				 	case "FTPIP": ftp.setIp(value);
				 		break;
				 	case "FTPuser": ftp.setUserName(value);
				 		break;
				 	case "FTPpass": ftp.setPassword(value);	
				 		break;			
				 }		
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(scanner != null)
				scanner.close();
		}	
		
	}
	
	public static void main(String[] args) {
		readParameters("Connection.properties");
		
		
		
	}

}
