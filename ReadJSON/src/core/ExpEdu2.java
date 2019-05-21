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

public class ExpEdu2 {

	String Income = "";
	String MaritalStatus = "";
	String Remarks = "";

	static String readExcel = "c:\\eclipse\\NCRDOC.xls";
	static String writeExcel = "c:\\eclipse\\4.xls";

	static String inputsheetname = "Sheet1";
	static String outputsheetname = "Sheet1";

	WebDriverWait wait;

	public static void main(String[] args) throws InterruptedException {

		try {

			ExcelUtils.SetExcelFile(readExcel, inputsheetname);

			int rowCount = ExcelUtils.GetRowCount();

			System.out.println("Row Count = " + rowCount);

			for (int i = 5000; i <= 10000; i++) {

				try {

					String JSON = ExcelUtils.GetCellData(i, 3);
					String DocURL = ExcelUtils.GetCellData(i, 4);

					System.out.println("Current Doc URL : " + DocURL);

					System.out.println(JSON);

					JSONObject obj = new JSONObject(JSON);

					JSONObject experience = obj.getJSONObject("Experience");

					Iterator<?> keys1 = experience.keys();
					String experience1 = "";

					ArrayList<String> experiences = new ArrayList<String>();

					while (keys1.hasNext()) {

						try {

							String key = (String) keys1.next();

							System.out.println("Experience number : " + key);

							JSONObject expkeys = experience.getJSONObject(key);

							String role = expkeys.getString("Role");

							String year = expkeys.getString("Year");

							String location = expkeys.getString("Location");

							String experienceFinal = role + " , " + year + " , " + location;

							System.out.println("Final Experience : " + experienceFinal);

							experiences.add(experienceFinal);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					JSONObject education = obj.getJSONObject("Education");

					Iterator<?> edukeys = education.keys();
					String education1 = "";

					ArrayList<String> role = new ArrayList<String>();
					ArrayList<String> year = new ArrayList<String>();
					ArrayList<String> location = new ArrayList<String>();

					while (edukeys.hasNext()) {

						try {

							String key = (String) edukeys.next();

							System.out.println("Education number : " + key);

							JSONObject expkeys = education.getJSONObject(key);

							role.add(expkeys.getString("Qualification"));

							year.add(expkeys.getString("Year"));

							location.add(expkeys.getString("Institute"));

						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					for (int j = 0; j < role.size(); j++) {

						if (experiences.size() >= 1) {

							for (int k = 0; k < experiences.size(); k++) {

								int rowNum = ExcelUtils.GetRowCount(writeExcel, outputsheetname);

								rowNum = rowNum + 1;

								System.out.println("Row Count for clinic : " + rowNum);

								System.out.println("Going for unique doc count : " + i);

								System.out.println("Experience : " + experiences.get(k));

								System.out.println("Education : " + role.get(j));

								try {

									System.out.println("Going to write data in Excel");
									ExcelUtils.SetPractoExpEdu2(writeExcel, outputsheetname, DocURL, experiences.get(k),
											role.get(j), year.get(j), location.get(j), rowNum);

								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						} else {

							int rowNum = ExcelUtils.GetRowCount(writeExcel, outputsheetname);

							rowNum = rowNum + 1;

							try {

								ExcelUtils.SetPractoExpEdu2(writeExcel, outputsheetname, DocURL, null, role.get(j),
										year.get(j), location.get(j), rowNum);

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
