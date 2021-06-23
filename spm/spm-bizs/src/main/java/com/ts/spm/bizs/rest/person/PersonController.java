package com.ts.spm.bizs.rest.person;

import com.github.ag.core.context.BaseContextHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.ts.spm.bizs.biz.LogBiz;
import com.ts.spm.bizs.biz.person.AjyBiz;
import com.ts.spm.bizs.biz.person.PersonBlackBiz;
import com.ts.spm.bizs.entity.person.AjyInfo;
import com.ts.spm.bizs.entity.person.PersonBlack;
import com.ts.spm.bizs.mapper.person.AjyInfoMapper;
import com.ts.spm.bizs.rest.admin.DepartController;
import com.ts.spm.bizs.rest.admin.UserController;
import com.ts.spm.bizs.util.DataUtil;
import com.ts.spm.bizs.util.PoiUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isNumeric;

/**
 * @Author luoyu
 * @Date 2020/6/18 14:17
 * @Version 1.0
 */
@RestController
@RequestMapping("ajy")
@CheckClientToken
@CheckUserToken
@Api(tags = "安检员管理")
public class PersonController {
    @Autowired
    private UserController userCtr;
    @Autowired
    private DepartController departCtr;
    @Autowired
    private AjyBiz ajyBiz;
    @Autowired
    private PersonBlackBiz personBlackBiz;
//    @Autowired
//    private AjyInfoMapper ajyInfoMapper;
    @Autowired
    private LogBiz logBiz;


