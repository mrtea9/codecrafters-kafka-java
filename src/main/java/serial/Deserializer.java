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
        var value = new KValue();

        final int messageSize = inputStream.readInt();
        System.out.println("messageSize = " + messageSize);

        parseHeader(value);

        parseBody(value);

//        final var remaining = new byte[messageSize - 49];
//        inputStream.readFully(remaining);

        return value;
    }

    private void parseBody(KValue value) throws IOException {
        final var arrayLength = readLength() - 1;
        System.out.println("array length = " + arrayLength);
        final var topicName = readString();
        System.out.println("topicName = " + topicName);
        inputStream.readByte(); // skip tag buffer

        final var partitionLimit = inputStream.readInt();

        System.out.println("partitionLimit = " + partitionLimit);

        inputStream.readByte(); // skip cursor
        inputStream.readByte(); // skip tag buffer
    }

    private void parseHeader(KValue value) throws IOException {
        final int apiKey = inputStream.readShort();
        final int apiVersion = inputStream.readShort();
        final int correlationId = inputStream.readInt();
        final int clientLength = inputStream.readShort();
        final var clientId = inputStream.readNBytes(clientLength);
        inputStream.readByte(); // skip tag buffer

        value.setApiKey(apiKey);
        value.setApiVersion(apiVersion);
        value.setCorrelationId(correlationId);
    }

    private String readString() throws IOException {
        final var length = readLength() - 1;

        System.out.println("length = " + length);

        final var content = inputStream.readNBytes(length);
        return new String(content, StandardCharsets.US_ASCII);
    }

    private int readUnsignedByte() throws IOException {
        return Byte.toUnsignedInt(inputStream.readByte());
    }

    private int readLength() throws IOException {
        final var first = readUnsignedByte();

        return first & 0b0011_1111;
    }
}
