// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: occupancy.proto

package grpc.smbuilding.occupancy;

public final class OccupancyServiceImpl {
  private OccupancyServiceImpl() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_occupancy_OccupancyManyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_occupancy_OccupancyManyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_occupancy_OccupancyManyResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_occupancy_OccupancyManyResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017occupancy.proto\022\toccupancy\"$\n\024Occupanc" +
      "yManyRequest\022\014\n\004room\030\001 \001(\005\"\'\n\025OccupancyM" +
      "anyResponse\022\016\n\006result\030\001 \001(\t2m\n\020Occupancy" +
      "Service\022Y\n\016OccupancyRooms\022\037.occupancy.Oc" +
      "cupancyManyRequest\032 .occupancy.Occupancy" +
      "ManyResponse\"\000(\0010\001B3\n\031grpc.smbuilding.oc" +
      "cupancyB\024OccupancyServiceImplP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_occupancy_OccupancyManyRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_occupancy_OccupancyManyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_occupancy_OccupancyManyRequest_descriptor,
        new java.lang.String[] { "Room", });
    internal_static_occupancy_OccupancyManyResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_occupancy_OccupancyManyResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_occupancy_OccupancyManyResponse_descriptor,
        new java.lang.String[] { "Result", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
