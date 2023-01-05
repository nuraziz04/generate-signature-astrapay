package signatureastrapay.service.model.response;

public class SignatureRes {
    private String responseCode;
    private String responseMessage;
    private String timestamp;
    private String signature;
    private String accessToken;

    public SignatureRes() {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.timestamp = timestamp;
        this.signature = signature;
        this.accessToken = accessToken;
    }

    public String getResponseCode() {

        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
