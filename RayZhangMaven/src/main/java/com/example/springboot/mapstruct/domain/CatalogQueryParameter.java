package com.example.springboot.mapstruct.domain;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CatalogQueryParameter extends PaginatedQueryParameter {
   
    private UUID catalogGuid;
    private UUID locationGuid;
    private UUID assignee;
    private Boolean linkFutureLocations;
    private Boolean isDisplayCui;

   
}
