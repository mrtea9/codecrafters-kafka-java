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

        var bos = new ByteArrayOutputStream();
        // size 32bit
        // written directly to output stream below
        //            bos.write(new byte[]{0, 0, 0, 0});
        // correlation id 32bit

        bos.write(new byte[] {0, 0, 0, 8});
        // tagged fields nullable
        //            bos.write(0); // tagged fields
        // request specific data
        // error code 16bit
        // APIVersions (v4)
        if (apiVersion < 0 || apiVersion > 4) {

            // error code 16bit
            bos.write(new byte[] {0, 35});
        } else {
            // error code 16bit
            //    api_key => INT16
            //    min_version => INT16
            //    max_version => INT16
            //  throttle_time_ms => INT32
            bos.write(new byte[] {0, 0});       // error code
            bos.write(2);                       // array size + 1
            bos.write(new byte[] {0, 18});      // api_key
            bos.write(new byte[] {0, 3});       // min version
            bos.write(new byte[] {0, 4});       // max version
            bos.write(0);                       // tagged fields
            bos.write(new byte[] {0, 0, 0, 0}); // throttle time
            // All requests and responses will end with a tagged field buffer.  If
            // there are no tagged fields, this will only be a single zero byte.
            bos.write(0); // tagged fields
        }
        // error message nullable string
        // tagged fields nullable
        int size = bos.size();
        byte[] sizeBytes = ByteBuffer.allocate(4).putInt(size).array();
        var response = bos.toByteArray();
        System.out.println(Arrays.toString(sizeBytes));
        System.out.println(Arrays.toString(response));
        outputStream.write(sizeBytes);
        outputStream.write(response);
        outputStream.flush();
    }
}
