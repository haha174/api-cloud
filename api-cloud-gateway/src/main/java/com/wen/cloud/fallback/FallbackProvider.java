package com.wen.cloud.fallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.cloud.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FallbackProvider implements ZuulFallbackProvider {
    @Override
    public String getRoute() {
        return "*";  
    }
    @Autowired
    ObjectMapper objectMapper;
    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR; //
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 500; //
            }

            @Override
            public String getStatusText() throws IOException {
                return "ERROR";  //
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                String josn = objectMapper.writeValueAsString( new BaseResponse(getRawStatusCode()+"","The system is busy. Please try again later"));
                return new ByteArrayInputStream(josn.getBytes());
            }  //

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}