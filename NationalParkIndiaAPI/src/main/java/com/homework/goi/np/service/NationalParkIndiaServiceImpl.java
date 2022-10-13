package com.homework.goi.np.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.TotalDigitsDocument.TotalDigits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.homework.goi.np.dao.NationalParkIndiaDao;
import com.homework.goi.np.entity.NationalParkIndia;
import com.homework.goi.np.sort.NationalParkStateComparator;
import com.homework.goi.np.sort.NationalParkYearComparator;
import com.homework.goi.np.validation.ValidateObject;

@Service
public class NationalParkIndiaServiceImpl implements NationalParkIndiaService {

	@Autowired
	private NationalParkIndiaDao dao;

	String excludedRows;
	int totalRecordCount = 0;

	@Override
	public boolean saveNationalPark(NationalParkIndia nationalParkIndia) {

		boolean isAdded = dao.saveNationalPark(nationalParkIndia);

		return isAdded;
	}

	@Override
	public boolean updateNationalPark(NationalParkIndia nationalParkIndia) {

		boolean isUpdated = dao.updateNationalPark(nationalParkIndia);

		return isUpdated;
	}

	@Override
	public List<NationalParkIndia> readExcel(String filepath) {
		Workbook workbook = null;
		FileInputStream fis = null;
		List<NationalParkIndia> list = new ArrayList<>();
		NationalParkIndia nationalParkIndia = null;

		try {
			fis = new FileInputStream(new File(filepath));
			workbook = new XSSFWorkbook(fis);

			Sheet sheet = workbook.getSheetAt(0);
			totalRecordCount = sheet.getLastRowNum();
			Iterator<Row> rows = sheet.rowIterator();
			int rowCount = 0;

			while (rows.hasNext()) {

				Row row = rows.next();
				if (rowCount == 0) {
					rowCount++;
					continue;
				}
				nationalParkIndia = new NationalParkIndia();
				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {
					Cell cell = cells.next();

					int column = cell.getColumnIndex();

					switch (column) {
					case 0:
						nationalParkIndia.setParkId((int) cell.getNumericCellValue());
						break;

					case 1:
						nationalParkIndia.setParkName(cell.getStringCellValue());
						break;

					case 2:
						nationalParkIndia.setState(cell.getStringCellValue());
						break;

					case 3:
						nationalParkIndia.setEstablishmentYear((int) cell.getNumericCellValue());
						break;

					case 4:
						nationalParkIndia.setParkAreaInSqkm(cell.getNumericCellValue());
						break;
					}
				}
				boolean isValid = ValidateObject.validateNationalPark(nationalParkIndia);
				if (isValid) {
					list.add(nationalParkIndia);
				} else {
					int rowNum = row.getRowNum() + 1;
					excludedRows = rowNum + ",";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Override
	public Map<String, String> uploadSheet(CommonsMultipartFile file, HttpSession session) {
		String path = session.getServletContext().getRealPath("/");
		String filename = file.getOriginalFilename();
		List<NationalParkIndia> list = null;
		HashMap<String, String> map = new HashMap<>();
		int count = 0;

		FileOutputStream fos = null;
		byte[] data = file.getBytes();
		try {
			fos = new FileOutputStream(new File(path + File.separator + filename));
			fos.write(data);

			list = readExcel(path + File.separator + filename);

			count = dao.updateNationalParkList(list);

			map.put("Total Record In Sheet", String.valueOf(totalRecordCount));
			map.put("Uploaded Record In DB", String.valueOf(count));
			map.put("Bad Record Row Number", excludedRows);
			map.put("Total Excluded", String.valueOf(totalRecordCount - count));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<NationalParkIndia> getAllNationalPark() {

		List<NationalParkIndia> list = dao.getAllNationalPark();

		return list;
	}

	@Override
	public List<NationalParkIndia> sortNationalPark(String sortBy) {

		List<NationalParkIndia> allNationalPark = getAllNationalPark();

		if (allNationalPark.size() > 1) {
			if (sortBy.equalsIgnoreCase("state")) {
				Collections.sort(allNationalPark, new NationalParkStateComparator());
			} else if (sortBy.equalsIgnoreCase("year")) {
				Collections.sort(allNationalPark, new NationalParkYearComparator());
			}
		}
		return allNationalPark;
	}

	@Override
	public List<NationalParkIndia> getNationalParkByState(String state) {

		List<NationalParkIndia> list = dao.getNationalParkByState(state);

		return list;
	}

	@Override
	public List<NationalParkIndia> getNationalParkByYear(int establishmentYear) {

		List<NationalParkIndia> list = dao.getNationalParkByYear(establishmentYear);

		return list;
	}

	@Override
	public boolean deleteNationalPark(int parkId) {

		boolean isDeleted = dao.deleteNationalPark(parkId);

		return isDeleted;
	}

	@Override
	public String exportToExcel(HttpSession session) {
		List<NationalParkIndia> nationalParkIndia = getAllNationalPark();
		String[] columns = { "ParkId", "ParkName", "State", "Establishment Year", "ParkArea(InSqkm)" };
		try {

			Workbook workbook = new XSSFWorkbook();

			CreationHelper createHelper = workbook.getCreationHelper();

			Sheet sheet = workbook.createSheet("National Park");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			headerFont.setColor(IndexedColors.GREEN.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);

			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
			}

			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

			int rowNum = 1;
			for (NationalParkIndia nationalParkIndia1 : nationalParkIndia) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(nationalParkIndia1.getParkId());

				row.createCell(1).setCellValue(nationalParkIndia1.getParkName());

				row.createCell(2).setCellValue(nationalParkIndia1.getState());

				row.createCell(3).setCellValue(nationalParkIndia1.getEstablishmentYear());

				row.createCell(4).setCellValue(nationalParkIndia1.getParkAreaInSqkm());
			}

			for (int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}
			String realPath = session.getServletContext().getRealPath("/export");
			FileOutputStream fileOut = new FileOutputStream(realPath + File.separator + "National Park.xlsx");
			workbook.write(fileOut);
			fileOut.close();

			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Created";
	}

}
