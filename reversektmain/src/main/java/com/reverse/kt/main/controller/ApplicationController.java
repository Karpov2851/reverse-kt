package com.reverse.kt.main.controller;

import com.reverse.kt.core.constants.ApplicationConstants;
import com.reverse.kt.core.ui.HistoryView;
import com.reverse.kt.core.ui.RegistrationModelView;
import com.reverse.kt.main.processor.UserProcessor;
import com.reverse.kt.main.service.ScheduleSessionService;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by vikas on 07-05-2020.
 */
@Controller
@Setter(onMethod = @__(@Inject))
public class ApplicationController {



    private UserProcessor userProcessor;

    private ScheduleSessionService scheduleSessionService;


    @GetMapping(value="/load-profile")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_TECH_ARCH','ROLE_SCRUM_MSTR','ROLE_DIRECTOR')")
    public String loadProfile(Model model, HttpSession httpSession, HttpServletRequest request){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        RegistrationModelView registrationModelView = new RegistrationModelView();
        int userProfileSeq = (int)(httpSession.getAttribute(ApplicationConstants.USER_PROFILE_SEQ));
        try{
            registrationModelView =  userProcessor.fetchViewProfileDetails(userProfileSeq);
            if(inputFlashMap != null) {
                RegistrationModelView rv = (RegistrationModelView) inputFlashMap.get("rv");
                registrationModelView.setMessage(rv.getMessage());
                registrationModelView.setShowSuccess(rv.isShowSuccess());
                registrationModelView.setShowError(rv.isShowError());
            }
            model.addAttribute("regVO",registrationModelView);
        }catch(Exception e){
            e.printStackTrace();
            registrationModelView.setShowError(true);
            registrationModelView.setMessage("Something went wrong");
        }
        return "view_profile";
    }

    @GetMapping(value="/load-history")
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE','ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_TECH_ARCH','ROLE_SCRUM_MSTR','ROLE_DIRECTOR')")
    public ModelAndView history(HttpSession httpSession){
        ModelAndView modelAndView = new ModelAndView("history");
        int userProfileSeq = (int)(httpSession.getAttribute(ApplicationConstants.USER_PROFILE_SEQ));
        try{
            List<HistoryView> historyViewList = scheduleSessionService.fetchHistoryViewForUser(userProfileSeq);
            modelAndView.addObject("historyList",historyViewList);
        }catch(Exception e){
            e.printStackTrace();
        }
        return modelAndView;
    }


    @PostMapping(value="/save-profile")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_TECH_ARCH','ROLE_SCRUM_MSTR','ROLE_DIRECTOR')")
    public RedirectView saveProfile(@ModelAttribute RegistrationModelView rv, HttpSession httpSession,RedirectAttributes redirectAttributes){
        try{

            int userProfileSeq = (int)(httpSession.getAttribute(ApplicationConstants.USER_PROFILE_SEQ));
            userProcessor.updateEmployeeDetails(userProfileSeq,rv);
            rv.setShowError(false);
            rv.setShowSuccess(true);
            rv.setMessage("Successfully saved profile");
        }catch(Exception e){
            e.printStackTrace();
            rv.setShowError(true);
            rv.setMessage("Something went wrong");

        }
        redirectAttributes.addFlashAttribute("rv",rv);
        return new RedirectView("/load-profile",true);
    }

}
