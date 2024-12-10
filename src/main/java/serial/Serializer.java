package serial;

import util.TrackedOutputStream;

import java.io.IOException;
import java.io.OutputStream;

public class Serializer {

    private final OutputStream outputStream;

    public Serializer(TrackedOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void write(byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }
}
