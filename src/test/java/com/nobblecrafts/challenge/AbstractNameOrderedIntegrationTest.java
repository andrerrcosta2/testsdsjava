package com.nobblecrafts.challenge;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nobblecrafts.challenge.util.JsonUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.google.gson.reflect.TypeToken;

import java.util.List;

@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AbstractNameOrderedIntegrationTest {

    @LocalServerPort
    protected Integer localPort;

    @Autowired
    protected TestRestTemplate rest;

    protected <DTO> DTO convertJsonToEntity(String json, Class<DTO> clazz) {
        Gson gson = new Gson();
        DTO entity = gson.fromJson(json, clazz);
        return entity;
    }

    protected <DTO> List<DTO> convertJsonToEntityList(String json, Class<DTO> clazz) {
        Gson gson = new Gson();
        List<DTO> entities = gson.fromJson(json, new TypeToken<List<DTO>>() {
        }.getType());
        return entities;
    }

    protected String convertEntityToJson(Object object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(object);

        JsonElement je = JsonParser.parseString(jsonString);
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;
    }

    protected String prettifyJsonString(String json) {
        return JsonUtils.prettifyJsonString(json);
    }

    protected String getDataFromJson(String json, String... patterns) {
        return JsonUtils.extractFromJson(json, patterns);
    }

//    public static class RestRequestBuilder {
//        private final TestRestTemplate restTemplate;
//        private HttpHeaders headers;
//        private Object requestBody;
//        private Class<?> responseClass;
//
//        public RestRequestBuilder(TestRestTemplate restTemplate) {
//            this.restTemplate = restTemplate;
//            this.headers = new HttpHeaders();
//        }
//
//        public RestRequestBuilder header(String name, String value) {
//            this.headers.add(name, value);
//            return this;
//        }
//
//        public RestRequestBuilder send(Object requestBody) {
//            this.requestBody = requestBody;
//            return this;
//        }
//
//        public RestRequestBuilder retrieves(Class<?> responseClass) {
//            this.responseClass = responseClass;
//            return this;
//        }
//
//        public ResponseEntity<?> execute(String url, HttpMethod method) {
//            System.out.println(String.format("\n\nURL: %s, METHOD: %s\n\n", url, method));
//            HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);
//            ResponseEntity<?> output = restTemplate.exchange(url, method, requestEntity, responseClass);
//            this.clear();
//            return output;
//        }
//
//        private void clear() {
//            this.headers = new HttpHeaders();
//            this.requestBody = null;
//            this.responseClass = null;
//        }
//    }
}
