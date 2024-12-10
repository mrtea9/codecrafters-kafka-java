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


    public String read() throws IOException {
        final var messageSize = inputStream.readNBytes(4);

        System.out.println(ByteBuffer.wrap(messageSize).getInt());

        return "sad";
    }
}
