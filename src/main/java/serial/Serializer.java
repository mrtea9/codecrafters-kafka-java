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

        byte[] bytes = new byte[10];

        ByteBuffer buff = ByteBuffer.wrap(bytes);
        buff.put(ByteBuffer.allocate(4).putInt(messageSize));
        buff.put(ByteBuffer.allocate(4).putInt(correlationId));
        buff.put(ByteBuffer.allocate(2).putShort((short) errorCode));

        outputStream.write(buff.array());
    }
}
