package com.nicolas.eshop.admin.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
