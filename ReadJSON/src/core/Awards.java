package core;

import java.util.ArrayList;
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

public class Awards {

	String Income = "";
	String MaritalStatus = "";
	String Remarks = "";

	static String readExcel = "c:\\eclipse\\NCRDOC.xls";
	static String writeExcel = "c:\\eclipse\\2.xls";

	static String inputsheetname = "Sheet1";
	static String outputsheetname = "Sheet1";

	WebDriverWait wait;

	public static void main(String[] args) throws InterruptedException {

		try {

			ExcelUtils.SetExcelFile(readExcel, inputsheetname);

			int rowCount = ExcelUtils.GetRowCount();

			System.out.println("Row Count = " + rowCount);

			for (int i = 1; i <= 15000; i++) {

				try {

				
					String JSON = ExcelUtils.GetCellData(i, 3);
					
					String DocURL = ExcelUtils.GetCellData(i, 4);

					System.out.println("Current Doc URL : " + DocURL);

					System.out.println(JSON);

					JSONObject obj = new JSONObject(JSON);

					JSONObject membership = obj.getJSONObject("Memberships");

					Iterator<?> keys1 = membership.keys();

					ArrayList<String> memberships = new ArrayList<String>();

					while (keys1.hasNext()) {

						try {

							String key = (String) keys1.next();

							System.out.println("membership number : " + key);
							
							String membershipFinal = membership.getString(key);

							System.out.println("Final Experence : " + membershipFinal);

							memberships.add(membershipFinal);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					JSONObject award = obj.getJSONObject("Awards");

					Iterator<?> edukeys = award.keys();

					ArrayList<String> awards = new ArrayList<String>();

					while (edukeys.hasNext()) {

						try {

							String key2 = (String) edukeys.next();

							System.out.println("award number : " + key2);
							
							String awardFinal = award.getString(key2);

							System.out.println("Final award : " + awardFinal);

							awards.add(awardFinal);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					for (int j = 0; j < memberships.size(); j++) {

						if(awards.size() >= 1){
						
						for (int k = 0; k < awards.size(); k++) {

							int rowNum = ExcelUtils.GetRowCount(writeExcel, outputsheetname);

							rowNum = rowNum + 1;

							System.out.println("Row Count for clinic : " + rowNum);

							System.out.println("Going for unique doc count : " + i);

							System.out.println("membership : " + memberships.get(j));

							System.out.println("award : " + awards.get(k));

							try {

								ExcelUtils.SetPractoExpEdu(writeExcel, outputsheetname, DocURL, memberships.get(j),
										awards.get(k), rowNum);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
						
						}else{
							
							int rowNum = ExcelUtils.GetRowCount(writeExcel, outputsheetname);

							rowNum = rowNum + 1;

							
							try {

								ExcelUtils.SetPractoExpEdu(writeExcel, outputsheetname, DocURL, memberships.get(j),
										null, rowNum);

							} catch (Exception e) {
								e.printStackTrace();
							}

							
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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
