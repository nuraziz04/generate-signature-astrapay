package signatureastrapay.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {

    public static String objectToJson(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = "";
        try {
            jsonData = objectMapper.writeValueAsString(data);
        } catch (Exception exception) {
            jsonData = exception.getMessage();
        }
        return jsonData;
    }

}
