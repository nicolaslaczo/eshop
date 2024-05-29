package com.nicolas.eshop.security.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OurUsersDto {

    private Integer id;
    private String name;
    private String city;
}
