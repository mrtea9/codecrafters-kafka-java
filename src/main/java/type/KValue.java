package type;

import java.util.ArrayList;
import java.util.List;

public class KValue {

    private final int messageSize;
    private final int apiKey;
    private final int apiVersion;
    private final int correlationId;
    private final int errorCode;

    private List<Integer> content = new ArrayList<>();

    public KValue(List<Integer> content) {
        this.messageSize = content.get(0);
        this.apiKey = content.get(1);
        this.apiVersion = content.get(2);
        this.correlationId = content.get(3);
        this.errorCode = (apiVersion > 0 && apiVersion < 5) ? 0 : 35;

        this.content = List.of(messageSize, apiKey, apiVersion, correlationId, errorCode);
        content.clear();
    }

    public List<Integer> getContent() {
        return content;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getApiKey() {
        return apiKey;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
