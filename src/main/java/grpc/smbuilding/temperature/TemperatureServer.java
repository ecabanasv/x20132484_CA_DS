package grpc.smbuilding.temperature;

import grpc.smbuilding.temperature.TemperatureServiceGrpc.TemperatureServiceImplBase;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import io.grpc.Server;
import io.grpc.ServerBuilder;
//import io.grpc.stub.StreamObserver;
import io.grpc.stub.StreamObserver;

public class TemperatureServer extends TemperatureServiceImplBase {

	public static void main(String[] args) {

		TemperatureServer temperatureserver = new TemperatureServer();

		Properties prop = temperatureserver.getProperties();

		temperatureserver.registerService(prop);

		int port = Integer.valueOf(prop.getProperty("service_port"));// #.50051;

		try {

			Server server = ServerBuilder.forPort(port).addService(temperatureserver).build().start();

			System.out.println("Temperature Server started, listening on " + port);

			server.awaitTermination();

		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

	private Properties getProperties() {

		Properties prop = null;

		try (InputStream input = new FileInputStream("src/main/resources/temperature/temperature.properties")) {

			prop = new Properties();

			// load a properties file
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return prop;
	}

	private void registerService(Properties prop) {

		try {
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			String service_type = prop.getProperty("service_type");// "_http._tcp.local.";
			String service_name = prop.getProperty("service_name");// "example";
			// int service_port = 1234;
			int service_port = Integer.valueOf(prop.getProperty("service_port"));// #.50051;

			String service_description_properties = prop.getProperty("service_description");// "path=index.html";

			// Register a service
			ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port,
					service_description_properties);
			jmdns.registerService(serviceInfo);

			System.out.printf("Registering service with type %s and name %s \n", service_type, service_name);

			// Wait a bit
			Thread.sleep(1000);

			// Unregister all services
			// jmdns.unregisterAllServices();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public StreamObserver<CheckTemperatureRequest> checkTemperature(StreamObserver<CheckTemperatureResponse> responseObserver) {
		
		return new StreamObserver<CheckTemperatureRequest>() {
			
			float sumTemperature = 0;
			
			float averageTemperature;
			
			int counter = 0;
			
			@Override
			public void onNext(CheckTemperatureRequest value) {
				
				System.out.println("Receiving temperature of Room Nº: " + value.getRoom());
				
		        //JSON parser object to parse read file
		        JSONParser jsonParser = new JSONParser();
		        
		        try (FileReader reader = new FileReader("src/main/resources/rooms.json"))
		        {
		            //Read JSON file
		            Object obj = jsonParser.parse(reader);
		            
		            JSONObject roomsList = (JSONObject)obj;
		            
		            JSONArray roomsArray = (JSONArray)roomsList.get("rooms");
		            
					for (int i = 0; i<roomsArray.size(); i++)
					{
						JSONObject room = (JSONObject)roomsArray.get(i);
						
						int id = Integer.parseInt(room.get("id").toString());
						
						int temperature = Integer.parseInt(room.get("temperature").toString());
									
						if (id == value.getRoom()) {
							
							sumTemperature += temperature;
							
						}
					}
		 
		        } catch (FileNotFoundException e) {
		        	
		            e.printStackTrace();
		            
		        } catch (IOException e) {
		        	
		            e.printStackTrace();
		            
		        } catch (ParseException e) {
		        	
		            e.printStackTrace();
		            
		        }
				counter++;
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCompleted() {
				
				averageTemperature = sumTemperature / counter;
				
				CheckTemperatureResponse res = CheckTemperatureResponse.newBuilder().setAverage(averageTemperature).build();
				
		        responseObserver.onNext(res);
		          
		        responseObserver.onCompleted();
			}
			
			
		};
	}
	
	 @Override
	 public void temperatureReport(TemperatureReportRequest request, StreamObserver<TemperatureReportResponse> responseObserver) {
		 
		    System.out.println("\nReceiving room temperature report...");
			
	        //JSON parser object to parse read file
	        JSONParser jsonParser = new JSONParser();
	        
	        try (FileReader reader = new FileReader("src/main/resources/rooms.json"))
	        {
	            //Read JSON file
	            Object obj = jsonParser.parse(reader);
	            
	            JSONObject roomsList = (JSONObject)obj;
	            
	            JSONArray roomsArray = (JSONArray)roomsList.get("rooms");
	            
				for (int i = 0; i<roomsArray.size(); i++)
				{
					JSONObject room = (JSONObject)roomsArray.get(i);
					
					int id = Integer.parseInt(room.get("id").toString());
					
					String temperature = String.valueOf(room.get("temperature").toString());
					
						
						String result = "Room (" + id + "): " + temperature + " Celsius";
						
						responseObserver.onNext(TemperatureReportResponse.newBuilder().setResult(result).build());
						
					
				}
				
			responseObserver.onCompleted();
	 
	        } catch (FileNotFoundException e) {
	        	
	            e.printStackTrace();
	            
	        } catch (IOException e) {
	        	
	            e.printStackTrace();
	            
	        } catch (ParseException e) {
	        	
	            e.printStackTrace();
	            
	        }
	}
}
