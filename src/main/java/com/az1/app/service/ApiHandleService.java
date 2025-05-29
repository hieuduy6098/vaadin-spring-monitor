package com.az1.app.service;

import com.az1.app.model.AssistantRequestModel;
import com.az1.app.model.BaseResponseModel;
import com.az1.app.model.StationModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ApiHandleService {
//    private String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJleHAiOjE3NDgyNzM2OTEsInNjb3BlIjpbIlNZU1RFTS5TRU5ETUFJTCIsIkNBTUVSQS5FRElUIiwiU1lTVEVNLkVESVQiLCJVU0VSUy5FRElUIiwiVVNFUlMuVklFVyIsIlNZU1RFTS5WSUVXIiwiRVhDRUwuRVhQT1JUIiwiVVNFUi5QVUJMSUMiXSwicm9sZSI6WyJDQlRUX01UIiwiTVRfQURNSU4iXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJqdGkiOiIyNmRhNzdmYS0wOTZiLTRiZTYtOWEwOC03NjQxODNkNTgwYWEifQ.gEcy3RSpjv7721LIPWwHu1hkBMXMePAxEpEwc0O9oz63VLULl8jlUJgRr_bLgEZuQfGTq2lVzY7a1qovPBldFrY5KfNHsqDJm0OGvRqtySgyZxbQCZVV6Fum3OBdWbElJ6NZROZ60W7YgChyGY0Qun8-8C-rXccjqTH0ZSh3iYREx4g0nOx5U21qYSqXVRJL2UCbkT9Qi4niGFjGsy9Kb-oKg6_m9BulU4ymzdyZmBksI8SfYio0VSAPBb-PzgBBjyF9iMP6ExEji32ewm-uPsUpgexWgjYma-WmUS2lqYo33gzwNPf6J2a5GVGg3zBPAyWPP1Wwke3NIX2xgSZgmQ";
    private String accessToken;
    @PostConstruct
    public void init() {
        updateToken();
    }
//    @PostConstruct
    public void updateToken(){
        try {
            String systemHeader = "Basic YWRtaW46RGVhaGFuU2khQDEyMw==";
            String systemHost = "http://192.168.1.61:6969/api/generate-token?appId=MT&grantType=PASSWORD_GRANT&secret=753f03e5-378a-4fa8-a343-f8dcb0bf6961";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", systemHeader);

//            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            // Yêu cầu trả về định dạng JSON
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HttpEntity<String>: To get result as String.
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            // RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Gửi yêu cầu với phương thức GET, và các thông tin Headers.
            ResponseEntity<String> response =restTemplate.exchange(systemHost,HttpMethod.GET,entity, String.class);

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
//    @PostConstruct
    public List<StationModel> getStations(){
        if (this.accessToken == null || this.accessToken.isEmpty()) {
            System.err.println("Token không hợp lệ.");
            this.updateToken();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.1.61:9002/api/station?page=0&pageSize=" + "10";

        try {
            ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.GET,entity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());
            JsonNode dataNode = rootNode.get("data").get("items");
            return mapper.readValue(dataNode.toString(), new TypeReference<List<StationModel>>() {});

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Lỗi gọi API: " + ex.getStatusCode());
            System.err.println("Phản hồi lỗi: " + ex.getResponseBodyAsString());
            return new ArrayList<>();
        } catch (Exception ex) {
            System.err.println("Lỗi tổng quát: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public String getResponseAi(String message){
//        if (this.accessToken == null || this.accessToken.isEmpty()) {
//            this.updateToken();
//        }
        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(this.accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        AssistantRequestModel bodyData = new AssistantRequestModel(message);
        HttpEntity<AssistantRequestModel> entity = new HttpEntity<>(bodyData,headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/api/test";

        try {
            ResponseEntity<BaseResponseModel<String>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<BaseResponseModel<String>>() {}
            );

            BaseResponseModel<String> responseMessage = response.getBody();
            assert responseMessage != null;
            return responseMessage.getData();
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode rootNode = mapper.readTree(response.getBody());
//            JsonNode dataNode = rootNode.get("data").get("items");

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Lỗi gọi API: " + ex.getStatusCode());
            System.err.println("Phản hồi lỗi: " + ex.getResponseBodyAsString());
            return "";
        } catch (Exception ex) {
            System.err.println("Lỗi tổng quát: " + ex.getMessage());
            return "";
        }
    }
}
