package com.moneycounter.exchangeservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversionDTO {

    private String countryCode;

    private String from;

    private String to;

    private double count;
}
