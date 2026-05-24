package com.example.myschool.commons.core.checkTrungPro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<UniqueField, Object> {
    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entity;
    private String field;

    @Override
    public void initialize(UniqueField annotation) {
        this.entity = annotation.entity();
        this.field = annotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null || value.toString().trim().isEmpty()) {
            return true;
        }

        String entityName = entity.getSimpleName();

        String jpql = "SELECT COUNT(e) FROM " + entityName +
                " e WHERE e." + field + " = :value";

        Long count = entityManager
                .createQuery(jpql, Long.class)
                .setParameter("value", value)
                .getSingleResult();

        return count == 0;
    }
}
