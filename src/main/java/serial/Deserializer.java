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
        System.out.println("sad");
        if (first == -1) return null;
        System.out.println("sad");

        final StringBuilder builder = new StringBuilder((char) first);

        int value;
        while ((value = inputStream.read()) != -1) {
            System.out.println(value);
            builder.append((char) value);
        }
        System.out.println("sad + " + builder.toString());

        return builder.toString();
    }
}
