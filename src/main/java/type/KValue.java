package type;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public record KValue(List<Integer> content) {

    @Override
    public String toString() {
        return content.toString();
    }
}
