package com.reverse.kt.core.specifications;

import com.reverse.kt.core.model.SkillMstr;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

/**
 * Created by vikas on 11-05-2020.
 */
public class SkillMstrSpecification {

    final static ISpecification<SkillMstr>
            fetchSkillForProjectItem = (root, builder, exp) -> {
        Predicate[] pr = (Predicate[]) Arrays.asList(builder.equal(root.get("projectItemSeq"), ((int) exp)),
                builder.equal(root.get("status"), 'A')).toArray();
        return pr;
    };

    public static Predicate [] fetchSkillForProjectItem(Root<SkillMstr> r, CriteriaBuilder cb, int projectItemSeq){
        return fetchSkillForProjectItem.toPredicate(r,cb,projectItemSeq);
    }
}
