package simplest.soap.client;

/* SocketRequestResponseServer.java
 * Copyright (c) HerongYang.com. All Rights Reserved.
 */
import java.io.*;
import java.net.*;

public class SocketRequestResponseServer {

	public static void main(String[] args) {
		PrintStream out = System.out;

// Checking command line arguments
		if (args.length < 2) {
			out.println("Usage:");
			out.println("java SocketRequestResponseServer inFile outFile");
			return;
		}
		String inFile = args[0];
		String outFile = args[1];

		try {
// Reading the response from a file
			File objFile = new File(inFile);
			int resLen = (int) objFile.length();
			byte[] resBytes = new byte[resLen];
			FileInputStream inStream = new FileInputStream(objFile);
			inStream.read(resBytes);
			inStream.close();

// Creating socket socket
			ServerSocket server = new ServerSocket(8888);
			out.println("Listening at 8888");
			Socket con = server.accept();
			out.println("Connection received from " + con.getInetAddress().toString());

// Receiving the request
			InputStream reqStream = con.getInputStream();
			byte[] reqBytes = new byte[10240];
			int reqLen = reqStream.read(reqBytes);

// Sending the response
			OutputStream resStream = con.getOutputStream();
			resStream.write(resBytes);

			reqStream.close();
			resStream.close();

// Writing the request to a file 
			FileOutputStream outStream = new FileOutputStream(outFile);
			outStream.write(reqBytes, 0, reqLen);
			outStream.close();

// Output counts
			out.println("Request length: " + reqLen);
			out.println("Response length: " + resLen);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
