package type;

import java.util.Arrays;

public record KValue(byte[] content) {

    private int messageSize = content.


    @Override
    public String toString() {
        return Arrays.toString(content);
    }
}
