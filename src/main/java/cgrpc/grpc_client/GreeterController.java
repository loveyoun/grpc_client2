package cgrpc.grpc_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class GreeterController {

    @Autowired private GreeterClient greeterClient;
    @Autowired private FileUploadClient fileUploadClient;

    @GetMapping("/hello")
    public void Hello(){
        String user = "jeong youn";
        int age = 27;

        greeterClient.greet(user, age);
    }

    @PostMapping("/upload")
    public void Upload(@RequestParam("path") String path) throws IOException {
        fileUploadClient.fileupload(path);
    }

}
