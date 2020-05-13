package com.reverse.kt.core.specifications;

import com.reverse.kt.core.model.Project;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

/**
 * Created by vikas on 09-05-2020.
 */
public class ProjectSpecification {

    final static ISpecification<Project>
            fetchProjectForBusinessUnit = (root, builder, exp) -> {
        Predicate[] pr = (Predicate[]) Arrays.asList(builder.equal(root.get("businessUnit"), ((int) exp)),
                builder.equal(root.get("status"), 'A')).toArray();
        return pr;
    };

    public static Predicate [] fetchProjectForBusinessUnit(Root<Project> r, CriteriaBuilder cb,int businessUnitSeq){
        return fetchProjectForBusinessUnit.toPredicate(r,cb,businessUnitSeq);
    }
}
