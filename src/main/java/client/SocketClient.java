package client;

import serial.Deserializer;
import serial.Serializer;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class SocketClient implements Runnable {

    private static final AtomicInteger ID_INCREMENT = new AtomicInteger();

    private final int id;
    private final Socket socket;
    private boolean connected;

    public SocketClient(Socket socket) {
        this.id = ID_INCREMENT.incrementAndGet();
        this.socket = socket;
    }

    @Override
    public void run() {
        connected = true;
        System.out.println("%d: connected".formatted(id));

        try (socket) {
            final var inputStream = socket.getInputStream();
            final var outputStream = socket.getOutputStream();

            final var deserializer = new Deserializer(inputStream);
            final var serializer = new Serializer(outputStream);

            while (true) {

                final var request = deserializer.read();
                if (request == null) return;

                System.out.println("Received " + request);

                serializer.write(request);

                outputStream.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
