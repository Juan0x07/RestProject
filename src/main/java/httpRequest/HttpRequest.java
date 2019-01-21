package httpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpRequest {
	
	// read respense
	public String response(HttpURLConnection con) throws IOException{
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		//close buffer
		in.close();
		//close connection
		con.disconnect();
		return content.toString();
	}
	
	// set body
	public void setBody(HttpURLConnection con, String representation) throws IOException{
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");    
		osw.write(representation);
		osw.flush();
		osw.close();
		os.close(); 
	}

	// create a get request (need the url of resource) -- retrieve 
	public String getReq(String url) throws IOException{
		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		//set method: GET
		con.setRequestMethod("GET");
		//set headers
		con.setRequestProperty("X-M2M-Origin", "admin:admin");
		con.setRequestProperty("Accept", "application/xml");
		//send request
		int status = con.getResponseCode();
		//read response
		return response(con);
	}

	
	// create a post request (need the type of resource) -- create
	public String postReq(String url, String type, String representation) throws IOException{
		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		//set method: POST
		con.setRequestMethod("POST");
		//set headers
		con.setRequestProperty("X-M2M-Origin", "admin:admin");
		con.setRequestProperty("Content-Type", "application/xml;ty="+type);
		//set body
		setBody(con,representation);
		//send request
		int status = con.getResponseCode();
		//read response
		return response(con);
	}
	
	// create a put request (need the type of resource) -- update
		public String putReq(String url, String representation) throws IOException{
			URL u = new URL(url);
			HttpURLConnection con = (HttpURLConnection) u.openConnection();
			//set method: POST
			con.setRequestMethod("PUT");
			//set headers
			con.setRequestProperty("X-M2M-Origin", "admin:admin");
			con.setRequestProperty("Content-Type", "application/xml");
			//set body
			setBody(con,representation);
			//send request
			int status = con.getResponseCode();
			//read response
			return response(con);
		}
	
	// create a delete request (need the url of resource) -- delete
		public String deleteReq(String url) throws IOException{
			URL u = new URL(url);
			HttpURLConnection con = (HttpURLConnection) u.openConnection();
			//set method: POST
			con.setRequestMethod("DELETE");
			//set headers
			con.setRequestProperty("X-M2M-Origin", "admin:admin");
			con.setRequestProperty("Content-Type", "application/xml");			
			//send request
			int status = con.getResponseCode();
			//read response
			return response(con);
		}
		
	// requests for monitor to provide services
		// create a post request (need the type of resource) -- create
		public String reqAPI(String method, String url) throws IOException{
			URL u = new URL(url);
			HttpURLConnection con = (HttpURLConnection) u.openConnection();
			//set method
			con.setRequestMethod(method);
			//send request
			int status = con.getResponseCode();
			//read response
			return response(con);
		}
}
