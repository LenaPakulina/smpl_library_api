package com.api.smpl_library_api.service;

import com.api.smpl_library_api.dto.UserDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SecurityConnector {

    @Value("${security.url}")
    private String securityUrl;

    @Value("${connect.timeout:2000}")
    private int connectTimeout = 200;

    private RestTemplate restTemplate;

    @PostConstruct
    public void configureRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        restTemplate = new RestTemplate(factory);
    }

    public boolean checkUser(UserDTO userDTO) {
        HttpEntity<?> body = new HttpEntity<UserDTO>(userDTO);
        var result = restTemplate.exchange(securityUrl, HttpMethod.POST, body, String.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            return true;
        }
        return false;
    }
}
