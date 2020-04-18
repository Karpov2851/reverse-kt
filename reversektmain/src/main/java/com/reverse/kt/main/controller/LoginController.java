package com.reverse.kt.main.controller;

import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.service.CompanyService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by vikas on 16-04-2020.
 */
@Slf4j
@Controller
@Setter(onMethod = @__(@Inject))
public class LoginController {

    private CompanyService companyService;


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
    public String login(@RequestParam(required = false) String error, Model model){
        model.addAttribute("isError",error);
        return "login";
    }

    @GetMapping(value="/load-register")
    public String loadRegister(Model model,@RequestParam String fl){
        try{
            RegistrationModelView registrationModelView = companyService.generateRegistrationModelView();
            registrationModelView.setShowSection(fl);
            model.addAttribute("regVO",registrationModelView);
        }catch(Exception e){
            log.error("Error in loadRegister with",e);
        }
        return "registration";
    }

    //Check if the user exists.Make entries in the respective tables
    @GetMapping(value="/register-save")
    public String registerUser(Model m,@ModelAttribute @Valid RegistrationModelView registrationModelView, @RequestParam String fl){
        try{
            System.out.println(registrationModelView.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return loadRegister(m,fl);
    }

    @GetMapping(value="/home")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_TECH_ARCH','ROLE_SCRUM_MSTR','ROLE_DIRECTOR')")
    public String home(){
        return "home";
    }

}
