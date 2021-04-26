package grpc.smbuilding.temperature;

import java.util.Random;

import javax.net.ssl.SSLException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class TemperatureClient {
	
	private static TemperatureServiceGrpc.TemperatureServiceStub asyncStub;

	public static void main(String[] args) throws SSLException, InterruptedException {

		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50053).usePlaintext().build();

		// stubs -- generate from proto
		asyncStub = TemperatureServiceGrpc.newStub(channel);

		checkTemperature();
		
		temperatureReport();

		System.out.println("\nShutting down channel");

		channel.shutdownNow();
	}

	public static void checkTemperature() {

		StreamObserver<CheckTemperatureResponse> responseObserver = new StreamObserver<CheckTemperatureResponse>() {

			@Override
			public void onNext(CheckTemperatureResponse value) {
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

	}

	public static void temperatureReport() {
		TemperatureReportRequest request = TemperatureReportRequest.newBuilder().build();

		StreamObserver<TemperatureReportResponse> responseObserver = new StreamObserver<TemperatureReportResponse>() {

			@Override
			public void onNext(TemperatureReportResponse value) {
				
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
			
			Thread.sleep(30000);
			
		} catch (InterruptedException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}
