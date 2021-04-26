package grpc.smbuilding.temperature;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.net.ssl.SSLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class TemperatureGUI {
	
	private static TemperatureServiceGrpc.TemperatureServiceStub asyncStub;
	private static String result1 = "";
	private static String result2 = "";
	private ServiceInfo temperatureServiceInfo;
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
	
	/**
	 * Create the application.
	 */
	public TemperatureGUI() {
		String temperature_service_type = "_temperature._tcp.local.";
		discoverTemperatureService(temperature_service_type);
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053).usePlaintext().build();

		asyncStub = TemperatureServiceGrpc.newStub(channel);

		initialize();	

	}

	private void discoverTemperatureService(String service_type) {
		
		try {
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			jmdns.addServiceListener(service_type, new ServiceListener() {
				
				@Override
				public void serviceResolved(ServiceEvent event) {
					
					System.out.println("Temperature Service resolved: " + event.getInfo());

					temperatureServiceInfo = event.getInfo();

					int port = temperatureServiceInfo.getPort();
					
					System.out.println("resolving " + service_type + " with properties ...");
					System.out.println("\t port: " + port);
					System.out.println("\t type:"+ event.getType());
					System.out.println("\t name: " + event.getName());
					System.out.println("\t description/properties: " + temperatureServiceInfo.getNiceTextString());
					System.out.println("\t host: " + temperatureServiceInfo.getHostAddresses()[0]);	
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Service: Temperature report");
		frame.setBounds(100, 100, 500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BoxLayout bl = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		
		frame.getContentPane().setLayout(bl);
		
		JPanel panel_service_1 = new JPanel();
		frame.getContentPane().add(panel_service_1);
		panel_service_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
		JButton btnCalculate1 = new JButton("Average: Temperature Rooms");
		btnCalculate1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				checkTemperature();
				
				textResponse.append(result1);

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
		
		//textResponse.setSize(new Dimension(15, 30));
		panel_service_1.add(scrollPane);
		
		
		JPanel panel_service_2 = new JPanel();
		frame.getContentPane().add(panel_service_2);
		
		JPanel panel_service_3 = new JPanel();
		frame.getContentPane().add(panel_service_3);
	}
	
	public static String checkTemperature() {

		StreamObserver<CheckTemperatureResponse> responseObserver = new StreamObserver<CheckTemperatureResponse>() {

			@Override
			public void onNext(CheckTemperatureResponse value) {
				result1 = "Average temperature: " + value.getAverage() + "C";
				System.out.println("\nAverage temperature: " + value.getAverage());
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				System.out.println("\nRequest completed.");
				System.out.println("");
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
				
				Thread.sleep(1);
				
			} catch (InterruptedException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			return result2;
		}
	
}
