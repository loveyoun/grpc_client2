package cgrpc.grpc_client;

import com.google.protobuf.ByteString;
import file.*;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
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

    //Path path = Paths.get("src/main/resources/input/suzy.jpg");
    String path = "D:\\chim.jpg";
    java.io.File file = new java.io.File(path);

    private final FileServiceGrpc.FileServiceStub fileServiceStub = FileServiceGrpc.newStub(
            ManagedChannelBuilder.forAddress(HOST, PORT)
            .usePlaintext()
            .build()
    );


    public void fileupload() throws IOException {
        System.out.println("Will try to upload file...");

        InputStream inputStream = new FileInputStream(file);
        //int len = inputStream.readAllBytes().length; //영혼 빨아먹듯 다 읽어버리네 얘가...

        //custom request를 처리하는 StreamObserver 객체 얻기
        StreamObserver<FileUploadRequest> streamObserver = this.fileServiceStub.upload(new FileUploadObserver());
        FileUploadRequest metadata = FileUploadRequest.newBuilder()
                .setMetadata(MetaData.newBuilder()
                        .setName("output")
                        .setType("jpg")
                        .build())
                .build();
        streamObserver.onNext(metadata);

        //InputStream inputStream = Files.newInputStream(path);

        byte[] bytes = new byte[4096];
        int size; FileUploadRequest uploadRequest;
        while(true){
            size = inputStream.read(bytes);
            if(size <= 0) break;

            uploadRequest = FileUploadRequest.newBuilder()
                    .setFile(File.newBuilder().setContents(ByteString.copyFrom(bytes, 0, size)).build())
                    .build();

            streamObserver.onNext(uploadRequest);
            log.info("sent file with size: " + size);
        }
        /*while((size = inputStream.read(bytes)) > 0){
            uploadRequest = FileUploadRequest.newBuilder()
                    .setFile(File.newBuilder().setContents(ByteString.copyFrom(bytes, 0, size)).build())
                    .build();

            streamObserver.onNext(uploadRequest);
            log.info("sent file with size: " + size);
        }*/

        streamObserver.onCompleted();
        inputStream.close();
    }


    public static class FileUploadObserver implements StreamObserver<FileUploadResponse> {
        @Override
        public void onNext(FileUploadResponse fileUploadResponse) {
            System.out.println("File upload status::" + fileUploadResponse.getStatus());
        }

        @Override
        public void onError(Throwable throwable) {
        }

        @Override
        public void onCompleted() {
        }
    }

}
