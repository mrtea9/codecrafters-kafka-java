package type;

public class KValue {

    private int apiKey;
    private int apiVersion;
    private int correlationId;
    private int errorCode;
    private KValueType type;

    public void setApiKey(int apiKey) {
        this.apiKey = apiKey;
        this.type = switch (this.apiKey) {
            case 18 -> KValueType.ApiVersion;
            case 75 -> KValueType.DescribeTopic;
            default -> KValueType.Unknown;
        };
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
        this.errorCode = (apiVersion > 0 && apiVersion < 5) ? 0 : 35;
    }

    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    public KValueType getType() {
        return type;
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
        return ("Correlation ID: %d\n" +
                "Error Code: %d\n" +
                "Api Key: %d\n" +
                "Api Version: %d")
                .formatted(correlationId, errorCode, apiKey, apiVersion);
    }
}
