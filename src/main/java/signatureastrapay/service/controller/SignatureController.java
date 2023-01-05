package signatureastrapay.service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import signatureastrapay.service.model.request.SignatureReq;
import signatureastrapay.service.model.request.SignatureServiceReq;
import signatureastrapay.service.model.request.TokenReq;
import signatureastrapay.service.model.response.SignatureRes;
import signatureastrapay.service.model.response.TokenRes;
import signatureastrapay.service.service.impl.SignatureAstrapay;


@RestController
public class SignatureController {

    @Autowired
    SignatureAstrapay signatureAstrapay;

    @PostMapping("/astrapay/generateSignatureAuth")
    public ResponseEntity<Object> generateSignature(@RequestBody SignatureReq request) {

        try {
            SignatureRes res = signatureAstrapay.generateSignature(request);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/astrapay/generateToken")
    public  ResponseEntity<Object> generateToken(
            @RequestHeader(value = "x-client-key") String xCLientKey,
            @RequestHeader(value = "x-private-key") String xPrivateKey,
            @RequestBody TokenReq tokeReq) {

        SignatureReq signatureReq = new SignatureReq();
        signatureReq.setClientKey(xCLientKey);
        signatureReq.setPrivateKey(xPrivateKey);

        try {
            TokenRes res = signatureAstrapay.generateToken(signatureReq, tokeReq, xCLientKey);

            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }

    }

    @PostMapping("/astrapay/generateSignatureService")
    public ResponseEntity<Object> generateSignatureService(
            @RequestHeader(value = "x-client-key") String xCLientKey,
            @RequestHeader(value = "x-private-key") String xPrivateKey,
            @RequestBody SignatureServiceReq signatureServiceReq
            ) {

        SignatureReq signatureReq = new SignatureReq();
        signatureReq.setClientKey(xCLientKey);
        signatureReq.setPrivateKey(xPrivateKey);

        try {
            SignatureRes signatureServiceRes = signatureAstrapay.generateSignatureService(signatureReq, signatureServiceReq, xCLientKey);

            return new ResponseEntity<>(signatureServiceRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }
}