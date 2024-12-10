package serial;

import util.TrackedInputStream;

import java.io.IOException;
import java.io.InputStream;

public class Deserializer {

    private final InputStream inputStream;

    public Deserializer(TrackedInputStream inputStream) {
        this.inputStream = inputStream;
    }


    public String read() throws IOException {
        final int first = inputStream.read();
        if (first == -1) return null;

        final StringBuilder builder = new StringBuilder((char) first);

        int value;
        while ((value = inputStream.read()) != -1) {
            builder.append((char) value);
        }

        return builder.toString();
    }
}
