package co.axelrod.protobuf.worker;

import co.axelrod.protobuf.GetWorkerDataGrpc;
import co.axelrod.protobuf.GetWorkerDataRequest;
import co.axelrod.protobuf.GetWorkerDataResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@CommonsLog
@Component
public class WorkerServer implements DisposableBean {
    @Value("${environment.HOSTNAME}")
    private String hostName;

    private Server server;

    public WorkerServer() throws IOException, InterruptedException {
        log.info("Starting server");

        int port = 50051;

        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build();

        server.start();
        log.info("Server started, listening on " + port);

        server.awaitTermination();
    }

    @Override
    public void destroy() {
        server.shutdown();
    }


    private class GreeterImpl extends GetWorkerDataGrpc.GetWorkerDataImplBase {
        @Override
        public void getWorkerData(GetWorkerDataRequest request, StreamObserver<GetWorkerDataResponse> responseObserver) {
            log.info("Received request with name = " + request.getName());

            GetWorkerDataResponse response = GetWorkerDataResponse.newBuilder()
                    .setMessage(hostName + " at " + new Date().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
