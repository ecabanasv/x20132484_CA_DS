package grpc.smbuilding.temperature;

// AWT Libraries
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Generic Libraries
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

// Swing Libraries
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// gRPC Libraries
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class TemperatureGUI extends JFrame {
	
	// For use JFrame from MasterGUI
	private static final long serialVersionUID = 1L;
	
	// Blockingstub gRPC
	private static TemperatureServiceGrpc.TemperatureServiceStub asyncStub;
	
	private static String result1 = "";
	
	private static String result2 = "";
	
	private JFrame frame;
	
	private JTextArea textResponse;
	
	public static void main(String[] args) throws SSLException, InterruptedException {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					TemperatureGUI window = new TemperatureGUI();
					
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
			}
		});
	}
	
	// Constructor TemperatureGUI
	public TemperatureGUI() {
		
		String temperature_service_type = "_temperature._tcp.local.";
		
		discoverTemperatureService(temperature_service_type);
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053).usePlaintext().build();

		asyncStub = TemperatureServiceGrpc.newStub(channel);

		initialize();	

	}

	// Discover Temperature Service
	private void discoverTemperatureService(String service_type) {
		
		try {
			
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			jmdns.addServiceListener(service_type, new ServiceListener() {
				
				@Override
				public void serviceResolved(ServiceEvent event) {
					
					System.out.println("Temperature Service resolved: " + event.getInfo());

				}
				
				@Override
				public void serviceRemoved(ServiceEvent event) {
					
					System.out.println("Temperature Service removed: " + event.getInfo());
					
				}
				
				@Override
				public void serviceAdded(ServiceEvent event) {
					
					System.out.println("Temperature Service added: " + event.getInfo());
					
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
		
		frame.setTitle("Service: Temperature report");
		
		frame.setBounds(100, 300, 550, 300);
		
		BoxLayout bl = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		
		frame.getContentPane().setLayout(bl);
		
		JPanel panel_service_1 = new JPanel();
		
		frame.getContentPane().add(panel_service_1);
		
		panel_service_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
		JButton btnCalculate1 = new JButton("Average: Temperature Rooms");
		
		btnCalculate1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				checkTemperature();
				
				textResponse.append("---------------\n" + result1 + "---------------\n");

			}
		});
		
		panel_service_1.add(btnCalculate1);
		
		JButton btnCalculate2 = new JButton("Report: Temperature Rooms");
		
		btnCalculate2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {		
				
				temperatureReport();
				
				textResponse.append(result2);

			}
		});
		
		panel_service_1.add(btnCalculate2);
		
		textResponse = new JTextArea(12, 40);
		
		textResponse .setLineWrap(true);
		
		textResponse.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane(textResponse);
		
		panel_service_1.add(scrollPane);
		
		frame.setVisible(true);
	}
	
	// checkTemperature (client-streaming)
	public static String checkTemperature() {

		StreamObserver<CheckTemperatureResponse> responseObserver = new StreamObserver<CheckTemperatureResponse>() {

			@Override
			public void onNext(CheckTemperatureResponse value) {
				
				result1 = "Average temperature of rooms: " + value.getAverage() + "C\n";
				
				System.out.println(result1);
				
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				
				System.out.println("\nRequest completed.\n");
				
			}

		};

		//
		StreamObserver<CheckTemperatureRequest> requestObserver = asyncStub.checkTemperature(responseObserver);

		try {
			
			for (int i = 1; i<11; i++) {
				
				requestObserver.onNext(CheckTemperatureRequest.newBuilder().setRoom(i).build());
				
			}
			
			System.out.println("\nSending request to the Temperature server...");

			// Mark the end of requests
			requestObserver.onCompleted();

			// Sleep for a bit before sending the next one.
			Thread.sleep(new Random().nextInt(1000) + 500);

		} catch (RuntimeException e) {
			
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			
		}
		return result1;
	}
	
	// temperatureReport (server-streaming)
	public static String temperatureReport() {
			
		TemperatureReportRequest request = TemperatureReportRequest.newBuilder().build();

		StreamObserver<TemperatureReportResponse> responseObserver = new StreamObserver<TemperatureReportResponse>() {

			@Override
			public void onNext(TemperatureReportResponse value) {
					
				result2 += value.getResult() + "\n";
					
				System.out.println(value.getResult());
					
			}

			@Override
			public void onError(Throwable t) {
					
				t.printStackTrace();

			}

			@Override
			public void onCompleted() {
					
				System.out.println("\nTemperature report completed.");
			}

		};

		asyncStub.temperatureReport(request, responseObserver);

		try {
				
			Thread.sleep(0);
				
		} catch (InterruptedException e) {
				
			// TODO Auto-generated catch block
			e.printStackTrace();
				
		}
		
		return result2;
	}
	
}
