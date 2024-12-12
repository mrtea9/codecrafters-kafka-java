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

    public Deserializer(TrackedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public KValue read() throws IOException {
        var message = new ArrayList<Integer>();

//        final var dataInputStream = new DataInputStream(inputStream);
//
//        final int messageSize = dataInputStream.readInt();
//        final int apiKey = dataInputStream.readShort();
//        final int apiVersion = dataInputStream.readShort();
//        final int correlationId = dataInputStream.readInt();
//
//        message.add(messageSize);
//        message.add(apiKey);
//        message.add(apiVersion);
//        message.add(correlationId);

        final var test = inputStream.readAllBytes();

        System.out.println(Arrays.toString(test));

        //System.out.println(message);

        return new KValue(message);
    }
}
