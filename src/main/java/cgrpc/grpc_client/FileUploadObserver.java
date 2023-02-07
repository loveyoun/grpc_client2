package cgrpc.grpc_client;

import file.FileUploadResponse;
import io.grpc.stub.StreamObserver;

public class FileUploadObserver implements StreamObserver<FileUploadResponse> {

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
