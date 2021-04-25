package grpc.smbuilding.booking;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: booking.proto")
public final class BookingServiceGrpc {

  private BookingServiceGrpc() {}

  public static final String SERVICE_NAME = "booking.BookingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.smbuilding.booking.BookingRequest,
      grpc.smbuilding.booking.BookingResponse> getBookingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Booking",
      requestType = grpc.smbuilding.booking.BookingRequest.class,
      responseType = grpc.smbuilding.booking.BookingResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.smbuilding.booking.BookingRequest,
      grpc.smbuilding.booking.BookingResponse> getBookingMethod() {
    io.grpc.MethodDescriptor<grpc.smbuilding.booking.BookingRequest, grpc.smbuilding.booking.BookingResponse> getBookingMethod;
    if ((getBookingMethod = BookingServiceGrpc.getBookingMethod) == null) {
      synchronized (BookingServiceGrpc.class) {
        if ((getBookingMethod = BookingServiceGrpc.getBookingMethod) == null) {
          BookingServiceGrpc.getBookingMethod = getBookingMethod = 
              io.grpc.MethodDescriptor.<grpc.smbuilding.booking.BookingRequest, grpc.smbuilding.booking.BookingResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "booking.BookingService", "Booking"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.smbuilding.booking.BookingRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.smbuilding.booking.BookingResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new BookingServiceMethodDescriptorSupplier("Booking"))
                  .build();
          }
        }
     }
     return getBookingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BookingServiceStub newStub(io.grpc.Channel channel) {
    return new BookingServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BookingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new BookingServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BookingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new BookingServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class BookingServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void booking(grpc.smbuilding.booking.BookingRequest request,
        io.grpc.stub.StreamObserver<grpc.smbuilding.booking.BookingResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getBookingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getBookingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                grpc.smbuilding.booking.BookingRequest,
                grpc.smbuilding.booking.BookingResponse>(
                  this, METHODID_BOOKING)))
          .build();
    }
  }

  /**
   */
  public static final class BookingServiceStub extends io.grpc.stub.AbstractStub<BookingServiceStub> {
    private BookingServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookingServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookingServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookingServiceStub(channel, callOptions);
    }

    /**
     */
    public void booking(grpc.smbuilding.booking.BookingRequest request,
        io.grpc.stub.StreamObserver<grpc.smbuilding.booking.BookingResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getBookingMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BookingServiceBlockingStub extends io.grpc.stub.AbstractStub<BookingServiceBlockingStub> {
    private BookingServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookingServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookingServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.smbuilding.booking.BookingResponse booking(grpc.smbuilding.booking.BookingRequest request) {
      return blockingUnaryCall(
          getChannel(), getBookingMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BookingServiceFutureStub extends io.grpc.stub.AbstractStub<BookingServiceFutureStub> {
    private BookingServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BookingServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BookingServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BookingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.smbuilding.booking.BookingResponse> booking(
        grpc.smbuilding.booking.BookingRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getBookingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_BOOKING = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BookingServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BookingServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_BOOKING:
          serviceImpl.booking((grpc.smbuilding.booking.BookingRequest) request,
              (io.grpc.stub.StreamObserver<grpc.smbuilding.booking.BookingResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class BookingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BookingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.smbuilding.booking.BookingServiceImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BookingService");
    }
  }

  private static final class BookingServiceFileDescriptorSupplier
      extends BookingServiceBaseDescriptorSupplier {
    BookingServiceFileDescriptorSupplier() {}
  }

  private static final class BookingServiceMethodDescriptorSupplier
      extends BookingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BookingServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BookingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BookingServiceFileDescriptorSupplier())
              .addMethod(getBookingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
