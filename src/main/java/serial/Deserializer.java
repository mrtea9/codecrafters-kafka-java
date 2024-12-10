package serial;

import util.TrackedInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Deserializer {

    private final InputStream inputStream;

    public Deserializer(TrackedInputStream inputStream) {
        this.inputStream = inputStream;
    }


    public String read() throws IOException {
        final var test = new String(inputStream.readNBytes(4), StandardCharsets.US_ASCII);
        System.out.println("message size " + test);


        final StringBuilder builder = new StringBuilder();

        int value;
        while ((value = inputStream.read()) != -1) {
            System.out.println(value);
            builder.append((char) value);
        }

        return builder.toString();
    }
}
