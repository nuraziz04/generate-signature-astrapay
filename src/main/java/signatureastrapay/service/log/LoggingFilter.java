package signatureastrapay.service.log;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);


        long startTime = System.currentTimeMillis();
        long timeTaken = System.currentTimeMillis() - startTime;

        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);

        String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getStringValue(contentCachingResponseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        LOGGER.info("Filter Logs : METHOD = {}; REQUEST_URI = {}; X-CLIENT-KEY = {}; X-PRIVATE-KEY = {}; REQUEST BODY = {}; RESPONSE CODE = {}; RESPONSE BODY = {}; TIME TAKEN = {}", request.getMethod(), request.getRequestURI(), request.getHeader("x-client-key"), request.getHeader("x-private-key"), requestBody, response.getStatus(), responseBody, timeTaken);

        contentCachingResponseWrapper.copyBodyToResponse();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {

        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
