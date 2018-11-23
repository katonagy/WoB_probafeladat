package ftp;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class FTPUpload {
	
	private static FTPClient client = new FTPClient();
	private static FileInputStream fis = null;

	public static void upload(String IP, String user, String pass, String filename) {
		try {
			client.connect(IP);
			client.login(user, pass);

			fis = new FileInputStream(filename);

			client.storeFile(filename, fis);
			client.logout();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				client.disconnect();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

	}

}
