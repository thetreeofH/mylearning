package com.ts.spm.bizs.rest.up;

import com.alibaba.fastjson.JSON;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.ts.spm.bizs.biz.up.*;
import com.ts.spm.bizs.entity.up.*;
import com.ts.spm.bizs.util.IdcardUtils;
import com.ts.spm.bizs.vo.up.OpenInspectionVo;
import com.ts.spm.bizs.vo.up.PassPersonVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoukun on 2021/4/16.
 * 智慧安检上报
 */
@RestController
@RequestMapping("data")
public class UpController {
    @Autowired
    private SecurityDeviceInfoBiz securityDeviceInfoBiz;
    @Autowired
    private PassPersonBiz passPersonBiz;
    @Autowired
    private PointDangerInfoBiz pointDangerBiz;
    @Autowired
    private ThermalBiz thermalBiz;
    @Autowired
    private OpenInspectionBiz openInspectionBiz;

    @Value("${up.picpath}")
    private String picPath;
    @Value("${up.base64}")
    private boolean base64;
    @Value("${ftp.ftpLocal}")
    private String ftpLocal;
    @Value("${up.uppath}")
    private String upPath;
    @Value("${up.switch}")
    private boolean upSwitch;

    private SimpleDateFormat formatTime = new SimpleDateFormat("YYYYMMddHHmmss");
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 */5 5-23 * * ?")
    public void PassPersionCount() {
        if(upSwitch) {
            List<PassPerson> info = passPersonBiz.getLastInfo();
            if(info.size() > 0) {
                Date lasttime = info.get(0).getLastTime();
                List<PassPersonVo> listInfos = new ArrayList<>();
                Date t = new Date();
                for(PassPerson item1 : info) {
                    if(lasttime.getTime() < item1.getLastTime().getTime())
                        lasttime = item1.getLastTime();
                    List<SecurityDeviceInfo> devs = securityDeviceInfoBiz.selectList(item1.getCheckPositionCode());
                    if(devs.size() == 0) {
                        PassPersonVo vo = new PassPersonVo();
                        vo.setLineCode(item1.getLineCode());
                        vo.setLineName(item1.getLineName());
                        vo.setStationCode(item1.getStationCode());
                        vo.setStationName(item1.getStationName());
                        vo.setCheckPositionCode(item1.getCheckPositionCode());
                        vo.setCheckPositionName(item1.getCheckPositionName());
                        vo.setCollectTime(t.getTime());
                        vo.setIncreaseCount(item1.getIncreaseCount());
                        vo.setCurrentTotalCount(item1.getCurrentTotalCount());
                        vo.setSource(item1.getSource());
                    } else {
                        int num = item1.getIncreaseCount();
                        int n1 = num / devs.size();
                        int sum = item1.getCurrentTotalCount();
                        int n2 = sum / devs.size();
                        int index = 1;
                        for(SecurityDeviceInfo item2: devs) {
                            PassPersonVo vo = new PassPersonVo();
                            vo.setLineCode(item1.getLineCode());
                            vo.setLineName(item1.getLineName());
                            vo.setStationCode(item1.getStationCode());
                            vo.setStationName(item1.getStationName());
                            vo.setCheckPositionCode(item1.getCheckPositionCode());
                            vo.setCheckPositionName(item1.getCheckPositionName());
                            vo.setDeviceSerialNumber(item2.getDevId());
                            vo.setCollectTime(t.getTime());
                            vo.setSource("2");
                            if(index == devs.size()) {
                                vo.setIncreaseCount(num - n1 * (devs.size() - 1));
                                vo.setCurrentTotalCount(sum - n2 * (devs.size() - 1));
                            } else {
                                vo.setIncreaseCount(n1);
                                vo.setCurrentTotalCount(n2);
                            }
                            listInfos.add(vo);
                            index++;
                        }
                    }
                }
                passPersonBiz.updatetime(formatTime.format(lasttime));
                System.out.println("客流数据上传");
                System.out.println(JSON.toJSONString(listInfos));
                String result = restTemplate.postForObject(upPath + "exchange/persontotal", listInfos, String.class);
                System.out.println(result);
            }
        }
    }

