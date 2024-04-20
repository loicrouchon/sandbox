package com.loicrouchon.blog.typedquerystringtemplateprocessor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue
    public Long id;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @ManyToMany(mappedBy = "authors")
    public List<Book> books;


    @Override
    public String toString() {
        return STR."\{firstName} \{lastName}";
    }
}
