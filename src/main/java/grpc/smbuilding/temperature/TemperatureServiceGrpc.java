package grpc.smbuilding.temperature;

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
    comments = "Source: temperature.proto")
public final class TemperatureServiceGrpc {

  private TemperatureServiceGrpc() {}

  public static final String SERVICE_NAME = "temperature.TemperatureService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.smbuilding.temperature.CheckTemperatureRequest,
      grpc.smbuilding.temperature.CheckTemperatureResponse> getCheckTemperatureMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckTemperature",
      requestType = grpc.smbuilding.temperature.CheckTemperatureRequest.class,
      responseType = grpc.smbuilding.temperature.CheckTemperatureResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.smbuilding.temperature.CheckTemperatureRequest,
      grpc.smbuilding.temperature.CheckTemperatureResponse> getCheckTemperatureMethod() {
    io.grpc.MethodDescriptor<grpc.smbuilding.temperature.CheckTemperatureRequest, grpc.smbuilding.temperature.CheckTemperatureResponse> getCheckTemperatureMethod;
    if ((getCheckTemperatureMethod = TemperatureServiceGrpc.getCheckTemperatureMethod) == null) {
      synchronized (TemperatureServiceGrpc.class) {
        if ((getCheckTemperatureMethod = TemperatureServiceGrpc.getCheckTemperatureMethod) == null) {
          TemperatureServiceGrpc.getCheckTemperatureMethod = getCheckTemperatureMethod = 
              io.grpc.MethodDescriptor.<grpc.smbuilding.temperature.CheckTemperatureRequest, grpc.smbuilding.temperature.CheckTemperatureResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "temperature.TemperatureService", "CheckTemperature"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.smbuilding.temperature.CheckTemperatureRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.smbuilding.temperature.CheckTemperatureResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new TemperatureServiceMethodDescriptorSupplier("CheckTemperature"))
                  .build();
          }
        }
     }
     return getCheckTemperatureMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.smbuilding.temperature.TemperatureReportRequest,
      grpc.smbuilding.temperature.TemperatureReportResponse> getTemperatureReportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TemperatureReport",
      requestType = grpc.smbuilding.temperature.TemperatureReportRequest.class,
      responseType = grpc.smbuilding.temperature.TemperatureReportResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<grpc.smbuilding.temperature.TemperatureReportRequest,
      grpc.smbuilding.temperature.TemperatureReportResponse> getTemperatureReportMethod() {
    io.grpc.MethodDescriptor<grpc.smbuilding.temperature.TemperatureReportRequest, grpc.smbuilding.temperature.TemperatureReportResponse> getTemperatureReportMethod;
    if ((getTemperatureReportMethod = TemperatureServiceGrpc.getTemperatureReportMethod) == null) {
      synchronized (TemperatureServiceGrpc.class) {
        if ((getTemperatureReportMethod = TemperatureServiceGrpc.getTemperatureReportMethod) == null) {
          TemperatureServiceGrpc.getTemperatureReportMethod = getTemperatureReportMethod = 
              io.grpc.MethodDescriptor.<grpc.smbuilding.temperature.TemperatureReportRequest, grpc.smbuilding.temperature.TemperatureReportResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "temperature.TemperatureService", "TemperatureReport"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.smbuilding.temperature.TemperatureReportRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.smbuilding.temperature.TemperatureReportResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new TemperatureServiceMethodDescriptorSupplier("TemperatureReport"))
                  .build();
          }
        }
     }
     return getTemperatureReportMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TemperatureServiceStub newStub(io.grpc.Channel channel) {
    return new TemperatureServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TemperatureServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TemperatureServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TemperatureServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TemperatureServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class TemperatureServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Client Streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<grpc.smbuilding.temperature.CheckTemperatureRequest> checkTemperature(
        io.grpc.stub.StreamObserver<grpc.smbuilding.temperature.CheckTemperatureResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getCheckTemperatureMethod(), responseObserver);
    }

    /**
     * <pre>
     * Server Streaming
     * </pre>
     */
    public void temperatureReport(grpc.smbuilding.temperature.TemperatureReportRequest request,
        io.grpc.stub.StreamObserver<grpc.smbuilding.temperature.TemperatureReportResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getTemperatureReportMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCheckTemperatureMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                grpc.smbuilding.temperature.CheckTemperatureRequest,
                grpc.smbuilding.temperature.CheckTemperatureResponse>(
                  this, METHODID_CHECK_TEMPERATURE)))
          .addMethod(
            getTemperatureReportMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                grpc.smbuilding.temperature.TemperatureReportRequest,
                grpc.smbuilding.temperature.TemperatureReportResponse>(
                  this, METHODID_TEMPERATURE_REPORT)))
          .build();
    }
  }

  /**
   */
  public static final class TemperatureServiceStub extends io.grpc.stub.AbstractStub<TemperatureServiceStub> {
    private TemperatureServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TemperatureServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TemperatureServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TemperatureServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Client Streaming
     * </pre>
     */
    public io.grpc.stub.StreamObserver<grpc.smbuilding.temperature.CheckTemperatureRequest> checkTemperature(
        io.grpc.stub.StreamObserver<grpc.smbuilding.temperature.CheckTemperatureResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getCheckTemperatureMethod(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     * Server Streaming
     * </pre>
     */
    public void temperatureReport(grpc.smbuilding.temperature.TemperatureReportRequest request,
        io.grpc.stub.StreamObserver<grpc.smbuilding.temperature.TemperatureReportResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getTemperatureReportMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TemperatureServiceBlockingStub extends io.grpc.stub.AbstractStub<TemperatureServiceBlockingStub> {
    private TemperatureServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TemperatureServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TemperatureServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TemperatureServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Server Streaming
     * </pre>
     */
    public java.util.Iterator<grpc.smbuilding.temperature.TemperatureReportResponse> temperatureReport(
        grpc.smbuilding.temperature.TemperatureReportRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getTemperatureReportMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TemperatureServiceFutureStub extends io.grpc.stub.AbstractStub<TemperatureServiceFutureStub> {
    private TemperatureServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TemperatureServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TemperatureServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TemperatureServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_TEMPERATURE_REPORT = 0;
  private static final int METHODID_CHECK_TEMPERATURE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TemperatureServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TemperatureServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TEMPERATURE_REPORT:
          serviceImpl.temperatureReport((grpc.smbuilding.temperature.TemperatureReportRequest) request,
              (io.grpc.stub.StreamObserver<grpc.smbuilding.temperature.TemperatureReportResponse>) responseObserver);
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
        case METHODID_CHECK_TEMPERATURE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.checkTemperature(
              (io.grpc.stub.StreamObserver<grpc.smbuilding.temperature.CheckTemperatureResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class TemperatureServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TemperatureServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.smbuilding.temperature.TemperatureServiceImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TemperatureService");
    }
  }

  private static final class TemperatureServiceFileDescriptorSupplier
      extends TemperatureServiceBaseDescriptorSupplier {
    TemperatureServiceFileDescriptorSupplier() {}
  }

  private static final class TemperatureServiceMethodDescriptorSupplier
      extends TemperatureServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TemperatureServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (TemperatureServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TemperatureServiceFileDescriptorSupplier())
              .addMethod(getCheckTemperatureMethod())
              .addMethod(getTemperatureReportMethod())
              .build();
        }
      }
    }
    return result;
  }
}
