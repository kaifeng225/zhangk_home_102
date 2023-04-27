package com.example.springboot.mapstruct.domain;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatalogEntity  {
    private Long id;
    private String name;
    private CatalogCategory category;
    private Long productId;
    private Boolean isDisplayCui;
    private Integer sorting;
    private Boolean isDeleted;
    private UUID agencyGuid;
    private UUID catalogGuid;
    private Boolean linkFutureLocations;
    
    private String type;
    
}
