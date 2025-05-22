package com.az1.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
@Service
public class ApiHandleService {
    //    http://mt-api-v2:9000/api/
    private String accessToken;

    @PostConstruct
    public void init() {
        updateToken();
    }

    public void updateToken(){
        try {
            String systemHeader = "Basic YWRtaW46RGVhaGFuU2khQDEyMw==";
            String systemHost = "http://192.168.1.61:6969/api/generate-token?appId=MT&grantType=PASSWORD_GRANT&secret=753f03e5-378a-4fa8-a343-f8dcb0bf6961";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", systemHeader);

            headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
            // Yêu cầu trả về định dạng JSON
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HttpEntity<String>: To get result as String.
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            // RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Gửi yêu cầu với phương thức GET, và các thông tin Headers.
            ResponseEntity<String> response = restTemplate.getForEntity(systemHost, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());
            JsonNode dataNode = rootNode.get("data");
            this.accessToken = dataNode != null ? dataNode.get("accessToken").asText() : null;

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error occurred with status code: " + ex.getStatusCode().value());
            System.err.println("Response body: " + ex.getResponseBodyAsString());
            this.accessToken = null;
        } catch (Exception ex) {
            System.err.println("General error: " + ex.getMessage());
            this.accessToken = null;
        }
    }

    public void getStations(int limit){
        if (this.accessToken == null || this.accessToken.isEmpty()) {
            System.err.println("Token không hợp lệ.");
            this.updateToken();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.1.61:9002/api/station?page=0&pageSize=" + limit;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());
            JsonNode dataNode = rootNode.get("data");

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Lỗi gọi API: " + ex.getStatusCode());
            System.err.println("Phản hồi lỗi: " + ex.getResponseBodyAsString());
//            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            System.err.println("Lỗi tổng quát: " + ex.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống");
        }
    }
}
