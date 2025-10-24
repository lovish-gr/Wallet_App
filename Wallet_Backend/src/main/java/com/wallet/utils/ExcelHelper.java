package com.wallet.utils;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.model.Address;
import com.wallet.model.Customer;
import com.wallet.model.Gender;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {

    private static final String SHEET_NAME = "Customers";

    private static final String[] HEADERS = {
            "firstName", "lastName", "emailId", "contactNo", "gender",
            "password", "registrationDate", "lastTrailDate",
            "addressLine1", "addressLine2", "city", "state", "pincode"
    };

    // ---------- Dependency note ----------
    // Ensure you have both poi and poi-ooxml (poi-ooxml pulls poi transitively,
    // but if you get class-not-found for HSSF, add explicit poi dependency)
    //
    // In pom.xml:
    // <dependency>
    //   <groupId>org.apache.poi</groupId>
    //   <artifactId>poi-ooxml</artifactId>
    //   <version>5.2.5</version>
    // </dependency>

    // ---------- Generate sample (.xlsx) ----------
    public static ByteArrayInputStream generateSampleExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            // Example data row
            Row sample = sheet.createRow(1);
            sample.createCell(0).setCellValue("Lovish");
            sample.createCell(1).setCellValue("Grover");
            sample.createCell(2).setCellValue("lovish@example.com");
            sample.createCell(3).setCellValue("9876543210");
            sample.createCell(4).setCellValue("MALE");
            sample.createCell(5).setCellValue("pass123");
            sample.createCell(6).setCellValue("2025-10-24");
            sample.createCell(7).setCellValue("2025-11-24");
            sample.createCell(8).setCellValue("Sector 12");
            sample.createCell(9).setCellValue("Near Mall Road");
            sample.createCell(10).setCellValue("Panipat");
            sample.createCell(11).setCellValue("Haryana");
            sample.createCell(12).setCellValue("132103");

            // autosize cols (small performance cost)
            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    // ---------- Parse uploaded Excel (supports .xls and .xlsx) ----------
    public static List<Customer> parseExcelFile(InputStream is) {
        List<Customer> customers = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(is)) { // <-- auto-detects format
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                // fallback: take first sheet
                sheet = workbook.getSheetAt(0);
            }

            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;

            while (rows.hasNext()) {
                Row row = rows.next();

                // Skip header row
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                // Skip empty rows
                if (isRowEmpty(row)) {
                    rowNumber++;
                    continue;
                }

                Customer customer = new Customer();
                Address address = new Address();

                customer.setFirstName(getCellString(row, 0));
                customer.setLastName(getCellString(row, 1));
                customer.setEmailId(getCellString(row, 2));
                customer.setContactNo(getCellString(row, 3));

                String genderStr = getCellString(row, 4);
                if (genderStr != null && !genderStr.isBlank()) {
                    try {
                        customer.setGender(Gender.valueOf(genderStr.trim().toUpperCase()));
                    } catch (IllegalArgumentException ex) {
                        // If invalid gender, you can set default or throw
                        throw new RuntimeException("Invalid gender value '" + genderStr + "' at row " + (rowNumber+1));
                    }
                }

                customer.setPassword(getCellString(row, 5));

                String regDateStr = getCellString(row, 6);
                if (regDateStr != null && !regDateStr.isBlank()) {
                    customer.setRegistrationDate(parseLocalDateFlexible(row.getCell(6)));
                }

                String lastTrailStr = getCellString(row, 7);
                if (lastTrailStr != null && !lastTrailStr.isBlank()) {
                    customer.setLastTrailDate(parseLocalDateFlexible(row.getCell(7)));
                }

                address.setAddressLine1(getCellString(row, 8));
                address.setAddressLine2(getCellString(row, 9));
                address.setCity(getCellString(row, 10));
                address.setState(getCellString(row, 11));
                address.setPincode(getCellString(row, 12));

                customer.setAddress(address);
                customers.add(customer);

                rowNumber++;
            }

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to parse Excel file: " + e.getMessage(), e);
        }

        return customers;
    }

    // ---------- File type check ----------
    public static boolean isExcelFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null) return false;
        filename = filename.toLowerCase();
        return filename.endsWith(".xlsx") || filename.endsWith(".xls");
    }

    // ---------- Helpers ----------

    private static boolean isRowEmpty(Row row) {
        if (row == null) return true;
        for (int c = 0; c < HEADERS.length; c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().trim().isEmpty()) {
                    return false;
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    return false;
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    return false;
                } else if (cell.getCellType() == CellType.FORMULA) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String getCellString(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            if (cell == null) return null;

            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // Return ISO date (yyyy-MM-dd)
                        return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                    } else {
                        // Remove .0 for whole numbers
                        double d = cell.getNumericCellValue();
                        if (d == (long) d) {
                            return String.valueOf((long) d);
                        } else {
                            return String.valueOf(d);
                        }
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    // Evaluate formula result
                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue value = evaluator.evaluate(cell);
                    switch (value.getCellType()) {
                        case STRING: return value.getStringValue();
                        case NUMERIC:
                            double d = value.getNumberValue();
                            if (d == (long) d) return String.valueOf((long) d);
                            return String.valueOf(d);
                        case BOOLEAN: return String.valueOf(value.getBooleanValue());
                        default: return null;
                    }
                default:
                    return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    private static LocalDate parseLocalDateFlexible(Cell cell) {
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate();
                } else {
                    // numeric but not date -> treat as epoch or throw
                    // fallback: convert numeric to string and parse
                    String s = String.valueOf(cell.getNumericCellValue());
                    return LocalDate.parse(s);
                }
            } else {
                String s = getCellString(cell.getRow(), cell.getColumnIndex());
                if (s == null || s.isBlank()) return null;
                return LocalDate.parse(s.trim());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse date in cell " + cell.getAddress() + ": " + ex.getMessage(), ex);
        }
    }
}
