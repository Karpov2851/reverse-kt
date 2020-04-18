package com.reverse.kt.core.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by vikas on 06-04-2020.
 */
@FunctionalInterface
public interface ISpecification<T> {
    Predicate [] toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, final Object ob);
}