package com.example.demo.Service;

import com.example.demo.Controller.ReadingDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class HumidityClient {
    private final RestClient restClient;

    public HumidityClient(@Value("${humidity.service.url}") String humidityServiceUrl) {
        this.restClient = RestClient.create(humidityServiceUrl);
    }

    public ReadingDTO getHumidity() {
        return restClient.get()
                .uri("/humidity")
                .retrieve()
                .body(ReadingDTO.class);
    }
}
