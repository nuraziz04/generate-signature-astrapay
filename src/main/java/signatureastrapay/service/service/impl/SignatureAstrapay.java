package signatureastrapay.service.service.impl;


import signatureastrapay.service.model.request.SignatureReq;
import signatureastrapay.service.model.request.SignatureServiceReq;
import signatureastrapay.service.model.request.TokenReq;
import signatureastrapay.service.model.response.SignatureRes;
import signatureastrapay.service.model.response.TokenRes;

public interface SignatureAstrapay {
    public SignatureRes generateSignature(SignatureReq request);
    public TokenRes generateToken(SignatureReq signReq, TokenReq request, String xClientKey);
    public SignatureRes generateSignatureService(SignatureReq signReq, SignatureServiceReq signatureServiceReq, String xClientKey);
}
