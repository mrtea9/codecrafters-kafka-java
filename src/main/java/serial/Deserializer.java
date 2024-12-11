package serial;

import type.KValue;
import util.TrackedInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class Deserializer {

    private final InputStream inputStream;

    public Deserializer(TrackedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public KValue read() throws IOException {

        final var messageSize = inputStream.readNBytes(4);
        final var apiKey = inputStream.readNBytes(2);
        final var apiVersion = inputStream.readNBytes(2);
        final var correlationId = inputStream.readNBytes(4);
        final var totalLength = messageSize.length + apiKey.length + apiVersion.length + correlationId.length;

        byte[] message = new byte[totalLength];
        ByteBuffer buff = ByteBuffer.wrap(message);

        buff.put(messageSize);
        buff.put(apiKey);
        buff.put(apiVersion);
        buff.put(correlationId);

        return new KValue(buff.array());
    }
}
