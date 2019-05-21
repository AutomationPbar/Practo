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

public class Hyundai5 {

	String Income = "";
	String MaritalStatus = "";
	String Remarks = "";

	static String readExcel = "c:\\eclipse\\NCRDOC.xls";
	static String writeExcel = "c:\\eclipse\\9.xls";

	static String inputsheetname = "Sheet2";
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

					System.out.println(
							i + " - " + DocName + " - " + DocURL + " - " + Rating + " - " + Votes + " - " + IsPrime);

					System.out.println(JSON);

					JSONObject obj = new JSONObject(JSON);

					System.out.println(obj.get("DocImageURL"));
					System.out.println(obj.get("ProfileClaimed"));

					String profileClaimed = obj.get("ProfileClaimed").toString();

					try {

						Rating = obj.get("Rating").toString();

						int index1 = Rating.indexOf("%");

						Rating = Rating.substring(0, index1);

					} catch (Exception e) {

					}

					try {

						Votes = obj.get("RatingVotes").toString();

						int index1 = Votes.indexOf("v");

						Votes = Votes.substring(1, index1 - 1);

					} catch (Exception e) {

					}

					String HealthFeedCount = obj.get("HealthFeedCount").toString();
					String FeedbackVotes = obj.get("FeedbackVotes").toString();

					try {

						int index1 = FeedbackVotes.indexOf("(");

						int index2 = FeedbackVotes.indexOf(")");

						FeedbackVotes = FeedbackVotes.substring(index1 + 1, index2);

						System.out.println("Rating final: " + Rating + "-" + Votes + "-" + FeedbackVotes);

					} catch (Exception e) {

					}

					// Get Memberships

					try {

						int membershipsCount = obj.getJSONObject("Memberships").length();

						String memberships = "";

						for (int l = 1; l <= membershipsCount; l++) {

							if (l == 1) {

								memberships = obj.getJSONObject("Memberships").getString(String.valueOf(l));

							} else {

								memberships = memberships + " & "
										+ obj.getJSONObject("Memberships").getString(String.valueOf(l));

							}

						}

						System.out.println("Memberships : " + memberships);

					} catch (Exception e) {

					}
					// Get Qualifications

					int qualCount = obj.getJSONObject("Education").length();

					System.out.println("Education Count : " + qualCount);

					String qualifications = "";

					try {
						qualifications = (String) obj.getJSONObject("Education").getJSONObject(String.valueOf(1))
								.get("Qualification");
					} catch (Exception e) {

					}

					JSONObject services = obj.getJSONObject("Services");

					Iterator<?> keys1 = services.keys();
					String Services = "";

					while (keys1.hasNext()) {

						String key = (String) keys1.next();

						System.out.println("Service number : " + key);

						if (Services.isEmpty() || Services.equals(null)) {

							Services = services.get(key).toString();

						} else {

							Services = Services + ", " + services.get(key).toString();

						}

					}

					System.out.println("Services : " + Services);

					/*
					 * for (int l = 1; l <= membershipsCount; l++) {
					 * 
					 * if (l == 1) {
					 * 
					 * qualifications = (String) obj.getJSONObject("Education")
					 * .getJSONObject(String.valueOf(l)) .get("Qualification");
					 * 
					 * } else {
					 * 
					 * qualifications = qualifications + " & " +
					 * obj.getJSONObject("Education")
					 * .getJSONObject(String.valueOf(l)) .get("Qualification");
					 * 
					 * }
					 * 
					 * }
					 */

					System.out.println("qualifications : " + qualifications);

					int linkedClinicsCount = obj.getJSONObject("LinkedClinics").length();
					int specializationsCount = obj.getJSONObject("Specializations").length();

					System.out.println("Specializations : " + specializationsCount);

					for (int k = 1; k <= specializationsCount; k++) {

						// JSONObject item = (JSONObject)listOfBranches.get(i);
						try {
							System.out.println(
									"Specialization : " + obj.getJSONObject("Specializations").get(String.valueOf(k)));

							String specialization = (String) obj.getJSONObject("Specializations")
									.get(String.valueOf(k));

							JSONObject lkdClinics = obj.getJSONObject("LinkedClinics");

							Iterator<?> keys = lkdClinics.keys();

							while (keys.hasNext()) {

								String fee = "";
								String bookapp = "";

								String key = (String) keys.next();

								System.out.println("Clinic : " + key);

								JSONArray clinicName = new JSONArray(lkdClinics.get(key).toString());

								try {
									fee = clinicName.getJSONObject(1).getString("Fee").toString();

									int index = fee.indexOf(" ");

									fee = fee.substring(index + 1, fee.length());

									System.out.println("FEE : " + fee);

								} catch (Exception e) {
									e.printStackTrace();
								}

								try {
									bookapp = clinicName.getJSONObject(3).getString("Book Appointment").toString();

								} catch (Exception e) {
									// System.out.println("In exception for " +
									// i + " - " + DocName + " - " + key + " - "
									// + fee);
								}
								System.out.println("Book App : " + bookapp);

								// Get Registrations

								int registrationsCount = obj.getJSONObject("Registrations").length();

								String registrations = "";

								if (registrationsCount >= 1) {

									for (int l = 1; l <= registrationsCount; l++) {

										try {
											registrations = obj.getJSONObject("Registrations")
													.getString(String.valueOf(l));

										} catch (Exception e) {

										}
										int rowNum = ExcelUtils.GetRowCount(writeExcel, outputsheetname);

										rowNum = rowNum + 1;

										System.out.println("Row Count for clinic : " + rowNum);

										System.out.println("Going for unique doc count : " + i);

										try {

											ExcelUtils.SetPractoJSon(writeExcel, outputsheetname, DocName, City,
													specialization, qualifications, key, fee, bookapp, Rating, Votes,
													FeedbackVotes, HealthFeedCount, registrations, DocURL, "N", Services, rowNum);

										} catch (Exception e) {
											e.printStackTrace();
										}

									}

								} else {

									int rowNum = ExcelUtils.GetRowCount(writeExcel, outputsheetname);

									rowNum = rowNum + 1;

									System.out.println("Row Count for clinic : " + rowNum);

									System.out.println("Going for unique doc count : " + i);

									try {

										ExcelUtils.SetPractoJSon(writeExcel, outputsheetname, DocName, City,
												specialization, qualifications, key, fee, bookapp, Rating, Votes,
												FeedbackVotes, HealthFeedCount, registrations, DocURL, "N", Services, rowNum);

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
