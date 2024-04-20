package com.loicrouchon.blog.typedquerystringtemplateprocessor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class Jpql {

    private final EntityManager em;

    @Inject
    public Jpql(EntityManager em) {
        this.em = em;
    }

    public <T> StringTemplate.Processor<TypedQuery<T>, RuntimeException> query(Class<T> type) {
        return new TypedQueryStringTemplateProcessor<>(em, type);
    }

    public static class TypedQueryStringTemplateProcessor<T>
        implements StringTemplate.Processor<TypedQuery<T>, RuntimeException> {

        private final EntityManager em;
        private final Class<T> type;

        public TypedQueryStringTemplateProcessor(EntityManager em, Class<T> type) {
            this.em = em;
            this.type = type;
        }

        @Override
        public TypedQuery<T> process(StringTemplate jpql) throws RuntimeException {
            List<String> fragments = jpql.fragments();
            List<Object> values = jpql.values();
            List<String> placeholders = new ArrayList<>(values.size());
            for (int i = 1; i <= values.size(); i++) {
                placeholders.add("?" + i);
            }
            String query = StringTemplate.interpolate(fragments, placeholders);
            TypedQuery<T> typedQuery = em.createQuery(query, type);
            int i = 1;
            for (Object value : values) {
                typedQuery.setParameter(i++, value);
            }
            return typedQuery;
        }
    }
}
