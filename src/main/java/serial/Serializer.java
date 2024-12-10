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

    public void write() throws IOException {
        outputStream.write(new byte[] {0, 1, 2, 3, 0, 0, 0, 7});
    }
}
