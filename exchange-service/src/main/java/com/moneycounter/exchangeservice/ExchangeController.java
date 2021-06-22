package com.moneycounter.exchangeservice;

import com.moneycounter.exchangeservice.model.ConversionDTO;
import com.moneycounter.exchangeservice.services.ConversionService;
import com.moneycounter.exchangeservice.services.PolishConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {

    @GetMapping("/")
    public ConversionDTO convert(@RequestBody ConversionDTO conversionDTO) {
        ConversionService conversionService;
        String countryCode = conversionDTO.getCountryCode().toUpperCase();

        switch (countryCode) {
            default:
                conversionService = new PolishConversionService();
        }

        return conversionService.convert(conversionDTO);
    }

}
