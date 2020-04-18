package com.reverse.kt.core.ui;

import lombok.*;

import java.util.Map;

/**
 * Created by vikas on 18-04-2020.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationModelView extends BaseModelView{

    private String firstName;

    private String lastName;

    private String pwd;

    private String userName;

    private String email;

    private String companySelected;

    private String businessUnitSelected;

    private String projectSelected;

    private String projectItemSelected;

    @Builder
    public RegistrationModelView(String showError,String showSuccess,String showSection){
        super(showError,showSuccess,showSection);
    }

    //Drodown values
    private Map<String,Object> companyDropDown;

    private Map<String,Object> businessUnitDropDown;

    private Map<String,Object> projectDropDown;

    private Map<String,Object> projectItemDropDown;


    @Override
    public String toString() {
        return "RegistrationModelView{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", companySelected='" + companySelected + '\'' +
                ", businessUnitSelected='" + businessUnitSelected + '\'' +
                ", projectSelected='" + projectSelected + '\'' +
                ", projectItemSelected='" + projectItemSelected + '\'' +
                '}';
    }
}
