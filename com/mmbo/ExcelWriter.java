package com.mmbo;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter {

    public static void appendResultsToExcel(String[][] data, String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook existingWorkbook = WorkbookFactory.create(fis);
             FileOutputStream fos = new FileOutputStream(filePath)) {

            Sheet sheet = existingWorkbook.getSheet("Results");

            // Find the last row number
            int lastRowNum = sheet.getLastRowNum();

            // Iterate over the data and append to the sheet
            for (String[] rowData : data) {
                Row row = sheet.createRow(++lastRowNum);
                int colNum = 0;
                for (String field : rowData) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(field);
                }
            }

            // Write the modified workbook content back to the file
            existingWorkbook.write(fos);
            System.out.println("Results appended successfully to " + filePath);
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }
}


