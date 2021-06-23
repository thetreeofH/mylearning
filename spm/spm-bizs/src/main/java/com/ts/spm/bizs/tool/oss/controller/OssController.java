/*
 *
 *  *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>
 *
 *  *  AG-Enterprise 企业版源码
 *  *  郑重声明:
 *  *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  *  老A将追究授予人和传播人的法律责任!
 *
 *  *  This program is free software; you can redistribute it and/or modify
 *  *  it under the terms of the GNU General Public License as published by
 *  *  the Free Software Foundation; either version 2 of the License, or
 *  *  (at your option) any later version.
 *
 *  *  This program is distributed in the hope that it will be useful,
 *  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *  GNU General Public License for more details.
 *
 *  *  You should have received a copy of the GNU General Public License along
 *  *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.ts.spm.bizs.tool.oss.controller;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.exception.base.BusinessException;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.ts.spm.bizs.tool.oss.cloud.ImgTools;
import com.ts.spm.bizs.tool.oss.cloud.OSSFactory;
import com.ts.spm.bizs.tool.oss.constants.OSSConstant;
import com.ts.spm.bizs.tool.oss.util.FTPUtils;
import com.ts.spm.bizs.tool.oss.util.FtpUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 文件上传
 *
 * @author ace
 */
@RestController
@RequestMapping("/oss")
@CheckClientToken
@CheckUserToken
public class OssController {
    @Autowired
    private OSSFactory ossFactory;

    @Value("${ftp.ftpHost}")
    private String ftpHost;

    @Value("${ftp.ftpPort}")
    private Integer ftpPort;

    @Value("${ftp.ftpUserName}")
    private String ftpUserName;

    @Value("${ftp.ftpPassword}")
    private String ftpPassword;

    @Value("${ftp.ftpPath}")
    private String ftpPath;

    @Value("${ftp.ftpDa}")
    private String ftpDa;

    @Value("${ftp.ftpOther}")
    private String ftpOther;

    @Value("${ftp.ftpRootDir}")
    private String ftpRootDir;

    @Value("${ftp.ftpLocal}")
    private String ftpLocal;

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public ObjectRestResponse upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }
        byte[] filebytes = ImgTools.compressUnderSize(file.getBytes(), 1000 * 1024);
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = ossFactory.build().uploadSuffix(filebytes, suffix);
        return new ObjectRestResponse<>().data(url);
    }


    /**
     * 上传文件
     */
    @RequestMapping("/ftpUpload")
    @ResponseBody
    public ObjectRestResponse ftpupload(@RequestParam("file") MultipartFile file, HttpServletRequest request, Integer type) throws Exception {
        String ftpPath = null;
        String StrDate = DateTime.now().toString("yyyyMMdd");
        String departID = BaseContextHandler.getDepartID() + "/";
        //String userID=BaseContextHandler.getUserID()+"/";
        if (type == OSSConstant.Da) {
            ftpPath = ftpDa + departID + StrDate;
        } else {
            ftpPath = ftpOther + departID + StrDate;
        }

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + suffix;
        Map<String, Object> test = new HashMap<>();
        //上传一个文件
        try {
            test = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, file.getInputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return new ObjectRestResponse<>().data(test.get("url"));
    }

    /**
     * 上传文件设备防区图
     */
    @RequestMapping("/daupload")
    @ResponseBody
    public ObjectRestResponse ftpupload2(@RequestBody String[] da) throws Exception {
        if(da == null || da.length != 2)
            return ObjectRestResponse.error("数据格式错误");
        String departId = da[0];
        String content = da[1];
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imgByte = decoder.decodeBuffer(content);
        String lx = getTypeByStream(imgByte);
        String ftpPath = ftpDa + departId;
        InputStream is = new ByteArrayInputStream(imgByte);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + "." + lx;
        //上传一个文件
        Map<String, Object> test = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, is);
        return new ObjectRestResponse<>().data(test.get("url"));
    }

    public String getTypeByStream(byte[] fileTypeByte){
        String type = bytesToHexString(fileTypeByte).toUpperCase();
        if(type.contains("FFD8FF")){
            return "jpg";
        }else if(type.contains("89504E47")){
            return "png";
        }else if(type.contains("47494638")){
            return "gif";
        }else if(type.contains("49492A00")){
            return "tif";
        }else if(type.contains("424D")){
            return "bmp";
        }else{
            return null;
        }
    }

    public String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 文件下载
     */
