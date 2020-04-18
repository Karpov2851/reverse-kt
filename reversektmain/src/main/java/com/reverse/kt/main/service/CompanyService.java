package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.CompanyDao;
import com.reverse.kt.core.model.BusinessUnit;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.ui.RegistrationModelView;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public RegistrationModelView generateRegistrationModelView() throws Exception{
        RegistrationModelView registrationModelView = null;
        List<CompanyMstr> fetchAllActiveCompanies = fetchAllActiveCompanies();
        if(fetchAllActiveCompanies!=null && fetchAllActiveCompanies.size() >0){
            registrationModelView = new RegistrationModelView();
                Map<String,Object> companyData =  fetchAllActiveCompanies.stream().collect(
                        Collectors.toMap(CompanyMstr::getCompanyCd, CompanyMstr::getCompanyName));
                registrationModelView.setCompanyDropDown(companyData);
        }
        return registrationModelView;
    }
}
