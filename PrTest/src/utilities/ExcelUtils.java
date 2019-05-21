package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.LIGHT_GREEN;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;

public class ExcelUtils {

	public static Workbook wb;
	public static Sheet sh;
	public static Row rowNumber;
	public static Cell cellNumber;

	public static void SetExcelFile(String path, String sheetName) throws Exception {

		try {
			// Opening Excel File

			FileInputStream ExcelFile = new FileInputStream(path);

			wb = WorkbookFactory.create(ExcelFile);
			sh = wb.getSheet(sheetName);

		} catch (Exception e) {
			throw (e);
		}

	}

	public static void GetExcelFile(String path, String sheetName) throws Exception {

		try {
			// Opening Excel File

			FileInputStream ExcelFile = new FileInputStream(path);

			HSSFWorkbook wb = new HSSFWorkbook(ExcelFile);
			HSSFSheet sh = wb.getSheet(sheetName);

		} catch (Exception e) {
			throw (e);
		}

	}

	public static Row GetRow(int rowNum) throws Exception {

		Row row;
		try {
			row = sh.getRow(rowNum);
		} catch (Exception e) {
			throw (e);
		}
		return row;
	}

	public static int GetRowCount(String path, String sheetName) throws Exception {

		int row;

		try {
			// Opening Excel File

			FileInputStream ExcelFile = new FileInputStream(path);

			HSSFWorkbook wb1 = new HSSFWorkbook(ExcelFile);
			HSSFSheet sh1 = wb1.getSheet(sheetName);

			row = sh1.getLastRowNum();
		} catch (Exception e) {
			throw (e);
		}
		return row;
	}

	public static int GetRowCount() throws Exception {

		int row;
		try {
			row = sh.getLastRowNum();
		} catch (Exception e) {
			throw (e);
		}
		return row;
	}

	public static String GetCellData(int rowNum, int colNum) throws Exception {

		try {

			Row r = sh.getRow(rowNum);
			if (r == null) {
				return "1234";
			}
			String cellData = new DataFormatter().formatCellValue(sh.getRow(rowNum).getCell(colNum));
			return cellData;

		} catch (Exception e) {
			throw (e);
		}
	}

	public static int GetIntCellData(int rowNum, int colNum) throws Exception {

		try {
			Row r = sh.getRow(rowNum);
			if (r == null) {
				return 1234;
			}

			double cellData = Math.round(sh.getRow(rowNum).getCell(colNum).getNumericCellValue());
			int cellData1 = (int) cellData;
			return cellData1;

		} catch (Exception e) {
			throw (e);
		}

	}

	public static void GetSheet(String sheetName) {

		try {

			wb.getSheet(sheetName);

		} catch (Exception e) {
			throw (e);
		}

	}

	public static int GetCurrentSheetIndex() {

		int sheetInd = 0;
		try {

			sheetInd = wb.getActiveSheetIndex();

		} catch (Exception e) {
			throw (e);
		}

		return sheetInd;
	}

	public static void SetCellData(String filePath, String sheetName, String proposalNo, int proposalNo2, int rowNum)
			throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		if (rowNum == 1) {
			System.out.println("Clearing sheet to write fresh data");
			for (int i = 1; i <= 2500; i++) {

				Row rowDel = inputSheet.createRow(i);

				inputSheet.removeRow(rowDel);

			}
		}

		Row row1 = inputSheet.getRow(rowNum);
		if (row1 == null) {
			row1 = inputSheet.createRow(rowNum);
		}

		row1.createCell(0).setCellValue(rowNum);
		row1.createCell(1).setCellValue(proposalNo);
		row1.createCell(2).setCellValue(proposalNo2);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetPractoJSon(String filePath, String sheetName, String name, String city, String speciality,
			String qualification, String hospital, String mrp, String bookapp, String rating, String ratingvotes,
			String feedback, String articles, String registration, String url, String isprime, String services,
			int rowNum) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		Row row1 = inputSheet.getRow(rowNum);
		if (row1 == null) {
			row1 = inputSheet.createRow(rowNum);
		}

		row1.createCell(0).setCellValue(rowNum);
		row1.createCell(1).setCellValue(name);
		row1.createCell(2).setCellValue(city);
		row1.createCell(3).setCellValue(speciality);
		row1.createCell(4).setCellValue(qualification);
		row1.createCell(5).setCellValue(hospital);
		row1.createCell(6).setCellValue(mrp);
		row1.createCell(7).setCellValue(bookapp);
		row1.createCell(8).setCellValue(rating);
		row1.createCell(9).setCellValue(ratingvotes);
		row1.createCell(10).setCellValue(feedback);
		row1.createCell(11).setCellValue(articles);
		row1.createCell(12).setCellValue(registration);
		row1.createCell(13).setCellValue(url);
		row1.createCell(14).setCellValue(isprime);
		row1.createCell(15).setCellValue(services);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetPractoMaster(String filePath, String sheetName, String url, String picURL, String about,
			String exp, String city, int rowNum) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		Row row1 = inputSheet.getRow(rowNum);
		if (row1 == null) {
			row1 = inputSheet.createRow(rowNum);
		}

		row1.createCell(0).setCellValue(rowNum);
		row1.createCell(1).setCellValue(url);
		row1.createCell(2).setCellValue(picURL);
		row1.createCell(3).setCellValue(about);
		row1.createCell(4).setCellValue(exp);
		row1.createCell(4).setCellValue(city);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetPractoExpEdu(String filePath, String sheetName, String url, String education,
			String experience, int rowNum) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		Row row1 = inputSheet.getRow(rowNum);
		if (row1 == null) {
			row1 = inputSheet.createRow(rowNum);
		}

		row1.createCell(0).setCellValue(rowNum);
		row1.createCell(1).setCellValue(url);
		row1.createCell(2).setCellValue(education);
		row1.createCell(3).setCellValue(experience);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetClinicMaster(String filePath, String sheetName, String url, String clinicname,
			String clinicurl, String daytime, String fee, String book, int rowNum) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		Row row1 = inputSheet.getRow(rowNum);
		if (row1 == null) {
			row1 = inputSheet.createRow(rowNum);
		}

