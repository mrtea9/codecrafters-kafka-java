package serial;

import type.KValue;
import util.TrackedInputStream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;


public class Deserializer {

    private final InputStream inputStream;

    public Deserializer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public KValue read() throws IOException {
        var message = new ArrayList<Integer>();

        final var messageSizeBytes = inputStream.readNBytes(4);
        final var apiKeyBytes = inputStream.readNBytes(2);
        final var apiVersionBytes = inputStream.readNBytes(2);
        final var correlationIdBytes = inputStream.readNBytes(4);

        while (inputStream.read() != -1) {
            continue;
        }

        final int messageSize = ByteBuffer.wrap(messageSizeBytes).getInt();
        final int apiKey = ByteBuffer.wrap(apiKeyBytes).getShort();
        final int apiVersion = ByteBuffer.wrap(apiVersionBytes).getShort();
        final int correlationId = ByteBuffer.wrap(correlationIdBytes).getInt();

        message.add(messageSize);
        message.add(apiKey);
        message.add(apiVersion);
        message.add(correlationId);

        System.out.println(message);

        return new KValue(message);
    }
}
