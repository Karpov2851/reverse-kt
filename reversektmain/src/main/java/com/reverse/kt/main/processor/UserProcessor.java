package com.reverse.kt.main.processor;

import com.reverse.kt.core.model.EmployeeMstr;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.service.CompanyService;
import com.reverse.kt.main.service.EmployeeService;
import com.reverse.kt.main.service.UserService;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by vikas on 07-05-2020.
 */
@Component
@Setter(onMethod = @__(@Inject))
@Transactional
public class UserProcessor {

    private Environment environment;

    private EmployeeService employeeService;

    private UserService userService;

    private CompanyService companyService;


    public RegistrationModelView fetchViewProfileDetails(int userProfileSeq) throws Exception{
        RegistrationModelView registrationModelView = null;
        UserProfile userProfileRecord = userService.fetchUserByProfileSeq(userProfileSeq);
        Map<String,String> companyMapDropDown = companyService.fetchMapOfCompanyCdAndCompanyName();
        if(userProfileRecord == null){
            throw new Exception("User doest not exist");
        }else{
            registrationModelView = new RegistrationModelView();
            registrationModelView.setCompanyDropDown(companyMapDropDown);
            registrationModelView.setCompanySelected(userProfileRecord.getCompanyMstr().getCompanyCd());

            EmployeeMstr employee = employeeService.fetchEmployeeForUserProfileSeq(userProfileSeq);
            if(employee!=null){
                registrationModelView.setFirstName(employee.getEmployeeFirstName());
                registrationModelView.setEmail(employee.getEmployeeEmail());
                registrationModelView.setLastName(employee.getEmployeeLastName());
                registrationModelView.setBusinessUnitSelected(employee.getBusinessUnit() != null ? employee.getBusinessUnit().getBusinessUnitName() : "");
                registrationModelView.setProjectItemSelected(employee.getProjectItem() != null ? employee.getProjectItem().getProjectItemDesc() : "");
                registrationModelView.setDesignation(employee.getDesignationMstr() !=null ? employee.getDesignationMstr().getDesignatioName() : "");
                registrationModelView.setProfileImageFilePath(
                        StringUtils.isEmpty(userProfileRecord.getUserProfileImageRefCd()) ?
                                "" : environment.getProperty("image.fetch.url")+userProfileRecord.getUserProfileImageRefCd());
            }
        }
        return registrationModelView;
    }
}