//    @PostMapping(value = "ftpDownload")
//    public void downLoadFile(HttpServletResponse response, HttpServletRequest request, String filePath) throws Exception {
//        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
//        //获取浏览器用户代理
//        String agent = request.getHeader("User-Agent");
//        //根据浏览器不同处理文件名防止中文乱码
//        if (agent.contains("MSIE")) {
//            fileName = URLEncoder.encode(fileName, "utf-8");
//            fileName = fileName.replace("+", " ");
//        } else if (agent.contains("Firefox")) {
//            BASE64Encoder base64Encoder = new BASE64Encoder();
//            fileName = "=?utf-8?B?" + base64Encoder.encode(fileName.getBytes("utf-8")) + "?=";
//        } else {
//            fileName = URLEncoder.encode(fileName, "utf-8");
//        }
//        //设置文件头：最后一个参数是设置下载文件名
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        //设置文件ContentType类型，这样设置，会自动判断下载文件类型
////        response.setContentType("application/multipart/form-data");
//        response.setContentType("application/octet-stream;charset=UTF-8");
//
//        FTPClient ftpClient = null;
//
//        try {
//            ftpClient = FtpUtil.getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
//            ftpClient.setControlEncoding("UTF-8"); // 中文支持
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            ftpClient.enterLocalPassiveMode();
//            InputStream fis = ftpClient.retrieveFileStream(filePath);
//            if(fis != null) {
//                OutputStream out = response.getOutputStream();
//                int BUFFER = 1024 * 10;
//                byte data[] = new byte[BUFFER];
//                BufferedInputStream bis = null;
//
//                int read;
//                bis = new BufferedInputStream(fis, BUFFER);
//                while ((read = bis.read(data)) != -1) {
//                    out.write(data, 0, read);
//                }
//                fis.close();
//                bis.close();
//            }
//
//        } catch (FileNotFoundException e) {
//            System.out.println("没有找到" + ftpPath + "文件");
//            e.printStackTrace();
//        } catch (SocketException e) {
//            System.out.println("连接FTP失败.");
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("文件读取错误。");
//            e.printStackTrace();
//        } finally {
//            if(ftpClient != null)
//                ftpClient.logout();
//        }
//    }
    @PostMapping(value = "ftpDownload")
    public void downLoadFile(HttpServletResponse response, HttpServletRequest request, String filePath) throws Exception {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String dir=filePath.substring(0,filePath.lastIndexOf("/")+1);

        //获取浏览器用户代理
         String agent = request.getHeader("User-Agent");
//        //根据浏览器不同处理文件名防止中文乱码
        if (agent.contains("MSIE")) {
            fileName = URLEncoder.encode(fileName, "utf-8");
            fileName = fileName.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            fileName = "=?utf-8?B?" + base64Encoder.encode(fileName.getBytes("utf-8")) + "?=";
        } else {
            fileName = URLEncoder.encode(fileName, "utf-8");
        }
//        //设置文件头：最后一个参数是设置下载文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        //设置文件ContentType类型，这样设置，会自动判断下载文件类型
        //response.setContentType("application/multipart/form-data");
        response.setContentType("application/octet-stream;charset=UTF-8");
        File file = new File( ftpLocal+File.separator+fileName);
        ServletOutputStream out;
        try {
//            boolean f= FTPUtils.ftpDownload(fileName,ftpHost,ftpPort,ftpUserName,ftpPassword,ftpRootDir+dir,ftpLocal);
//            if (f) {
//                System.out.println("=======下载文件"+ fileName +"成功=======");
//            } else {
//                System.out.println("=======下载文件"+ fileName +"失败=======");
//            }
            FileInputStream inputStream = new FileInputStream(file);
            //3.通过response获取ServletOutputStream对象(out)
            out = response.getOutputStream();
            int b = 0;
            byte[] buffer = new byte[1024];
            while (b != -1){
                b = inputStream.read(buffer);
                //4.写到输出流(out)中
                out.write(buffer,0,b);
            }
            inputStream.close();
            out.close();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("文件读取错误。"+e.getMessage());
            e.printStackTrace();
        } finally {

        }
    }
    /**
     * 上传文件
     */
//    @RequestMapping("/ftpupload2")
//    @ResponseBody
//    public ObjectRestResponse ftpupload2(String url, Integer type) throws Exception {
//        String ftpPath = null;
//        String StrDate = DateTime.now().toString("yyyyMMdd");
//        String departID = BaseContextHandler.getDepartID() + "/";
//        ftpPath = ftpAudio + departID + StrDate;
//        URL fileUrl = new URL(url);
//        URLConnection conn = fileUrl.openConnection();
//        InputStream is = conn.getInputStream();
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        String fileName = uuid + ".wav";
//        //上传一个文件
//        Map<String, Object> test = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, is);
//        return new ObjectRestResponse<>().data(test.get("url"));
//    }


    /**
     * 上传二维码文件
     */
//    @RequestMapping("/ftpQrcodeupload")
//    @ResponseBody
//    public Map<String, Object> ftpupload(@RequestParam("file") MultipartFile file, HttpServletRequest request, String path) throws Exception {
//        String ftpPath = null;
//        ftpPath = ftpQrcode + path;
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        String fileName = file.getOriginalFilename();
//        Map<String, Object> test = new HashMap<>();
//        //上传一个文件
//        try {
//            test = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName, file.getInputStream());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return test;
//    }
    /**
     * 删除FTP上的文件
     *
     * @return
     */
    @IgnoreUserToken
    @IgnoreClientToken
    @RequestMapping(value = "delFtpFile", method = RequestMethod.GET)
    public boolean delFtpFile(String filePath)  {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1); //截取最后一个“/”前面的内容
        filePath=filePath.substring(0,filePath.lastIndexOf("/")); //截取最后一个“/”前面的内容
        boolean b=false;
        try {
            b=FtpUtil.deleteFile(ftpHost, ftpUserName,
                    ftpPassword, ftpPort, filePath, fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

}
