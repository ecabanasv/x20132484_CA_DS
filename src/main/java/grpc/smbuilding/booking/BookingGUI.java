package grpc.smbuilding.booking;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.net.ssl.SSLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BookingGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BookingServiceGrpc.BookingServiceBlockingStub blockingStub;
	private ServiceInfo bookingServiceInfo;
	private JFrame frame;
	private JTextField textRoom;
	private JTextArea textResponse;
	
	public static void main(String[] args) throws SSLException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    BookingGUI window = new BookingGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public BookingGUI() {
		String booking_service_type = "_booking._tcp.local.";
		discoverBookingService(booking_service_type);
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

		//stubs -- generate from proto
		blockingStub = BookingServiceGrpc.newBlockingStub(channel);

		initialize();	

	}

	private void discoverBookingService(String service_type) {
		
		try {
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			jmdns.addServiceListener(service_type, new ServiceListener() {
				
				@Override
				public void serviceResolved(ServiceEvent event) {
					System.out.println("Booking Service resolved: " + event.getInfo());

					bookingServiceInfo = event.getInfo();

					int port = bookingServiceInfo.getPort();
					
					System.out.println("resolving " + service_type + " with properties ...");
					System.out.println("\t port: " + port);
					System.out.println("\t type:"+ event.getType());
					System.out.println("\t name: " + event.getName());
					System.out.println("\t description/properties: " + bookingServiceInfo.getNiceTextString());
					System.out.println("\t host: " + bookingServiceInfo.getHostAddresses()[0]);	
				}
				
				@Override
				public void serviceRemoved(ServiceEvent event) {
					System.out.println("Booking Service removed: " + event.getInfo());
				}
				
				@Override
				public void serviceAdded(ServiceEvent event) {
					System.out.println("Booking Service added: " + event.getInfo());
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Service: Check Room");
		frame.setBounds(100, 100, 500, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BoxLayout bl = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		
		frame.getContentPane().setLayout(bl);
		
		JPanel panel_service_1 = new JPanel();
		frame.getContentPane().add(panel_service_1);
		panel_service_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("Introduce room number (1-10):");
		panel_service_1.add(lblNewLabel_1);
		
		textRoom = new JTextField();
		panel_service_1.add(textRoom);
		textRoom.setColumns(10);
			
		JButton btnCalculate = new JButton("Book room");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int numRoom = Integer.parseInt(textRoom.getText().toString());
				
				BookingRequest req = BookingRequest.newBuilder().setNumRoom(numRoom).build();

				BookingResponse response = blockingStub.booking(req);

				textResponse.append(response.getResult() + "\n");
				
				System.out.println("res: " + response.getResult());

			}
		});
		
		panel_service_1.add(btnCalculate);
		
		textResponse = new JTextArea(6, 40);
		textResponse .setLineWrap(true);
		textResponse.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane(textResponse);
		
		//textResponse.setSize(new Dimension(15, 30));
		panel_service_1.add(scrollPane);
		
		frame.setVisible(true);
	}
}
