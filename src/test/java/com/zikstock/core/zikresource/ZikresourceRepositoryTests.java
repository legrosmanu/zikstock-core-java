package com.zikstock.core.zikresource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class ZikresourceRepositoryTests {

    @Autowired
    private ZikresourceRepository mongoTemplate;

    @Test
    public void shouldRetrieveZikresource() throws Exception {
        // Given the id of a resource in the DB
        Zikresource zikresource = new Zikresource();
        Zikresource zikresourceSaved = mongoTemplate.save(zikresource);
        String id = zikresourceSaved.getId();
        // When
        Optional<Zikresource> zikresourceRetrieved = mongoTemplate.findById(id);
        // Then
        assertThat(zikresourceRetrieved).isNotEmpty();
    }

    @Test
    public void shouldNotRetrieveZikresourceUnknown() throws Exception {
        // Given an unknown zikresource, like a bad id
        String id = "aaa";
        // When
        Optional<Zikresource> zikresourceRetrieved = mongoTemplate.findById(id);
        // Then
        assertThat(zikresourceRetrieved).isEmpty();
    }

}
