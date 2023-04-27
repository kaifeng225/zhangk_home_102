package com.example.springboot.mapstruct.domain;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class CatalogListResp {
    private UUID agencyGuid;
    private UUID catalogGuid;
    private String refId;
    private Long parentId;
    private String name;
    private Integer sorting;
    private CatalogCategory category;
    private String type;
    private String description;
    private String extraAttrs;
    private BigDecimal amount;
}