    @ApiOperation("违禁品包裹数据上传")
    @RequestMapping(value = "/packagealarm/{id}", method = RequestMethod.GET)
    public ObjectRestResponse PackageAlarmInfo(@PathVariable("id") String id, String suspected_items_picture, String suspected_items_visual_picture) {
        if(upSwitch) {
            PackageInfo info = pointDangerBiz.getPackageAlarmInfo(id);
            if(info != null) {
                info.setIsAlarm(true);
                if(base64) {
                    info.setImgMainUrl(suspected_items_picture);
                    info.setVisiableImgBackUrl(suspected_items_visual_picture);
                } else {
                    if (info.getImgMainUrl() != null && info.getImgMainUrl().length() > 0) {
                        info.setImgMainUrl(picPath + info.getImgMainUrl());
                    }
                    if (info.getVisiableImgBackUrl() != null && info.getVisiableImgBackUrl().length() > 0) {
                        info.setVisiableImgBackUrl(picPath + info.getVisiableImgBackUrl());
                    }
                }
                info.setContrabandNum(1);
                info.setHascontraband(1);
                System.out.println("违禁品包裹数据上传");
                //info = changePackageInfo(info);
                PackageAlarmDetail detail = new PackageAlarmDetail();
                detail.setViewType(1);
                detail.setDictValue(info.getDictValues());
                detail.setDictValueName(getWjpNameByCode(info.getDictValues()));
                info.setDetail(new PackageAlarmDetail[] {detail});
                System.out.println(JSON.toJSONString(info));
                String result = restTemplate.postForObject(upPath + "exchange/xraydata", info, String.class);
                System.out.println(result);
            }
        }
        return ObjectRestResponse.ok();
    }

    @ApiOperation("包裹数据上传")
    @RequestMapping(value = "/package/{id}", method = RequestMethod.GET)
    public ObjectRestResponse PackageInfo(@PathVariable("id") String id, String Xray_machine_picture, String natural_light_pictures) {
        if(upSwitch) {
            PackageInfo info = pointDangerBiz.getPackageInfo(id);
            if(info != null) {
                if(base64) {
                    info.setImgMainUrl(Xray_machine_picture);
                    info.setVisiableImgBackUrl(natural_light_pictures);
                } else {
                    info.setImgMainUrl(picPath + info.getImgMainUrl());
                    info.setVisiableImgBackUrl(picPath + info.getVisiableImgBackUrl());
                }
                info.setIsAlarm(false);
                info.setContrabandNum(0);
                info.setHascontraband(0);
                //info.setTag(null);
                System.out.println("包裹数据上传");
                //info = changePackageInfo(info);
                //System.out.println(JSON.toJSONString(info));
                String result = restTemplate.postForObject(upPath + "exchange/xraydata", info, String.class);
                System.out.println(result);
            }
        }
        return ObjectRestResponse.ok();
    }

    //@Scheduled(fixedRate = 10000)
    //@Scheduled(cron = "0 */5 5-23 * * ?")
//    @ApiOperation("违禁品处置")
//    @RequestMapping(value = "/handle/{id}", method = RequestMethod.GET)
//    public ObjectRestResponse HandleResult(@PathVariable("id") String id) {
//        List<PointDangerInfo> info = pointDangerBiz.getLastInfo();
//        if(info.size() > 0) {
//            long lasttime = info.get(0).getAlarmTime();
//            for(PointDangerInfo item : info) {
//                if(lasttime < item.getAlarmTime())
//                    lasttime = item.getAlarmTime();
//                if(item.getAlarmImgUrl() != null) {
//                    item.setAlarmImgUrl(picPath + item.getAlarmImgUrl());
//                }
//            }
//            pointDangerBiz.updatetime(lasttime);
//            System.out.println(JSON.toJSONString(info));
//        }
//
//        PointDangerInfo info = pointDangerBiz.getHandleInfo(id);
//        info.setAlarmImgUrl(picPath + info.getAlarmImgUrl());
//        System.out.println(JSON.toJSONString(info));
//
//        return ObjectRestResponse.ok();
//    }

