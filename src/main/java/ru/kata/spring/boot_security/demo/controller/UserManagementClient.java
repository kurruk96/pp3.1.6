package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public class UserManagementClient {

    static RestTemplate restTemplate = new RestTemplate();
    static String baseUrl = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        useExchangeMethodsOfRestTemplate();

    }

    private static void useExchangeMethodsOfRestTemplate() {
        String result = "";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        String cookie = getListUserByExchangeMethod(requestEntity);
        headers.add("Cookie", cookie);

        User sysUser = new User(3L, "James", "Brown", 0);
        HttpEntity<User> requestEntity0 = new HttpEntity<>(sysUser, headers);

        result += addUserByExchangeMethod(requestEntity0);

        sysUser.setName("Thomas");
        sysUser.setLastName("Shelby");
        result += updateUserByExchangeMethod(requestEntity0);

        result += deleteUserByExchangeMethod(requestEntity0);
        System.out.println(result);
    }

    private static String deleteUserByExchangeMethod(HttpEntity<User> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + "/3",
                HttpMethod.DELETE,
                requestEntity,
                String.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("status code - " + statusCode);
        String userDetails = responseEntity.getBody();
        System.out.println("response body - " + userDetails);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("response Headers - " + responseHeaders);
        return userDetails;
    }

    private static String updateUserByExchangeMethod(HttpEntity<User> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl,
                HttpMethod.PUT,
                requestEntity,
                String.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("status code - " + statusCode);
        String userDetails = responseEntity.getBody();
        System.out.println("response body - " + userDetails);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("response Headers - " + responseHeaders);
        return userDetails;
    }

    private static String addUserByExchangeMethod(HttpEntity<User> requestEntity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("status code - " + statusCode);
        String userDetails = responseEntity.getBody();
        System.out.println("response body - " + userDetails);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("response Headers - " + responseHeaders);
        return userDetails;
    }


    private static String getListUserByExchangeMethod(HttpEntity<Object> requestEntity) {
        ResponseEntity<List> responseEntity = restTemplate.exchange(baseUrl,
                HttpMethod.GET,
                requestEntity,
                List.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        System.out.println("status code - " + statusCode);
        List user = responseEntity.getBody();
        System.out.println("response body - " + user);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        System.out.println("response Headers - " + responseHeaders);
        String cookie = responseHeaders.getFirst("Set-Cookie");
        return cookie;
    }
}

