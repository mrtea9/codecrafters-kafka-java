package serial;

import type.KValue;
import util.TrackedOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
        final var apiKey = value.getApiKey();
        final var apiVersion = value.getApiVersion();

        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putInt(messageSize);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        // Write the request_length first
        dos.writeInt(messageSize);

        // Write header and body fields in order
        dos.writeShort(apiKey);
        dos.writeShort(apiVersion);
        dos.writeInt(correlationId);

        dos.flush();  // Ensure all data is written to baos

        // Get the final serialized request
        byte[] requestBytes = baos.toByteArray();

        // Send to the broker
        outputStream.write(requestBytes);
        outputStream.flush();
    }
}
