package com.example.springboot.mapstruct.converter;

import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.xmlgraphics.image.loader.spi.ImageConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.example.springboot.mapstruct.domain.CatalogEntity;
import com.example.springboot.mapstruct.domain.CatalogListResp;
import com.example.springboot.mapstruct.domain.CatalogQueryParameter;
import com.example.springboot.mapstruct.domain.CuiCatalogQueryReq;
import com.example.springboot.mapstruct.domain.TransactionSource;
import com.example.springboot.mapstruct.domain.UpdateOrCreateCatalogReq;

@Mapper(uses = ImageConverter.class,
  unmappedTargetPolicy = ReportingPolicy.IGNORE,
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CatalogConverter extends BaseConverter<UpdateOrCreateCatalogReq, CatalogListResp, CatalogEntity> {
    CatalogConverter INSTANCE = Mappers.getMapper(CatalogConverter.class);

    @Override
    @Mapping(target = "isDeleted", source = "req.archived")
    @Mapping(target = "sorting", expression = "java(req.getCategory().getSorting())")
    @Mapping(target = "catalogGuid", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "isDisplayCui", source = "req", qualifiedByName = "toIsDisplayCui")
    CatalogEntity toDomain(UUID agencyGuid, UpdateOrCreateCatalogReq req);

    @Named("toIsDisplayCui")
    default boolean toIsDisplayCui(UpdateOrCreateCatalogReq req) {
        return CollectionUtils.emptyIfNull(req.getSalesChannels()).contains(TransactionSource.CITIZEN_UI);
    }

    @Mapping(target = "pageNumber", source = "catalogQueryReq.pageNum")
    @Mapping(target = "sorts", source = "catalogQueryReq.sort")
    @Mapping(target = "isDisplayCui", constant = "true")
    CatalogQueryParameter toQueryParameter(UUID agencyGuid, CuiCatalogQueryReq catalogQueryReq);
    
}
