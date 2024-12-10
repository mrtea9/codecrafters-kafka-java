package serial;

import util.TrackedInputStream;

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
        final var test = inputStream.readNBytes(4);
        ByteBuffer wrap = ByteBuffer.wrap(test);
        System.out.println("message size " + wrap.getInt());


        final StringBuilder builder = new StringBuilder();

        int value;
        while ((value = inputStream.read()) != -1) {
            System.out.println(value);
            builder.append((char) value);
        }

        return builder.toString();
    }
}
