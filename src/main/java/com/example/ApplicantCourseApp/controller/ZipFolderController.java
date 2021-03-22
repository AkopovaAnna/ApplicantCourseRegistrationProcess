package com.example.ApplicantCourseApp.controller;

import com.example.ApplicantCourseApp.security.SecurityContextAccessor;
import com.example.ApplicantCourseApp.service.DownloadZipService;
import com.example.ApplicantCourseApp.system.UserPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/downloadzip")
public class ZipFolderController {

    @Autowired
    private DownloadZipService zipService;

    @Autowired
    private UserPermissionEvaluator permissionEvaluator;

    @Autowired
    private SecurityContextAccessor contextAccessor;

    @GetMapping()
    public void downloadFile(HttpServletResponse response) throws IOException {
        permissionEvaluator.checkPermission(contextAccessor.getUserDetails());

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
        zipService.zipFolder(zos);
        zos.close();

    }
}
