package com.hamaluik.po8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HTTPGet {
	private HTTPGet() {}
	
	public static String getURL(String url) {
		// all the variables we'll need
		HttpURLConnection connection = null;
		BufferedReader rd  = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		URL serverAddress = null;

		try {
			serverAddress = new URL(url);
	
			// open the connection
			// using GET request
			connection = (HttpURLConnection)serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
	
			connection.connect();
	
			//read the result from the server
			rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = rd.readLine()) != null) {
				sb.append(line + '\n');
			}
		}
		// catch the errors
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (ProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		//close the connection
		connection.disconnect();
		
		// and return the results!
		return sb.toString();
	}
}
