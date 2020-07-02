package com.reverse.kt.main.controller;

import com.reverse.kt.core.constants.ApplicationConstants;
import com.reverse.kt.core.ui.UploadLectureView;
import com.reverse.kt.main.processor.UploadProcessor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Created by vikas on 16-06-2020.
 */
@Controller
@Setter(onMethod = @__(@Inject))
public class UploadController {

    private UploadProcessor uploadProcessor;

    @GetMapping(value="/load-upload")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_TECH_ARCH','ROLE_SCRUM_MSTR','ROLE_DIRECTOR')")
    public ModelAndView loadUploadView(HttpSession httpSession, @RequestParam(required = false) Integer videoDetailSeq,UploadLectureView lectureView){
        int userProfileSeq = (int)httpSession.getAttribute(ApplicationConstants.USER_PROFILE_SEQ);
        ModelAndView modelAndView = new ModelAndView("upload_view");
        UploadLectureView uploadLectureView = null;
        try{
            uploadLectureView = uploadProcessor.fetchUploadViewDetails(videoDetailSeq,userProfileSeq);
            Optional.of(uploadLectureView);
            if(lectureView!=null){
                uploadLectureView.setShowSuccess(lectureView.isShowSuccess());
                uploadLectureView.setShowError(lectureView.isShowError());
                uploadLectureView.setMessage(lectureView.getMessage());
            }
        }catch(Exception e){
            uploadLectureView = UploadLectureView.builder().build();
            e.printStackTrace();
            uploadLectureView.setMessage("Something went wrong");
            uploadLectureView.setShowError(true);
        }
        modelAndView.addObject("uploadLectureView",uploadLectureView);
        return modelAndView;
    }

    @PostMapping(value="/save-upload")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PROJECT_MANAGER','ROLE_TECH_ARCH','ROLE_SCRUM_MSTR','ROLE_DIRECTOR')")
    public ModelAndView saveUploadView(@ModelAttribute UploadLectureView lectureView, HttpSession httpSession){
        try{
            uploadProcessor.uploadLectureVideoDetails(lectureView);
            lectureView.setMessage("Sucessfully uploaded video");
            lectureView.setShowSuccess(true);
        }catch(Exception ex){
            ex.printStackTrace();
            lectureView.setMessage("Something went wrong");
            lectureView.setShowError(true);
        }
        return loadUploadView(httpSession,null,lectureView);
    }
}