		row1.createCell(0).setCellValue(rowNum);
		row1.createCell(1).setCellValue(url);
		row1.createCell(2).setCellValue(clinicname);
		row1.createCell(3).setCellValue(clinicurl);
		row1.createCell(4).setCellValue(daytime);
		row1.createCell(5).setCellValue(fee);
		row1.createCell(6).setCellValue(book);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData1(String filePath, String sheetName, String proposalNo, String proposerName,
			String appNo, String policyNo, String leadId, String productId, String supplierId, String issuedStatus,
			String proposalStatus, String productAndPlan, String loggedPremium, String issuedDate,
			String policyEffectiveDate, String loggedDate, String terminationDate, String Income, String MaritalStatus,
			String Remarks, String createdOn, String currentDate, int rowNum) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		if (rowNum == 1) {
			System.out.println("Clearing sheet to write fresh data");
			for (int i = 1; i <= 500; i++) {

				Row rowDel = inputSheet.createRow(i);

				inputSheet.removeRow(rowDel);

			}
		}

		Row row1 = inputSheet.getRow(rowNum);
		if (row1 == null) {
			row1 = inputSheet.createRow(rowNum);
		}

		row1.createCell(0).setCellValue(rowNum);
		row1.createCell(1).setCellValue(proposalNo);
		row1.createCell(2).setCellValue(proposerName);
		row1.createCell(3).setCellValue(appNo);
		row1.createCell(4).setCellValue(policyNo);
		row1.createCell(5).setCellValue(leadId);
		row1.createCell(6).setCellValue(productId);
		row1.createCell(7).setCellValue(supplierId);
		row1.createCell(8).setCellValue(issuedStatus);
		row1.createCell(9).setCellValue(proposalStatus);
		row1.createCell(10).setCellValue(productAndPlan);
		row1.createCell(11).setCellValue(loggedPremium);
		row1.createCell(12).setCellValue(issuedDate);
		row1.createCell(13).setCellValue(policyEffectiveDate);
		row1.createCell(14).setCellValue(loggedDate);
		row1.createCell(15).setCellValue(terminationDate);
		row1.createCell(16).setCellValue(Income);
		row1.createCell(17).setCellValue(MaritalStatus);
		row1.createCell(18).setCellValue(Remarks);
		row1.createCell(19).setCellValue(createdOn);
		row1.createCell(20).setCellValue(currentDate);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData2(String filePath, String sheetName, int proposalID, String insuredData1,
			String insuredDat2, String insuredDat3, String insuredDat4, String createdOn, String currentDate,
			int rowNum) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		if (rowNum == 1) {
			System.out.println("Clearing sheet to write fresh data");
			for (int i = 1; i <= 500; i++) {

				Row rowDel = inputSheet.createRow(i);

				inputSheet.removeRow(rowDel);

			}
		}

		Row row1 = inputSheet.getRow(rowNum);
		if (row1 == null) {
			row1 = inputSheet.createRow(rowNum);
		}

		row1.createCell(0).setCellValue(rowNum);
		row1.createCell(1).setCellValue(proposalID);
		row1.createCell(2).setCellValue(insuredData1);
		row1.createCell(3).setCellValue(insuredDat2);
		row1.createCell(4).setCellValue(insuredDat3);
		row1.createCell(5).setCellValue(insuredDat4);
		row1.createCell(6).setCellValue(createdOn);
		row1.createCell(7).setCellValue(currentDate);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData3(String filePath, String sheetName, int proposalID, String UWData1, String UWData2,
			String UWData3, String UWData4, String UWData5, String UWData6, String createdOn, String currentDate,
			int rowNum) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		if (rowNum == 1) {

			System.out.println("Clearing sheet to write fresh data");

			for (int i = 1; i <= 500; i++) {

				Row rowDel = inputSheet.createRow(i);

				inputSheet.removeRow(rowDel);

			}
		}

		Row row1 = inputSheet.getRow(rowNum);
		if (row1 == null) {
			row1 = inputSheet.createRow(rowNum);
		}

		row1.createCell(0).setCellValue(rowNum);
		row1.createCell(1).setCellValue(proposalID);
		row1.createCell(2).setCellValue(UWData1);
		row1.createCell(3).setCellValue(UWData2);
		row1.createCell(4).setCellValue(UWData3);
		row1.createCell(5).setCellValue(UWData4);
		row1.createCell(6).setCellValue(UWData5);
		row1.createCell(7).setCellValue(UWData6);
		row1.createCell(8).setCellValue(createdOn);
		row1.createCell(9).setCellValue(currentDate);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData4(String filePath, String sheetName, int proposalID, String dispatchData1,
			String dispatchData2, String dispatchData3, String createdOn, String currentDate, int rowNum)
			throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		if (rowNum == 1) {

			System.out.println("Clearing sheet to write fresh data");

			for (int i = 1; i <= 500; i++) {

				Row rowDel = inputSheet.createRow(i);

				inputSheet.removeRow(rowDel);

			}
		}

		Row row1 = inputSheet.getRow(rowNum);
		if (row1 == null) {
			row1 = inputSheet.createRow(rowNum);
		}

		row1.createCell(0).setCellValue(rowNum);
		row1.createCell(1).setCellValue(proposalID);
		row1.createCell(2).setCellValue(dispatchData1);
		row1.createCell(3).setCellValue(dispatchData2);
		row1.createCell(4).setCellValue(dispatchData3);
		row1.createCell(8).setCellValue(createdOn);
		row1.createCell(9).setCellValue(currentDate);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetCellData(String filePath, String sheetName, String[][] result) throws Exception {

		Workbook wb2 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName); // Will
																			// convert
																			// invalid
																			// characters
																			// to
																			// space
																			// '
																			// '.

		Sheet resultSheet = wb2.createSheet(safeSheetName);

		Row row0 = resultSheet.createRow(0);

		row0.createCell(0).setCellValue("S.No.");
		row0.createCell(1).setCellValue("Proposal");
		row0.createCell(2).setCellValue("Policy No");
		row0.createCell(3).setCellValue("Name");
		row0.createCell(4).setCellValue("Status");
		row0.createCell(5).setCellValue("Initiated From");
		row0.createCell(6).setCellValue("Schedule");
		row0.createCell(7).setCellValue("View");
		row0.createCell(8).setCellValue("Cust Code");
		row0.createCell(9).setCellValue("Receipt No");
		row0.createCell(10).setCellValue("Receipt Date");
		row0.createCell(11).setCellValue("Status");

