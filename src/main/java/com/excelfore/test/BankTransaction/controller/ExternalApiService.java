package com.excelfore.test.BankTransaction.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {

    @Value("${mock.api.base-url}")
    private String mockApiBaseUrl;

    public String getMockData() {
        String url = mockApiBaseUrl + "/externalService";
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }


}

