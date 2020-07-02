package com.reverse.kt.main.ws;

import com.reverse.kt.core.constants.ApplicationConstants;
import com.reverse.kt.core.model.UserProfile;
import com.reverse.kt.core.ui.UiWebServiceRequest;
import com.reverse.kt.core.ui.UiWebServiceResponse;
import com.reverse.kt.main.processor.CompanyProcessor;
import com.reverse.kt.main.processor.ProjectProcessor;
import com.reverse.kt.main.processor.UserProcessor;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by vikas on 08-05-2020.
 */
@RestController
@Setter(onMethod = @__(@Inject))
@RequestMapping(value = "/ws")
public class ApplicationWebService {

    private ProjectProcessor projectProcessor;

    private CompanyProcessor companyProcessor;

    private UserProcessor userProcessor;

    private Environment environment;

    @PostMapping(value = "/ddown")
    public ResponseEntity<Map<Object,Object>> fetchCompanyRelatedEntities(HttpSession httpSession,
            @RequestBody UiWebServiceRequest uiWebServiceRequest) {
        try {
            int userProfileSeq = (int)httpSession.getAttribute(ApplicationConstants.USER_PROFILE_SEQ);
            CompanyProcessor.OperationOps op = CompanyProcessor.OperationOps.checkEnumValue(uiWebServiceRequest.getCompanyFetchOps());
            Map<Object,Object> map = companyProcessor.fetchCompanyRelatedEntity(userProfileSeq,uiWebServiceRequest.getCompanyRelatedParams(),op);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/fetch-skills")
    public ResponseEntity<Map<String,Integer>> fetchSkillsForProjectItem(@RequestParam Integer projectItemSeq) {
        try {
            Map<String,Integer> skillsDropDownForProjectItem = projectProcessor.fetchSkillsDropDownForProjectItemSeq(projectItemSeq);
            return new ResponseEntity<>(skillsDropDownForProjectItem,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @CrossOrigin
    @PostMapping(value="/saveimage")
    public ResponseEntity<?> saveImage(HttpSession httpSession,HttpServletResponse response, @RequestParam("imageFile") MultipartFile file){
        try{
            int userProfileSeq = (int)httpSession.getAttribute(ApplicationConstants.USER_PROFILE_SEQ);
            StringBuilder stringBuilder = new StringBuilder(environment.getProperty("application.url"));
            userProcessor.saveUserProfileImage(userProfileSeq,file,environment.getProperty("reverse.kt.file.image.path"));
            UiWebServiceResponse uiResponse = UiWebServiceResponse.builder().imageFileSrc(stringBuilder.append("/").append("ws").append("/").append("fetch-image")
                    .append("/").toString()).msg(HttpStatus.OK.getReasonPhrase()).build();
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            return new ResponseEntity<>(uiResponse,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/fetch-image")
    public ResponseEntity<byte[]> getImageAsResponseEntity(HttpSession httpSession, HttpServletResponse response) throws IOException {
        ResponseEntity<byte[]> responseEntity = null;
        File downloadImage = null;
        FileInputStream in = null;
        try{
            int userProfileSeq = (int)httpSession.getAttribute(ApplicationConstants.USER_PROFILE_SEQ);
            UserProfile userProfile = userProcessor.fetchUserByProfileSeq(userProfileSeq);
            downloadImage = new File(userProfile.getUserProfileImagePath());
            in = new FileInputStream(downloadImage);
            byte[] media = IOUtils.toByteArray(in);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            responseEntity = new ResponseEntity<>(media, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            in.close();
        }
        return responseEntity;
    }
}
