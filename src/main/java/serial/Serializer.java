package serial;

import type.KValue;
import util.TrackedOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Serializer {

    private final OutputStream outputStream;

    public Serializer(TrackedOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void write(KValue value) throws IOException {
        short apiKey = 18;      // ApiVersions
        short apiVersion = 4;
        int myCorrelationId = value.getCorrelationId();
        String clientId = "";   // empty client_id

        byte[] clientIdBytes = clientId.getBytes(StandardCharsets.UTF_8);
        short clientIdLength = (short) clientIdBytes.length;

        // Calculate the request length (excluding the length field itself)
        // api_key (2 bytes) + api_version (2 bytes) + correlation_id (4 bytes)
        // + client_id_length (2 bytes) + client_id (N bytes)
        int requestLength = 2 + 2 + 4 + 2 + clientIdBytes.length;

        ByteBuffer buffer = ByteBuffer.allocate(4 + requestLength);
        buffer.putInt(requestLength);          // request_length
        buffer.putShort(apiKey);               // api_key
        buffer.putShort(apiVersion);           // api_version
        buffer.putInt(myCorrelationId);        // correlation_id
        buffer.putShort(clientIdLength);       // client_id_length
        buffer.put(clientIdBytes);             // client_id

        outputStream.write(buffer.array());
        outputStream.flush();
    }
}
