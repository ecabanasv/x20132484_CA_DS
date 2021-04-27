package grpc.smbuilding.occupancy;

// AWT Libraries
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Swing Libraries
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Libraries
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import javax.net.ssl.SSLException;

// jmDNS Libraries
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

// gRPC Libraries
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class OccupancyGUI extends JFrame {
	
	// For use JFrame from MasterGUI
	private static final long serialVersionUID = 1L;
	
	// Blockingstub gRPC
	private static OccupancyServiceGrpc.OccupancyServiceStub asyncStub;
	
	private static String result = "";
	
	private ServiceInfo occupancyServiceInfo;
	
	private JFrame frame;
	
	private JTextArea textResponse;
	
	public static void main(String[] args) throws SSLException, InterruptedException {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					OccupancyGUI window = new OccupancyGUI();
					
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
			}
		});
	}
	
	// Constructor OccupancyGUI
	public OccupancyGUI() {
		
		String occupancy_service_type = "_occupancy._tcp.local.";
		
		discoverOccupancyService(occupancy_service_type);
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052).usePlaintext().build();

		asyncStub = OccupancyServiceGrpc.newStub(channel);

		initialize();	

	}

	// Discover OccupancyService
	private void discoverOccupancyService(String service_type) {
		
		try {
			
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			jmdns.addServiceListener(service_type, new ServiceListener() {
				
				@Override
				public void serviceResolved(ServiceEvent event) {
					
					System.out.println("Occupancy Service resolved: " + event.getInfo());

					occupancyServiceInfo = event.getInfo();

					int port = occupancyServiceInfo.getPort();
					
					System.out.println("resolving " + service_type + " with properties ...");
					
					System.out.println("\t port: " + port);
					
					System.out.println("\t type:"+ event.getType());
					
					System.out.println("\t name: " + event.getName());
					
					System.out.println("\t description/properties: " + occupancyServiceInfo.getNiceTextString());
					
					System.out.println("\t host: " + occupancyServiceInfo.getHostAddresses()[0]);	
				}
				
				@Override
				public void serviceRemoved(ServiceEvent event) {
					
					System.out.println("Occupancy Service removed: " + event.getInfo());
					
				}
				
				@Override
				public void serviceAdded(ServiceEvent event) {
					
					System.out.println("Occupancy Service added: " + event.getInfo());
					
				}
			});
			
			// Wait a bit
			Thread.sleep(2000);
			
			jmdns.close();

		} catch (UnknownHostException e) {
			
			System.out.println(e.getMessage());
			
		} catch (IOException e) {
			
			System.out.println(e.getMessage());
			
		} catch (InterruptedException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	// Interface
	private void initialize() {
		
		frame = new JFrame();
		
		frame.setTitle("Service: Occupancy report");
		
		frame.setBounds(100, 100, 500, 300);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BoxLayout bl = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		
		frame.getContentPane().setLayout(bl);
		
		JPanel panel_service_1 = new JPanel();
		
		frame.getContentPane().add(panel_service_1);
		
		panel_service_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
		JButton btnCalculate = new JButton("Request Occupancy Report");
		
		btnCalculate.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				occupancyRooms();
				
				textResponse.append(result);

			}
		});
		
		panel_service_1.add(btnCalculate);
		
		textResponse = new JTextArea(12, 40);
		
		textResponse .setLineWrap(true);
		
		textResponse.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane(textResponse);
		
		panel_service_1.add(scrollPane);
		
		frame.setVisible(true);
	}
	
	// occupancyRooms (Bidi streaming)
	public static String occupancyRooms() {

		StreamObserver<OccupancyManyResponse> responseObserver = new StreamObserver<OccupancyManyResponse>() {
			
			@Override
			public void onNext(OccupancyManyResponse value) {
						
				result += value.getResult() + "\n";
				
				System.out.println(value.getResult());

			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				
				// TODO Auto-generated method stub
				System.out.println("\nRequest completed.");
			}

		};

		//
		StreamObserver<OccupancyManyRequest> requestObserver = asyncStub.occupancyRooms(responseObserver);

		try {
			System.out.println("\n- Occupancy -\n");

			for (int i = 1; i<11; i++) {
				
				System.out.println("Request sent for Room ("+i+")");
				
				requestObserver.onNext(OccupancyManyRequest.newBuilder().setRoom(i).build());
				
			}
			
			System.out.println("---");

			// Mark the end of requests
			requestObserver.onCompleted();

			// Sleep for a bit before sending the next one.
			Thread.sleep(new Random().nextInt(1000) + 500);

		} catch (RuntimeException e) {
			
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			
		}
		return result;
	}
	
}
