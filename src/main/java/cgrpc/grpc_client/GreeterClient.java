package cgrpc.grpc_client;

import greet.GreeterGrpc;
import greet.GreeterOuterClass;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GreeterClient {

    private static final int PORT = 50051;
    public static final String HOST = "localhost";

    private final GreeterGrpc.GreeterStub greeterStub = GreeterGrpc.newStub(
            ManagedChannelBuilder.forAddress(HOST, PORT)
                    .usePlaintext()
                    .build()
    );

    public void greet(String name, int age){
        System.out.println("Will try to greet " + name + "(" + age + ")" + "...");

        final GreeterOuterClass.Request request = GreeterOuterClass.Request.newBuilder()
                .setAge(age)
                .setName(name)
                .build();

        greeterStub.hello(request, new StreamObserver<GreeterOuterClass.Response>() {
            @Override
            public void onNext(GreeterOuterClass.Response response) {
                log.info("hello onNext - {}", response);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("hello - onError");
            }

            @Override
            public void onCompleted() {
                log.info("hello - onCompleted");
            }
        });
    }

}
