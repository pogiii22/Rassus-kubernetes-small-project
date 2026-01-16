package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float temperature;
    private Float pressure;
    private Float humidity;
    private Float co;
    private Float no2;
    private Float so2;

    public Reading(Float temperature, Float pressure,
                   Float humidity, Float co, Float no2,
                   Float so2) {
        this.so2 = so2;
        this.no2 = no2;
        this.co = co;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
    }
}
