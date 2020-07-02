package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.specifications.CompanyMstrSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by vikas on 04-04-2020.
 */
@Repository("companyDao")
public class CompanyDao extends GenericDao<CompanyMstr,Integer>{

    public CompanyMstr fetchCompanyMstrByCompanyCd(String companyCd) throws Exception{
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CompanyMstr> companyMstrCriteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<CompanyMstr> companyMstrRoot = companyMstrCriteriaQuery.from(getEntityClass());
        CompanyMstr c = CompanyMstr.builder().companyCd(companyCd).build();
        Predicate[] pr = CompanyMstrSpecification.fetchByCountryCd(companyMstrRoot,criteriaBuilder, c);
        List<CompanyMstr> companyMstrs = getEntityManager().createQuery(companyMstrCriteriaQuery.where(pr)).getResultList();
        if (companyMstrs != null && companyMstrs.size() > 0) {
            return companyMstrs.get(0);
        }
        return null;
    }


    public List<CompanyMstr> fetchAllAcitveCompanies() throws Exception{
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CompanyMstr> companyMstrCriteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<CompanyMstr> companyMstrRoot = companyMstrCriteriaQuery.from(getEntityClass());
        Predicate [] pr = CompanyMstrSpecification.fetchAllAcitveCompanies(companyMstrRoot,criteriaBuilder,null);
        return getEntityManager().createQuery(companyMstrCriteriaQuery.where(pr)).getResultList();
    }

}
