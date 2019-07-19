package com.example.excel;

import com.example.model.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtil {

    /**
     *  獲取工作簿
     */
    public static HSSFWorkbook getWorkbook(OutputStream outputStream){
        //1、创建工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //1.1、创建合并单元格对象CellRangeAddress(起始行号，终止行号， 起始列号，终止列号）
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0,1);
        //1.2、头标题样式
        HSSFCellStyle titleStyle =  createCellStyle(workbook,(short)16);
        //1.3、列标题样式
        HSSFCellStyle columnHeaderStyle = createCellStyle(workbook,(short)13);

        //2、创建工作表
        HSSFSheet sheet = workbook.createSheet("用户列表");
        //2.1、加载合并单元格对象
        sheet.addMergedRegion(cellRangeAddress);
        //设置默认列宽
        sheet.setDefaultColumnWidth(25);

        //3、创建行
        //3.1、创建头标题行；并且设置头标题
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        //加载单元格样式
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue("用户列表");

        //3.2、创建列标题行；并且设置列标题
        HSSFRow columnHeaderRow = sheet.createRow(1);
        String[] titles = {"用戶名","密碼"};

        for(int i=0;i<titles.length;i++){
            HSSFCell columnHeaderCell = columnHeaderRow.createCell(i);
            //加载单元格样式
            columnHeaderCell.setCellStyle(columnHeaderStyle);
            columnHeaderCell.setCellValue(titles[i]);
        }
        return workbook;
    }

    /**
     *  導出用戶需要遵循的Excel格式
     */
    public static void exportTemplateExcel(OutputStream outputStream){
        try{
            HSSFWorkbook workbook = ExcelUtil.getWorkbook(outputStream);
            //5、输出
            workbook.write(outputStream);
            workbook.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 导出用户的所有列表到excel
     * @param userList 用户列表
     * @param outputStream 输出流
     */
    public static void exportUserExcel(List<User> userList, OutputStream outputStream){
        HSSFWorkbook workbook = ExcelUtil.getWorkbook(outputStream);
        //獲取指定sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        //4、操作单元格；将用户列表写入excel
        if(userList != null){
            for(int j=0;j<userList.size();j++){
                HSSFRow row = sheet.createRow(j+2);

                HSSFCell dataCell1 = row.createCell(0);
                dataCell1.setCellValue(userList.get(j).getUsername());
                HSSFCell dataCell2 = row.createCell(1);
                dataCell2.setCellValue(userList.get(j).getPassword());
            }
        }
        try{
            //5、输出
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建单元格样式
     * @param workbook 工作簿
     * @param fontSize 字体大小
     */
    private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        //创建字体
        HSSFFont font = workbook.createFont();
        font.setBold(true); //字体加粗
        font.setFontHeightInPoints(fontSize);
        //加载字体
        style.setFont(font);
        return style;
    }


}
