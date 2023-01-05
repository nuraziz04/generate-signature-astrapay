package signatureastrapay.service.model.request;

public class SignatureReq {
    private String clientKey;
    private String privateKey;

    public SignatureReq(String clientKey, String privateKey) {
        this.clientKey = clientKey;
        this.privateKey = privateKey;
    }

    public SignatureReq() {

    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
