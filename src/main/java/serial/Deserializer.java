package serial;

import type.KValue;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Deserializer {

    private final DataInputStream inputStream;

    public Deserializer(InputStream inputStream) {
        this.inputStream = new DataInputStream(inputStream);
    }

    public KValue read() throws IOException {
        List<Integer> header = new ArrayList<>();

        final int messageSize = inputStream.readInt();
        header.add(messageSize);

        parseHeader(header);

        parseBody();

        final var remaining = new byte[messageSize - 23];
        inputStream.readFully(remaining);

        return new KValue(header);
    }

    private void parseBody() throws IOException {
        final var arrayLength = inputStream.readByte();
        System.out.println(arrayLength);
    }

    private void parseHeader(List<Integer> header) throws IOException {
        final int apiKey = inputStream.readShort();
        final int apiVersion = inputStream.readShort();
        final int correlationId = inputStream.readInt();
        final int clientLength = inputStream.readShort();
        final var clientId = inputStream.readNBytes(clientLength);
        inputStream.readByte(); // skip tag buffer

        header.add(apiKey);
        header.add(apiVersion);
        header.add(correlationId);
    }
}
