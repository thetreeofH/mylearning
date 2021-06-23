package com.ts.spm.bizs.biz.msg;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.ts.spm.bizs.entity.matter.PointDanger;
import com.ts.spm.bizs.entity.msg.AppPushToken;
import com.ts.spm.bizs.entity.msg.AppPushUserMap;
import com.ts.spm.bizs.entity.msg.TblAppPushUserMap;
import com.ts.spm.bizs.mapper.msg.TblAppPushUserMapMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TblAppPushUserMapBiz
 * @Description TODO
 * @Author fengzheng
 * @Date 2020/11/5 16:25
 * @Version 1.0
 **/
@Service
public class TblAppPushUserMapBiz extends BusinessBiz<TblAppPushUserMapMapper, TblAppPushUserMap> {

    public AppPushToken getPushToken(PointDanger pointDanger, Integer appType) {
        //首先获取station和point信息
        String stationName=null;
        String pointName=null;
        String pointId = null;
        String stationId = null;
        if (pointDanger != null && pointDanger.getPointId() != null){
            pointId=pointDanger.getPointId();
            pointName = pointDanger.getPointName();
            stationId = pointDanger.getDepartId();
            stationName = pointDanger.getDepartName();
        }else{
            return null;
        }

        AppPushToken pd = null; //返回值
        List<AppPushUserMap> tokens = new ArrayList<AppPushUserMap>();

        List<TblAppPushUserMap> listCommon = getPushCodes(stationId,appType);
        if(listCommon != null && listCommon.size() > 0){
            for(TblAppPushUserMap tblAppPushUserMap : listCommon){
                AppPushUserMap appPushUserMap = new AppPushUserMap();
                appPushUserMap.setId(tblAppPushUserMap.getId());
                appPushUserMap.setPushCode(tblAppPushUserMap.getPushCode());
                appPushUserMap.setAppType(tblAppPushUserMap.getAppType());
                appPushUserMap.setUserId(tblAppPushUserMap.getUserId());
                tokens.add(appPushUserMap);
            }
        }

        if (tokens.size() > 0){
            pd = new AppPushToken();
            pd.setStation(stationName);
            pd.setPoint(pointName);
            pd.setTokens(tokens);

        }
        return pd;
    }

    public List<TblAppPushUserMap> getPushCodes(String stationId,Integer appType){
        return mapper.getPushCodes(stationId,appType);
    }

    public Map<String,String> selectForbiddenName(Integer id){
        return mapper.selectForbiddenName(id);
    }


    public int deleteByPushCode(String userId,String pushCode,String appType){
        return mapper.deleteByPushCode(userId,pushCode,appType);
    }

    public Map<String,String> selectById(String id){
        return mapper.selectById(id);
    }

    public List<TblAppPushUserMap> selectByUserIdAndDevCode(String userId,String devCode){
        return mapper.selectByUserIdAndDevCode(userId,devCode);
    }
}
