package com.reverse.kt.core.dao;

import com.reverse.kt.core.model.BusinessUnit;
import com.reverse.kt.core.specifications.BusinessUnitSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Create
 * d by vikas on 15-04-2020.
 */
@Repository("businessUnitDao")
public class BusinessUnitDao extends GenericDao<BusinessUnit,Integer>{

    public BusinessUnit fetchBusinessUnitByBuCd(String buCd) throws Exception{
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BusinessUnit> businessUnitCriteriaQuery = criteriaBuilder.createQuery(getEntityClass());
        Root<BusinessUnit> businessUnitRoot = businessUnitCriteriaQuery.from(getEntityClass());
        Predicate[] pr = BusinessUnitSpecification.fetchBusinessUnitByBuCd(businessUnitRoot,criteriaBuilder,buCd);
        List<BusinessUnit> businessUnits = getEntityManager().createQuery(businessUnitCriteriaQuery.where(pr)).getResultList();
        if(businessUnits!=null && businessUnits.size()>0){
            return businessUnits.get(0);
        }
        return null;
    }
}
