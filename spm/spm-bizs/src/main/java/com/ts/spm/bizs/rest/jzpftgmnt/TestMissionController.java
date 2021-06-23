package com.ts.spm.bizs.rest.jzpftgmnt;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.ts.spm.bizs.biz.jzpmq.TblTestMissionBiz;
import com.ts.spm.bizs.entity.jzpmq.TblTestMission;
import com.ts.spm.bizs.util.FtpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FictitiousJudgePictureController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/9/16 19:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("fictitiousJudgePicture")
@CheckClientToken
@CheckUserToken
public class TestMissionController extends BaseController<TblTestMissionBiz, TblTestMission,String> {

    @Value("${oipftp.local}")
    private String local;

    @Value("${oipftp.prot}")
    private String prot;

    @Value("${oipftp.userName}")
    private String userName;

    @Value("${oipftp.passWord}")
    private String passWord;

    @Value("${oipftp.fictitiousJudgePicturePath}")
    private String fictitiousJudgePicturePath;

    @Autowired
    TblTestMissionBiz tblTestMissionBiz;
    /**
     * 新增虚拟判图任务
     * @param tblTestMission
     * @return
     */
    @RequestMapping(value = "/information/add",method = RequestMethod.POST)
    public ObjectRestResponse add(@RequestBody TblTestMission tblTestMission){
        String[] userIds = tblTestMission.getJudgePictureUserId().split(",");
        for(String userId : userIds){
            tblTestMission.setId(UUIDUtils.generateUuid());
            tblTestMission.setMissionId(UUIDUtils.generateUuid());
            tblTestMission.setJudgePictureUserId(userId);
            tblTestMission.setIfDistinguish(0);
            tblTestMission.setIfSend(0);
            tblTestMission.setCreateTime(new Date());
            baseBiz.insertSelective2(tblTestMission);
        }
        return ObjectRestResponse.ok();
    }

    /**
     * 值机端查询虚拟判图任务
     * @param userId
     * @return
     */
    @RequestMapping(value = "/information/query/{userId}/{userName}",method = RequestMethod.GET)
    public ObjectRestResponse query(@PathVariable String userId,@PathVariable String userName){

        Example example = new Example(TblTestMission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("judgePictureUserId",userId);
        String startTime = DateUtil.getCurrentDay()+" 00:00:00";
        String endTime = DateUtil.getCurrentDay()+" 23:59:59";
        criteria.andBetween("createTime",DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        criteria.andEqualTo("ifSend",0);
        List<TblTestMission> tblTestMissionList = baseBiz.selectByExample(example);
        if(tblTestMissionList != null && tblTestMissionList.size() > 0){
            for(TblTestMission tblTestMission : tblTestMissionList){
                tblTestMission.setIfSend(1);
                tblTestMission.setJudgePictureUserName(userName);
                baseBiz.updateSelectiveById2(tblTestMission);
            }

            return ObjectRestResponse.ok(tblTestMissionList);

        }
        return ObjectRestResponse.error("当前用户无虚拟判图任务！");
    }

    /**
     * 已发送虚拟判图任务
     * @param limit
     * @param page
     * @param startTime
     * @param endTime
     * @param testMissionName
     * @return
     */
    @RequestMapping(value = "/information/query/send",method = RequestMethod.GET)
    public TableResultResponse send(@RequestParam(defaultValue = "10") int limit,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "") String startTime,
                                    @RequestParam(defaultValue = "") String endTime,
                                    @RequestParam(defaultValue = "") String testMissionName){
        Example example = new Example(TblTestMission.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(testMissionName)){
            criteria.andEqualTo("testMissionName",testMissionName);
        }

        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            criteria.andBetween("createTime", DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        }
        Page<TblTestMission> testMissionPage = PageHelper.startPage(page,limit);
        List<TblTestMission> tblTestMissionList = baseBiz.selectByExample(example);
        return new TableResultResponse(testMissionPage.getTotal(),tblTestMissionList);
    }

    /**
     * 虚拟判图任务列表查询
     * @param limit
     * @param page
     * @param startTime
     * @param endTime
     * @param userName
     * @param ifDistinguish
     * @param testMissionName
     * @return
     */
    @RequestMapping(value = "/information/queryTestMission",method = RequestMethod.GET)
    public TableResultResponse queryMission(@RequestParam(defaultValue = "10") int limit,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "") String startTime,
                                            @RequestParam(defaultValue = "") String endTime,
                                            @RequestParam(defaultValue = "") String userName,
                                            @RequestParam(defaultValue = "") String ifDistinguish,
                                            @RequestParam(defaultValue = "") String testMissionName){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        Example example = new Example(TblTestMission.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(testMissionName)){
            criteria.andEqualTo("testMissionName",testMissionName);
        }
        if(StringUtils.isNotBlank(userName)){
            criteria.andLike("judgePictureUserName","%"+userName+"%");
        }
        if(StringUtils.isNotBlank(ifDistinguish)){
            criteria.andEqualTo("ifDistinguish",ifDistinguish);
        }
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            criteria.andBetween("createTime", DateUtil.stringToDate(startTime),DateUtil.stringToDate(endTime));
        }
        example.setOrderByClause("create_time desc");
        Page<TblTestMission> testMissionPage = PageHelper.startPage(page,limit);
        List<TblTestMission> tblTestMissionList = baseBiz.selectByExample(example);
        return new TableResultResponse(testMissionPage.getTotal(),tblTestMissionList);

    }

    /**
     * 虚拟判图任务统计
     * @param startTime
     * @param endTime
     * @param judgePictureUserName
     * @return
     */
    @RequestMapping(value = "/information/statistics",method = RequestMethod.GET)
    public ObjectRestResponse statistics(@RequestParam String startTime,
                                         @RequestParam String endTime,
                                         @RequestParam Integer ifDistinguish,
                                         @RequestParam(value = "judgePictureUserName",required = false) String  judgePictureUserName){
        if(StringUtils.isNoneBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNoneBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }


        List<Map<String,String>> distinguishList = tblTestMissionBiz.statistics(startTime,endTime,judgePictureUserName,ifDistinguish);


        return ObjectRestResponse.ok(distinguishList);

    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public ObjectRestResponse uploadFile(@RequestParam(value = "file") MultipartFile file){
        String filePath = fictitiousJudgePicturePath + "/"
                + DateUtil.date2Str(new Date(), "yyyy/MM/dd/HH/mm/ss");

        String fileName = "suspectedItemsPicturePath" + DateUtil.getRevTime() + ".jpg";

        Map<String, Object> map2 = null;
        try {
            map2 = FtpUtil.uploadFile(local, userName, passWord, Integer.parseInt(prot),
                    filePath, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if ((boolean) map2.get("status") == true) {
            return new ObjectRestResponse<>().data(map2.get("url").toString());
        }
        return new ObjectRestResponse<>().error("上传失败");
    }
}
