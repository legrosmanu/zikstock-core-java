package com.zikstock.core.zikresource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test de Zikresource DAO, even if we have specific rules only on the creation.
 */
@DataMongoTest
public class ZikresourceRepositoryTests {

    @Autowired
    private ZikresourceRepository mongoTemplate;

    private static Validator validator;

    private Zikresource getCorrectZikresource() {
        Zikresource zikresource = new Zikresource();
        zikresource.setUrl("https://www.songsterr.com/a/wsa/tool-sober-tab-s19923t2");
        zikresource.setTitle("Sober");
        zikresource.setArtist("Tool");
        List<ZikresourceTag> tags = new ArrayList<>();
        tags.add(new ZikresourceTag("difficult", "intermediate"));
        zikresource.setTags(tags);
        return zikresource;
    }

    private Zikresource createZikresourceInDataBase() {
        Zikresource zikresource = getCorrectZikresource();
        return this.mongoTemplate.save(zikresource);
    }

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
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

    @Test
    public void shouldProduceViolationBecauseUrlAndTitleAreMissing() {
        // Given a Zikresource without URL and title fields
        Zikresource zikResourceWithoutUrl = new Zikresource();
        zikResourceWithoutUrl.setArtist("Tool");
        // When
        Set<ConstraintViolation<Zikresource>> violations = validator.validate(zikResourceWithoutUrl);
        // Then
        assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    public void shouldProduceViolationBecauseTooMuchTags() {
        // Given a Zikresource with more than 10 tags
        Zikresource zikResourceWithTooMuchTags = new Zikresource();
        zikResourceWithTooMuchTags.setUrl("fake");
        zikResourceWithTooMuchTags.setTitle("fake");
        List<ZikresourceTag> tags = new ArrayList<>();
        for (int i = 0 ; i < 12 ; i++) {
            tags.add(new ZikresourceTag("label" + i, "value" + i));
        }
        zikResourceWithTooMuchTags.setTags(tags);
        // When
        Set<ConstraintViolation<Zikresource>> violations = validator.validate(zikResourceWithTooMuchTags);
        // Then
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void shouldRetrieveOneZikresource() {
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

    @Test
    public void shouldDeleteTheZikresourceIfExists() {
        // Given a ZikResource on database
        String id = createZikresourceInDataBase().getId();
        // When
        this.mongoTemplate.deleteById(id);
        // Then
        Optional<Zikresource> zikresourceRetrieved = this.mongoTemplate.findById(id);
        assertThat(zikresourceRetrieved).isEmpty();
    }

    @Test
    public void shouldHaveNoImpactIfWeTryToDeleteAnUnknownZikresource() {
        // Given an unknown zikresource, like a bad id
        String id = "aaa";
        long nbZikResourcesBefore = this.mongoTemplate.count();
        // When
        this.mongoTemplate.deleteById(id);
        // Then
        long nbZikResourcesAfter = this.mongoTemplate.count();
        assertThat(nbZikResourcesBefore == nbZikResourcesAfter).isTrue();
    }

}
