package grpc.smbuilding.booking;

import javax.net.ssl.SSLException;

import io.grpc.ManagedChannel;

import io.grpc.ManagedChannelBuilder;

public class BookingClient {

	private static BookingServiceGrpc.BookingServiceBlockingStub blockingStub;

	public static void main(String[] args) throws SSLException, InterruptedException {

		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

		// stubs -- generate from proto
		blockingStub = BookingServiceGrpc.newBlockingStub(channel);

//		asyncStub = BookingServiceGrpc.newStub(channel);

		booking();

		System.out.println("Shutting down channel");

		channel.shutdownNow();
	}

	// Unary rpc
	public static void booking() {

		BookingRequest req = BookingRequest.newBuilder().setNumRoom(2).build();

		BookingResponse response = blockingStub.booking(req);

		System.out.println(response.getResult());
	}

}
