package com.example.springboot.mapstruct.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Andy Jiang on 9/2/20.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaginatedQueryParameter {
	private UUID agencyGuid;
    private Integer pageNumber;
    private Integer pageSize;
    private List<Sort> sorts = new ArrayList<>();

    public PaginatedQueryParameter(UUID agencyGuid) {
    	this.agencyGuid=agencyGuid;
    }

    public String getOrderBy() {
        return sorts.stream().map(sort -> parseSortName(sort.getName()) + " " +
          sort.getDirection()).collect(Collectors.joining(","));
    }

    public void setSorts(List<Sort> sorts) {
        List<Sort> nonNullSorts = ListUtils.emptyIfNull(sorts);
        String unsupportedFields = nonNullSorts.stream().map(Sort::getName).filter(name -> !isSupport(name))
          .collect(Collectors.joining(","));
        if (StringUtils.isNotBlank(unsupportedFields)) {
            throw new IllegalArgumentException("These sorted fields not support: " + unsupportedFields);
        }
        if (nonNullSorts.stream().map(Sort::getName).collect(Collectors.toSet()).size() != nonNullSorts.size()) {
            throw new IllegalArgumentException("duplicated sort name!");
        }
        this.sorts = nonNullSorts;
    }

    protected String parseSortName(String name) {
        return name;
    }

    protected boolean isSupport(String name) {
        return true;
    }
}
