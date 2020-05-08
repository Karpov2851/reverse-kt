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

    private String profileImageFilePath;

    private String designation;

    @Builder
    public RegistrationModelView(String firstName,String lastName,String userName,String email,String pwd,String companySelected,boolean showError,boolean showSuccess,String showSection,String message){
        super(showError,showSuccess,showSection,message);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.pwd = pwd;
        this.companySelected = companySelected;
    }


    public RegistrationModelView(boolean showError,boolean showSuccess,String showSection,String message){
        super(showError,showSuccess,showSection,message);
    }

    //Drodown values
    private Map<String,String> companyDropDown;

    private Map<String,Object> businessUnitDropDown;

    private Map<String,Object> projectDropDown;

    private Map<String,Object> projectItemDropDown;



    //TODO need to think about a cleaner solution. Just making it work now.
    public static void generateLoginModel(RegistrationModelView r,String error,String logout){
        r.setShowError(error.equals("true") && logout.equals("N"));
        r.setShowSuccess(error.equals("false") && logout.equals("Y"));
        if(error.equals("true")){
            r.setMessage("Incorrect user id or password.");
        }else if(logout.equals("Y")){
            r.setMessage("User logged out successfully");
        }
    }

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
