package com.example.springboot.mapstruct.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrCreateCatalogReq {
    private String name;    
    private CatalogCategory category;    
    private Long productId;    
    private List<TransactionSource> salesChannels;    
    private Boolean archived;
    private Boolean linkFutureLocations;
    
}
