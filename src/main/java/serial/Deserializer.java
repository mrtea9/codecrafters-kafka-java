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
//        //final var test = inputStream.readNBytes(4);
//        final var dataInputStream = new DataInputStream(inputStream);
//        //ByteBuffer wrap = ByteBuffer.wrap(test);
//        System.out.println("message size " + dataInputStream.readInt());
//        System.out.println("api_key " + dataInputStream.readInt());


        final StringBuilder builder = new StringBuilder();

        int value;
        while ((value = inputStream.read()) != -1) {
            //System.out.println("value = " + value + "; char value = " + (char) value);
            builder.append((char) value);
        }

        //System.out.println(builder);
        return builder.toString();
    }
}
