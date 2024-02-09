package com.example.springboot.mapstruct.domain;

import java.util.List;

import lombok.Data;

/**
 * Created by Andy Jiang on 9/26/20.
 */
@Data
public class PageQueryParam {
    private Integer pageNum = 1;
    private Integer pageSize = Integer.MAX_VALUE;
    private List<Sort> sort;
}
