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

        DataInputStream dis = new DataInputStream(inputStream);

        final int messageSize = dis.readInt();
        final int apiKey = dis.readShort();
        final int apiVersion = dis.readShort();
        final int correlationId = dis.readInt();

        final var remaining = new byte[messageSize - 8];
        dis.readFully(remaining);

        System.out.println("message size = " + messageSize);

        message.add(messageSize);
        message.add(apiKey);
        message.add(apiVersion);
        message.add(correlationId);

        return new KValue(message);
    }
}
