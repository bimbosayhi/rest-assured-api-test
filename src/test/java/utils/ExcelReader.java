package utils;

import model.TestCase;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.util.*;

public class ExcelReader {

    public static List<TestCase> read(String filePath) throws Exception {

        List<TestCase> list = new ArrayList<>();

        FileInputStream fis = new FileInputStream(filePath);
        Workbook wb = WorkbookFactory.create(fis);
        Sheet sheet = wb.getSheetAt(0);
        // System.out.print(sheet.getLastRowNum());
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            Cell idCell = row.getCell(0);
            if (idCell == null || idCell.toString().trim().isEmpty()) {
                continue;
            }

            TestCase tc = new TestCase();

            tc.id = idCell.toString();
            tc.method = row.getCell(1).toString();
            tc.url = row.getCell(2).toString();
            tc.body = row.getCell(3).toString();
            tc.expectedStatus = (int) row.getCell(4).getNumericCellValue();

            list.add(tc);
        }

        return list;
    }
}
