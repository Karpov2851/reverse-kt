package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.CompanyDao;
import com.reverse.kt.core.model.CompanyMstr;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by vikas on 18-04-2020.
 */
@Service
@Transactional
@Setter(onMethod = @__(@Inject))
public class CompanyService {

    private CompanyDao companyDao;

    public List<CompanyMstr> fetchAllActiveCompanies() throws Exception{
        return companyDao.fetchAllAcitveCompanies();
    }

    public CompanyMstr fetchCompanyByCompanyCd(String companyCd)throws Exception{
        return companyDao.fetchCompanyMstrByCompanyCd(companyCd);
    }
}
