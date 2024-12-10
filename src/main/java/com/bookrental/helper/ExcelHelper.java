package com.bookrental.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.bookrental.dto.BookAddRequest;

public class ExcelHelper {

	
    public static boolean isExcelFile(MultipartFile file) {
//        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        
        return fileName != null && (fileName.endsWith(".xls") || fileName.endsWith(".xlsm"));
//        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
//               contentType.equals("application/vnd.ms-excel");
    }

//    Read excel data
	public static List<BookAddRequest> readExcelFile(MultipartFile file) throws IOException {

		List<BookAddRequest> bookAddRequests = new ArrayList<>();

		InputStream inputStream = file.getInputStream();

		Workbook workbook = new XSSFWorkbook(inputStream);

		Sheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();

		if (rowIterator.hasNext()) {
			rowIterator.next();
		}

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

	
			BookAddRequest bookAddRequest = new BookAddRequest();

		
			bookAddRequest.setName(getCellValue(row.getCell(0)));
			bookAddRequest.setNumberOfPages(Integer.parseInt(getCellValue(row.getCell(1))));
			bookAddRequest.setIsbn(String.valueOf(getCellValue(row.getCell(2))));
			bookAddRequest.setRating(Double.parseDouble(getCellValue(row.getCell(3))));
			bookAddRequest.setStockCount(Integer.parseInt(getCellValue(row.getCell(4))));
//			bookAddRequest.setPublishedDate(java.sql.Date.valueOf(getCellValue(row.getCell(5))));
			bookAddRequest.setPhoto(getCellValue(row.getCell(6)));

		
			String authorIdsCell = getCellValue(row.getCell(7));
																	
			Set<Integer> authorIds = new HashSet<>();

			if (authorIdsCell != null && !authorIdsCell.isEmpty()) {
				String[] authorIdsArray = authorIdsCell.split(",");
				for (String authorId : authorIdsArray) {
					try {
						authorIds.add(Integer.parseInt(authorId.trim())); 
					} catch (NumberFormatException e) {
						System.err.println("Invalid author ID: " + authorId);
					}
				}
			}

			bookAddRequest.setAuthorId(authorIds); 
			bookAddRequest.setCategoryId(Integer.parseInt(getCellValue(row.getCell(8))));			

			bookAddRequests.add(bookAddRequest);
		}

		workbook.close();

		return bookAddRequests;
	}

	private static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}
	}
	
//	Export data in excel format
	 public static ByteArrayInputStream exportToExcel(List<BookAddRequest> bookAddRequests) throws IOException {
	        // Step 1: Create a new workbook
	        Workbook workbook = new XSSFWorkbook();

	        // Step 2: Create a sheet
	        Sheet sheet = workbook.createSheet("Users");
	        
	        ByteArrayOutputStream out = new ByteArrayOutputStream();

	        // Step 3: Create the header row
	        Row headerRow = sheet.createRow(0);
	        String[] headers = {"ID","Name", "NoOfPages","ISBN","Rating", "StockCount", "PublisedDate", "Photo","AuthorIDs","CategoryId"};
	        for (int i = 0; i < headers.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(headers[i]);
	        }

	        // Step 4: Populate the rows with user data
	        int rowNum = 1;
	        for (BookAddRequest book : bookAddRequests) {
	            Row row = sheet.createRow(rowNum++);

	            row.createCell(0).setCellValue(book.getId());
	            row.createCell(1).setCellValue(book.getName());
	            row.createCell(2).setCellValue(book.getNumberOfPages());
	            row.createCell(3).setCellValue(book.getIsbn());
	            row.createCell(4).setCellValue(book.getRating());
	            row.createCell(5).setCellValue(book.getStockCount());
	            row.createCell(6).setCellValue(book.getPublishedDate());
	            row.createCell(7).setCellValue(book.getPhoto());
	            row.createCell(8).setCellValue(String.join(",", book.getAuthorId().stream().map(String::valueOf).toList()));
	            row.createCell(9).setCellValue(book.getCategoryId());
	            
	        }

	        // Step 5: Write the workbook to stream
	        workbook.write(out);
	   

	        // Step 6: Close the workbook
	        workbook.close();
	        
	        return new ByteArrayInputStream(out.toByteArray());
	    }
}
