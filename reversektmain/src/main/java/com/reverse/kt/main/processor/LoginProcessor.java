package com.reverse.kt.main.processor;

import com.reverse.kt.core.constants.UserRoleIdentifier;
import com.reverse.kt.core.model.CompanyMstr;
import com.reverse.kt.core.model.EmployeeMstr;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.core.model.UserRole;
import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.service.CompanyService;
import com.reverse.kt.main.service.EmployeeService;
import com.reverse.kt.main.service.UserService;
import com.reverse.kt.main.util.MainCommonUtil;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vikas on 21-04-2020.
 */
@Component
@Setter(onMethod = @__(@Inject))
@Transactional
public class LoginProcessor {

    private EmployeeService employeeService;

    private UserService userService;

    private CompanyService companyService;

    private MainCommonUtil mainCommonUtil;

    public RegistrationModelView registerUser(RegistrationModelView registrationModelView) throws Exception{
        if(registrationModelView!=null){
            UserProfile userProfile = userService.fetchUserProfileByUserId(registrationModelView.getUserName());
            if(userProfile!=null){
                registrationModelView.setShowError(true);
                registrationModelView.setMessage("User already exists with this username");
            }else{
                CompanyMstr userCompany = companyService.fetchCompanyByCompanyCd(registrationModelView.getCompanySelected());
                UserRole userRole = userService.fetchUserForRoleAndCompany(UserRoleIdentifier.EMPLOYEE,userCompany.getCompanyMstrSeq());
                if(userRole!=null){
                    userProfile = UserProfile.builder().userId(registrationModelView.getUserName()).userRole(userRole)
                            .password(mainCommonUtil.convertToBecrypt(registrationModelView.getPwd())).companyMstr(userCompany).build();
                    EmployeeMstr employeeMstr = EmployeeMstr.builder().companyMstr(userCompany).employeeEmail(registrationModelView.getEmail())
                            .employeeFirstName(registrationModelView.getFirstName()).employeeLastName(registrationModelView.getLastName()).build();

                    userService.createUserProfile(userProfile,true);
                    employeeService.createEmployeeRecord(employeeMstr,true);
                    registrationModelView.setShowSuccess(true);
                    registrationModelView.setMessage("User created successfully");
                }else{
                    throw new Exception("User role does not exist");
                }
        }
        }else {
            throw new IllegalArgumentException("RegistrationModelView is null in registerUser method");
        }
        return registrationModelView;
    }

    public RegistrationModelView generateRegistrationModelView() throws Exception{
        RegistrationModelView registrationModelView = null;
        List<CompanyMstr> fetchAllActiveCompanies = companyService.fetchAllActiveCompanies();
        if(fetchAllActiveCompanies!=null && fetchAllActiveCompanies.size() >0){
            registrationModelView = new RegistrationModelView();
            Map<String,Object> companyData =  fetchAllActiveCompanies.stream().collect(
                    Collectors.toMap(CompanyMstr::getCompanyCd, CompanyMstr::getCompanyName));
            registrationModelView.setCompanyDropDown(companyData);
        }
        return registrationModelView;
    }
}
