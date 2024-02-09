package com.example.springboot.mapstruct.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Let's assume we have a class representing cars (e.g. a JPA entity) and an accompanying data transfer object (DTO)
 * Both types are rather similar, only the seat count attributes have different names and the type attribute is of a special enum type in the Car class but is a plain string in the DTO.
 * @author rzhang2
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
 
    private String make;
    private int numberOfSeats;
    /**
     * Where required and possible a type conversion will be executed for attributes with different types in source and target,
     * e.g. the type attribute will be converted from the enumeration type into a string.
     */
    private CarType type; 
    
    /**
     * test ignore
     */
    private String createBy;
}
