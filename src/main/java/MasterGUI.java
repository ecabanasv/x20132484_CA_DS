import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.net.ssl.SSLException;

import javax.swing.*;

import grpc.smbuilding.booking.BookingGUI;
import grpc.smbuilding.booking.BookingServer;
import grpc.smbuilding.occupancy.OccupancyGUI;
import grpc.smbuilding.occupancy.OccupancyServer;
import grpc.smbuilding.temperature.TemperatureGUI;
import grpc.smbuilding.temperature.TemperatureServer;

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
	
	public MasterGUI() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
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
		
//		JButton btnButton4 = new JButton("Run Booking");
//		btnButton4.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				EventQueue.invokeLater(new Runnable() {
//					public void run() {
//						try {
//							
//					        BookingServer sBooking = new BookingServer(); 
//							
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				});   
//			}
//		});
//		
//		panel_service_1.add(btnButton4);
//		
//		JButton btnButton5 = new JButton("Run Occup.");
//		btnButton5.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				OccupancyServer sOccupancy = new OccupancyServer();  
//
//			}
//		});
//		
//		panel_service_1.add(btnButton5);
//		
//		JButton btnButton6 = new JButton("Run Temp.");
//		btnButton6.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				TemperatureServer sTemperature = new TemperatureServer(); 
//
//			}
//		});	
//		
//		panel_service_1.add(btnButton6);
		
		JPanel panel_service_2 = new JPanel();
		MasterF.getContentPane().add(panel_service_2);
		
		JPanel panel_service_3 = new JPanel();
		MasterF.getContentPane().add(panel_service_3);
	}
}