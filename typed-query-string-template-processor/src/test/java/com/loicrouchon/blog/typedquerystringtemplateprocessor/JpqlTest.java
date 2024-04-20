package com.loicrouchon.blog.typedquerystringtemplateprocessor;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class JpqlTest {

    @Inject
    EntityManager em;
    @Inject
    Library library;

    @Test
    void jpqlQuery_shouldSupport_stringTemplate() {
        assertThat(library.findBooksByAuthorPublishedBetween("Simone", "de Beauvoir", 1944, 1950))
            .hasSize(2)
            .extracting(book -> STR."\{book.title} (\{book.publishingYear})")
            .containsExactly(
                "Existentialist ethics (1944)",
                "The Second Sex (1949)");
    }
}
