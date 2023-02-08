package cgrpc.grpc_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GrpcClientApplication {

	@Autowired
	public static FileUploadClient fileUploadClient;
	public GrpcClientApplication(FileUploadClient fileUploadClient) {
		this.fileUploadClient = fileUploadClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(GrpcClientApplication.class, args);

		try {
			fileUploadClient.fileupload();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
