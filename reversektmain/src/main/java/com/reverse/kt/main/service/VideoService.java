package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.VideoDetailsDao;
import com.reverse.kt.core.model.VideoDetails;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by vikas on 17-06-2020.
 */
@Service
@Transactional
@Setter(onMethod = @__(@Inject))
public class VideoService {

    private VideoDetailsDao videoDetailsDao;

    public void saveVideoDetails(VideoDetails videoDetails,boolean isInsert) throws Exception{
        videoDetailsDao.saveOrUpdateEntity(videoDetails,isInsert);
    }

    public VideoDetails fetchVideoDetailsById(Integer recordSeq) throws Exception{
        return videoDetailsDao.findById(recordSeq);
    }
}
