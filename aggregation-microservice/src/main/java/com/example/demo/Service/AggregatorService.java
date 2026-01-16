package com.example.demo.Service;

import com.example.demo.Controller.ReadingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregatorService {
    @Autowired
    private  TemperatureClient temperatureClient;

    @Autowired
    private HumidityClient humidityClient;

    public List<ReadingDTO> getReadings() {

        ReadingDTO humidity = humidityClient.getHumidity();
        ReadingDTO temperature = temperatureClient.getTemperature();

        return List.of(humidity, temperature);
    }
}
