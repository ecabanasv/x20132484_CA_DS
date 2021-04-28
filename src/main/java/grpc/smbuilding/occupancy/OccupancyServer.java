package grpc.smbuilding.occupancy;

// Generic Libraries
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.InetAddress;
import java.util.Properties;

// jmDNS Libraries
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

// JSONSimple Libraries
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// gRPC Libraries
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import grpc.smbuilding.occupancy.OccupancyServiceGrpc.OccupancyServiceImplBase;

public class OccupancyServer extends OccupancyServiceImplBase {

	public static void main(String[] args) {

		OccupancyServer occupancyserver = new OccupancyServer();

		Properties prop = occupancyserver.getProperties();

		occupancyserver.registerService(prop);

		int port = Integer.valueOf(prop.getProperty("service_port"));// #.50052;

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

	// Get properties from occupancy.properties file
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

	// Register Occupancy service (port: 50052)
	private void registerService(Properties prop) {

		try {
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			String service_type = prop.getProperty("service_type");
			
			String service_name = prop.getProperty("service_name");
			
			int service_port = Integer.valueOf(prop.getProperty("service_port"));// #.50052;

			String service_description_properties = prop.getProperty("service_description");

			// Register a service
			ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port, service_description_properties);
			
			jmdns.registerService(serviceInfo);

			System.out.printf("Registering service with type %s and name %s \n", service_type, service_name);

			Thread.sleep(1000);

		} catch (IOException e) {
			
			System.out.println(e.getMessage());
			
		} catch (InterruptedException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// occupancyRooms function (BiDi streaming)
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
							String result = "Occupancy of Room (" + id + ") is at " + occupancy + "%";
							
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
