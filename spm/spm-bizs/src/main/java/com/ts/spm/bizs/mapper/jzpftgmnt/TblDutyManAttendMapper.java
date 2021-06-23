package com.ts.spm.bizs.mapper.jzpftgmnt;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.ts.spm.bizs.entity.jzpftgmnt.TblDutyManAttend;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TblDutyManAttendMapper extends CommonMapper<TblDutyManAttend> {
    Map<String,String> selectByUserId(@Param("userId") String userId);

    ////////////acm
    /************** 用户打卡状态
     * @param date2 *****************/

    Map selectInfo(@Param("date") String date, @Param("date2") String date2);

    Short selsectUserType(@Param("id") String userID, @Param("name") String name);

    List<String> selectUserCar(@Param("id") String userID);

    void updateStatus(@Param("id") String id, @Param("time") String time);

    /************** 值机员考勤*****************/

    List<Map> query(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                    @Param("userName") String userName);

    List<BigDecimal> queryRecess(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                                 @Param("userId") String userId);

    List<Map> mysqlQuery(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                         @Param("userName") String userName);

    Map<String, BigDecimal> mysqlQuery2(@Param("userId") String userId, @Param("beginTime") String beginTime,
                                        @Param("endTime") String endTime);

    Map<String, BigDecimal> mysqlQuery3(@Param("userId") String userId, @Param("beginTime") String beginTime,
                                        @Param("endTime") String endTime);

    Integer mysqlQuery4(@Param("userId") String userId, @Param("beginTime") String beginTime,
                        @Param("endTime") String endTime);

    List<Map> queryInfoAttend(@Param("userId") String userId, @Param("monthBegin") String monthBegin);

    Map queryInfo(@Param("scheduleId") String scheduleId, @Param("userId") String userId);

    String queryClassType(@Param("scheduleId") String scheduleId);

    List<Map> queryPerformance(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                               @Param("userName") String userName);

    Double queryProbability(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                            @Param("userId") String userId);

    Map queryifBeLate(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                      @Param("userId") String userId);

    /************** 开检员考勤*****************/
    List<Map> openInspectionQuery(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
                                  @Param("userName") String userName, @Param("pointIds") List<String> pointIds);
}