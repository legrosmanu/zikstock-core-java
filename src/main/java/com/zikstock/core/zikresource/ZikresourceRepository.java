package com.zikstock.core.zikresource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ZikresourceRepository extends MongoRepository<Zikresource, String> {
}
