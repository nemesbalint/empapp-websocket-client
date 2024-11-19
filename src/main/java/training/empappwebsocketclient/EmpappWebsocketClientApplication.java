package training.empappwebsocketclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.time.Duration;
import java.util.List;

@SpringBootApplication
@Slf4j
public class EmpappWebsocketClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EmpappWebsocketClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Hello Command Line Spring");

        var sockJsClient = new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient())));
        var stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        var feature = stompClient.connectAsync("ws://localhost:8080/websocket-endpoint", new MyStompMessageHandler());

        feature.thenAccept(stompSession -> {
            log.info("Sending messages");
            for (int i = 0; i < 10; i++) {
                stompSession.send("/app/messages", new Request("John Doe " + i));
            }
        });

        log.info("after connect");

        Thread.sleep(Duration.ofHours(1));
    }
}
