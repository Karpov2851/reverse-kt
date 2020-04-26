package com.reverse.kt.main.controller;

import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.processor.LoginProcessor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

/**
 * Created by vikas on 16-04-2020.
 */

@Controller
@Setter(onMethod = @__(@Inject))
public class LoginController {

    private LoginProcessor loginProcessor;


    @GetMapping(value="/server")
    public ResponseEntity<?> server(){
        return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK);
    }

    @GetMapping(value="/server-authenticated")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> serverAuthenticated(){
        return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK);
    }

    @GetMapping(value="/access-denied")
    public String accessDenied(){
        return "access_denied";
    }

    @GetMapping(value="/login")
    public String login(@RequestParam(required = false,defaultValue = "false") String error,@RequestParam(required = false,defaultValue = "N") String logout, Model model){
        RegistrationModelView rv = RegistrationModelView.builder().build();
        RegistrationModelView.generateLoginModel(rv,error,logout);
        model.addAttribute("regVO",rv);
        return "login";
    }

    @GetMapping(value="/load-register")
    public String loadRegister(Model model,@RequestParam(required = false,defaultValue = "N") String fl,@ModelAttribute RegistrationModelView rv){
        try{
            RegistrationModelView registrationModelView = loginProcessor.generateRegistrationModelView();
            if(rv!=null){
                registrationModelView.setMessage(rv.getMessage());
                registrationModelView.setShowError(rv.isShowError());
                registrationModelView.setShowSuccess(rv.isShowSuccess());
            }
            registrationModelView.setShowSection(fl);
            model.addAttribute("regVO",registrationModelView);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "registration";
    }

    //Check if the user exists.Make entries in the respective tables
    @PostMapping(value="/register-save")
    public String registerSave(Model m,@ModelAttribute RegistrationModelView registrationModelView, @RequestParam String fl){
        try{
            registrationModelView = loginProcessor.registerUser(registrationModelView);
        }catch(Exception e){
            registrationModelView.setShowError(true);
            registrationModelView.setMessage("Something went wrong");
            e.printStackTrace();
        }
        return loadRegister(m,fl,registrationModelView);
    }

    @GetMapping(value="/home")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_TECH_ARCH','ROLE_SCRUM_MSTR','ROLE_DIRECTOR')")
    public String home(){
        return "home";
    }

    @GetMapping(value="/denied")
    public String denied(){
        return "denied";
    }

}
