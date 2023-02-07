package cgrpc.grpc_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GreeterController {

    @Autowired
    private GreeterClient greeterClient;
    @Autowired private FileUploadClient fileUploadClient;

    @GetMapping("/hello")
    public void Hello(){
        String user = "jeong youn";
        int age = 27;

        greeterClient.greet(user, age);
    }

    @GetMapping("/upload")
    public void Upload() throws IOException {
        fileUploadClient.fileupload();
    }

}
