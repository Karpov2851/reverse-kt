package com.reverse.kt.core.specifications;

import com.reverse.kt.core.model.CompanyMstr;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;

/**
 * Created by vikas on 06-04-2020.
 */
public class CompanyMstrSpecification {

    final static ISpecification<CompanyMstr> fetchByCountryCd = (root,builder,exp) ->{
        return (Predicate[]) Arrays.asList(
                builder.like(root.get("companyCd"),"%"+((CompanyMstr) exp).getCompanyCd()+"%"),
                builder.equal(root.get("status"),'A')).toArray();
    };

    final static ISpecification<CompanyMstr> fetchAllAcitveCompanies = (root,builder,exp) ->{
        return (Predicate[]) Arrays.asList(builder.equal(root.get("status"),'A')).toArray();
    };

    public static Predicate [] fetchByCountryCd(Root<CompanyMstr> r, CriteriaBuilder cb, CompanyMstr companyMstr){
        return fetchByCountryCd.toPredicate(r,cb,companyMstr);
    }

    public static Predicate [] fetchAllAcitveCompanies(Root<CompanyMstr> r, CriteriaBuilder cb, CompanyMstr companyMstr){
        return fetchAllAcitveCompanies.toPredicate(r,cb,companyMstr);
    }
}
