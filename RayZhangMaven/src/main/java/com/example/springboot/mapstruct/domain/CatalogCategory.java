package com.example.springboot.mapstruct.domain;

public enum CatalogCategory {
    FOLDER(100),
    WORKFLOW(200),
    BILL_ITEM(300),
    ;
    private final Integer sorting;

    CatalogCategory(Integer sorting) {
        this.sorting = sorting;
    }

    public Integer getSorting() {
        return sorting;
    }
}