		// TODO give max i length as result.length
		for (int i = 0; i < result.length; i++) {

			Row row2 = resultSheet.createRow(i + 2);

			row2.createCell(0).setCellValue(i + 1);
			row2.createCell(1).setCellValue(result[i][0]);
			row2.createCell(2).setCellValue(result[i][1]);
			row2.createCell(3).setCellValue(result[i][2]);
			row2.createCell(4).setCellValue(result[i][3]);
			row2.createCell(5).setCellValue(result[i][4]);

		}
		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		wb2.close();

	}

	public static void SetCellData1(String filePath, String sheetName, String[][] result, int row) throws Exception {

		Workbook wb2 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName); // Will
																			// convert
																			// invalid
																			// characters
																			// to
																			// space
																			// '
																			// '.

		Sheet resultSheet = wb2.createSheet(safeSheetName);

		if (row == 1) {

			System.out.println("Deleting Rows");
			for (int i = 1; i <= 100; i++) {

				Row rowDel = resultSheet.createRow(i);

				resultSheet.removeRow(rowDel);

			}
		}

		Row row0 = resultSheet.createRow(0);

		row0.createCell(0).setCellValue("S.No.");
		row0.createCell(1).setCellValue("Proposal");
		row0.createCell(2).setCellValue("Policy No");
		row0.createCell(3).setCellValue("Name");
		row0.createCell(4).setCellValue("Status");
		row0.createCell(5).setCellValue("Initiated From");
		row0.createCell(6).setCellValue("Schedule");
		row0.createCell(7).setCellValue("View");
		row0.createCell(8).setCellValue("Cust Code");
		row0.createCell(9).setCellValue("Receipt No");
		row0.createCell(10).setCellValue("Receipt Date");
		row0.createCell(11).setCellValue("Status");

		// TODO give max i length as result.length
		for (int i = 0; i < result.length; i++) {

			Row row2 = resultSheet.createRow(row + i);

			System.out.println("Row Created :" + (row + i));

			row2.createCell(0).setCellValue(i + 1);
			row2.createCell(1).setCellValue(result[i][0]);
			row2.createCell(2).setCellValue(result[i][1]);
			row2.createCell(3).setCellValue(result[i][2]);
			row2.createCell(4).setCellValue(result[i][3]);
			row2.createCell(5).setCellValue(result[i][4]);
			row2.createCell(6).setCellValue(result[i][5]);
			row2.createCell(7).setCellValue(result[i][6]);
			row2.createCell(8).setCellValue(result[i][7]);
			row2.createCell(9).setCellValue(result[i][8]);
			row2.createCell(10).setCellValue(result[i][9]);
			row2.createCell(10).setCellValue(result[i][10]);

		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		wb2.close();

	}

	public static void SetInputData(String filePath, String sheetName, String value, int cell) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3 = new HSSFWorkbook(fin);

		Sheet inputSheet = Wb3.getSheet(sheetName);

		Row row0 = inputSheet.getRow(0);
		if (row0 == null) {
			row0 = inputSheet.createRow(0);
		}

		Row row1 = inputSheet.getRow(1);
		if (row1 == null) {
			row1 = inputSheet.createRow(1);
		}

		row0.createCell(cell).setCellValue("Value");
		row1.createCell(cell).setCellValue(value);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, String[] bookings) throws Exception {

		Workbook Wb3 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);

		Sheet inputSheet = Wb3.createSheet(safeSheetName);

		for (int i = 0; i < bookings.length; i++) {

			Row row1 = inputSheet.createRow(i);

			row1.createCell(0).setCellValue("Product");

			row1.createCell(1).setCellValue(bookings[i]);

		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, long mobile, HashMap<String, String> data,
			int cell) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3;

		// String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);

		Sheet inputSheet;

		if (cell == 1) {

			Wb3 = new HSSFWorkbook();
			System.out.println("ExcelUtils creating new sheet");
			inputSheet = Wb3.createSheet(sheetName);

		} else {

			Wb3 = new HSSFWorkbook(fin);
			inputSheet = Wb3.getSheet(sheetName);

		}

		Row row0 = inputSheet.getRow(0);

		if (row0 == null) {

			row0 = inputSheet.createRow(0);
		}

		// Set Customer Mobile Number
		row0.createCell(cell).setCellValue(mobile);

		for (String key : data.keySet()) {

			System.out.println("ExcelUtils : " + key);

			Row row1 = inputSheet.getRow(Integer.parseInt(key));

			if (row1 == null) {

				row1 = inputSheet.createRow(Integer.parseInt(key));
			}

			row1.createCell(0).setCellValue(Integer.parseInt(key));

			row1.createCell(cell).setCellValue(data.get(key));

		}

		fin.close();

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, ArrayList<String> bookings) throws Exception {

		Workbook Wb3 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);

		Sheet inputSheet = Wb3.createSheet(safeSheetName);

		Row row0 = inputSheet.createRow(0);

		row0.createCell(0).setCellValue("S.No.");

		row0.createCell(1).setCellValue("Data");

		for (int i = 1; i <= bookings.size(); i++) {

			Row row1 = inputSheet.createRow(i);

			row1.createCell(0).setCellValue(i);

			row1.createCell(1).setCellValue(bookings.get(i - 1));

		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, long mobile, ArrayList<String> bookings,
			ArrayList<String> bookingsWithoutStatus, String searchedProduct, String notification, int cell)
			throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook Wb3;
		Sheet inputSheet;

		if (cell == 1) {

			Wb3 = new HSSFWorkbook();

			System.out.println("Rogue ExcelUtils - Creating new Sheet:");

			inputSheet = Wb3.createSheet(sheetName);

		} else {

			Wb3 = new HSSFWorkbook(fin);

			System.out.println("Rogue ExcelUtils - Using Existing Sheet:");

			inputSheet = Wb3.getSheet(sheetName);

		}

		Row row0 = inputSheet.getRow(0);

		if (row0 == null) {
			row0 = inputSheet.createRow(0);
		}

		row0.createCell(0).setCellValue("S.No.");

		// Setting Rogue Bookings
		for (int i = 1; i <= bookings.size(); i++) {

			Row row1 = inputSheet.getRow(i);

			if (row1 == null) {
				row1 = inputSheet.createRow(i);
			}

			row1.createCell(0).setCellValue(i);

			row0.createCell(cell + 1).setCellValue("RogueBookings");

			row1.createCell(cell + 1).setCellValue(bookings.get(i - 1));

		}

		// Setting Bookings without Status
		for (int j = 1; j <= bookingsWithoutStatus.size(); j++) {

			Row row1 = inputSheet.getRow(j);

			row0.createCell(cell + 2).setCellValue("BookingsWithoutStatus");
			row1.createCell(cell + 2).setCellValue(bookingsWithoutStatus.get(j - 1));

		}

		Row row1 = inputSheet.getRow(1);

		if (row1 == null) {
			row1 = inputSheet.createRow(1);
		}

		// Setting Customer Mobile number
		row0.createCell(cell).setCellValue("Customer Mobile");
		row1.createCell(cell).setCellValue(mobile);

		// Setting the Searched Product and Notification
		row0.createCell(cell + 3).setCellValue("Searched Product");
		row0.createCell(cell + 4).setCellValue("Notification");
		row1.createCell(cell + 3).setCellValue(searchedProduct);
		row1.createCell(cell + 4).setCellValue(notification);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();
	}

	public static void SetInputData(String filePath, String sheetName, String[] vehicle, String[][] vehicleField,
			String[][] vehicleFieldValue) throws Exception {

		Workbook Wb3 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName);

		Sheet inputSheet = Wb3.createSheet(safeSheetName);

		for (int i = 0; i < vehicle.length; i++) {

			Row newRow = inputSheet.createRow(i + 1);

			Row row0 = inputSheet.createRow(0);

			newRow.createCell(0).setCellValue(vehicle[i]);

			row0.createCell(0).setCellValue("Vehicle Name");

			for (int j = 0; j < vehicleField[i].length; j++) {

				row0.createCell(j + 1).setCellValue(vehicleField[i][j]);

				newRow.createCell(j + 1).setCellValue(vehicleFieldValue[i][j]);

			}
		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			Wb3.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		Wb3.close();

	}

	// Proc to Generate a new Report Sheet every Time. Below it is another proc
	// to update the already existing Report Sheet. (So that the formatting is
	// not to be done by POI).
	// To Reuse it, just change the proc name in
	// GenerateReport.GenerateReportSheet from GenerateReport2 to
	// GenerateReport. And pass Arraylist parameters - rogueBookings,
	// vehicleNames instead of Array paramenters as used in GenerateReport2.

	public static void GenerateReport(String filePath, String sheetName, ArrayList<String> vehicles,
			ArrayList<String> rogueBookings, ArrayList<String> rawResult, long mobile) throws Exception {

		Workbook wb2 = new HSSFWorkbook();

		String safeSheetName = WorkbookUtil.createSafeSheetName(sheetName); // Will
																			// convert
																			// invalid
																			// characters
																			// to
																			// space
																			// '
																			// '.

		Sheet resultSheet = wb2.createSheet(safeSheetName);

		Row row0 = resultSheet.createRow(0);

		row0.createCell(0).setCellValue("Module");
		row0.createCell(1).setCellValue("Scenario");
		row0.createCell(2).setCellValue("Result");
		row0.createCell(3).setCellValue("Customer Data");
		row0.createCell(4).setCellValue("Complexity");
		row0.createCell(5).setCellValue("Tester");
		row0.createCell(6).setCellValue("Defect ID");
		row0.createCell(7).setCellValue("Severity");
		row0.createCell(8).setCellValue("Status");
		row0.createCell(9).setCellValue("Customer Vehicles");
		row0.createCell(10).setCellValue("Rogue Bookings");

		row0.setHeight((short) 700);

		CellStyle my_style = wb2.createCellStyle();
		my_style.setFillForegroundColor(new HSSFColor.ORANGE().getIndex());
		my_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		for (int i = 0; i <= 10; i++) {
			row0.getCell(i).setCellStyle(my_style);
		}
		// row0.setRowStyle(my_style);

		Row row1 = resultSheet.getRow(2);

		if (row1 == null) {
			row1 = resultSheet.createRow(2);
		}

		row1.createCell(3).setCellValue(mobile);

		for (int i = 0; i < vehicles.size(); i++) {

			Row row2 = resultSheet.getRow(i + 2);

			if (row2 == null) {
				row2 = resultSheet.createRow(i + 2);
			}

			row2.createCell(9).setCellValue(vehicles.get(i));

		}

		for (int j = 0; j < rogueBookings.size(); j++) {

			Row row2 = resultSheet.getRow(j + 2);

			if (row2 == null) {
				row2 = resultSheet.createRow(j + 2);
			}

			row2.createCell(10).setCellValue(rogueBookings.get(j));

		}

		String[] Modules = { "Log In", "Customer Bookings", "Booking Details", "My Searches", "My Vehicles", "Get Help",
				"Customer Notifications", "Log Out" };
		String[] Scenarios = { "Log In using customer Credentials", "Identify Rogue Bookings",
				"Verify Booking Details Link", "Get the Latest searched product", "Identify all Customer Vehicles",
				"Verify Get Help link", "Get first Notification", "Log Out Successfully" };

		for (int k = 0; k <= 7; k++) {

			Row row2 = resultSheet.getRow(k + 2);
			if (row2 == null) {
				row2 = resultSheet.createRow(k + 2);
			}

			row2.createCell(0).setCellValue(Modules[k]);

			row2.createCell(1).setCellValue(Scenarios[k]);

			row2.createCell(2).setCellValue(rawResult.get(k));

			resultSheet.setColumnWidth(3, resultSheet.getColumnWidth(3) + 300);
			resultSheet.setColumnWidth(0, resultSheet.getColumnWidth(0) + 400);
			resultSheet.setColumnWidth(1, resultSheet.getColumnWidth(1) + 800);
			resultSheet.setColumnWidth(2, resultSheet.getColumnWidth(2) + 300);
			resultSheet.setColumnWidth(9, resultSheet.getColumnWidth(9) + 800);
			resultSheet.setColumnWidth(10, resultSheet.getColumnWidth(10) + 600);

		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		wb2.close();

	}

	@SuppressWarnings("deprecation")
	public static void GenerateReport2(String filePath, String myPoliciesSheetName, String mySearchesSheetName,
			String myVehiclesSheetName, String myNotificationsSheetName, String docsUploadSheetName,
			String otherDetailsSheetName, String reportSummarySheetName, String productName, int productCell,
			String currentProductBookings, int currentProductBookingsCell, String allCustBookings, int allBookingsCell,
			String vehicles, int vehicleCell, String bookingsWithoutStatus, int bookingsWithoutStatusCell,
			String rogueBookings, int rogueBookingsCell, String badPolicycopy, int badPolicycopyCell, String mobile,
			int policiesMobileCell, int mobileCell, String searchedProduct, int searchedProductCell,
			String notification, int notificationCell, int rowNum) throws Exception {

		FileInputStream fin = new FileInputStream(filePath);

		Workbook wb2 = new HSSFWorkbook(fin);

		CellStyle my_style1 = wb2.createCellStyle();
		my_style1.setFillForegroundColor(new LIGHT_GREEN().getIndex());
		my_style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		my_style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		my_style1.setAlignment(CellStyle.ALIGN_CENTER);
		my_style1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setWrapText(true);

		CellStyle my_style2 = wb2.createCellStyle();
		my_style2.setFillForegroundColor(new HSSFColor.ORANGE().getIndex());
		my_style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		my_style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		my_style2.setAlignment(CellStyle.ALIGN_CENTER);
		my_style2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		my_style2.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		my_style2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		my_style2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		my_style2.setWrapText(true);

		// ************************************************* Get Bookings Sheet
		// **********************************************************//

		Sheet myPoliciesSheet = wb2.getSheet(myPoliciesSheetName);

		// To delete the rest of the rows if the current Customer list is
		// smaller than the customer list of last iteration.

		if (rowNum == 1) {
			System.out.println("Clearing myPolicies sheet to write fresh result");
			for (int i = 1; i <= 100; i++) {

				Row rowDel = myPoliciesSheet.createRow(i);

				myPoliciesSheet.removeRow(rowDel);

			}
		}

		// Row to start setting Customer Data starting from Row 2 and
		// incrementing for
		// every Customer as the supplied parameter 'rowNum' increments from
		// Wrapper class

		System.out.println("allCustBookings :" + allCustBookings);

		if (allCustBookings != null && !allCustBookings.isEmpty()) {

			Row row1 = myPoliciesSheet.getRow(rowNum);

			if (row1 == null) {
				row1 = myPoliciesSheet.createRow(rowNum);
			}

			row1.createCell(0).setCellValue(rowNum);

			System.out.println("Writing mobile Number in Excel");

			row1.createCell(policiesMobileCell).setCellValue(mobile);

			// Set Product Name

			row1.createCell(productCell).setCellValue(productName);

			// System.out.println("Merge Counter before update :" +
			// cellMergeCounter);
			// System.out.println("Cell Range Address : Row" + cellMergeCounter
			// + " to Row"
			// + (cellMergeCounter + perProductCustCount) + " Cells - " +
			// productCell);

			// Merge the cells containing Product Name

			/*
			 * myPoliciesSheet.addMergedRegion( new
			 * CellRangeAddress(cellMergeCounter, cellMergeCounter +
			 * perProductCustCount, productCell, productCell));
			 */

			// Set Current Product Bookings

			row1.createCell(currentProductBookingsCell).setCellValue(currentProductBookings);

			System.out.println("Writing currentProductBookingsCell Number in Excel : " + currentProductBookingsCell);

			// Set All Customer Bookings

			row1.createCell(allBookingsCell).setCellValue(allCustBookings);

			// row1.getCell(allBookingsCell).setCellStyle(my_style1);

			// set Bookings without Status

			row1.createCell(bookingsWithoutStatusCell).setCellValue("Failed for Bookings : " + bookingsWithoutStatus);

			// set bookingsWithoutStatus Results Color Coding and Pass case data
			// value
			if (bookingsWithoutStatus == null || bookingsWithoutStatus.isEmpty()) {
				row1.getCell(bookingsWithoutStatusCell).setCellValue("PASS");
				row1.getCell(bookingsWithoutStatusCell).setCellStyle(my_style1);
			} else {
				row1.getCell(bookingsWithoutStatusCell).setCellStyle(my_style2);
			}

			// set Rogue Bookings

			row1.createCell(rogueBookingsCell).setCellValue("Failed for Bookings : " + rogueBookings);

			// set rogueBookings Results Color Coding and Pass case data value

			if (rogueBookings == null || rogueBookings.isEmpty()) {
				row1.createCell(rogueBookingsCell).setCellValue("PASS");
				row1.getCell(rogueBookingsCell).setCellStyle(my_style1);
			} else {
				row1.getCell(rogueBookingsCell).setCellStyle(my_style2);
			}

			// set Bad Policy Copy Bookings

			row1.createCell(badPolicycopyCell).setCellValue("Failed for Bookings : " + badPolicycopy);

			// set badPolicycopy Results Color Coding and Pass case data value

			if (badPolicycopy == null || badPolicycopy.isEmpty()) {
				row1.createCell(badPolicycopyCell).setCellValue("PASS");
				row1.getCell(badPolicycopyCell).setCellStyle(my_style1);
			} else {
				row1.getCell(badPolicycopyCell).setCellStyle(my_style2);
			}

			// For Policy Details Cell
			row1.createCell(badPolicycopyCell + 1).setCellValue("PASS");
			row1.getCell(badPolicycopyCell + 1).setCellStyle(my_style1);

			row1.setHeight((short) 700);
			/*
			 * for (String key : rawResult.keySet()) {
			 * 
			 * //
			 * row1.createCell(Integer.parseInt(key)).setCellValue(rawResult.get
			 * (key));
			 * 
			 * 
			 * if (rawResult.get(key).equalsIgnoreCase("Pass")) { Cell C1 =
			 * row1.getCell(Integer.parseInt(key)); if (C1 == null) { C1 =
			 * row1.createCell(Integer.parseInt(key)); }
			 * C1.setCellStyle(my_style1); }
			 * 
			 * if (rawResult.get(key).equalsIgnoreCase("Fail")) { Cell C1 =
			 * row1.getCell(Integer.parseInt(key)); if (C1 == null) { C1 =
			 * row1.createCell(Integer.parseInt(key)); }
			 * C1.setCellStyle(my_style2); }
			 * 
			 * }
			 */

		}
		// ************************************************* Get Searches Sheet
		// *******************************************************************//

		Sheet mySearchesSheet = wb2.getSheet(mySearchesSheetName);

		if (rowNum == 1) {
			System.out.println("Clearing mySearches sheet to write fresh result");
			for (int i = 1; i <= 100; i++) {

				Row rowDel = mySearchesSheet.createRow(i);

				mySearchesSheet.removeRow(rowDel);

			}
		}

		if (searchedProduct != null && !searchedProduct.isEmpty()) {
			Row row2 = mySearchesSheet.getRow(rowNum);

			if (row2 == null) {
				row2 = mySearchesSheet.createRow(rowNum);
			}

			row2.setHeight((short) 700);

			// row2.createCell(0).setCellValue(rowNum);

			row2.createCell(0).setCellValue(mobile);

			// set Searched Product

			System.out.println("Inserting Searched Product");

			row2.createCell(searchedProductCell).setCellValue(searchedProduct);

		}
		// ************************************************* Get Vehicles Sheet
		// *******************************************************************//

		Sheet myVehiclesSheet = wb2.getSheet(myVehiclesSheetName);

		if (rowNum == 1) {
			System.out.println("Clearing myVehicles sheet to write fresh result");
			for (int i = 1; i <= 100; i++) {

				Row rowDel = myVehiclesSheet.createRow(i);

				myVehiclesSheet.removeRow(rowDel);

			}
		}

		if (vehicles != null && !vehicles.isEmpty()) {
			Row row3 = myVehiclesSheet.getRow(rowNum);

			if (row3 == null) {
				row3 = myVehiclesSheet.createRow(rowNum);
			}

			row3.setHeight((short) 700);

			// row3.createCell(0).setCellValue(rowNum);

			row3.createCell(0).setCellValue(mobile);

			// set Vehicles

			row3.createCell(vehicleCell).setCellValue(vehicles);

		}

		// ************************************************* Get Notifications
		// Sheet
		// *******************************************************************//

		Sheet myNotificationsSheet = wb2.getSheet(myNotificationsSheetName);

		if (rowNum == 1) {

			System.out.println("Clearing myNotifications sheet to write fresh result");

			for (int i = 1; i <= 100; i++) {

				Row rowDel = myNotificationsSheet.createRow(i);

				myNotificationsSheet.removeRow(rowDel);

			}
		}

		if (notification != null && !notification.isEmpty()) {
			System.out.println("myNotifications sheet");

			Row row4 = myNotificationsSheet.getRow(rowNum);

			if (row4 == null) {
				row4 = myNotificationsSheet.createRow(rowNum);
			}

			row4.setHeight((short) 700);

			// row4.createCell(0).setCellValue(rowNum);

			row4.createCell(0).setCellValue(mobile);

			// set Notification

			row4.createCell(notificationCell).setCellValue(notification);
		}

		// ************************************ Set the
		// Values******************************************//

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		wb2.close();
		fin.close();

	}

	@SuppressWarnings("deprecation")
	public static void GenerateDocsUploadReport(String reportPath, String docsUploadSheetName, String mobile,
			int mobileCell, int custDocsCount, int docsUploadedCell, int visibleDocsCount, int docsVisibleCell,
			int rowNum) throws IOException {

		FileInputStream fin = new FileInputStream(reportPath);

		Workbook wb2 = new HSSFWorkbook(fin);

		CellStyle my_style1 = wb2.createCellStyle();
		my_style1.setFillForegroundColor(new LIGHT_GREEN().getIndex());
		my_style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		my_style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		my_style1.setAlignment(CellStyle.ALIGN_CENTER);
		my_style1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setWrapText(true);

		CellStyle my_style2 = wb2.createCellStyle();
		my_style2.setFillForegroundColor(new HSSFColor.ORANGE().getIndex());
		my_style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		my_style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		my_style2.setAlignment(CellStyle.ALIGN_CENTER);
		my_style2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		my_style2.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		my_style2.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		my_style2.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		my_style2.setWrapText(true);

		// ************************************************* Uploaded Docs Sheet
		// *******************************************************************//

		Sheet docsUploadSheet = wb2.getSheet(docsUploadSheetName);

		if (rowNum == 1) {

			System.out.println("Clearing docsUpload sheet to write fresh result");

			for (int i = 1; i <= 100; i++) {

				Row rowDel = docsUploadSheet.createRow(i);

				docsUploadSheet.removeRow(rowDel);

			}
		}

		if (custDocsCount != 0) {
			System.out.println("Docs Upload sheet");

			Row row5 = docsUploadSheet.getRow(rowNum);

			if (row5 == null) {
				row5 = docsUploadSheet.createRow(rowNum);
			}

			row5.setHeight((short) 700);

			// row5.createCell(0).setCellValue(rowNum);

			row5.createCell(mobileCell).setCellValue(mobile);

			// set Notification

			row5.createCell(docsUploadedCell).setCellValue(custDocsCount);
			row5.createCell(docsVisibleCell).setCellValue(visibleDocsCount);
		}

		// ************************************ Set the
		// Values******************************************//

		try (FileOutputStream fileOut = new FileOutputStream(reportPath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		wb2.close();
		fin.close();

	}

	@SuppressWarnings("deprecation")
	public static void SetSummarySheet(String reportPath, String reportSummarySheetName,
			HashMap<String, String> perProdCustCount, HashMap<String, String> AllPoliciesCount,
			HashMap<String, String> SuccessCount, HashMap<String, String> SoftCopyFailCases,
			HashMap<String, String> StatusFailCases, HashMap<String, String> policyCorruptCount) throws IOException {

		System.out.println("Setting Summary");

		FileInputStream fin = new FileInputStream(reportPath);

		Workbook wb2 = new HSSFWorkbook(fin);

		Sheet reportSummarySheet = wb2.getSheet(reportSummarySheetName);

		// Clear All rows before entering fresh data
		for (int i = 1; i <= 50; i++) {

			Row rowDel = reportSummarySheet.createRow(i);

			reportSummarySheet.removeRow(rowDel);

		}

		CellStyle my_style1 = wb2.createCellStyle();
		my_style1.setFillForegroundColor(new HSSFColor.ORANGE().getIndex());
		my_style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		my_style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		my_style1.setAlignment(CellStyle.ALIGN_CENTER);
		my_style1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setWrapText(true);

		// Input Car Summary

		Row r1 = reportSummarySheet.getRow(1);

		if (r1 == null) {

			r1 = reportSummarySheet.createRow(1);
		}

		System.out.println("Going to insert Values in report summary");

		r1.createCell(0).setCellValue("Car");

		int count = Integer.parseInt(perProdCustCount.get("Car"));
		System.out.println("Count for Car :" + count);

		r1.createCell(1).setCellValue(count);

		int count1 = Integer.parseInt(AllPoliciesCount.get("Car"));
		System.out.println("Count All policies Car :" + count1);

		r1.createCell(2).setCellValue(count1);

		int count2 = Integer.parseInt(SoftCopyFailCases.get("Car"));
		System.out.println("Count soft copy Car :" + count2);

		r1.createCell(3).setCellValue(Integer.parseInt(SuccessCount.get("Car")));

		r1.createCell(4).setCellValue(count2);

		if (count2 != 0) {
			r1.getCell(4).setCellStyle(my_style1);
		}

		int count3 = Integer.parseInt(StatusFailCases.get("Car"));
		System.out.println("Count status Car :" + count3);

		r1.createCell(5).setCellValue(count3);

		if (count3 != 0) {
			r1.getCell(5).setCellStyle(my_style1);
		}

		int count4 = Integer.parseInt(policyCorruptCount.get("Car"));
		System.out.println("Count status Car :" + count4);

		r1.createCell(6).setCellValue(count4);

		if (count4 != 0) {
			r1.getCell(6).setCellStyle(my_style1);
		}

		r1.setHeight((short) 700);

		// r1.setRowStyle(my_style1);

		// Input Health summary

		Row r2 = reportSummarySheet.getRow(2);

		if (r2 == null) {

			r2 = reportSummarySheet.createRow(2);
		}

		r2.createCell(0).setCellValue("Health");

		r2.createCell(1).setCellValue(Integer.parseInt(perProdCustCount.get("Health")));

		r2.createCell(2).setCellValue(Integer.parseInt(AllPoliciesCount.get("Health")));

		r2.createCell(3).setCellValue(Integer.parseInt(SuccessCount.get("Health")));

		r2.createCell(4).setCellValue(Integer.parseInt(SoftCopyFailCases.get("Health")));

		r2.createCell(5).setCellValue(Integer.parseInt(StatusFailCases.get("Health")));

		r2.createCell(6).setCellValue(Integer.parseInt(policyCorruptCount.get("Health")));

		if (Integer.parseInt(SoftCopyFailCases.get("Health")) != 0) {
			r2.getCell(4).setCellStyle(my_style1);
		}

		if (Integer.parseInt(StatusFailCases.get("Health")) != 0) {
			r2.getCell(5).setCellStyle(my_style1);
		}

		if (Integer.parseInt(policyCorruptCount.get("Health")) != 0) {
			r2.getCell(6).setCellStyle(my_style1);
		}

		r2.setHeight((short) 700);

		// r2.setRowStyle(my_style1);

		// Input Term Summary

		Row r3 = reportSummarySheet.getRow(3);

		if (r3 == null) {

			r3 = reportSummarySheet.createRow(3);
		}

		r3.createCell(0).setCellValue("Term Life");

		r3.createCell(1).setCellValue(Integer.parseInt(perProdCustCount.get("Term")));

		r3.createCell(2).setCellValue(Integer.parseInt(AllPoliciesCount.get("Term")));

		r3.createCell(3).setCellValue(Integer.parseInt(SuccessCount.get("Term")));

		r3.createCell(4).setCellValue(Integer.parseInt(SoftCopyFailCases.get("Term")));

		r3.createCell(5).setCellValue(Integer.parseInt(StatusFailCases.get("Term")));

		r3.createCell(6).setCellValue(Integer.parseInt(policyCorruptCount.get("Term")));

		if (Integer.parseInt(SoftCopyFailCases.get("Term")) != 0) {
			r3.getCell(4).setCellStyle(my_style1);
		}

		if (Integer.parseInt(StatusFailCases.get("Term")) != 0) {
			r3.getCell(5).setCellStyle(my_style1);
		}

		if (Integer.parseInt(policyCorruptCount.get("Term")) != 0) {
			r3.getCell(6).setCellStyle(my_style1);
		}

		r3.setHeight((short) 700);

		// r3.setRowStyle(my_style1);

		// Input Investment Summary

		Row r4 = reportSummarySheet.getRow(4);

		if (r4 == null) {

			r4 = reportSummarySheet.createRow(4);
		}

		r4.createCell(0).setCellValue("Investment");

		r4.createCell(1).setCellValue(Integer.parseInt(perProdCustCount.get("Investment")));

		r4.createCell(2).setCellValue(Integer.parseInt(AllPoliciesCount.get("Investment")));

		r4.createCell(3).setCellValue(Integer.parseInt(SuccessCount.get("Investment")));

		r4.createCell(4).setCellValue(Integer.parseInt(SoftCopyFailCases.get("Investment")));

		r4.createCell(5).setCellValue(Integer.parseInt(StatusFailCases.get("Investment")));

		r4.createCell(6).setCellValue(Integer.parseInt(policyCorruptCount.get("Investment")));

		if (Integer.parseInt(SoftCopyFailCases.get("Investment")) != 0) {
			r4.getCell(4).setCellStyle(my_style1);
		}

		if (Integer.parseInt(StatusFailCases.get("Investment")) != 0) {
			r4.getCell(5).setCellStyle(my_style1);
		}

		if (Integer.parseInt(policyCorruptCount.get("Investment")) != 0) {
			r4.getCell(6).setCellStyle(my_style1);
		}

		r4.setHeight((short) 700);

		// r4.setRowStyle(my_style1);

		fin.close();

		// Write the File
		try (FileOutputStream fileOut = new FileOutputStream(reportPath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		wb2.close();

	}

	@SuppressWarnings("deprecation")
	public static void SetRenewalSummarySheet(String reportPath, String reportSummarySheetName,
			HashMap<String, String> perProdCustCount, HashMap<String, String> AllPoliciesCount,
			HashMap<String, String> SuccessCount, HashMap<String, String> SoftCopyFailCases,
			HashMap<String, String> StatusFailCases, int carRenewalRow, int healthRenewalRow) throws IOException {

		System.out.println("Setting Summary");

		FileInputStream fin = new FileInputStream(reportPath);

		Workbook wb2 = new HSSFWorkbook(fin);

		Sheet reportSummarySheet = wb2.getSheet(reportSummarySheetName);

		CellStyle my_style1 = wb2.createCellStyle();
		my_style1.setFillForegroundColor(new HSSFColor.ORANGE().getIndex());
		my_style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		my_style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		my_style1.setAlignment(CellStyle.ALIGN_CENTER);
		my_style1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		my_style1.setWrapText(true);

		// Input Car Summary

		Row r1 = reportSummarySheet.getRow(carRenewalRow);

		if (r1 == null) {

			r1 = reportSummarySheet.createRow(carRenewalRow);
		}

		System.out.println("Going to insert Values in report summary");

		r1.createCell(0).setCellValue("Car Renewal");

		int count = Integer.parseInt(perProdCustCount.get("Car"));
		System.out.println("Count for Car :" + count);

		r1.createCell(1).setCellValue(count);

		int count1 = Integer.parseInt(AllPoliciesCount.get("Car"));
		System.out.println("Count All policies Car :" + count1);

		r1.createCell(2).setCellValue(count1);

		int count2 = Integer.parseInt(SoftCopyFailCases.get("Car"));
		System.out.println("Count soft copy Car :" + count2);

		r1.createCell(3).setCellValue(Integer.parseInt(SuccessCount.get("Car")));

		r1.createCell(4).setCellValue(count2);

		int count3 = Integer.parseInt(StatusFailCases.get("Car"));
		System.out.println("Count status Car :" + count3);

		r1.createCell(5).setCellValue(count3);

		if (count2 != 0) {
			r1.getCell(4).setCellStyle(my_style1);
		}

		if (count3 != 0) {
			r1.getCell(5).setCellStyle(my_style1);
		}

		r1.setHeight((short) 700);

		// r1.setRowStyle(my_style1);

		// Input Health summary

		Row r2 = reportSummarySheet.getRow(healthRenewalRow);

		if (r2 == null) {

			r2 = reportSummarySheet.createRow(healthRenewalRow);
		}

		r2.createCell(0).setCellValue("Health Renewal");

		r2.createCell(1).setCellValue(Integer.parseInt(perProdCustCount.get("Health")));

		r2.createCell(2).setCellValue(Integer.parseInt(AllPoliciesCount.get("Health")));

		r2.createCell(3).setCellValue(Integer.parseInt(SuccessCount.get("Health")));

		r2.createCell(4).setCellValue(Integer.parseInt(SoftCopyFailCases.get("Health")));

		if (Integer.parseInt(SoftCopyFailCases.get("Health")) != 0) {
			r2.getCell(4).setCellStyle(my_style1);
		}

		r2.createCell(5).setCellValue(Integer.parseInt(StatusFailCases.get("Health")));

		if (Integer.parseInt(StatusFailCases.get("Health")) != 0) {
			r2.getCell(5).setCellStyle(my_style1);
		}

		r2.setHeight((short) 700);

		// r2.setRowStyle(my_style1);

		/*
		 * // Input Term Summary
		 * 
		 * Row r3 = reportSummarySheet.getRow(3);
		 * 
		 * if (r3 == null) {
		 * 
		 * r3 = reportSummarySheet.createRow(3); }
		 * 
		 * r3.createCell(0).setCellValue("Term Life");
		 * 
		 * r3.createCell(1).setCellValue(Integer.parseInt(perProdCustCount.get(
		 * "Term")));
		 * 
		 * r3.createCell(2).setCellValue(Integer.parseInt(AllPoliciesCount.get(
		 * "Term")));
		 * 
		 * r3.createCell(3).setCellValue(Integer.parseInt(SuccessCount.get(
		 * "Term")));
		 * 
		 * r3.createCell(4).setCellValue(Integer.parseInt(SoftCopyFailCases.get(
		 * "Term")));
		 * 
		 * r3.createCell(5).setCellValue(Integer.parseInt(StatusFailCases.get(
		 * "Term")));
		 * 
		 * //r3.setRowStyle(my_style1);
		 * 
		 * // Input Investment Summary
		 * 
		 * Row r4 = reportSummarySheet.getRow(4);
		 * 
		 * if (r4 == null) {
		 * 
		 * r4 = reportSummarySheet.createRow(4); }
		 * 
		 * r4.createCell(0).setCellValue("Investment");
		 * 
		 * r4.createCell(1).setCellValue(Integer.parseInt(perProdCustCount.get(
		 * "Investment")));
		 * 
		 * r4.createCell(2).setCellValue(Integer.parseInt(AllPoliciesCount.get(
		 * "Investment")));
		 * 
		 * r4.createCell(3).setCellValue(Integer.parseInt(SuccessCount.get(
		 * "Investment")));
		 * 
		 * r4.createCell(4).setCellValue(Integer.parseInt(SoftCopyFailCases.get(
		 * "Investment")));
		 * 
		 * r4.createCell(5).setCellValue(Integer.parseInt(StatusFailCases.get(
		 * "Investment")));
		 * 
		 * //r4.setRowStyle(my_style1);
		 */

		fin.close();

		// Write the File
		try (FileOutputStream fileOut = new FileOutputStream(reportPath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		wb2.close();

	}

	public static void SetSearchSummarySheet(String reportPath, String reportSummarySheetName,
			HashMap<String, String> searchCount) throws Exception {

		System.out.println("Setting Summary");

		FileInputStream fin = new FileInputStream(reportPath);

		Workbook wb2 = new HSSFWorkbook(fin);

		Sheet reportSummarySheet = wb2.getSheet(reportSummarySheetName);

		reportSummarySheet.getRow(1).createCell(7).setCellValue(Integer.parseInt(searchCount.get("Car")));
		reportSummarySheet.getRow(2).createCell(7).setCellValue(Integer.parseInt(searchCount.get("Health")));
		reportSummarySheet.getRow(3).createCell(7).setCellValue(Integer.parseInt(searchCount.get("Term")));
		reportSummarySheet.getRow(4).createCell(7).setCellValue(Integer.parseInt(searchCount.get("Investment")));

		/*
		 * for(int i =1; i<=4; i++){
		 * 
		 * String prod = GetCellData(i, 0);
		 * 
		 * Row r1 = reportSummarySheet.getRow(i); //Cell c1 = r1.getCell(6);
		 * //if(c1 == null){ Cell c1 = r1.createCell(6); //}
		 * 
		 * if(prod.equals("Car")){
		 * c1.setCellValue(Integer.parseInt(searchCount.get("Car"))); }
		 * if(prod.equals("Health")){
		 * c1.setCellValue(Integer.parseInt(searchCount.get("Health"))); }
		 * if(prod.equals("Investment")){
		 * c1.setCellValue(Integer.parseInt(searchCount.get("Investment"))); }
		 * if(prod.equals("Term")){
		 * c1.setCellValue(Integer.parseInt(searchCount.get("Term"))); } }
		 */

		fin.close();

		// Write the File
		try (FileOutputStream fileOut = new FileOutputStream(reportPath)) {
			wb2.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		wb2.close();

	}

	@SuppressWarnings("deprecation")
	public static boolean isRowEmpty(Row row) {

		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);

			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}

	public static void UpdateSheetDemo(String file, String SheetName, String data) throws IOException {

		FileInputStream fin = new FileInputStream(file);

		HSSFWorkbook wb = new HSSFWorkbook(fin);

		HSSFSheet sh = wb.createSheet(SheetName);

		HSSFRow row = sh.getRow(1);

		if (row == null) {
			sh.createRow(1).createCell(1).setCellValue(data);
		} else {
			row.createCell(1).setCellValue(data);
		}

		fin.close();

		FileOutputStream fout = new FileOutputStream(file);

		wb.write(fout);

		fout.close();
		wb.close();

	}

	public static void CloseFile() throws Exception {

		try {
			wb.close();
		} catch (Exception e) {
			throw (e);
		}

	}

}
