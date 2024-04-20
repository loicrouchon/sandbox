package com.loicrouchon.blog.typedquerystringtemplateprocessor;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class Library {

    private final Jpql jpql;

    public Library(Jpql jpql) {
        this.jpql = jpql;
    }

    public List<Book> findBooksByAuthorPublishedBetween(String firstName, String lastName, int publishingYearIntervalStart, int publishingYearIntervalEnd) {
        return jpql.query(Book.class)."""
            select distinct book
            from Author author
            join author.books book
            where author.firstName = \{firstName}
              and author.lastName = \{lastName}
              and book.publishingYear between \{publishingYearIntervalStart} and \{publishingYearIntervalEnd}
            order by book.publishingYear asc
            """.getResultList();
    }
}
