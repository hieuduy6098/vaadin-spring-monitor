package com.az1.app.service;

import com.az1.app.model.AssistantRequestModel;
import com.az1.app.model.BaseResponseModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiHandleService {

    public String getResponseAi(String message){
        HttpHeaders headers = new HttpHeaders();
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

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Lỗi gọi API: " + ex.getStatusCode());
            System.err.println("Phản hồi lỗi: " + ex.getResponseBodyAsString());
            return "";
        }
        catch (Exception ex) {
            System.err.println("Lỗi tổng quát: " + ex.getMessage());
            return "";
        }
    }
}
