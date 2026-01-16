package com.example.demo.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingDTO {
    String name;
    String unit;
    Float value;

    public void setValueCelsiusToKelvin() {
        this.value = (float) (this.value + 273.15);
        this.unit = "K";
    }
}
