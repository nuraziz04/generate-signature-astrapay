package signatureastrapay.service.service.impl;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringVersion;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import signatureastrapay.service.model.request.SignatureReq;
import signatureastrapay.service.model.request.SignatureServiceReq;
import signatureastrapay.service.model.request.TokenReq;
import signatureastrapay.service.model.response.SignatureRes;
import signatureastrapay.service.model.response.TokenRes;
import signatureastrapay.service.util.CommonUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class SignatureAstrapayImpl implements SignatureAstrapay {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SignatureRes generateSignature(SignatureReq request) {


        String signature = "";

        String clientKey = request.getClientKey();
        String privateKeyString = request.getPrivateKey();
        String timestampDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ").format(ZonedDateTime.now()).replace("+0700","+07:00");

        String reqParam = clientKey + "|" + timestampDate;

        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(privateKey);
            privateSignature.update(reqParam.getBytes(StandardCharsets.UTF_8));
            byte[] signatureBytes = privateSignature.sign();

            signature = Base64.getEncoder().encodeToString(signatureBytes);

            SignatureRes response = new SignatureRes();
            response.setResponseCode("00");
            response.setResponseMessage("Successful");
            response.setTimestamp(timestampDate);
            response.setSignature(signature);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public TokenRes generateToken(SignatureReq signReq, TokenReq request, String xClientKey) {
        // logic manggil service generate signature auth
        SignatureRes signRes = this.generateSignature(signReq);

        // call api generate token astrapay
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-client-key", xClientKey);
        headers.set("x-timestamp", signRes.getTimestamp());
        headers.set("x-signature", signRes.getSignature());

        HttpEntity<TokenReq> requestEntity = new HttpEntity<>(request, headers);
        String url = "https://sandbox.astrapay.com/snap-service/snap/v1.0/access-token/b2b";
        try {
            ResponseEntity<TokenRes> response = restTemplate.postForEntity(url, requestEntity, TokenRes.class);
            if(response.getBody() != null){
                return response.getBody();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SignatureRes generateSignatureService(SignatureReq signReq, SignatureServiceReq signatureServiceReq, String xClientKey) {
        // logic manggil service generate token
        TokenRes tokenRes = this.generateToken(signReq, signatureServiceReq.getParamGenerateToken(), xClientKey);

        try {

            String method = signatureServiceReq.getMethod();
            String pathUrl = signatureServiceReq.getPathUrl();
            String paramSnapBI = CommonUtil.objectToJson(signatureServiceReq.getParamSnapBI());
            String token = tokenRes.getAccessToken();
            String timestampDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ").format(ZonedDateTime.now()).replace("+0700","+07:00");

            // logic untuk extract field from String json
            String paramGenerateToken = CommonUtil.objectToJson(signatureServiceReq.getParamGenerateToken());

            JSONObject jsonObj = new JSONObject(paramGenerateToken);
            String clientSecret = jsonObj.getJSONObject("additionalInfo").getString("clientSecret");

            // LowerCase(HexEncode(SHA-256(Minify([HTTP BODY]))))
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashRB = digest.digest(
                    paramSnapBI.getBytes(StandardCharsets.UTF_8));

            String signRB = Hex.encodeHexString(hashRB).toString().toLowerCase();

            // Format payload untuk men-generate signature service
            String reqParam = method + ":" + pathUrl + ":" + token + ":" + signRB + ":" + timestampDate;

            // HMAC-SHA512
            Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
            SecretKeySpec client_secret = new SecretKeySpec(clientSecret.getBytes(), "HmacSHA512");
            sha512_HMAC.init(client_secret);

            byte[] hash = sha512_HMAC.doFinal(reqParam.getBytes());
            String signature = Base64.getEncoder().encodeToString(hash);

            SignatureRes response = new SignatureRes();

            response.setResponseCode("00");
            response.setResponseMessage("Successful");
            response.setTimestamp(timestampDate);
            response.setSignature(signature);
            response.setAccessToken(tokenRes.getAccessToken());

            System.out.println("Spring Version : " + SpringVersion.getVersion());

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
