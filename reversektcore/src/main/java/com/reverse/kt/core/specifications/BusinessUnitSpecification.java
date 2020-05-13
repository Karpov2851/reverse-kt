package com.reverse.kt.core.specifications;

import com.reverse.kt.core.model.BusinessUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

/**
 * Created by vikas on 12-05-2020.
 */
public class BusinessUnitSpecification {
    final static ISpecification<BusinessUnit> fetchBusinessUnitByBuCd = (root, builder, exp) ->{
        return (Predicate[]) Arrays.asList(
                builder.like(root.get("businessCd"),"%"+exp+"%"),
                builder.equal(root.get("status"),'A')).toArray();
    };

    public static Predicate [] fetchBusinessUnitByBuCd(Root<BusinessUnit> r, CriteriaBuilder cb, String buCd){
        return fetchBusinessUnitByBuCd.toPredicate(r,cb,buCd);
    }
}
