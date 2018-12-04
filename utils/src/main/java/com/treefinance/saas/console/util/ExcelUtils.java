package com.treefinance.saas.console.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

/**
 * @author guoguoyun
 * @date 2018/8/21下午3:24
 */
public final class ExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    private ExcelUtils() {}

    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param workbook HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook workbook) {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = workbook;
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        createSheet(wb, sheetName, title, values);

        return wb;
    }

    public static HSSFWorkbook createWorkbook(String sheetName, String[] title, String[][] values) {
        // 创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        createSheet(wb, sheetName, title, values);

        return wb;
    }

    public static void createSheet(HSSFWorkbook workbook, String sheetName, String[] title, String[][] values) {
        // Step1: 在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);

        // Step2: 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // Step3: 创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = workbook.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HorizontalAlignment.CENTER);

        // 创建标题
        HSSFCell cell;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        // 创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
    }

    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        String filename = fileName;
        try {
            filename = new String(filename.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            LOGGER.warn("Unsupported encoding:{}", "ISO8859-1", e);
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
    }
}