package com.example.springboot.mapstruct.domain;

import lombok.Data;

/**
 * Created by Andy Jiang on 9/9/20.
 */
@Data
public class Sort {
    private static final String SEPARATOR = ",";
    private String name;
    private Direction direction;

    public Sort(String sortString) {
        String[] splits = sortString.split(SEPARATOR);
        if (splits.length != 2) {
            throw new IllegalArgumentException("sort field pattern should be like name,ASC or name,DESC");
        }
        this.name = splits[0];
        this.direction = Direction.valueOf(splits[1].toUpperCase());
    }

    public enum Direction {
        ASC, DESC
    }

}
