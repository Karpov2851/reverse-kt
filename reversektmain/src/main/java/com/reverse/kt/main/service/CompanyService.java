package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.CompanyDao;
import com.reverse.kt.core.model.CompanyMstr;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<String,String> fetchMapOfCompanyCdAndCompanyName() throws Exception{
        List<CompanyMstr> fetchAllActiveCompanies = fetchAllActiveCompanies();
        if(fetchAllActiveCompanies!=null && fetchAllActiveCompanies.size() >0){
            return  fetchAllActiveCompanies.stream().collect(
                    Collectors.toMap(CompanyMstr::getCompanyCd, CompanyMstr::getCompanyName));
        }
        return null;
    }
}
