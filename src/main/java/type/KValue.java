package type;

import java.nio.ByteBuffer;
import java.util.Arrays;

public record KValue(ByteBuffer content) {

    private int messageSize = content.


    @Override
    public String toString() {
        return Arrays.toString(content.array());
    }
}