    @ApiOperation("金属门告警（开包）")
    @RequestMapping(value = "/door/{id}", method = RequestMethod.GET)
    public ObjectRestResponse DoorAlarm(@PathVariable("id") String id) {
//    public ObjectRestResponse DoorAlarm() {
//        PointDangerInfo info = pointDangerBiz.getDoorAlarmInfo("13b9484ccb8c431bb9eaa03fdd307ff4");
        if(upSwitch) {
            PointDangerInfo info = pointDangerBiz.getDoorAlarmInfo(id);
            info.setAlarmImgUrl(picPath + info.getAlarmImgUrl());
            if(info.getStatus() == null) {
                info.setStatus("untreated");
            }
            //info = changePointDangerInfo(info);
            System.out.println("金属门告警");
            System.out.println(JSON.toJSONString(info));
            String result = restTemplate.postForObject(upPath + "exchange/dooralarm", info, String.class);
            System.out.println(result);
        }
        return ObjectRestResponse.ok();
    }

    @ApiOperation("开包处置")
    @RequestMapping(value = "/open/{id}", method = RequestMethod.GET)
    public ObjectRestResponse OpenInspection(@PathVariable("id") String id) {
//    public ObjectRestResponse OpenInspection() {
        if(upSwitch) {
            OpenInspection info = openInspectionBiz.GetOpenInspection(id);
            OpenInspectionVo vo = new OpenInspectionVo();
            vo.setId(info.getId());
            vo.setStatus(info.getStatus());
            vo.setViolatorCardId(info.getViolatorCardId());
            vo.setViolatorName(info.getViolatorName());
            vo.setViolatorTelNo(info.getViolatorTelNo());
            vo.setGoodsUrls(new String[] { picPath + info.getGoodsUrls() });
            vo.setViolatorType("02");
            vo.setSource(info.getSource());
            if(info.getStatus() == null) {
                vo.setStatus("untreated");
            }
            //#contrabandTypes:[gun:枪支弹药,knife:管制刀具,flammable:易燃易爆,toxic:有毒物品,other:其他违禁品,none:无违禁品]
            switch (info.getContrabandTypes()) {
                case "1":
                    vo.setContrabandTypes(new String[] {"gun"});
                    break;
                case "2":
                    vo.setContrabandTypes(new String[] {"flammable"});
                    break;
                case "3":
                    vo.setContrabandTypes(new String[] {"knife"});
                    break;
                case "4":
                    vo.setContrabandTypes(new String[] {"flammable"});
                    break;
                case "5":
                    vo.setContrabandTypes(new String[] {"toxic"});
                    break;
                case "6":
                    vo.setContrabandTypes(new String[] {"toxic"});
                    break;
                case "7":
                    vo.setContrabandTypes(new String[] {"toxic"});
                    break;
                case "8":
                    vo.setContrabandTypes(new String[] {"toxic"});
                    break;
                case "9":
                    vo.setContrabandTypes(new String[] {"other"});
                    break;
                case "10":
                    vo.setContrabandTypes(new String[] {"other"});
                    break;
                default:
                    vo.setContrabandTypes(new String[] {"none"});
            }
            if(vo.getViolatorCardId() != null && vo.getViolatorCardId().length() > 0 && IdcardUtils.validateCard(vo.getViolatorCardId())) {
                String birthday = IdcardUtils.getBirthByIdCard(info.getViolatorCardId());
                vo.setViolatorBirthday(birthday);
                int sex = IdcardUtils.getGenderByIdCard(info.getViolatorCardId());
                vo.setViolatorSex(sex);
                String sNative = IdcardUtils.getNativeByIdCard(info.getViolatorCardId());
                vo.setViolatorNativePlace(sNative);
            }
            System.out.println("开包处置");
            System.out.println(JSON.toJSONString(vo));
            String result = restTemplate.postForObject(upPath + "exchange/xrayopen", vo, String.class);
            System.out.println(result);
        }
        return ObjectRestResponse.ok();
    }

