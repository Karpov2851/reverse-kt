package com.reverse.kt.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by vikas on 07-05-2020.
 */
@Controller
public class ApplicationController {

    @GetMapping(value="/history")
    public String history(){
        return "history";
    }

    @GetMapping(value="/upload")
    public String upload(){
        return "upload_view";
    }

    @GetMapping(value="/schedule")
    public String schedule(){
        return "schedule_session";
    }


    @GetMapping(value="/profile")
    public String profile(){
        return "view_profile";
    }
}
