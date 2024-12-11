package serial;

import type.KValue;
import util.TrackedOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

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
        final var apiKey = value.getApiKey();
        final var apiVersion = value.getApiVersion();

        DataOutputStream dos = new DataOutputStream(outputStream);
        int numberOfApiVersions = 0;
        int throttleTimeMs = 0;

        // The total size of the fields after the length field is 14 bytes
        dos.writeInt(14);                 // length
        dos.writeInt(correlationId);      // correlationId
        dos.writeShort(errorCode);        // errorCode
        dos.writeInt(numberOfApiVersions);// numberOfApiVersions = 0
        dos.writeInt(throttleTimeMs);     // throttleTimeMs = 0

        dos.flush();
    }
}
