package com.reverse.kt.core.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by vikas on 11-05-2020.
 */
public class FileUtil {

    public void writeImageToAFile(String filePath,String filePathToWrite) throws IOException{
        BufferedImage bImage = null;
        File initialImage = new File(filePath);
        bImage = ImageIO.read(initialImage);
        ImageIO.write(bImage, "jpg", new File(filePathToWrite));
    }

    public static void writeByteArrayToFile(MultipartFile file, String filePathToSave,String fileNameToWrite) throws IOException{
        byte [] imageFileInBytes = file.getBytes();
        File serverFileDest = new File(filePathToSave);
        if (!serverFileDest.exists()){
            serverFileDest.mkdirs();
        }
        File serverFile = new File(serverFileDest.getAbsolutePath()
                + File.separator + fileNameToWrite);
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
        stream.write(imageFileInBytes);
        stream.close();
    }
}
