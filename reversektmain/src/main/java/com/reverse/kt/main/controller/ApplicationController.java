package com.reverse.kt.main.controller;

import com.reverse.kt.core.constants.ApplicationConstants;
import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.processor.UserProcessor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Created by vikas on 07-05-2020.
 */
@Controller
@Setter(onMethod = @__(@Inject))
public class ApplicationController {

    private HttpSession httpSession;

    private UserProcessor userProcessor;


    @GetMapping(value="/load-profile")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_TECH_ARCH','ROLE_SCRUM_MSTR','ROLE_DIRECTOR')")
    public String loadProfile(Model model){
        RegistrationModelView registrationModelView = null;
        int userProfileSeq = (int)(httpSession.getAttribute(ApplicationConstants.USER_PROFILE_SEQ));
        try{
            registrationModelView =  userProcessor.fetchViewProfileDetails(userProfileSeq);
            model.addAttribute("regVO",registrationModelView);
        }catch(Exception e){
            registrationModelView.setShowError(true);
            registrationModelView.setMessage("Something went wrong");
        }
        return "view_profile";
    }

    @GetMapping(value="/history")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_TECH_ARCH','ROLE_SCRUM_MSTR','ROLE_DIRECTOR')")
    public String history(){
        return "history";
    }
}
