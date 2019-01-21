package fr.insat.om2m.tp2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.om2m.commons.resource.Notification;

import fr.insat.om2m.tp2.mapper.Mapper;
import httpRequest.HttpRequest;

public class HttpServer {

	private static Mapper mapper = new Mapper();

	private static int PORT = 1400;
	private static String CONTEXT = "/monitor";

	/**
	 * Get the payload as string
	 * 
	 * @param bufferedReader
	 * @return payload as string
	 */
	public static String getPayload(BufferedReader bufferedReader) {
		Scanner sc = new Scanner(bufferedReader);
		String payload = "";
		while (sc.hasNextLine()) {
			payload += sc.nextLine() + "\n";
		}
		sc.close();
		return payload;
	}

	public static class MonitorServlet extends HttpServlet {

		private static final long serialVersionUID = 2302036096330714914L;

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			String payload = getPayload(req.getReader());
			System.out.println("Subscription received with payload:\n"+ payload);
			// unmarshalling the notification
			Notification notif = (Notification) mapper.unmarshal(payload);
			
			//get id of the sensor
			String id = notif.getSubscriptionReference().replace("/in-cse/in-name/","");
			id = id.replace("/DATA/SUBSCRIPTION", "");
			System.out.println(id);
			//get the data
			String data = payload.replaceAll("(.)*[^con]>","").replaceAll("\n","").replaceAll(" ","");			
			data = data.replace("</m2m:sgn>","");
			data = data.replace("<con>","").replace("</con>","");
			System.out.println(data);		
			
			/*if (notif.isVerificationRequest()){
				System.out.println("Received verification request");
				return;
			}*/
			
			// services :) finally !!!
			// we do it static : change services by hands :(
			
			// service 1 : if there is no one in the room, light go off
			if (id.equals("Sensor") & data.equals("off")){
				HttpRequest reqApi = new HttpRequest();
				// turn off Light1-4
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light1/off");
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light2/off");
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light3/off");
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light4/off");
			}
			
			// service 2 : 22h lights off, if have person still in the room alarm turns on
			//
			if ( (id.equals("Sensor") & data.equals("on")) /* à finir */){
				HttpRequest reqApi = new HttpRequest();
				// turn on Alarm1
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/alarm/Alarm1/on");
			}
			
			// service 3 : if the temperature is more than 20 all the windows open; 
			// 			   if the temperature is less than 20 the heating turns on
			if (id.equals("TempInt") & (Integer.valueOf(data) > 20)){
				HttpRequest reqApi = new HttpRequest();
				// turn on Window1-4
				System.out.println("open the windows");
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window1/open");
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window2/open");
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window3/open");
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window4/open");
			}
			if (id.equals("TempInt") & (Integer.valueOf(data) < 20)){
				HttpRequest reqApi = new HttpRequest();
				// turn on Heating1
				System.out.println("turn on the heating");
				reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/heating/Heating1/on");
			}
			
			resp.setStatus(HttpServletResponse.SC_OK);
		}

	}

	public static void main(String[] args) throws Exception {
		
		// start the server
		Server server = new Server(PORT);
		ServletHandler servletHandler = new ServletHandler();
		
		// add servlet and context
		servletHandler.addServletWithMapping(MonitorServlet.class, CONTEXT);
		server.setHandler(servletHandler);
		server.start();
		server.join();
		
	}

}
