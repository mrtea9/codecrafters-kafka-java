import client.SocketClient;
import kafka.Kafka;
import kafka.KafkaLoader;
import store.Storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadFactory;

public class Main {

    public static void main(String[] args) throws IOException {

        final ThreadFactory threadFactory = Thread.ofVirtual().factory();
        final Storage storage = new Storage();
        final KafkaLoader kafkaLoader = KafkaLoader.load("/tmp/kraft-combined-logs/", storage);

        final Kafka kafka = new Kafka();

        final int port = 9092;

        System.out.println("port: %s".formatted(port));

        try (final ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);

            while (true) {
                final Socket socket = serverSocket.accept();
                final var client = new SocketClient(socket, kafka);

                final Thread thread = threadFactory.newThread(client);
                thread.start();
            }
        }
    }

}
