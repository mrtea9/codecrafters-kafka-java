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

        System.out.println(value);

        ByteBuffer response = ByteBuffer.allocate(1024);
        response.order(ByteOrder.BIG_ENDIAN);

        ByteBuffer message = ByteBuffer.allocate(1024);
        message.order(ByteOrder.BIG_ENDIAN);

        message.putInt(correlationId);

        if (errorCode == 0) {
            message.putShort((short) 0);
            message.put((byte) 2)
                    .putShort((short) apiVersion)
                    .putShort((short) 3)
                    .putShort((short) 4)
                    .put((byte) 0)
                    .putInt(0)
                    .put((byte) 0);
        } else {
            message.putShort((short) errorCode);
        }

        message.flip();

        byte[] messageBytes = new byte[message.remaining()];
        message.get(messageBytes);

        System.out.println("message byte length: " + messageBytes.length);
        System.out.println("message bytes: " + Arrays.toString(messageBytes));
        System.out.println(Arrays.toString(response.array()));

        response.putInt(messageBytes.length);
        response.put(messageBytes);

        outputStream.write(response.array());
        outputStream.flush();

        //ByteBuffer buffer = ByteBuffer.allocate(12);
        //buffer.putInt(messageSize);
//        buffer.putInt(correlationId);
//        buffer.putShort((short) errorCode);
//        buffer.putShort((short) apiKey);

        //outputStream.write(buffer.array());
    }
}
