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
public class CuiCatalogQueryReq extends PageQueryParam {
    private UUID catalogGuid;
}
