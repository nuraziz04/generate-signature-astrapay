package signatureastrapay.service.model.request;

public class SignatureServiceReq {
    private String method;
    private String pathUrl;
    private TokenReq paramGenerateToken;
    private Object paramSnapBI;

    public SignatureServiceReq(String method, String pathUrl, TokenReq paramGenerateToken, Object paramSnapBI) {
        this.method = method;
        this.pathUrl = pathUrl;
        this.paramGenerateToken = paramGenerateToken;
        this.paramSnapBI = paramSnapBI;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public TokenReq getParamGenerateToken() {
        return paramGenerateToken;
    }

    public void setParamGenerateToken(TokenReq paramGenerateToken) {
        this.paramGenerateToken = paramGenerateToken;
    }

    public Object getParamSnapBI() {
        return paramSnapBI;
    }

    public void setParamSnapBI(Object paramSnapBI) {
        this.paramSnapBI = paramSnapBI;
    }
}
