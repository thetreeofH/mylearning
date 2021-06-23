package com.ts.spm.bizs.rest.msg;

import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.JDKBase64Util;
import com.ts.spm.bizs.biz.msg.InnerCheckerBiz;
import com.ts.spm.bizs.entity.stat.TblInnerCheck;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblem;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPerson;
import com.ts.spm.bizs.entity.stat.TblInnerCheckProblemPhoto;
import com.ts.spm.bizs.rest.stat.InnerCheckController;
import com.ts.spm.bizs.tool.oss.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProbCheckController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/11/16 9:56
 * @Version 1.0
 *
 * updater 马居朝
 * Line52和Line49改成了对应配置文件里的ftp。
 **/
@RestController
@RequestMapping("videoCheck")
@IgnoreClientToken
@IgnoreUserToken
public class VideoCheckController {

    @Value("${ftp.ftpHost}")
    private String local;

    @Value("${ftp.ftpPort}")
    private String prot;

    @Value("${ftp.ftpUserName}")
    private String ftpUserName;

    @Value("${ftp.ftpPassword}")
    private String passWord;

    @Value("${ftp.probCheckPicturePath}")
    private String probCheckPicturePath;

    @Autowired
    InnerCheckController InnerCheckControl;

    @Autowired
    InnerCheckerBiz innerCheckBiz;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestParam("picture") String picture,
                                  @RequestParam("userName") String userName,
                                  @RequestParam("stationId") String stationId,
                                  @RequestParam("pointId") String pointId,
                                  @RequestParam("discoveryTime") String discoveryTime,
                                  @RequestParam("stationName") String stationName,
                                  @RequestParam("securityCheckPonit") String securityCheckPonit,
                                  @RequestParam("positionType") Integer positionType,
                                  @RequestParam("problemType") Integer problemType,
                                  @RequestParam("supplement") String supplement){
        String picPath = null;
        String filePath1 = probCheckPicturePath + "/"
                + DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");

        String fileName1 = "probCheckPicturePath" + DateUtil.getRevTime() + ".jpg";
        byte[] bytes = null;
        try {
            bytes = JDKBase64Util.decryptBASE64(picture.trim());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        Map<String, Object> map1 = FtpUtil.uploadFile(local, ftpUserName, passWord, Integer.parseInt(prot),
                filePath1, fileName1, inputStream);
        if ((boolean) map1.get("status") == true) {
            picPath = map1.get("url").toString();
        }
        TblInnerCheck innerCheck = new TblInnerCheck();

        Map<String,String> map = innerCheckBiz.getDepartByUserName(userName);
        String userId = null;
        if(map != null && !map.isEmpty()){
            userId = map.get("userId");
            innerCheck.setCheckOrg(map.get("departId"));
            innerCheck.setCrtUserId(map.get("userId"));
            innerCheck.setCheckName(map.get("userName"));
        }
        innerCheck.setCrtUserName(userName);
        innerCheck.setCheckTime(DateUtil.stringToDate(discoveryTime));
        innerCheck.setCheckType(1);//0现场检查1视频检查
        innerCheck.setPointId(pointId);
        innerCheck.setPointName(securityCheckPonit);
        innerCheck.setStationId(stationId);
        innerCheck.setStationName(stationName);
        innerCheck.setHasProb(true);
        innerCheck.setPlanCount(3);
        innerCheck.setReanCount(3);
        innerCheck.setCardCount(3);
        innerCheck.setHasLackPerson(false);
        innerCheck.setCrtTime(new Date());
        innerCheck.setProbSource(0);//0：运营公司 1：安检公司
        //视频检查问题
        List<TblInnerCheckProblem> tblInnerCheckProblemList = new ArrayList<>();
        TblInnerCheckProblem innerCheckProblem = new TblInnerCheckProblem();
        innerCheckProblem.setAjyType(positionType);
        innerCheckProblem.setProbType(problemType);
        innerCheckProblem.setProbDesp(supplement);
        innerCheckProblem.setCrtTime(new Date());
        innerCheckProblem.setCrtUserId(userId);
        innerCheckProblem.setCrtUserName(userName);



        //视频检查问题人员
        List<TblInnerCheckProblemPerson> tblInnerCheckProblemPersonList = new ArrayList<>();
        TblInnerCheckProblemPerson innerCheckProblemPerson = new TblInnerCheckProblemPerson();
        innerCheckProblemPerson.setAjyType(positionType);
        innerCheckProblemPerson.setCheckerName(userName);
        innerCheckProblemPerson.setCheckerId(userId);
        innerCheckProblemPerson.setCrtTime(new Date());
        innerCheckProblemPerson.setCrtUserId(userId);
        innerCheckProblemPerson.setCrtUserName(userName);
        tblInnerCheckProblemPersonList.add(innerCheckProblemPerson);

        //视频检查图片
        List<TblInnerCheckProblemPhoto> tblInnerCheckProblemPhotoList = new ArrayList<>();
        TblInnerCheckProblemPhoto innerCheckProblemPhoto = new TblInnerCheckProblemPhoto();
        innerCheckProblemPhoto.setPicName("视频检查图片");
        innerCheckProblemPhoto.setPicPath(picPath);
        innerCheckProblemPhoto.setCrtTime(new Date());
        innerCheckProblemPhoto.setCrtUserId(userId);
        innerCheckProblemPhoto.setCrtUserName(userName);
        tblInnerCheckProblemPhotoList.add(innerCheckProblemPhoto);

        innerCheckProblem.setTblInnerCheckProblemPersonList(tblInnerCheckProblemPersonList);
        innerCheckProblem.setTblInnerCheckProblemPhotoList(tblInnerCheckProblemPhotoList);

        tblInnerCheckProblemList.add(innerCheckProblem);

        innerCheck.setTblInnerCheckProblemList(tblInnerCheckProblemList);

        return InnerCheckControl.add(innerCheck);

    }
}
