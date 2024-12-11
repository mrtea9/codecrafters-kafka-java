package serial;

import type.KValue;
import util.TrackedInputStream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class Deserializer {

    private final InputStream inputStream;

    public Deserializer(TrackedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public KValue read() throws IOException {

        final var dataInputStream = new DataInputStream(inputStream);

        final var messageSize = dataInputStream.readInt();
        final var apiKey = dataInputStream.readShort();
        final var apiVersion = dataInputStream.readShort();
        final var correlationId = dataInputStream.readInt();

        System.out.println(messageSize);
        System.out.println(apiKey);
        System.out.println(apiVersion);
        System.out.println(correlationId);

        return null;
    }
}
