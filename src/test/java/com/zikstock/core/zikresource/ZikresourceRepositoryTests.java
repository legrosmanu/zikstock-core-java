package com.zikstock.core.zikresource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class ZikresourceRepositoryTests {

    @Autowired
    private ZikresourceRepository mongoTemplate;

    private Zikresource createZikresourceInDataBase() {
        Zikresource zikresource = new Zikresource();
        return this.mongoTemplate.save(zikresource);
    }

    private Zikresource getCorrectZikresource() {
        Zikresource zikresource = new Zikresource();
        zikresource.setUrl("https://www.songsterr.com/a/wsa/tool-sober-tab-s19923t2");
        zikresource.setTitle("Sober");
        zikresource.setArtist("Tool");
        zikresource.addTag("difficult", "intermediate");
        return zikresource;
    }

    @AfterEach
    private void cleanDataBase() {
        this.mongoTemplate.deleteAll();
    }

    @Test
    public void shouldCreateZikresource() {
        // Given a correct Zikresource
        Zikresource zikresource = this.getCorrectZikresource();
        // When
        Zikresource zikresourceSaved = this.mongoTemplate.save(zikresource);
        // Then
        assertThat(zikresourceSaved).isNotNull();
        assertThat(zikresourceSaved.getUrl()).isEqualTo(zikresource.getUrl());
        assertThat(zikresourceSaved.getTitle()).isEqualTo(zikresource.getTitle());
    }

    /** TODO
    @Test
    public void shouldNotCreateTheZikresourceAndThrowAnExceptionBecauseUrlMissing() {
    }

    @Test
    public void shouldNotCreateTheZikresourceAndThrowAnExceptionBecauseTitleMissing() {

    }

    @Test
    public void shouldNotCreateTheZikresourceAndThrowAnExceptionBecauseTooMuchTags() {

    }*/

    @Test
    public void shouldOneRetrieveZikresource() {
        // Given the id of a resource in the DB
        String id = createZikresourceInDataBase().getId();
        // When
        Optional<Zikresource> zikresourceRetrieved = this.mongoTemplate.findById(id);
        // Then
        assertThat(zikresourceRetrieved).isNotEmpty();
    }

    @Test
    public void shouldNotRetrieveZikresourceUnknown() {
        // Given an unknown zikresource, like a bad id
        String id = "aaa";
        // When
        Optional<Zikresource> zikresourceRetrieved = this.mongoTemplate.findById(id);
        // Then
        assertThat(zikresourceRetrieved).isEmpty();
    }

}
