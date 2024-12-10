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
        final byte[] first = inputStream.readNBytes(4);
        System.out.println(new String(first));


        final StringBuilder builder = new StringBuilder();

        int value;
        while ((value = inputStream.read()) != -1) {
            System.out.println(value);
            builder.append((char) value);
        }

        return builder.toString();
    }
}