    @ApiOperation("安检员信息导入")
    @RequestMapping(value = "/dic/import", method = RequestMethod.POST)
    public ObjectRestResponse personImport(@RequestParam("importFile") MultipartFile file, HttpServletRequest request){
//        int num=0;
        try{
            request.setCharacterEncoding("UTF-8");
            String fileName = file.getOriginalFilename();
            String eName = fileName.substring(fileName.lastIndexOf(".")+1);
            Workbook wb = null;
            //判断是xls、xlsx
            if ("xls".equals(eName)&& file!=null) {
                wb = new HSSFWorkbook(file.getInputStream());//操作Excel2003以前（包括2003）的版本，扩展名是.xls
            } else if ("xlsx".equals(eName)&& file!=null) {
                wb = new XSSFWorkbook(file.getInputStream());//Excel2007，扩展名是.xlsx
            } else {
                return ObjectRestResponse.error("请导入excel类型文件！");
            }
            Sheet sheet1 = wb.getSheetAt(0);
            if(sheet1.getRow(0).getPhysicalNumberOfCells() == 15){
                //序列、#线别#、中队、车站、姓名、性别、籍贯、身份证号、学历、毕业院校、政治面貌、#从业取证日期#、证件编号、岗位工资、备注
                List<AjyInfo> listAjy = new ArrayList<>();
                List<AjyInfo> existListAjy = new ArrayList<>();
//                        num = sheet1.getLastRowNum();
                for(Row row:sheet1){
                    if (row.getRowNum()<=3)
                        continue;
                    //除了从业取证日期字段，其他字段均设为String类型，并将String类型数据中的空格、回车、水平制表符、换行去除。
                    for (int i = 0; i <=row.getPhysicalNumberOfCells()-6; i++) {
                        row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                        if (row.getCell(i).getStringCellValue() !=null && row.getCell(4).getStringCellValue().length()>0){
                            row.getCell(i).setCellValue(row.getCell(i).getStringCellValue().replaceAll("[\\s\\t\\n\\r]", ""));
                        }
                    }
                    for (int j = 12; j <=row.getPhysicalNumberOfCells()-1; j++) {
                        row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                        if (row.getCell(j).getStringCellValue() !=null && row.getCell(4).getStringCellValue().length()>0) {
                            row.getCell(j).setCellValue(row.getCell(j).getStringCellValue().replaceAll("[\\s\\t\\n\\r]", ""));
                        }
                    }
                    AjyInfo info = new AjyInfo();
                    String nameValue = row.getCell(4).getStringCellValue();
                    String idCardValue =  row.getCell(7).getStringCellValue();
                    if ( nameValue.length() <= 0 && idCardValue.length() <= 0 )
//                        ||!sexValue.equals("男")&&!sexValue.equals("女")&& !(sexValue.length() <=0)
                        continue;
                    //ID
                    info.setId(ajyBiz.getId());
                    //中队
                    if(row.getCell(2) != null && row.getCell(2).getStringCellValue().length()>0){
                        info.setTeam(row.getCell(2).getStringCellValue());
                    }else{
                        return ObjectRestResponse.error("第"+(row.getRowNum()+1)+"行“中队”不能为空！");
                    }
                    //车站//安检公司
                    if(row.getCell(3) != null && row.getCell(3).getStringCellValue().length()>0){
                        String  station = row.getCell(3).getStringCellValue();
                        AjyInfo ajyInfo=departCtr.getDepartIdByName(station);
                        if(null!=ajyInfo){
                            String departId = ajyInfo.getDepartId();
                            String ajCom = ajyInfo.getAjcom();
                            info.setDepartId(departId);
                            info.setAjcom(ajCom);
                        }
                    }else{
                        return ObjectRestResponse.error("第"+(row.getRowNum()+1)+"行“车站”不能为空！");
                    }
                    //姓名
                    if(row.getCell(4) != null && row.getCell(4).getStringCellValue().length()>0){
                        info.setName(row.getCell(4).getStringCellValue());
                    }else{
                        return ObjectRestResponse.error("第"+(row.getRowNum()+1)+"行“姓名”不能为空！");
                    }
                    //籍贯
                    if(row.getCell(6) != null && row.getCell(6).getStringCellValue().length()>0){
                        info.setNativePlace(row.getCell(6).getStringCellValue());
                    }
                    //身份证号//生日//性别
                    int idCardLength = row.getCell(7).getStringCellValue().length();
                    String idCard = row.getCell(7).getStringCellValue();
                    if (row.getCell(7) != null  && idCardLength==18) {
                        if (isNumeric(idCard.substring(0, 17)) == false){
                            return ObjectRestResponse.error("第"+(row.getRowNum()+1)+"行“身份证号”不合法!");
                        }
                        String year = idCard.substring(6, 10);
                        String month = idCard.substring(10, 12);
                        String day = idCard.substring(12, 14);
                        String sCardNum = idCard.substring(16, 17);
                        String birthday = year + "-" + month + "-" + day + " " + "00" + ":" + "00" + ":" + "00";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        info.setBirthday(simpleDateFormat.parse(birthday));
                        info.setIdcard(row.getCell(7).getStringCellValue());
                        if (Integer.parseInt(sCardNum) % 2 != 0) {
                            long a = 1;
                            info.setSex(a);
                        } else {
                            long a = 0;
                            info.setSex(a);
                        }
                    } else {
                        return ObjectRestResponse.error("第" + (row.getRowNum()+1) + "行“身份证号”不能为空或“身份证号”无效！");
                    }
                    //学历
                    if(row.getCell(8) != null && row.getCell(8).getStringCellValue().length()>0){
                        info.setDegree(row.getCell(8).getStringCellValue());
                    }else{
                        return ObjectRestResponse.error("第"+(row.getRowNum()+1)+"行“学历”不能为空！");
                    }
                    //毕业院校
                    if(row.getCell(9) != null && row.getCell(9).getStringCellValue().length()>0){
                        info.setSchool(row.getCell(9).getStringCellValue());
                    }else{
                        return ObjectRestResponse.error("第"+(row.getRowNum()+1)+"行“毕业院校”不能为空！");
                    }
                    //政治面貌
                    if(row.getCell(10) != null && row.getCell(10).getStringCellValue().length()>0){
                        info.setPolitical(row.getCell(10).getStringCellValue());
                    }else{
                        return ObjectRestResponse.error("第"+(row.getRowNum()+1)+"行“政治面貌”不能为空！");
                    }
                    //从业取证时间
                    if(row.getCell(11) != null ){
//                        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//                        Matcher m = p.matcher((CharSequence) row.getCell(11).getDateCellValue());
//                        m.replaceAll("");
                        Date w_time = row.getCell(11).getDateCellValue();
                        info.setwTime(w_time);
                    }
                    //证件编号
                    if(row.getCell(12) != null && row.getCell(12).getStringCellValue().length()>0){
                        info.setSecurityid(row.getCell(12).getStringCellValue());
                    }else{
                        return ObjectRestResponse.error("第"+(row.getRowNum()+1)+"行“证件编号”不能为空！");
                    }
                    //L_Check和F_Check
                    info.setlCheck(new Date());
                    info.setfCheck(new Date());
                    AjyInfo existInfo = ajyBiz.getPersonCard(idCard) ;
                    if(existInfo !=null){
                        info.setId(existInfo.getId());
                        existListAjy.add(info);
                    }else {
                        listAjy.add(info);
                    }
                }
                for (AjyInfo existInfo:existListAjy){
                    ajyBiz.updateSelectiveById2(existInfo);
                }
                for (AjyInfo info:listAjy){
                    ajyBiz.insertSelective2(info);
                }
                return ObjectRestResponse.ok("成功导入"+listAjy.size()+"条新安检员信息，更新"+existListAjy.size()+"条安检员信息");
            } else {
                return ObjectRestResponse.error("导入文件格式错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ObjectRestResponse.error("导入失败");
        }
    }


    @ApiOperation("安检员信息查询")
    @RequestMapping(value = "/dic/query", method = RequestMethod.GET)
    public TableResultResponse getPersonInfo(String departId,String name,String cardID,String company,String securityId,
                                             String ifCard,String ifYoung,
                                             @RequestParam(name = "limit", defaultValue = "10") int limit,
                                             @RequestParam(name = "page", defaultValue = "1") int page){

        List<String> departIds = getUserDepartId(departId);
        Page<Object> result = PageHelper.startPage(page, limit);
        List<Map> list=ajyBiz.getPersonInfoList(departIds,name,cardID,company,securityId,ifCard,ifYoung);
        for(Map map:list){
            //隐藏身份证中间数字
            String idCard=map.get("idcard").toString();
            String idCardHide=idCard.replace(idCard.substring(6,14), "********");
            map.replace("idcard",idCard,idCardHide);
        }

        logBiz.saveLog("安检员管理","安检员信息查询", "api/person/ajy/dic/query");

        return new TableResultResponse(result.getTotal(),list);
    }

    @ApiOperation("安检员信息添加")
    @RequestMapping(value = "/dic/add", method = RequestMethod.POST)
    public ObjectRestResponse addPerson(@RequestBody AjyInfo person){
        List<PersonBlack> list=personBlackBiz.getPersonBlack("",person.getIdcard());
        if(list.size()>0){
            return ObjectRestResponse.error("该安检员已加入黑名单，此处不允许添加！");
        }
        List<String> departIds = new ArrayList<>();
        List<Map> personInfoList=ajyBiz.getPersonInfoList(departIds,"",person.getIdcard(),"","","","");
        if(personInfoList.size()>0){
            return ObjectRestResponse.error("该安检员已添加，不可重复添加！");
        }

        //Integer ajyId=ajyBiz.getId();
        person.setfCheck(person.getfCheck()==null?new Date():person.getfCheck());
        person.setlCheck(person.getlCheck()==null?new Date():person.getlCheck());
        //person.setId(ajyId);
        person.setCrtTime(new Date());
        person.setCrtUserId(BaseContextHandler.getUserID());
        person.setCrtUserName(BaseContextHandler.getUsername());
        person.setTenantId(BaseContextHandler.getTenantID());
        ajyBiz.insertSelective(person);

        logBiz.saveLog("安检员管理","安检员信息添加", "api/person/ajy/dic/add");

        return ObjectRestResponse.ok();

    }

    @ApiOperation("安检员信息详情")
    @RequestMapping(value = "/dic/detail/{id}", method = RequestMethod.GET)
    public ObjectRestResponse detailPerson(@PathVariable String id){
        Integer personId=Integer.parseInt(id);
        AjyInfo person=ajyBiz.selectById(personId);
        DataUtil.checkNull(person);

        logBiz.saveLog("安检员管理","安检员信息详情", "api/person/ajy/dic/detail");

        return ObjectRestResponse.ok(person);
    }

    @ApiOperation("安检员信息编辑")
    @RequestMapping(value = "/dic/update", method = RequestMethod.PUT)
    public ObjectRestResponse updatePerson(@RequestBody AjyInfo person){
        List<PersonBlack> list=personBlackBiz.getPersonBlack("",person.getIdcard());
        if(list.size()>0) {
            return ObjectRestResponse.error("该安检员已加入黑名单！");
        } else {
            person.setfCheck(person.getfCheck()==null?(Date)null:person.getfCheck());
            person.setlCheck(person.getlCheck()==null?(Date)null:person.getlCheck());
            person.setUpdTime(new Date());
            person.setUpdUserId(BaseContextHandler.getUserID());
            person.setUpdUserName(BaseContextHandler.getUsername());
            ajyBiz.updateSelectiveById(person);

            logBiz.saveLog("安检员管理","安检员信息编辑", "api/person/ajy/dic/update");

            return ObjectRestResponse.ok();
        }

    }

    @ApiOperation("安检员信息删除")
    @RequestMapping(value = "/dic/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse deletePerson(@PathVariable String id){
        Integer personId=Integer.parseInt(id);
        ajyBiz.deleteById(personId);

        logBiz.saveLog("安检员管理","安检员信息删除", "api/person/ajy/dic/delete");

        return ObjectRestResponse.ok();
    }

    @ApiOperation("安检员拉入黑名单")
    @RequestMapping(value = "/dic/block", method = RequestMethod.POST)
    public ObjectRestResponse blockPerson(String id,String remark){
        Integer personId=Integer.parseInt(id);
        AjyInfo person=ajyBiz.selectById(personId);
        PersonBlack personBlack=new PersonBlack();
        personBlack.setCardid(person.getIdcard());
        personBlack.setName(person.getName());
        personBlack.setRemark(remark);
        personBlackBiz.addPersonBlack(personBlack);

        logBiz.saveLog("安检员管理","安检员拉入黑名单", "api/person/ajy/dic/block");

        ajyBiz.blockAjy(personId,1);

        logBiz.saveLog("安检员管理","黑名单添加", "api/person/ajy/black/add");

        return ObjectRestResponse.ok();
    }

    @ApiOperation("安检员从黑名单恢复")
    @RequestMapping(value = "/dic/recover", method = RequestMethod.POST)
    public ObjectRestResponse recoverPerson(String id){
        Integer personId=Integer.parseInt(id);
        AjyInfo person=ajyBiz.selectById(personId);
        ajyBiz.blockAjy(personId,0);
        logBiz.saveLog("安检员管理","安检员从黑名单恢复", "api/person/ajy/dic/recover");

        personBlackBiz.deletePersonBlack(person.getIdcard());
        logBiz.saveLog("安检员管理","黑名单删除", "api/person/ajy/black/delete");

        return ObjectRestResponse.ok();
    }

    @ApiOperation("安检员黑名单查询")
    @RequestMapping(value = "/black/query", method = RequestMethod.GET)
    public TableResultResponse getPersonBlack(String name,String cardID,
                                              @RequestParam(name = "limit", defaultValue = "10") int limit,
                                              @RequestParam(name = "page", defaultValue = "1") int page){
        Page<PersonBlack> result = PageHelper.startPage(page, limit);

        List<PersonBlack> list=personBlackBiz.getPersonBlack(name,cardID);

        logBiz.saveLog("安检员管理","安检员黑名单查询", "api/person/ajy/black/query");

        return new TableResultResponse(result.getTotal(),list);
    }


    @ApiOperation("安检员黑名单添加")
    @RequestMapping(value = "/black/add", method = RequestMethod.POST)
    public ObjectRestResponse addPersonBlack(@RequestBody PersonBlack personBlack){
        List<PersonBlack> list=personBlackBiz.getPersonBlack("",personBlack.getCardid());
        if(list.size()>0){
            return ObjectRestResponse.error("已加入黑名单，不可重复添加！");
        }else{
            personBlackBiz.addPersonBlack(personBlack);

            logBiz.saveLog("安检员管理","安检员黑名单添加", "api/person/ajy/black/add");

            return ObjectRestResponse.ok();
        }
    }

    @ApiOperation("安检员黑名单详情")
    @RequestMapping(value = "/black/detail/{id}", method = RequestMethod.GET)
    public ObjectRestResponse detailPersonBlack(@PathVariable String id){

       Integer Id=Integer.parseInt(id);
       PersonBlack personBlack=personBlackBiz.selectById(Id);
        DataUtil.checkNull(personBlack);
        logBiz.saveLog("安检员管理","安检员黑名单详情", "api/person/ajy/black/detail");

        return ObjectRestResponse.ok(personBlack);
    }

    @ApiOperation("安检员黑名单编辑")
    @RequestMapping(value = "/black/update", method = RequestMethod.PUT)
    public ObjectRestResponse updatePersonBlack(@RequestBody PersonBlack personBlack){

        personBlack.setTime(new Date());
        personBlackBiz.updateById(personBlack);

        logBiz.saveLog("安检员管理","安检员黑名单编辑", "api/person/ajy/black/update");

        return ObjectRestResponse.ok();
    }

    @ApiOperation("安检员黑名单删除")
    @RequestMapping(value = "/black/delete/{id}", method = RequestMethod.DELETE)
    public ObjectRestResponse deletePersonBlack(@PathVariable String id){
        Integer Id=Integer.parseInt(id);
        personBlackBiz.deleteById(Id);

        logBiz.saveLog("安检员管理","安检员黑名单删除", "api/person/ajy/black/delete");

        return ObjectRestResponse.ok();
    }


    @ApiOperation("安检员动态统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ObjectRestResponse ajyStatistics(String departId, String ajyName, String idCard, String ifWork,
                                            @RequestParam(name = "limit", defaultValue = "10") int limit,
                                            @RequestParam(name = "page", defaultValue = "1") int page){

        Map<String,Object> AjyStatistic=new HashMap<String,Object>();
        List<String> departIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            List<Map<String,String>> departMap=departCtr.getDepartChildren(departId);
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
        }else {
            List<Map<String,String>> departMap=userCtr.getStation(BaseContextHandler.getUserID());
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
        }

        int workCnt=0;
        int noWorkCnt=0;
        int cardCnt=0;
        int monthNoWorkCnt=0;

        List<Map> ajyList=ajyBiz.getPersonInfo(departIds, ajyName,idCard,ifWork);
        for(Map<String,String> ajy:ajyList){
            DataUtil.nullToEmpty(ajy);
            if(ajy.get("ifWork").equals("1")){
                noWorkCnt++;
            }
            if(ajy.get("ifWork").equals("0")){
                workCnt++;
                if ( ajy.get("ifCard").equals("0") ){
                    cardCnt++;
                }
            }

        }


        List<Map<String,String>> monthAjyList=ajyBiz.getMonthPerson(departIds,ajyName,idCard,ifWork);
        for(Map<String,String> monthAjy:monthAjyList){
            if(monthAjy.get("ifWork").equals("1")){
                monthNoWorkCnt++;
            }
        }

        List<Map<String,String>> newAjyList=ajyBiz.getNewPerson(departIds,ajyName,idCard,ifWork);

        AjyStatistic.put("allCnt",ajyList.size());
        AjyStatistic.put("onWorkCnt",workCnt);
        AjyStatistic.put("noWorkCnt",noWorkCnt);
        AjyStatistic.put("cardCnt",cardCnt);
        AjyStatistic.put("nocardCnt",workCnt-cardCnt);
        AjyStatistic.put("monthNewCnt",newAjyList.size());
        AjyStatistic.put("monthNoWorkCnt",monthNoWorkCnt);
        AjyStatistic.put("ajyList",ajyBiz.getAjyList(departIds,ajyName,idCard,ifWork,page,limit).getData());

        logBiz.saveLog("安检员管理","安检员动态统计", "api/person/ajy/statistics");

        return ObjectRestResponse.ok(AjyStatistic);

    }

    @ApiOperation("安检员导出")
    @RequestMapping(value = "/dic/export", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public void PersonExport(HttpServletRequest request, HttpServletResponse res, String departId,String name,String cardID,String company,String securityId,
                                 String ifCard,String ifYoung){
        try {
            String colHeads[] = { "序号", "线路","车站", "安检员姓名", "性别","身份证号","安检员证书编号","通信地址","民族","籍贯" };
            String keys[] = { "id", "lineName","stationName","name","sex","idcard","securityid","address","fork","nativePlace" };
            List<String> departIds = getUserDepartId(departId);
            List<Map> list=ajyBiz.getPersonInfoList(departIds,name,cardID,company,securityId,ifCard,ifYoung);

            int index=1;
            for(Map map:list){
                Integer sex=Integer.parseInt(map.get("sex").toString());
                if(sex==0){
                    map.put("sex","女");
                }
                if(sex==1){
                    map.put("sex","男");
                }
                if(sex==null){
                    map.put("sex","未知");
                }

                map.replace("id",index++);
            }

            PoiUtil.start_download(request,res,com.github.wxiaoqi.security.common.util.DateUtil.getCurrentDate()+".xls", list, colHeads, keys);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logBiz.saveLog("安检员管理","安检员导出", "api/person/ajy/dic/export");

    }

    @ApiOperation("安检员黑名单导出")
    @RequestMapping(value = "/black/export", method = RequestMethod.GET)
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public void PersonBlackExport(HttpServletRequest request, HttpServletResponse res, String name,String cardID){
        try {
            String colHeads[] = { "序号", "身份证号","姓名", "时间", "备注" };
            String keys[] = { "id", "cardid","name","time","remark" };
            List<Map> list=personBlackBiz.getPersonBlackList(name,cardID);

            PoiUtil.start_download(request,res,com.github.wxiaoqi.security.common.util.DateUtil.getCurrentDate()+".xls", list, colHeads, keys);
        } catch (Exception e) {
            e.printStackTrace();
        }

        logBiz.saveLog("安检员管理","安检员黑名单导出", "api/person/ajy/black/export");

    }

    private List<String> getUserDepartId(String departId) {
        List<String> departIds=new ArrayList<String>();
        if(!"".equals(departId)&&departId!=null){
            List<Map<String,String>> departMap=departCtr.getDepartChildren(departId);
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());

        }else {
            List<Map<String,String>> departMap=userCtr.getStation(BaseContextHandler.getUserID());
            departIds=departMap.stream().map(u->u.get("id")).collect(Collectors.toList());
        }
        return departIds;
    }
}
