package com.reverse.kt.main.processor;

import com.reverse.kt.core.model.*;
import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.core.util.FileUtil;
import com.reverse.kt.main.service.BusinessUnitService;
import com.reverse.kt.main.service.EmployeeService;
import com.reverse.kt.main.service.ProjectService;
import com.reverse.kt.main.service.UserService;
import com.reverse.kt.main.util.MainCommonUtil;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private BusinessUnitService businessUnitService;

    private ProjectService projectService;

    private MainCommonUtil mainCommonUtil;


    public RegistrationModelView fetchViewProfileDetails(int userProfileSeq) throws Exception{
        RegistrationModelView registrationModelView = null;
        UserProfile userProfileRecord = fetchUserByProfileSeq(userProfileSeq);
        if(userProfileRecord == null){
            throw new Exception("User doest not exist");
        }else{
            CompanyMstr userCompany = userProfileRecord.getCompanyMstr();
            registrationModelView = new RegistrationModelView();
            registrationModelView.setUserName(userProfileRecord.getUserId());
            registrationModelView.setCompanySelected(userCompany.getCompanyCd());
            registrationModelView.setBusinessUnitDropDown(Optional.of(userCompany.getBusinessUnits()).get().stream().collect(Collectors.toMap(BusinessUnit::getBusinessCd,BusinessUnit::getBusinessUnitName)));
            registrationModelView.setProjectDropDown(Optional.of(userCompany.getProjects()).get().stream().collect(Collectors.toMap(Project::getProjectSeq,Project::getProjectName)));
            registrationModelView.setProjectItemDropDown(Optional.of(userCompany.getProjects()).get().stream().flatMap(t->t.getProjectItems().stream()).collect(Collectors.toMap(ProjectItem::getProjectItemSeq,ProjectItem::getProjectItemDesc)));
            registrationModelView.setDesignationDropDown(
                    Optional.of(userCompany.getDesignationMstrs()).get().stream().collect(Collectors.toMap(DesignationMstr::getDesignationMstrSeq,DesignationMstr::getDesignatioName)));
            EmployeeMstr employee = employeeService.fetchEmployeeForUserProfileSeq(userProfileSeq);
            if(employee!=null){
                registrationModelView.setFirstName(employee.getEmployeeFirstName());
                registrationModelView.setEmail(employee.getEmployeeEmail());
                registrationModelView.setLastName(employee.getEmployeeLastName());
                registrationModelView.setBusinessUnitSelected(employee.getBusinessUnit() != null ? employee.getBusinessUnit().getBusinessUnitName() : "");
                registrationModelView.setProjectItemSelected(employee.getProjectItem() != null ? employee.getProjectItem().getProjectItemDesc() : "");
                registrationModelView.setDesignation(employee.getDesignationMstr() !=null ? employee.getDesignationMstr().getDesignatioName() : "");
                registrationModelView.setProjectSelected(employee.getProjectItem() !=null ? employee.getProjectItem().getProject().getProjectName() : "");
                registrationModelView.setProfileImageFilePath(
                        StringUtils.isEmpty(userProfileRecord.getUserProfileImageRefCd()) ?
                                "" : new StringBuilder(environment.getProperty("application.url")).append("/").append("ws").append("/").append("fetch-image").toString());
            }
        }
        return registrationModelView;
    }

    public void saveUserProfileImage(int userProfileSeq,MultipartFile file,String filePathToSave) throws Exception{
        UserProfile userProfile = fetchUserByProfileSeq(userProfileSeq);
        Optional.of(userProfile);
        if(file!=null){
            StringBuilder fileNameToWrite = new StringBuilder(userProfile.getUserId()).append("_")
                    .append(System.currentTimeMillis()).append(".").append(FilenameUtils.getExtension(file.getOriginalFilename()));
            String imageRefCd  = mainCommonUtil.decryptEncrypt(fileNameToWrite.toString(), Cipher.ENCRYPT_MODE);
            FileUtil.writeByteArrayToFile(file,filePathToSave,fileNameToWrite.toString());
            userProfile.setUserProfileImageRefCd(imageRefCd);
            userProfile.setUserProfileImagePath(filePathToSave+fileNameToWrite);
            userService.createUserProfile(userProfile,false);
        }else{
            throw new Exception("File value is null");
        }
    }

    public void updateEmployeeDetails(int userProfileSeq,RegistrationModelView registrationModelView) throws Exception{
        EmployeeMstr employeeMstr = employeeService.fetchEmployeeForUserProfileSeq(userProfileSeq);
        Optional.of(employeeMstr);
        if(registrationModelView!=null){
            UserProfile userProfile = employeeMstr.getUserProfile();
            BusinessUnit businessUnit = businessUnitService.fetchBusinessUnitForBuCd(registrationModelView.getBusinessUnitSelected());
            ProjectItem projectItem = projectService.fetchProjectItemForProjectItemSeq(Integer.parseInt(registrationModelView.getProjectItemSelected()));
            employeeMstr.setEmployeeFirstName(registrationModelView.getFirstName());
            employeeMstr.setEmployeeLastName(registrationModelView.getLastName());
            employeeMstr.setEmployeeEmail(registrationModelView.getEmail());
            userProfile.setUserId(registrationModelView.getUserName());
            employeeMstr.setBusinessUnit(businessUnit);
            employeeMstr.setProjectItem(projectItem);
            employeeService.createEmployeeRecord(employeeMstr,false);
        }
    }

    public UserProfile fetchUserByProfileSeq(int userProfileSeq) throws Exception{
        return userService.fetchUserByProfileSeq(userProfileSeq);
    }

}
