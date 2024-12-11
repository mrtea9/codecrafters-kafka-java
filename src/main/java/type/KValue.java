package type;

import java.util.List;

public class KValue {

    private final int messageSize;
    private final int apiKey;
    private final int apiVersion;
    private final int correlationId;
    private final int errorCode;

    public KValue(List<Integer> content) {
        this.messageSize = content.get(0);
        this.apiKey = content.get(1);
        this.apiVersion = content.get(2);
        this.correlationId = content.get(3);
        this.errorCode = (apiVersion > 0 && apiVersion < 5) ? 0 : 35;
    }

    @Override
    public String toString() {
        final var content = List.of(messageSize, apiKey, apiVersion, correlationId, errorCode);

        return content.toString();
    }
}
