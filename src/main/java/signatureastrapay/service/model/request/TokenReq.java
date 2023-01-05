package signatureastrapay.service.model.request;

import java.util.Map;

public class TokenReq {
    private String grantType;
    private Object additionalInfo;

    public TokenReq(String grantType, Object additionalInfo) {
        this.grantType = grantType;
        this.additionalInfo = additionalInfo;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public Object getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Object additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
