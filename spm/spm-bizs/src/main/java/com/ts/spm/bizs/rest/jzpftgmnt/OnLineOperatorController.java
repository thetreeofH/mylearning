package com.ts.spm.bizs.rest.jzpftgmnt;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.rest.BaseController;
import com.github.wxiaoqi.security.common.util.DateUtil;
import com.ts.spm.bizs.biz.jzpftgmnt.TblDutyManAttendBiz;
import com.ts.spm.bizs.entity.jzpftgmnt.OnDutyManAttend;
import com.ts.spm.bizs.entity.jzpftgmnt.TblDutyManAttend;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OnLineOperatorController
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/9/21 16:00
 * @Version 1.0
 **/
@RestController
@RequestMapping("onLineOperator")
@CheckClientToken
@CheckUserToken
public class OnLineOperatorController extends BaseController<TblDutyManAttendBiz, TblDutyManAttend,String> {

    @Autowired
    TblDutyManAttendBiz tblDutyManAttendBiz;


    /**
     * 在线值机员列表
     * @return
     */
    @RequestMapping(value = "/judgePictureUsers/query",method = RequestMethod.GET)
    public ObjectRestResponse query(){
        Example example = new Example(TblDutyManAttend.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dutyDate", DateUtil.getCurrentDay());
        criteria.andBetween("stime", DateUtil.stringToDate(DateUtil.getCurrentDay()+" 00:00:00"),DateUtil.stringToDate(DateUtil.getCurrentDay()+" 23:59:59"));
        criteria.andIsNull("etime");
        criteria.andEqualTo("state",1);
        criteria.andEqualTo("userType",1);
        List<OnDutyManAttend> onDutyManAttendList = new ArrayList<OnDutyManAttend>();
        List<TblDutyManAttend> tblDutyManAttendList = baseBiz.selectByExample(example);
        if(tblDutyManAttendList != null && tblDutyManAttendList.size() > 0){
            for(TblDutyManAttend tblDutyManAttend : tblDutyManAttendList){
                OnDutyManAttend onDutyManAttend = new OnDutyManAttend();
                onDutyManAttend.setId(tblDutyManAttend.getId());
                onDutyManAttend.setDutyDate(tblDutyManAttend.getDutyDate());
                onDutyManAttend.setUserType(tblDutyManAttend.getUserType());
                onDutyManAttend.setScheduleId(tblDutyManAttend.getScheduleId());
                onDutyManAttend.setStime(tblDutyManAttend.getStime());
                onDutyManAttend.setState(tblDutyManAttend.getState());
                onDutyManAttend.setCrtTime(tblDutyManAttend.getCrtTime());
                if(tblDutyManAttend.getUserId() != null && !"".equals(tblDutyManAttend.getUserId())){
                    onDutyManAttend.setUserId(tblDutyManAttend.getUserId());
                    Map map = tblDutyManAttendBiz.selectByUserId(tblDutyManAttend.getUserId());
                    if(map != null && !map.isEmpty()){

                        onDutyManAttend.setUserName((String) map.get("handle_user_name"));

                    }
                }
                onDutyManAttendList.add(onDutyManAttend);
            }
        }
        return ObjectRestResponse.ok(onDutyManAttendList);
    }

}
