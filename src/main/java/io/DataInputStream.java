package io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class DataInputStream implements DataInput {

    private final java.io.DataInputStream delegate;

    public DataInputStream(InputStream in) {
        this.delegate = new java.io.DataInputStream(in);
    }

    public ByteBuffer readNBytes(int n) throws IOException {
        return ByteBuffer.wrap(delegate.readNBytes(n));
    }

    @Override
    public byte peekByte() throws IOException {
        delegate.mark(1);
        byte value = delegate.readByte();
        delegate.reset();

        return value;
    }

    @Override
    public byte readSignedByte() throws IOException {
        return delegate.readByte();
    }

    @Override
    public short readSignedShort() throws IOException {
        return delegate.readShort();
    }

    @Override
    public int readSignedInt() throws IOException {
        return delegate.readInt();
    }

    @Override
    public long readSignedLong() throws IOException {
        return delegate.readLong();
    }
}
