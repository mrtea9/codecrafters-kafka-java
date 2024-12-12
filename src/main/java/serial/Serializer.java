package serial;

import type.KValue;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Serializer {

    private final OutputStream outputStream;

    public Serializer(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void write(KValue value) throws IOException {
        final var response = createResponse(value);

        outputStream.write(response);
    }

    private byte[] createResponse(KValue value) {
        final var header = createHeader(value.getCorrelationId());
        final var body = createBody(value);

        ByteBuffer response = ByteBuffer.allocate(4 + header.length + body.length);
        response.order(ByteOrder.BIG_ENDIAN);

        response.putInt(header.length + body.length);
        response.put(header);
        response.put(body);

        return response.array();
    }

    private byte[] createHeader(int correlationId) {
        ByteBuffer header = ByteBuffer.allocate(4);
        header.putInt(correlationId);

        return header.array();
    }

    private byte[] createBody(KValue value) {
        final var errorCode = value.getErrorCode();

        ByteBuffer body = ByteBuffer.allocate(28);

        body.putShort((short) errorCode);

        if (errorCode == 0) {
            final var apiKey = value.getApiKey();
            final var apiVersion = value.getApiVersion();

            body.put((byte) 3);
            body.putShort((short) apiKey);
            body.putShort((short) 2);
            body.putShort((short) apiVersion);
            body.putShort((short) 75);
            body.putShort((short) 0);
            body.putShort((short) 0);
            body.put((byte) 0);
            body.putInt(0);
            body.put((byte) 0);
        }

        body.flip();

        byte[] bodyBytes = new byte[body.remaining()];
        body.get(bodyBytes);

        return bodyBytes;
    }
}
