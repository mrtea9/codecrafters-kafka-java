package serial;

import util.TrackedInputStream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Deserializer {

    private final InputStream inputStream;

    public Deserializer(TrackedInputStream inputStream) {
        this.inputStream = inputStream;
    }


    public byte[] read() throws IOException {

        final var messageSize = inputStream.readNBytes(4);
        final var apiKey = inputStream.readNBytes(2);
        final var apiVersion = inputStream.readNBytes(2);
        final var correlationId = inputStream.readNBytes(4);

        byte[] response = new byte[messageSize.length + correlationId.length];

        ByteBuffer buff = ByteBuffer.wrap(response);
        buff.put(messageSize);
        buff.put(correlationId);

        return buff.array();
    }
}
