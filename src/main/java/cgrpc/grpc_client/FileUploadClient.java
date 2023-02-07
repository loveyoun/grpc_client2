package cgrpc.grpc_client;

import com.google.protobuf.ByteString;
import file.*;
import greet.GreeterGrpc;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class FileUploadClient {

    private static final int PORT = 50052;
    public static final String HOST = "localhost";

    Path path = Paths.get("src/main/resources/input/suzy.jpg");

    private final FileServiceGrpc.FileServiceStub fileServiceStub = FileServiceGrpc.newStub(
            ManagedChannelBuilder.forAddress(HOST, PORT)
            .usePlaintext()
            .build()
    );


    public void fileupload() throws IOException {
        System.out.println("Will try to upload file...");

        StreamObserver<FileUploadRequest> streamObserver = this.fileServiceStub.upload(new FileUploadObserver());
        FileUploadRequest metadata = FileUploadRequest.newBuilder()
                .setMetadata(MetaData.newBuilder()
                        .setName("output")
                        .setType("jpg").build())
                .build();
        streamObserver.onNext(metadata);

        InputStream inputStream = Files.newInputStream(path);
        byte[] bytes = new byte[4096];
        int size;
        while((size=inputStream.read(bytes)) > 0){
            FileUploadRequest uploadRequest = FileUploadRequest.newBuilder()
                    .setFile(File.newBuilder().setContents(ByteString.copyFrom(bytes, 0, size)).build())
                    .build();

            streamObserver.onNext(uploadRequest);
        }

        inputStream.close();
        streamObserver.onCompleted();
    }

}
