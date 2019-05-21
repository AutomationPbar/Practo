package core;

import java.util.Iterator;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import utilities.ExcelUtils;

public class Master3 {

	String Income = "";
	String MaritalStatus = "";
	String Remarks = "";

	static String readExcel = "c:\\eclipse\\NCRDOC.xls";
	static String writeExcel = "c:\\eclipse\\10.xls";

	static String inputsheetname = "Greater Noida";
	static String outputsheetname = "Sheet1";

	WebDriverWait wait;

	public static void main(String[] args) throws InterruptedException {

		try {

			ExcelUtils.SetExcelFile(readExcel, inputsheetname);

			int rowCount = ExcelUtils.GetRowCount();

			System.out.println("Row Count = " + rowCount);

			for (int i = 1; i <= rowCount; i++) {

				try {

					String City = ExcelUtils.GetCellData(i, 1);

					String DocName = ExcelUtils.GetCellData(i, 2);
					String JSON = ExcelUtils.GetCellData(i, 3);
					String DocURL = ExcelUtils.GetCellData(i, 4);
					String Rating = ExcelUtils.GetCellData(i, 5);
					String Votes = ExcelUtils.GetCellData(i, 6);
					String IsPrime = ExcelUtils.GetCellData(i, 7);
					String docImageURL = "";
					String about = "";

					System.out.println(
							i + " - " + DocName + " - " + DocURL + " - " + Rating + " - " + Votes + " - " + IsPrime);

					System.out.println(JSON);

					JSONObject obj = new JSONObject(JSON);

					docImageURL = (String) obj.get("DocImageURL");

					about = (String) obj.get("About");

					System.out.println(obj.get("DocImageURL"));

					int rowNum = i;

					JSONObject education = (JSONObject) obj.get("Education");
					
					JSONObject education1 = (JSONObject) education.get("1");
					
					String experience = education1.getString("Year");

					int expint = Integer.parseInt(experience);
					
					String exp = String.valueOf(2018 - expint);
					
					try {

						ExcelUtils.SetPractoMaster(writeExcel, outputsheetname, DocURL, docImageURL, about, exp, inputsheetname,
								rowNum);

					} catch (Exception e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// d.switchTo().parentFrame();

	private void encode(String sourceFile, String targetFile, boolean isChunked) throws Exception {

		byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(sourceFile), isChunked);

		writeByteArraysToFile(targetFile, base64EncodedData);
	}

	public void decode(String sourceFile, String targetFile) throws Exception {

		byte[] decodedBytes = Base64.decodeBase64(loadFileAsBytesArray(sourceFile));

		writeByteArraysToFile(targetFile, decodedBytes);
	}

	public byte[] loadFileAsBytesArray(String fileName) throws Exception {

		File file = new File(fileName);
		int length = (int) file.length();
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[length];
		reader.read(bytes, 0, length);
		reader.close();
		return bytes;

	}

	public void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

		File file = new File(fileName);
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
		writer.write(content);
		writer.flush();
		writer.close();

	}

}
