package com.example.springboot.mapstruct.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
 
    private String make;
    private int seatCount;
    private String type;
    private String createBy;
}