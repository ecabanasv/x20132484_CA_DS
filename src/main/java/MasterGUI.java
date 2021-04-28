// Panel control with 3 services button: Booking, Occupancy and Temperature

// AWT Libraries
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.net.ssl.SSLException;

// Swing Libraries
import javax.swing.*;


// gRPC Libraries
import grpc.smbuilding.booking.BookingGUI;
import grpc.smbuilding.occupancy.OccupancyGUI;
import grpc.smbuilding.temperature.TemperatureGUI;

public class MasterGUI {
	
	private JFrame MasterF;	
	
	public static void main(String[] args) throws SSLException, InterruptedException {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					
					MasterGUI window = new MasterGUI();
					
					window.MasterF.setVisible(true);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
			}
		});
	}
	
	// Constructor MasterGUI
	public MasterGUI() {
		
		initialize();
		
	}
	
	// Interface MasterGUI
	private void initialize() {
		
		MasterF = new JFrame();
		
		MasterF.setTitle("Mini Smart Building: Panel Control");
		
		MasterF.setBounds(750, 0, 400, 80);
		
		MasterF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BoxLayout bl = new BoxLayout(MasterF.getContentPane(), BoxLayout.Y_AXIS);
		
		MasterF.getContentPane().setLayout(bl);
		
		JPanel panel_service_1 = new JPanel();
		
		MasterF.getContentPane().add(panel_service_1);
		
		panel_service_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Button Booking service
		JButton btnButton1 = new JButton("Check Rooms");
		
		btnButton1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
					
					public void run() {
						
						try {
							
					        @SuppressWarnings("unused")
					        
							BookingGUI gBooking = new BookingGUI();
							
						} catch (Exception e) {
							
							e.printStackTrace();
							
						}
					}
				});   
			}
		});
		
		panel_service_1.add(btnButton1);
		
		// Button Occupancy service
		JButton btnButton2 = new JButton("Occupancy report");
		
		btnButton2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {	
				
				EventQueue.invokeLater(new Runnable() {
					
					public void run() {
						
						try {
							
							@SuppressWarnings("unused")
							
							OccupancyGUI gOccupancy = new OccupancyGUI();  
							
						} catch (Exception e) {
							
							e.printStackTrace();
							
						}
					}
				});   
			}
		});
		
		panel_service_1.add(btnButton2);
		
		// Button Temperature service
		JButton btnButton3 = new JButton("Temperature");
		
		btnButton3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
					
					public void run() {
						
						try {
							
							@SuppressWarnings("unused")
							
							TemperatureGUI gTemperature = new TemperatureGUI(); 
							
						} catch (Exception e) {
							
							e.printStackTrace();
							
						}
					}
				}); 
			}
		});	
		
		panel_service_1.add(btnButton3);
	}
}
