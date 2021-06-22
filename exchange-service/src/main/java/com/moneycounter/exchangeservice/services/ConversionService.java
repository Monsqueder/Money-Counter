package com.moneycounter.exchangeservice.services;

import com.moneycounter.exchangeservice.model.ConversionDTO;
import org.springframework.stereotype.Service;

@Service
public interface ConversionService {

    ConversionDTO convert(ConversionDTO conversionDTO);

}
