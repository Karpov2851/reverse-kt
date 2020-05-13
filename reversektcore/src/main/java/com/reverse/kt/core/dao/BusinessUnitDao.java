package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.BusinessUnit;
import com.reverse.kt.core.specifications.BusinessUnitSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Create
 * d by vikas on 15-04-2020.
 */
@Repository("businessUnitDao")
public class BusinessUnitDao extends GenericDao<BusinessUnit,Integer>{

    public BusinessUnit fetchBusinessUnitByBuCd(String buCd) throws Exception{
        Predicate[] pr = BusinessUnitSpecification.fetchBusinessUnitByBuCd(getRoot(),getCriteriaBuilder(),buCd);
        List<BusinessUnit> businessUnits = findListOfEntireRecordsBasedOnCriteria(pr);
        if(businessUnits!=null && businessUnits.size()>0){
            return businessUnits.get(0);
        }
        return null;
    }
}
