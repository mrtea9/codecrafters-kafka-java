package io;

import java.io.IOException;
import java.io.OutputStream;

public class DataOutputStream implements DataOutput, AutoCloseable {

    private final java.io.DataOutputStream delegate;

    public DataOutputStream(OutputStream out) {
        this.delegate = new java.io.DataOutputStream(out);
    }

    @Override
    public void writeRawBytes(byte[] bytes) throws IOException {
        delegate.write(bytes);
    }

    @Override
    public void writeByte(byte value) throws IOException {
        delegate.write(value);
    }

    @Override
    public void writeShort(short value) throws IOException {
        delegate.writeShort(value);
    }

    @Override
    public void writeInt(int value) throws IOException {
        delegate.writeInt(value);
    }

    @Override
    public void writeLong(long value) throws IOException {
        delegate.writeLong(value);
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }
}
