package com.ts.spm.bizs.tool.oss.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * Created by zhoukun on 2020/5/6.
 */
@RestController
@RequestMapping("/pic")
public class PicController {
    @Value("${ftp.ftpPath}")
    private String ftpPath;

    @ApiOperation("图片预览")
    @GetMapping(value = "view/{filepath}/**")
    public void download(@PathVariable String filepath, HttpServletRequest request, HttpServletResponse resp) {
        final String path =
                request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
        final String bestMatchingPattern =
                request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        String arguments = new AntPathMatcher().extractPathWithinPattern(bestMatchingPattern, path);
        String filePath;
        if (null != arguments && !arguments.isEmpty()) {
            filePath = filepath + '/' + arguments;
        } else {
            filePath = filepath;
        }
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String lx = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(lx.length() > 0) {
            switch (lx) {
                case "jpg":
                    resp.setContentType("image/jpeg");
                    break;
                case "png":
                    resp.setContentType("image/png");
                    break;
                case "gif":
                    resp.setContentType("image/gif");
                    break;
                case "bmp":
                    resp.setContentType("application/x-bmp");
                    break;
                default:
                    resp.setContentType("image/jpeg");
            }
            try {
                FileInputStream is = new FileInputStream(ftpPath + filePath);
                OutputStream os = resp.getOutputStream();
                byte[] imgByte = new byte[is.available()];
                is.read(imgByte);
                os.write(imgByte);
                os.close();
                is.close();
            } catch (Exception e) {

            }
        }
    }
}
