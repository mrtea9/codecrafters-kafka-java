package type;

import java.util.ArrayList;
import java.util.List;

public class KValue {

    private int apiKey;
    private int apiVersion;
    private int correlationId;
    private int errorCode;

    public void setApiKey(int apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
        this.errorCode = (apiVersion > 0 && apiVersion < 5) ? 0 : 35;
    }

    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
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
                "Api Version: %d\n").formatted(correlationId, errorCode, apiKey, apiVersion);
    }
}
