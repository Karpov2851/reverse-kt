package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.specifications.CompanyMstrSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by vikas on 04-04-2020.
 */
@Repository("companyDao")
public class CompanyDao extends GenericDao<CompanyMstr,Integer>{

    public CompanyMstr fetchCompanyMstrByCompanyCd(String companyCd) throws Exception{
           CompanyMstr c = CompanyMstr.builder().companyCd(companyCd).build();
           Predicate [] pr = CompanyMstrSpecification.fetchByCountryCd(getRoot(),getCriteriaBuilder(),c);
           List<CompanyMstr> companyMstrs = findListOfEntireRecordsBasedOnCriteria(pr);
           if(companyMstrs!=null && companyMstrs.size()>0){
               return companyMstrs.get(0);
           }
           return null;
    }


    public List<CompanyMstr> fetchAllAcitveCompanies() throws Exception{
        Predicate [] pr = CompanyMstrSpecification.fetchAllAcitveCompanies(getRoot(),getCriteriaBuilder(),null);
        List<CompanyMstr> companyMstrs = findListOfEntireRecordsBasedOnCriteria(pr);
        return companyMstrs;
    }

}
