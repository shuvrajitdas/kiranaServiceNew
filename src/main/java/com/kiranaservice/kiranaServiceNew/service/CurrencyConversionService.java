package com.kiranaservice.kiranaServiceNew.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
public class CurrencyConversionService {
    public double convertToInr(double amount, String fromCurrency) {
        if(fromCurrency.equals("USD")) {
            return amount * 83.0777943633;
        }
        return 0;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class ConversionRate {
    private Map<String, Double> rates;
}


