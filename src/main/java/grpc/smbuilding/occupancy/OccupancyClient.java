package grpc.smbuilding.occupancy;

import java.util.Random;

import javax.net.ssl.SSLException;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class OccupancyClient {
	
	private static OccupancyServiceGrpc.OccupancyServiceStub asyncStub;

	public static void main(String[] args) throws SSLException, InterruptedException {

		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052).usePlaintext().build();

		// stubs -- generate from proto	
		asyncStub = OccupancyServiceGrpc.newStub(channel);

		occupancyRooms();

		System.out.println("\nShutting down channel");

		channel.shutdownNow();
	}

	public static void occupancyRooms() {

		StreamObserver<OccupancyManyResponse> responseObserver = new StreamObserver<OccupancyManyResponse>() {

			@Override
			public void onNext(OccupancyManyResponse value) {
				
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

	}

}
