package grpc.smbuilding.occupancy;

import grpc.smbuilding.occupancy.OccupancyServiceGrpc.OccupancyServiceImplBase;

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

public class OccupancyServer extends OccupancyServiceImplBase {

	public static void main(String[] args) {

		OccupancyServer occupancyserver = new OccupancyServer();

		Properties prop = occupancyserver.getProperties();

		occupancyserver.registerService(prop);

		int port = Integer.valueOf(prop.getProperty("service_port"));// #.50051;

		try {

			Server server = ServerBuilder.forPort(port).addService(occupancyserver).build().start();

			System.out.println("Occupancy Server started, listening on " + port);

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

		try (InputStream input = new FileInputStream("src/main/resources/occupancy/occupancy.properties")) {

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

	public StreamObserver<OccupancyManyRequest> occupancyRooms(StreamObserver<OccupancyManyResponse> responseObserver) {
		
		return new StreamObserver<OccupancyManyRequest>() {

			@Override
			public void onNext(OccupancyManyRequest request) {
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
						
						String occupancy = String.valueOf(room.get("occupancy").toString());
						
						
						if (id == request.getRoom())
						{
							String result = "Request received... Occupancy of Room (" + id + ") is at " + occupancy + "%";
							
				            OccupancyManyResponse reply = OccupancyManyResponse.newBuilder().setResult(result).build();
						      
				            responseObserver.onNext(reply);
						}
					}	
					
		 
		        } catch (FileNotFoundException e) {
		        	
		            e.printStackTrace();
		            
		        } catch (IOException e) {
		        	
		            e.printStackTrace();
		            
		        } catch (ParseException e) {
		        	
		            e.printStackTrace();
		            
		        }
		        
				
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stubal
				
			}

			@Override
			public void onCompleted() {
				
				 responseObserver.onCompleted();
				
			}
			
		};
	}
	
}
