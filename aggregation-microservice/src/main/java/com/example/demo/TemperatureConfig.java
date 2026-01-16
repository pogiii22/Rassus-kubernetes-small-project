package com.example.demo;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "temperature")
public class TemperatureConfig {

    private String unit = "K"; // default

    public void setUnit(String unit) {
        System.out.println("POSTAVLJAM UNIT IZ PROPERTIES: " + unit);
        this.unit = unit.toUpperCase();
        System.out.println("POSTAVLJEN UNIT IZ PROPERTIES: " + unit);
    }

    public boolean isKelvin() {
        System.out.println("this unit is tested" + unit);
        return "K".equals(this.unit);
    }
}
