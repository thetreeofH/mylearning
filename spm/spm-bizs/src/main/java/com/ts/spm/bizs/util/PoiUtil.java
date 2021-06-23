package com.ts.spm.bizs.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//
public class PoiUtil {
    public static void start_download(HttpServletRequest request,HttpServletResponse res, String fileName, List<Map> data, String[] colHeads, String[] keys) throws IOException {
        List<Map<String,Object>> list=createExcelRecord(data, keys);
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        OutputStream os = null;
        try {
            res.reset();
            res.setContentType("application/x-download");

            res.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
            HSSFWorkbook workbook=createWorkBook(list,keys,colHeads);

            os=res.getOutputStream();

            workbook.write(os);
            os.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(os != null){
                    os.close();
                    os = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Map<String, Object>> createExcelRecord(List<Map> data, String[] keys) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("sheetName", "sheet1");
        list.add(map);

        for (int j = 0; j < data.size(); j++) {
            Map<String, Object> mapValue = new HashMap<>();
            for(int i=0; i<keys.length; i++){
                mapValue.put(keys[i], data.get(j).get(keys[i]));
            }
            list.add(mapValue);
        }
        return list;
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HSSFWorkbook createWorkBook(List<Map<String, Object>> list, String []keys, String columnNames[]) {
        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        Row row = sheet.createRow((short) 0);
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        Font f = wb.createFont();
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        Font f2 = wb.createFont();
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 1; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i);
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }
}
