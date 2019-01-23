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
			
			// services only be provide from 7 to 22
			HttpRequest reqApi = new HttpRequest();
			int t = Integer.valueOf(reqApi.reqAPI("GET", "http://localhost:8888/RestProject/webapi/myresources/clock/Clock"));
			if ((t >= 7)& (t < 22)){
				// service 1 : if there is no one in the room, light go off
				if (id.equals("Sensor")){
					
					if (data.equals("off")){
					// turn off Light1-4
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light1/off");
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light2/off");
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light3/off");
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light4/off");
					}
					
				// if there is someone in the room, light turn on
					else if (data.equals("on")){
					// turn on Light1-4
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light1/on");
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light2/on");
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light3/on");
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light4/on");
					}
				}
	
				
				
				// service 3 : 
				//If the outside temperature is lower than the indoor temperature and 
				//the inside temperature is higher than 28°C and the outside temperature 
				//is between 17°C and lower than the inside temperature,
				//the windows should be opened automatically.
				if (id.equals("TempInt")){
					t = Integer.valueOf(reqApi.reqAPI("GET", "http://localhost:8888/RestProject/webapi/myresources/tempSensor/TempExt"));
					// check TempInt & TempExt 
					if ((Integer.valueOf(data) > 28) & (t>17) & (t<(Integer.valueOf(data))) ) {
					// turn on Window1-4
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window1/open");
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window2/open");
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window3/open");
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window4/open");
					// turn off Heating1
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/heating/Heating1/off");
					}
				
				// if the temperature is less than 10 the heating turns on 
				// and the windows are closed
					if ((Integer.valueOf(data) < 10)){
						// turn on Heating1
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/heating/Heating1/on");
						// close Window1-4
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window1/closed");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window2/closed");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window3/closed");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window4/closed");
					}
				}
			}else{
				// service 2 : 22h lights off 
				
				if ( id.equals("Clock") ){
				//close & turn off all 
					if (data.equals("22")){
						// turn off Light1-4
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light1/off");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light2/off");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light3/off");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/light/Light4/off");
						// close Window1-4
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window1/closed");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window2/closed");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window3/closed");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/window/Window4/closed");
						// close Door1-2
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/door/Door1/closed");
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/door/Door2/closed");
						// turn off heating
						reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/heating/Heating1/off");
					}
				}	
				//22h to 8h next day, if have person still in the room alarm turns on
				if ( (id.equals("Sensor") & data.equals("on")) ){
					reqApi.reqAPI("POST", "http://localhost:8888/RestProject/webapi/myresources/alarm/Alarm1/on");
				}
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
