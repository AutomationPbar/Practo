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

public class Clinicdata {

	String Income = "";
	String MaritalStatus = "";
	String Remarks = "";

	static String readExcel = "c:\\Practo\\NCRDOC.xls";
	static String writeExcel = "c:\\Practo\\4.xls";

	static String inputsheetname = "Sheet1";
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

					// System.out.println("City " + City);
					System.out.println(i + " - " + DocName + " - " + DocURL);

					System.out.println(JSON);

					JSONObject obj = new JSONObject(JSON);

					System.out.println(obj.get("LinkedClinics"));

					JSONObject clinics = obj.getJSONObject("LinkedClinics");

					Iterator<?> keys1 = clinics.keys();

					while (keys1.hasNext()) {

						String Clinics = "";
						String key = (String) keys1.next();

						System.out.println("Clinics Name : " + key);

						JSONArray cliniclist = clinics.getJSONArray(key);

						int cliniccount = cliniclist.length();
						System.out.println("Clinics count is : " + cliniccount);

						String fees = "";
						String url = "";
						String bookapp = "";

						for (int cl = 0; cl < cliniccount; cl++) {

							try {
								JSONObject days = cliniclist.getJSONObject(cl);

								if (cl == 1) {

									fees = days.getString("Fee");
									System.out.println("Fees is " + fees);
								} else if (cl == 2) {

									url = days.getString("Clinic URL");
									System.out.println("clinic url" + url);
								} else if (cl == 3) {

									bookapp = days.getString("Book Appointment");
									System.out.println("Book Appointment " + bookapp);
								}
							} catch (Exception e) {

							}
						}

						for (int cl = 0; cl < cliniccount; cl++) {

							try {
								JSONObject days = cliniclist.getJSONObject(cl);

								if (cl == 0) {

									JSONObject days1 = days.getJSONObject("Visiting Days");

									Iterator<?> keys2 = days1.keys();

									while (keys2.hasNext()) {

										String keyday = (String) keys2.next();

										System.out.println("visit day : " + keyday);

										JSONArray visitday = days1.getJSONArray(keyday);

										int timecount = visitday.length();
										System.out.println("number of visit time: " + timecount);

										if (timecount > 0) {

											try {
												keyday = keyday + visitday.get(0).toString();
												System.out.println("The timing is : " + visitday.get(0).toString());
											} catch (Exception e) {

											}
										}

										int rowNum = ExcelUtils.GetRowCount(writeExcel, outputsheetname);

										rowNum = rowNum + 1;

										System.out.println("Row Count for clinic : " + rowNum);

										System.out.println("Going for unique doc count : " + i);

										try {

											ExcelUtils.SetClinicMaster(writeExcel, outputsheetname, DocURL, key, url,
													keyday, fees, bookapp, rowNum);

										} catch (Exception e) {
											e.printStackTrace();
										}

									}

								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}

					/*
					 * System.out.println("qualifications : " + qualifications);
					 * 
					 * int linkedClinicsCount =
					 * obj.getJSONObject("LinkedClinics").length(); int
					 * specializationsCount =
					 * obj.getJSONObject("Specializations").length();
					 * 
					 * System.out.println("Specializations : " +
					 * specializationsCount);
					 */

					/*
					 * for (int k = 1; k <= specializationsCount; k++) {
					 * 
					 * // JSONObject item = (JSONObject)listOfBranches.get(i);
					 * try { System.out.println( "Specialization : " +
					 * obj.getJSONObject("Specializations").get(String.valueOf(k
					 * )));
					 * 
					 * String specialization = (String)
					 * obj.getJSONObject("Specializations")
					 * .get(String.valueOf(k));
					 * 
					 * JSONObject lkdClinics =
					 * obj.getJSONObject("LinkedClinics");
					 * 
					 * Iterator<?> keys = lkdClinics.keys();
					 * 
					 * while (keys.hasNext()) {
					 * 
					 * String fee = ""; String bookapp = "";
					 * 
					 * String key = (String) keys.next();
					 * 
					 * System.out.println("Clinic : " + key);
					 * 
					 * JSONArray clinicName = new
					 * JSONArray(lkdClinics.get(key).toString());
					 * 
					 * try { fee =
					 * clinicName.getJSONObject(1).getString("Fee").toString();
					 * 
					 * int index = fee.indexOf(" ");
					 * 
					 * fee = fee.substring(index + 1, fee.length());
					 * 
					 * System.out.println("FEE : " + fee);
					 * 
					 * } catch (Exception e) { e.printStackTrace(); }
					 * 
					 * try { bookapp = clinicName.getJSONObject(3).getString(
					 * "Book Appointment").toString();
					 * 
					 * } catch (Exception e) { // System.out.println(
					 * "In exception for " + // i + " - " + DocName + " - " +
					 * key + " - " // + fee); } System.out.println("Book App : "
					 * + bookapp);
					 * 
					 * // Get Registrations
					 * 
					 * int registrationsCount =
					 * obj.getJSONObject("Registrations").length();
					 * 
					 * String registrations = "";
					 * 
					 * if (registrationsCount >= 1) {
					 * 
					 * for (int l = 1; l <= registrationsCount; l++) {
					 * 
					 * try { registrations = obj.getJSONObject("Registrations")
					 * .getString(String.valueOf(l));
					 * 
					 * } catch (Exception e) {
					 * 
					 * } int rowNum = ExcelUtils.GetRowCount(writeExcel,
					 * outputsheetname);
					 * 
					 * rowNum = rowNum + 1;
					 * 
					 * System.out.println("Row Count for clinic : " + rowNum);
					 * 
					 * System.out.println("Going for unique doc count : " + i);
					 * 
					 * try {
					 * 
					 * ExcelUtils.SetPractoJSon(writeExcel, outputsheetname,
					 * DocName, City, specialization, qualifications, key, fee,
					 * bookapp, Rating, Votes, FeedbackVotes, HealthFeedCount,
					 * registrations, DocURL, "N", Services, rowNum);
					 * 
					 * } catch (Exception e) { e.printStackTrace(); }
					 * 
					 * }
					 * 
					 * } else {
					 * 
					 * int rowNum = ExcelUtils.GetRowCount(writeExcel,
					 * outputsheetname);
					 * 
					 * rowNum = rowNum + 1;
					 * 
					 * System.out.println("Row Count for clinic : " + rowNum);
					 * 
					 * System.out.println("Going for unique doc count : " + i);
					 * 
					 * try {
					 * 
					 * ExcelUtils.SetPractoJSon(writeExcel, outputsheetname,
					 * DocName, City, specialization, qualifications, key, fee,
					 * bookapp, Rating, Votes, FeedbackVotes, HealthFeedCount,
					 * registrations, DocURL, "N", Services, rowNum);
					 * 
					 * } catch (Exception e) { e.printStackTrace(); }
					 * 
					 * } }
					 * 
					 * } catch (Exception e) { e.printStackTrace(); } }
					 */

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
