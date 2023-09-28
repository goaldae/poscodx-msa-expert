package com.poscodx.catalogservice.service;

import com.poscodx.catalogservice.jpa.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
