package serial;

import type.KValue;
import util.TrackedOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Serializer {

    private final OutputStream outputStream;

    public Serializer(TrackedOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void write(KValue value) throws IOException {
        final var messageSize = value.getMessageSize();
        final var correlationId = value.getCorrelationId();
        final var errorCode = value.getErrorCode();


        outputStream.write(messageSize);
        outputStream.write(correlationId);
        outputStream.write(errorCode);
    }
}
