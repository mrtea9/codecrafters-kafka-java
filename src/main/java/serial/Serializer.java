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

        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putInt(messageSize);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos.write(messageSize);
        baos.write(correlationId);
        baos.write(errorCode);
        baos.write(apiKey);
        baos.write(apiVersion);

        byte[] requestBytes = baos.toByteArray();

        System.out.println(Arrays.toString(requestBytes));

        outputStream.write(requestBytes);
        outputStream.flush();
    }
}
