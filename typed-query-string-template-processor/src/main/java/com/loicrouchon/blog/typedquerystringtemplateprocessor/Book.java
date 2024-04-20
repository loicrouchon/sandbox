package com.loicrouchon.blog.typedquerystringtemplateprocessor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue
    public Long id;

    @Column
    public String title;

    @Column
    public int publishingYear;

    @ManyToMany
    public List<Author> authors;

    @Override
    public String toString() {
        return title;
    }
}
