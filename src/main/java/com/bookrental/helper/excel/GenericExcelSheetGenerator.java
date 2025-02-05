package com.bookrental.helper.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class GenericExcelSheetGenerator<T> {

    public ByteArrayInputStream getExcelSheet(List<T> t) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Workbook workbook = new XSSFWorkbook();
        Class<?> className = t.getClass();
        Sheet sheet = workbook.createSheet(className.getName());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Row headerRow = sheet.createRow(0);
        List<String> fields = Arrays.stream(className.getDeclaredFields()).map(Field::getName).toList();
        for (int i = 0; i < fields.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(fields.get(i));
        }

        int rowNum = 1;
        for (T o : t) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < fields.size(); i++) {
                String getterName = "get" + capitalize(fields.get(i));
                Object value = o.getClass().getMethod(getterName).invoke(o);
                Cell cell = row.createCell(i);
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                } else if (value instanceof Boolean) {
                    cell.setCellValue((Boolean) value);
                } else if (value instanceof LocalDate) {
                    cell.setCellValue((LocalDate) value);
                } else if (value != null) {
                    cell.setCellValue(value.toString());
                } else {
                    cell.setCellValue("");
                }
            }
        }
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }


    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
