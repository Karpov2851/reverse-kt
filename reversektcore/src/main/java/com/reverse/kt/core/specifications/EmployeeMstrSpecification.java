package com.reverse.kt.core.specifications;

import com.reverse.kt.core.model.EmployeeMstr;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

/**
 * Created by vikas on 08-05-2020.
 */
public class EmployeeMstrSpecification {

    final static ISpecification<EmployeeMstr> fetchEmployeeMstrForUserProfileSeq = (root, builder, exp) -> {
        Predicate[] pr = (Predicate[]) Arrays.asList(builder.equal(root.get("userProfile"), ((int) exp)),
                builder.equal(root.get("status"), 'A')).toArray();
        return pr;
    };

    public static Predicate [] fetchEmployeeMstrForUserProfileSeq(Root<EmployeeMstr> r, CriteriaBuilder cb, int primarySeq){
        return fetchEmployeeMstrForUserProfileSeq.toPredicate(r,cb,primarySeq);
    }
}
