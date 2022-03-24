package com.bluemsun.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class JsonToExcelUtil {
    /**
     * json 转 excel
     * @param jsonArray
     * @return
     * @throws IOException
     */
    public static HSSFWorkbook jsonToExcel(JSONArray jsonArray) throws IOException {
        Set<String> keys = null;
        // 创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet0");
        int roleNo = 0;
        int rowNo = 0;
        List<JSONObject> jsonObjects = jsonArray.toList(JSONObject.class);

        // 创建HSSFRow对象
        HSSFRow row = sheet.createRow(roleNo++);
        // 创建HSSFCell对象
        //标题
        keys = jsonObjects.get(0).keySet();
        for (String s : keys) {
            HSSFCell cell = row.createCell(rowNo++);
            cell.setCellValue(s);
        }
        rowNo = 0;
        for (JSONObject jsonObject : jsonObjects) {
            row = sheet.createRow(roleNo++);
            for (String s : keys) {
                HSSFCell cell = row.createCell(rowNo++);
                cell.setCellValue(jsonObject.getStr(s));
            }
            rowNo = 0;
        }
        return wb;
    }
}