    //测温报警
//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 */1 5-23 * * ?")
    public void ThermalInfo() {
        if(upSwitch) {
            List<ThermalInfo> info = thermalBiz.getLastInfo();
            if(info.size() > 0) {
                for(ThermalInfo item1 : info) {
                    if(item1.getAlarmImgUrl() != null && item1.getAlarmImgUrl().length() > 0)
                        item1.setAlarmImgUrl(picPath + item1.getAlarmImgUrl());
                }
                thermalBiz.updatetime();
                System.out.println("测温报警上传");
                for(ThermalInfo item : info) {
                    System.out.println(JSON.toJSONString(item));
                    String result = restTemplate.postForObject(upPath + "exchange/temperaturealarm", item, String.class);
                    System.out.println(result);
                }
            }
        }
    }

    public String encodeBase64File(String path) {
        try {
            FileInputStream inputFile = new FileInputStream(path);
            byte[] buffer = new byte[inputFile.available()];
            inputFile.read(buffer);
            inputFile.close();
            return new BASE64Encoder().encode(buffer);
        } catch (Exception e) {
            return null;
        }
    }
    private String getWjpNameByCode(String code) {
        switch (code) {
            case "0": return "未知";
            case "1": return "管制刀具或利器";
            case "2": return "枪支及其仿制品";
            case "3": return "化学品类容器";
            case "4": return "酒精品类容器";
            case "5": return "化妆品类容器";
            case "6": return "普通液体容器";
            case "7": return "特殊液体容器";
            case "8": return "轻型压力罐";
            case "9": return "重型压力罐";
            case "10": return "电子设备";
            case "11": return "锂电池或充电宝";
            case "12": return "打火机";
            case "13": return "工具类";
            case "14": return "雨伞";
            case "15": return "整条香烟";
            case "16": return "公章";
            case "17": return "管制器具";
            case "18": return "烟花爆竹";
            case "19": return "子弹";
            case "20": return "雷管";
        }
        return "";
    }

//    private PointDangerInfo changePointDangerInfo(PointDangerInfo info) {
//        if(info.getSecurityCheckChannelId().equalsIgnoreCase("0514210D0001")) {
//            info.setSecurityCheckChannelId("05140B");
//            info.setSecurityCheckChannelName("安检点B");
//            info.setDeviceId("d1.05140B");
//            info.setDeviceName("安检门1");
//        }
//        if(info.getSecurityCheckChannelId().equalsIgnoreCase("0514210E0001")) {
//            info.setSecurityCheckChannelId("05140C");
//            info.setSecurityCheckChannelName("安检点C");
//            info.setDeviceId("d1.05140C");
//            info.setDeviceName("安检门1");
//        }
//        if(info.getSecurityCheckChannelId().equalsIgnoreCase("14090C")) {
//            info.setLineCode("02906");
//            info.setLineName("6号线1期");
//            info.setSecurityCheckChannelId("06130B");
//            info.setSecurityCheckChannelName("安检点B");
//            info.setDeviceId("d1.06130B");
//            info.setDeviceName("安检门1");
//            info.setStationCode("0613");
//            info.setStationName("甘家寨站");
//        }
//        return info;
//    }
//
//    private PackageInfo changePackageInfo(PackageInfo info) {
//        if(info.getSecurityCheckCode().equalsIgnoreCase("0514210D0001")) {
//            info.setSecurityCheckCode("05140B");
//            info.setSecurityCheck("安检点B");
//            info.setDeviceSerialNumber("x1.05140B");
//            info.setDeviceName("通道式X光机");
//        }
//        if(info.getSecurityCheckCode().equalsIgnoreCase("0514210E0001")) {
//            info.setSecurityCheckCode("05140C");
//            info.setSecurityCheck("安检点C");
//            info.setDeviceSerialNumber("x1.05140C");
//            info.setDeviceName("通道式X光机");
//        }
//        if(info.getSecurityCheckCode().equalsIgnoreCase("14090C")) {
//            info.setLineCode("02906");
//            info.setLineName("6号线1期");
//            info.setSecurityCheckCode("06130B");
//            info.setSecurityCheck("安检点B");
//            info.setDeviceSerialNumber("x1.06130B");
//            info.setDeviceName("通道式X光机");
//            info.setStationCode("0613");
//            info.setStationName("甘家寨站");
//        }
//        return info;
//    }
}