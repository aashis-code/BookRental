package com.bookrental.helper;

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

	public List<BookAddRequest> readExcelFile(MultipartFile file) throws IOException {

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

			// Create a new BookAddRequest object for each row
			BookAddRequest bookAddRequest = new BookAddRequest();

			// Map Excel data to BookAddRequest
			bookAddRequest.setName(getCellValue(row.getCell(0)));
			bookAddRequest.setNumber_of_pages(Integer.parseInt(getCellValue(row.getCell(1))));
			bookAddRequest.setIsbn(Integer.parseInt(getCellValue(row.getCell(2))));
			bookAddRequest.setRating(Double.parseDouble(getCellValue(row.getCell(3))));
			bookAddRequest.setStock_count(Integer.parseInt(getCellValue(row.getCell(4))));
			bookAddRequest.setPublished_date(java.sql.Date.valueOf(getCellValue(row.getCell(5))));
			bookAddRequest.setPhoto(getCellValue(row.getCell(6)));

			// Parse author IDs from a comma-separated string (assuming the authors are
			// listed in column 7)
			String authorIdsCell = getCellValue(row.getCell(7)); // Assuming author IDs are stored as comma-separated
																	// values in a cell
			Set<Integer> authorIds = new HashSet<>();

			if (authorIdsCell != null && !authorIdsCell.isEmpty()) {
				String[] authorIdsArray = authorIdsCell.split(","); // Split by comma
				for (String authorId : authorIdsArray) {
					try {
						authorIds.add(Integer.parseInt(authorId.trim())); // Add each author ID to the set
					} catch (NumberFormatException e) {
						// Handle invalid number format (optional)
						System.err.println("Invalid author ID: " + authorId);
					}
				}
			}

			bookAddRequest.setAuthor_id(authorIds); // Set the Set of author IDs
			bookAddRequest.setCategory_id(Integer.parseInt(getCellValue(row.getCell(8))));

			// Add the BookAddRequest object to the list
			bookAddRequests.add(bookAddRequest);
		}

		workbook.close();

		return bookAddRequests;
	}

	private String getCellValue(Cell cell) {
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
}
