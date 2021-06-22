package com.moneycounter.exchangeservice.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.moneycounter.exchangeservice.model.ConversionDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.ArrayList;

@Service
public class PolishConversionService implements ConversionService {

    @Override
    public ConversionDTO convert(ConversionDTO conversionDTO) {
        RestTemplate restTemplate = new RestTemplate();

        String origin = "PLN";
        double firstRate;
        double secondRate;

        if (!origin.equals(conversionDTO.getFrom())) {
            URI uri = URI.create("http://api.nbp.pl/api/exchangerates/rates/A/" + conversionDTO.getFrom() + "/?format=json");
            Rates rates = restTemplate.getForObject(uri, Rates.class);
            if (rates == null) {
                return null;
            }
            firstRate = rates.getRates().get(0).getMid();
        } else {
            firstRate = 1;
        }
        if (!origin.equals(conversionDTO.getTo())) {
            URI uri = URI.create("http://api.nbp.pl/api/exchangerates/rates/A/" + conversionDTO.getTo() + "/?format=json");
            Rates rates = restTemplate.getForObject(uri, Rates.class);
            if (rates == null) {
                return null;
            }
            secondRate = rates.getRates().get(0).getMid();
        } else {
            secondRate = 1;
        }
        conversionDTO.setCount(firstRate/secondRate);

        return conversionDTO;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    private static class Rates {
        ArrayList<PolishConversionService.Rate> rates;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    private static class Rate {

        double mid;

    }

}
