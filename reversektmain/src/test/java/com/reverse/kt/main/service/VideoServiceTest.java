package com.reverse.kt.main.service;

import com.reverse.kt.core.dao.VideoDetailsDao;
import com.reverse.kt.core.model.VideoDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


/**
 * Created by vikas on 17-06-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public class VideoServiceTest {

    @Mock
    private VideoDetailsDao videoDetailsDao;

    @InjectMocks
    private VideoService videoService;

    @Captor
    private ArgumentCaptor<VideoDetails> videoDetailsArgumentCaptor;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;

    @Test
    public void saveVideoDetailsTest() throws Exception{
        //given
        VideoDetails expected = videoDetailsRecord();
        doNothing().when(videoDetailsDao).saveOrUpdateEntity(videoDetailsArgumentCaptor.capture(),anyBoolean());

        //when
        videoService.saveVideoDetails(expected,false);

        //then
        assertThat(videoDetailsArgumentCaptor.getValue().getVideoName(),equalTo(expected.getVideoName()));
    }

    @Test
    public void fetchVideoDetailsByIdTest() throws Exception{
        //given
        VideoDetails expected = videoDetailsRecord();
        when(videoDetailsDao.findById(integerArgumentCaptor.capture())).thenReturn(expected);

        //when
        VideoDetails actual = videoService.fetchVideoDetailsById(expected.getVideoDetailSeq());

        //then
        assertNotNull(actual);
        assertThat(integerArgumentCaptor.getValue(),equalTo(expected.getVideoDetailSeq()));
    }


    private VideoDetails videoDetailsRecord(){
        return  VideoDetails.builder().videoDetailSeq(1).videoName("test video name")
                .videoDetails("video details").build();
    }
}
