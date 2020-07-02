package com.reverse.kt.core.ui;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by vikas on 16-06-2020.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadLectureView extends BaseModelView{

    private String videoName;
    private String videoDetails;
    private Map<String,Integer> projectItemDropDown;
    private Map<String,Integer> skillSetDropDown;
    private String projectItemSelected;
    private String skillMstrSelected;
    private MultipartFile videoFileUpload;
    private Integer videoDetailSeq;

    @Builder
    public UploadLectureView(Integer videoDetailSeq,String videoName,String videoDetails,
                             String projectItemSelected,String skillMstrSelected,MultipartFile videoFileUpload,
                             boolean showError,boolean showSuccess,String showSection,String message){
        super(showError,showSuccess,showSection,message);
        this.videoDetailSeq = videoDetailSeq;
        this.videoName = videoName;
        this.videoDetails = videoDetails;
        this.projectItemSelected = projectItemSelected;
        this.skillMstrSelected = skillMstrSelected;
        this.videoFileUpload = videoFileUpload;
    }
}
