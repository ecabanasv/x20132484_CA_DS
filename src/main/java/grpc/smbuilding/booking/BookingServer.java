package grpc.smbuilding.booking;

// Generic Libraries
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
import grpc.smbuilding.booking.BookingServiceGrpc.BookingServiceImplBase;

public class BookingServer extends BookingServiceImplBase {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		BookingServer bookingserver = new BookingServer();
		
		// Get services properties from /smbuilding/src/main/resources/booking/booking.properties
		Properties prop = bookingserver.getProperties();

		// Register BookingService
		bookingserver.registerService(prop);

		int port = Integer.valueOf(prop.getProperty("service_port"));// #.50051;

		try {

			// Add BookingService
			Server server = ServerBuilder.forPort(port).addService(bookingserver).build().start();

			System.out.println("\nBooking Server started, listening on " + port);

			server.awaitTermination();

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (InterruptedException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
	}

	// Get properties from booking.properties file
	private Properties getProperties() {

		Properties prop = null;

		try (InputStream input = new FileInputStream("src/main/resources/booking/booking.properties")) {

			prop = new Properties();

			// load a properties file
			prop.load(input);

		} catch (IOException ex) {

			ex.printStackTrace();
		}

		return prop;
	}

	// Register Booking service (port 50051)
	private void registerService(Properties prop) {

		try {
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			String service_type = prop.getProperty("service_type");
			
			String service_name = prop.getProperty("service_name");
			
			int service_port = Integer.valueOf(prop.getProperty("service_port"));// #.50051;

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

	// Booking function (Unary)
	public void booking(BookingRequest request, StreamObserver<BookingResponse> responseObserver) {
		
		String value = "";
		
		int numRoom = request.getNumRoom();
	
		System.out.println("Receiving booking request for Room Nº " + numRoom);
		
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
				
				int occupancy = Integer.parseInt(room.get("occupancy").toString());
				
				if (id == numRoom && occupancy < 100) {
					
					value = "Room " + id + ": Free slot for booking. Occupancy of the room is " + occupancy +"%";
					
					id = 0;
					
					break;
				}
				else if(id == numRoom && occupancy == 100)
				{
					
					value = "Room " + id + ": is fully booked. Occupancy of the room is " + occupancy +"%";
					
					id = 0;
					
				}
			}
		
		BookingResponse reply = BookingResponse.newBuilder().setResult(value).build();
		
		responseObserver.onNext(reply);
		
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
