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
        outputStream.write("00000023001200046f7fc66100096b61666b612d636c69000a6b61666b612d636c6904302e3100".getBytes());
    }
}
