package com.example.ApplicantCourseApp.service;

import com.example.ApplicantCourseApp.exception.DocumentDownloadException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DownloadZipService {

    private static final String SOURCE_FOLDER = "certifications";
    private List<File> fileList = new ArrayList<>();


    public void zipFolder(ZipOutputStream zos) {
        generateFileList(new File(SOURCE_FOLDER));
        try {
            for (File file : fileList) {
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(file);
                zos.putNextEntry(new ZipEntry(file.getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
        } catch (IOException ex) {
            throw new DocumentDownloadException(ex.getMessage());
        }
    }

    public void generateFileList(File node) {
        fileList = new ArrayList<>();
        URL url = this.getClass().getClassLoader().getResource("certifications");
        if (url != null) {
            File dir = new File(url.getPath());
            if (dir.exists()) {
                File[] filesArray = dir.listFiles();
                if (filesArray != null || filesArray.length > 0) {
                    for (File xlsFile : filesArray) {
                        if (!xlsFile.isFile()) continue;
                        fileList.add(xlsFile);
                    }
                }
            }
        }
    }
}
