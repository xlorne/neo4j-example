package com.codingapi.neo4j.example.llm;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public final class Ollama {

    private final String url;

    private final String model;
    private final float temperature;
    private final RestTemplate restTemplate = new RestTemplate();

    public Ollama(String url,  String model, float temperature) {
        this.model = model;
        this.temperature = temperature;
        this.url = url;
    }


    public Ollama( String model) {
        this("http://localhost:11434/api/generate",  model, 0.1f);
    }

    public String generate(String message) {
        Request request = new Request(model,false,message,temperature);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json;charset=UTF-8");
        HttpEntity<String> requestEntity = new HttpEntity<>(JSONObject.toJSONString(request),httpHeaders);
        ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Response.class);
        return Objects.requireNonNull(response.getBody()).getResponse();
    }

    @Setter
    @Getter
    private static class Response{
        private String created;
        private String model;
        private String response;
    }

    @Setter
    @Getter
    private static class Request {
        private String model;
        private boolean stream;
        private String prompt;
        private Options options;

        public Request(String model, boolean stream, String prompt, float temperature) {
            this.model = model;
            this.stream = stream;
            this.prompt = prompt;
            this.options = new Options(temperature);
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    private static class Options{
        private float temperature;
    }

}
