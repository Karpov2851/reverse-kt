package com.reverse.kt.main.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;

/**
 * Created by vikas on 19-06-2020.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class BaseMainTest {

    private static final String DUMMY_SOURCE_IMAGE = "images/dummy.jpg";
    private static final String DUMMY_PATH_TO_SAVE = "D:/Reverse-KT/";
    protected static File folder = new File(DUMMY_PATH_TO_SAVE);

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    protected File getDummyFile(){
        File file = new File(
                getClass().getClassLoader().getResource(DUMMY_SOURCE_IMAGE).getFile()
        );
        return file;
    }
}
