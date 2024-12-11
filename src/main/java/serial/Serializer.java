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

        // Calculate total response size:
        // correlationId: 4 bytes
        // errorCode: 2 bytes
        // numberOfApiVersions: 4 bytes
        // no ApiVersions entries since numberOfApiVersions = 0
        // throttleTimeMs: 4 bytes
        // Total = 4 + 2 + 4 + 4 = 14 bytes
        int responseSize = 14;

        dos.writeInt(responseSize);     // total size excluding these 4 bytes
        dos.writeInt(correlationId);    // correlationId
        dos.writeShort(errorCode);      // errorCode
        dos.writeInt(numberOfApiVersions); // numberOfApiVersions = 0
        dos.writeInt(throttleTimeMs);   // throttleTimeMs = 0
        dos.flush();
    }
}
