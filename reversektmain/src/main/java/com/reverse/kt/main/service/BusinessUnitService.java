package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.BusinessUnitDao;
import com.reverse.kt.core.model.BusinessUnit;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by vikas on 12-05-2020.
 */
@Service("businessUnitService")
@Setter(onMethod = @__(@Inject))
public class BusinessUnitService {

    private BusinessUnitDao businessUnitDao;

    public BusinessUnit fetchBusinessUnitForBuCd(String businessCd) throws Exception{
        return businessUnitDao.fetchBusinessUnitByBuCd(businessCd);
    }
}
