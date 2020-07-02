package com.reverse.kt.main.processor;

import com.mysql.jdbc.StringUtils;
import com.reverse.kt.core.model.EmployeeMstr;
import com.reverse.kt.core.model.ProjectItem;
import com.reverse.kt.core.model.VideoDetails;
import com.reverse.kt.core.ui.UploadLectureView;
import com.reverse.kt.core.util.FileUtil;
import com.reverse.kt.main.service.EmployeeService;
import com.reverse.kt.main.service.ProjectService;
import com.reverse.kt.main.service.VideoService;
import com.reverse.kt.main.util.MainCommonUtil;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by vikas on 17-06-2020.
 */
@Component
@Setter(onMethod = @__(@Inject))
@Transactional
public class UploadProcessor {

    private EmployeeService employeeService;
    private VideoService videoService;
    private ProjectService projectService;
    private MainCommonUtil mainCommonUtil;
    private Environment environment;

    public void uploadLectureVideoDetails(UploadLectureView uploadLectureView) throws Exception{
        boolean isEdit = uploadLectureView.getVideoDetailSeq()!=null;
        VideoDetails videoDetails = null;
        ProjectItem projectItem = null;
        Optional.of(uploadLectureView.getVideoFileUpload());
        StringBuilder fileNameToWrite = new StringBuilder(StringUtils.isNullOrEmpty(uploadLectureView.getVideoName()) ? "" : uploadLectureView.getVideoName()).append("_")
                .append(System.currentTimeMillis()).append(".").append(FilenameUtils.getExtension(uploadLectureView.getVideoFileUpload().getOriginalFilename()));
        String videoFileRefCd  = mainCommonUtil.decryptEncrypt(fileNameToWrite.toString(), Cipher.ENCRYPT_MODE);
        FileUtil.writeByteArrayToFile(uploadLectureView.getVideoFileUpload(),environment.getProperty("reverse.kt.file.video.path"),fileNameToWrite.toString());
        //indicates update
        if(isEdit){
            videoDetails  = videoService.fetchVideoDetailsById(uploadLectureView.getVideoDetailSeq());
            Optional.of(videoDetails);
        }else{
            videoDetails = VideoDetails.builder().build();
        }
        projectItem = projectService.fetchProjectItemForProjectItemSeq(Integer.valueOf(uploadLectureView.getProjectItemSelected()));
        videoDetails.setVideoName(uploadLectureView.getVideoName());
        videoDetails.setVideoDetails(uploadLectureView.getVideoDetails());
        videoDetails.setSkillMstrSeqList(uploadLectureView.getSkillMstrSelected());
        videoDetails.setProjectItem(projectItem);
        videoDetails.setVideoDirectoryPath(environment.getProperty("reverse.kt.file.video.path")+fileNameToWrite);
        videoDetails.setVideoDirectoryRefCd(videoFileRefCd);
        videoService.saveVideoDetails(videoDetails,isEdit);
    }

    public UploadLectureView fetchUploadViewDetails(Integer videoDetailSeq,Integer userProfileSeq) throws Exception{
        UploadLectureView uploadLectureView = UploadLectureView.builder().videoDetailSeq(videoDetailSeq).build();
        boolean isEdit = uploadLectureView.getVideoDetailSeq() != null;
        EmployeeMstr fetchEmployeeForUserProfileSeq = employeeService.fetchEmployeeForUserProfileSeq(userProfileSeq);
        Optional.of(fetchEmployeeForUserProfileSeq);
        if(isEdit){
            VideoDetails videoDetails =  videoService.fetchVideoDetailsById(uploadLectureView.getVideoDetailSeq());
            Optional.of(videoDetails);
            uploadLectureView.setVideoName(videoDetails.getVideoName());
            uploadLectureView.setVideoDetails(videoDetails.getVideoDetails());
            uploadLectureView.setProjectItemSelected(String.valueOf(videoDetails.getProjectItem().getProjectItemSeq()));
            uploadLectureView.setSkillMstrSelected(videoDetails.getSkillMstrSeqList());
        }
        Map<String,Integer> projectItemDropDown = Optional.ofNullable(fetchEmployeeForUserProfileSeq.getProjectItem().getProject().getProjectItems())
                .orElseGet(Collections::emptySet).stream()
                .collect(Collectors.toMap(ProjectItem::getProjectItemDesc,ProjectItem::getProjectItemSeq));
        uploadLectureView.setProjectItemDropDown(projectItemDropDown);
        return uploadLectureView;
    }
}
