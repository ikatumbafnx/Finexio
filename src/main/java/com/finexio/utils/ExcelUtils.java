package com.finexio.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.qameta.allure.Step;

/**
 * @author Ivan Katumba on 10/12/2021
 * @project HFD-Automation
 */
public class ExcelUtils {

    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wb;
    public static XSSFSheet ws;
    public static XSSFRow row;
    public static XSSFCell cell;

    @Step("Reading all the Rows from the {1} from the Excell Data")
    public static int getRowCount(String xlfile, String xlsheet) throws IOException {

        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        int rowcount = ws.getLastRowNum();
        fi.close();

        return rowcount;
    }

    @Step("Reading all the Cell Counts  from the {1} from the Excell Data")
    public static int getCellCount(String xlfile, String xlsheet, int rownum) throws IOException {

        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rownum);
        int cellcount = row.getLastCellNum();
        fi.close();

        return cellcount;
    }

    @Step("Reading all the Data  from the {1} from the Excell Data")
    public static String getCellData(String xlfile, String xlsheet, int rownum, int colnum) throws IOException {

        fi = new FileInputStream(xlfile);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rownum);
        cell = row.getCell(colnum);

        String data;
        try {

            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;

        } catch (Exception e) {
            // TODO: handle exception
            data = "";
        }

        fi.close();

        return data;
    }

    @Step("Creating the Excell Sheet for TestCases")
    public void createExcelSheet(String fileName) {

        // Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("TestCase Data");

        // This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] { "TestSuiteID", "TestCaseID", "OUTCOME" });
        // data.put("2", new Object[] { TestSuiteID, TestCaseID, outcome });

        // Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }
        try {
            // Write the workbook in file system
            FileOutputStream out = new FileOutputStream(
                    new File("src/test/resources/files/testcaseData/" + fileName + ".xlsx"));
            workbook.write(out);
            out.close();
            System.out.println(fileName + ".xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step("Updating the {0} Excell Sheet with TestSuite {1} , TestCaseID : {2}, and Test OutCome of : {3}")
    public void WriteToExcell(String fileName, String TestSuiteID, String TestCaseID, String outcome)
            throws InvalidFormatException {

        String excelFilePath = "src/test/resources/files/testcaseData/" + fileName + ".xlsx";

        try {

            FileInputStream fis = new FileInputStream(new File(excelFilePath));

            // Create object of XSSFWorkbook class
            XSSFWorkbook workbook = new XSSFWorkbook(fis);

            // Create object of XSSFWorkbook class
            XSSFSheet sheet = workbook.getSheet("TestCase Data");

            Object[][] bookData = { { TestSuiteID, TestCaseID, outcome }, };

            // Get last row in Sheet
            int rowCount = sheet.getLastRowNum();

            for (Object[] Book : bookData) {

                // Create row for all the new data
                XSSFRow row = sheet.createRow(++rowCount);

                int columnCount = 0;

                // Create new cell for each row
                XSSFCell cell = row.createCell(columnCount);

                for (Object field : Book) {

                    cell = row.createCell(columnCount++);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }

            }

            fis.close();

            // Write the workbook in file system
            FileOutputStream outputStream = new FileOutputStream(
                    "src/test/resources/files/testcaseData/" + fileName + ".xlsx");
            workbook.write(outputStream);
            System.out.println("Excel is updated successfully");

            // Close the workbook
            workbook.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Step("Deletting the TestCase WorkSheet")
    public void DeleteTestCaseFile(String fileName) {

        String excelFilePath = "src/test/resources/files/testcaseData/" + fileName + ".xlsx";
        File f1 = new File(excelFilePath);
        if (f1.exists()) {
            if (f1.delete()) {
                System.out.println("File " + f1.getName() + " is deleted.");
            } else {
                System.out.println("File " + f1.getName() + " not found.");
            }
        }

    }

}
