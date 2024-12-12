package serial;

import type.KValue;
import util.TrackedOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Serializer {

    private final OutputStream outputStream;

    public Serializer(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void write(KValue value) throws IOException {
        final var correlationId = value.getCorrelationId();
        final var errorCode = value.getErrorCode();
        final var apiKey = value.getApiKey();
        final var apiVersion = value.getApiVersion();

        ByteBuffer response = ByteBuffer.allocate(1024);
        response.order(ByteOrder.BIG_ENDIAN);

        ByteBuffer message = ByteBuffer.allocate(1024);
        response.order(ByteOrder.BIG_ENDIAN);

        message.putInt(correlationId);

        message.putShort((short) errorCode);

        if (errorCode == 0) {
            message.put((byte) 2)
                    .putShort((short) apiKey)
                    .putShort((short) 2)
                    .putShort((short) apiVersion)
                    .put((byte) 0)
                    .putInt(0)
                    .put((byte) 0);
        }

        message.flip();

        byte[] messageBytes = new byte[message.remaining()];
        message.get(messageBytes);

        response.putInt(messageBytes.length);
        response.put(messageBytes);

        outputStream.write(response.array());
    }
}
