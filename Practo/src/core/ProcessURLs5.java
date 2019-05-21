package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import pom.PractoPOM;
import pom.ProcessURLPOM;

import static org.testng.Assert.assertEqualsNoOrder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import utilities.DBManager;
import utilities.ExcelUtils;

public class ProcessURLs5 {

	String Income = "";
	String MaritalStatus = "";
	String Remarks = "";

	static String readExcel = "c:\\eclipse\\PractoURL.xls";
	static String writeExcel = "c:\\eclipse\\1.xls";

	static String inputsheetname = "Sheet1";
	static String outputsheetname = "Sheet1";

	String LiveDB_Path = "jdbc:sqlserver://automation-data.cma4hvr5eoya.ap-south-1.rds.amazonaws.com:1433;DatabaseName=PBCroma";
	private String Liveusename = "admin";
	private String Livepassword = "DBauto!#$asd";

	DBManager dbm = new DBManager();
	String tableName = "Automation.PractoLinkedClinics";

	WebDriver d;
	WebDriverWait wait;

	@BeforeTest
	public void setProperties() {

		try {

			System.setProperty("webdriver.chrome.driver", "C:/eclipse/chromedriver.exe");

			HashMap<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);

			prefs.put("plugins.plugins_disabled", new String[] { "Adobe Flash Player", "Chrome PDF Viewer" });

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-bundled-ppapi-flash");
			options.setExperimentalOption("prefs", prefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			prefs.put("download.prompt_for_download", false);

			d = new ChromeDriver(cap);

			wait = new WebDriverWait(d, 10);

			dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

		} catch (Exception e) {

		}
	}

	@Test
	public void getData() throws InterruptedException {

		try {

			ExcelUtils.SetExcelFile(readExcel, inputsheetname);

			int rowCount = ExcelUtils.GetRowCount();

			System.out.println("Row Count = " + rowCount);

			ArrayList<String> Excelurls = new ArrayList<String>();

			for (int o = 10000; o <= 12500; o++) {

				Excelurls.add(ExcelUtils.GetCellData(o, 1));

			}

			System.out.println("Array Size : " + Excelurls.size());

			d.manage().window().maximize();

			for (int i = 0; i < Excelurls.size(); i++) {

				try {

					String DocURL = Excelurls.get(i);

					if (DocURL.contains("clinic") || DocURL.contains("hospital")) {

						continue;
					}

					System.out.println(i + " - " + DocURL);

					d.navigate().to(DocURL);

					Thread.sleep(3000);

					try {

						int docCountinClinic = 0;

						try {

							docCountinClinic = ProcessURLPOM.docClinicCount(d).size();

						} catch (Exception e) {
							e.printStackTrace();
						}

						if (docCountinClinic == 0) {

							try {

								docCountinClinic = ProcessURLPOM.docClinicCount2(d).size();

							} catch (Exception e) {
								e.printStackTrace();
							}

						}

						System.out.println("Clinic Count for Doc : " + docCountinClinic);

						String extraframe = "";

						int posFrame = 0;

						Thread.sleep(500);

						try {

							extraframe = ProcessURLPOM.docClinicCountExtraFrame(d).getText();

							posFrame = 1;

						} catch (Exception e) {
							// e.printStackTrace();
						}

						System.out.println("posFrame : " + posFrame);

						if (posFrame == 1) {

							if (extraframe.contains("clinics for")) {
								posFrame = 2;

								try {

									docCountinClinic = d
											.findElements(
													By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[2]/div"))
											.size();

								} catch (Exception e) {

								}

							}

						}

						System.out.println("Final Docs Count : " + docCountinClinic);
						System.out.println("posFramee : " + posFrame);

						if (docCountinClinic >= 1) {

							for (int f = 1; f <= docCountinClinic; f++) {

								try {

									int g = f;

									String clinicDocURL = "";
									String clinicDocName = "";
									int docnameFound = 0;

									try {
										clinicDocURL = wait
												.until(ExpectedConditions
														.visibilityOf(ProcessURLPOM.docClinicName2(d, g)))
												.getAttribute("href");

										clinicDocName = wait.until(
												ExpectedConditions.visibilityOf(ProcessURLPOM.docClinicName2(d, g)))
												.getText();

										docnameFound = 1;

									} catch (Exception e) {

									}

									if (docnameFound == 0) {

										try {
											clinicDocURL = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docClinicName3(d, g)))
													.getAttribute("href");

											clinicDocName = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docClinicName3(d, g)))
													.getText();

											docnameFound = 1;

										} catch (Exception e) {

										}

									}

									if (docnameFound == 0) {

										try {
											clinicDocURL = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docClinicName(d, g)))
													.getAttribute("href");

											clinicDocName = wait.until(
													ExpectedConditions.visibilityOf(ProcessURLPOM.docClinicName(d, g)))
													.getText();

										} catch (Exception e) {

										}

									}

									System.out.println("Clinic Name : " + clinicDocName);
									System.out.println("Clinic Doctor URL 1: " + clinicDocURL);

									if (clinicDocURL.contains("?")) {

										int indexOfParam = clinicDocURL.indexOf("?");

										clinicDocURL = clinicDocURL.substring(0, indexOfParam);

										System.out.println("Clinic Doctor URL : " + clinicDocURL);

									}

									clinicDocName = sanitize(clinicDocName);

									System.out.println("Final Clinic Namee : " + clinicDocName);

									// Get Doc Address and Google Address

									String ClinicAdd = "";

									int clinicaddfound = 0;
									try {

										ClinicAdd = wait.until(
												ExpectedConditions.visibilityOf(ProcessURLPOM.docClinicAdd(d, g)))
												.getText();

										clinicaddfound = 1;

									} catch (Exception e) {
										// e.printStackTrace();
									}

									if (clinicaddfound == 0) {

										try {

											ClinicAdd = wait.until(
													ExpectedConditions.visibilityOf(ProcessURLPOM.docClinicAdd2(d, g)))
													.getText();

											System.out.println("Going for fee 4");
										} catch (Exception e) {
											e.printStackTrace();
										}

									}

									try {
										ClinicAdd = sanitize(ClinicAdd);
									} catch (Exception e) {
										e.printStackTrace();
									}

									System.out.println("Clinic Address : " + ClinicAdd);

									// Google Address

									String ClinicGAdd = "";

									int clinicGaddfound = 0;
									try {

										ClinicGAdd = wait
												.until(ExpectedConditions
														.visibilityOf(ProcessURLPOM.docClinicGoogleAdd(d, g)))
												.getAttribute("href");

										clinicGaddfound = 1;

									} catch (Exception e) {
										e.printStackTrace();
									}

									if (clinicGaddfound == 0) {

										try {

											ClinicGAdd = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docClinicGoogleAdd2(d, g)))
													.getAttribute("href");

										} catch (Exception e) {
											e.printStackTrace();
										}

									}

									try {
										ClinicGAdd = sanitize(ClinicGAdd);
									} catch (Exception e) {
										e.printStackTrace();
									}

									System.out.println("Clinic Google Address : " + ClinicGAdd);

									// Get Doc Fee
									String DocFeeInHospital = "";

									int docFeefound = 0;
									try {

										// System.out.println("Going for fee");
										DocFeeInHospital = wait.until(
												ExpectedConditions.visibilityOf(ProcessURLPOM.docHospitalFee3(d, g)))
												.getText();

										docFeefound = 1;

										// System.out.println("Going for fee
										// 2");
									} catch (Exception e) {
										// e.printStackTrace();
									}

									if (docFeefound == 0) {

										try {

											// System.out.println("Going for fee
											// 3");
											DocFeeInHospital = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docHospitalFee2(d, g)))
													.getText();

											docFeefound = 1;
											// System.out.println("Going for fee
											// 4");
										} catch (Exception e) {
											// e.printStackTrace();
										}

									}

									if (docFeefound == 0) {

										try {

											// System.out.println("Going for fee
											// 3");
											DocFeeInHospital = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docHospitalFee(d, g)))
													.getText();

											docFeefound = 1;
											// System.out.println("Going for fee
											// 4");
										} catch (Exception e) {
											// e.printStackTrace();
										}

									}

									if (docFeefound == 0) {

										try {

											// System.out.println("Going for fee
											// 3");
											DocFeeInHospital = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docHospitalFee4(d, g)))
													.getText();

										} catch (Exception e) {
											e.printStackTrace();
										}

									}

									try {
										DocFeeInHospital = sanitize(DocFeeInHospital);
									} catch (Exception e) {
										e.printStackTrace();
									}

									System.out.println("Doc Fee : " + DocFeeInHospital);

									// Get Linked
									// Doc/Clinic
									// Booking

									String isBookApp = "";
									int gotbookapp = 0;

									try {

										isBookApp = wait.until(
												ExpectedConditions.visibilityOf(ProcessURLPOM.docBookingText(d, g)))
												.getText();

										gotbookapp = 1;

									} catch (Exception e) {

										// e.printStackTrace();
									}

									if (gotbookapp == 0) {

										try {
											isBookApp = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docBookingText2(d, g)))
													.getText();

											gotbookapp = 1;

										} catch (Exception e) {

											// e.printStackTrace();
										}
									}

									if (gotbookapp == 0) {

										try {
											isBookApp = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docBookingText3(d, g)))
													.getText();

											gotbookapp = 1;

										} catch (Exception e) {

											// e.printStackTrace();
										}
									}

									if (gotbookapp == 0) {

										try {
											isBookApp = wait
													.until(ExpectedConditions
															.visibilityOf(ProcessURLPOM.docBookingText4(d, g)))
													.getText();

										} catch (Exception e) {

											// e.printStackTrace();
										}
									}

									try {
										isBookApp = sanitize(isBookApp);

									} catch (Exception e) {

									}
									System.out.println("Is Booked : " + isBookApp);

									// Get Timings
									try {

										int timeCount = 0;

										try {

											timeCount = ProcessURLPOM.docHospitalTimingsCount2(d, g).size();

										} catch (Exception e) {
											e.printStackTrace();
										}

										if (timeCount == 0) {

											try {

												timeCount = ProcessURLPOM.docHospitalTimingsCount3(d, g).size();

											} catch (Exception e) {
												e.printStackTrace();
											}

										}

										if (timeCount == 0) {

											try {

												timeCount = ProcessURLPOM.docHospitalTimingsCount4(d, g).size();

											} catch (Exception e) {
												e.printStackTrace();
											}

										}

										if (timeCount == 0) {

											try {

												timeCount = ProcessURLPOM.docHospitalTimingsCount(d, g).size();

											} catch (Exception e) {
												e.printStackTrace();
											}

										}

										System.out.println("Time Count : " + timeCount);

										if (timeCount >= 1) {

											for (int t = 1; t <= timeCount; t++) {

												try {

													String day = "";

													int getday = 0;

													try {

														day = wait
																.until(ExpectedConditions.visibilityOf(
																		ProcessURLPOM.docHospitalTimingsDay3(d, g, t)))
																.getText();

														getday = 1;

														System.out.println("Day : " + day);

													} catch (Exception e) {

													}

													if (getday == 0) {

														try {

															day = wait
																	.until(ExpectedConditions.visibilityOf(ProcessURLPOM
																			.docHospitalTimingsDay2(d, g, t)))
																	.getText();

															getday = 1;
															System.out.println("Day : " + day);

														} catch (Exception e) {

														}

													}

													if (getday == 0) {

														try {

															day = wait
																	.until(ExpectedConditions.visibilityOf(ProcessURLPOM
																			.docHospitalTimingsDay4(d, g, t)))
																	.getText();

															getday = 1;

															System.out.println("Day : " + day);

														} catch (Exception e) {

														}

													}

													if (getday == 0) {

														try {

															day = wait.until(ExpectedConditions.visibilityOf(
																	ProcessURLPOM.docHospitalTimingsDay(d, g, t)))
																	.getText();

															getday  = 1;
															
															System.out.println("Day : " + day);

														} catch (Exception e) {

														}

													}

													if (getday == 0) {

														try {

															day = wait.until(ExpectedConditions.visibilityOf(
																	ProcessURLPOM.docHospitalTimingsDay5(d, g, t)))
																	.getText();

															System.out.println("Day : " + day);

														} catch (Exception e) {

														}

													}
													
													int perDayTimeCount = 0;

													int gottimingscount = 0;

													try {

														perDayTimeCount = ProcessURLPOM
																.docHospitalPerDayTimingsCount3(d, g, t).size();

														gottimingscount = 1;

													} catch (Exception e) {
														e.printStackTrace();
													}

													if (perDayTimeCount == 0) {

														try {

															perDayTimeCount = ProcessURLPOM
																	.docHospitalPerDayTimingsCount4(d, g, t).size();

															gottimingscount = 1;

														} catch (Exception e) {
															e.printStackTrace();
														}

													}

													if (perDayTimeCount == 0) {

														try {

															perDayTimeCount = ProcessURLPOM
																	.docHospitalPerDayTimingsCount2(d, g, t).size();

															gottimingscount = 1;

														} catch (Exception e) {
															e.printStackTrace();
														}

													}

													if (perDayTimeCount == 0) {

														try {

															perDayTimeCount = ProcessURLPOM
																	.docHospitalPerDayTimingsCount(d, g, t).size();

														} catch (Exception e) {
														}

													}

													System.out.println("Per Day time count : " + perDayTimeCount);

													String time = "";

													if (perDayTimeCount >= 1) {

														for (int c = 1; c <= perDayTimeCount; c++) {

															int gotindividualTime = 0;

															try {

																time = wait.until(
																		ExpectedConditions.visibilityOf(ProcessURLPOM
																				.docHospitalTimingsTime1(d, g, t, c)))
																		.getText();

																gotindividualTime = 1;

															} catch (Exception e) {
															}

															if (gotindividualTime == 0) {

																try {

																	time = wait
																			.until(ExpectedConditions
																					.visibilityOf(ProcessURLPOM
																							.docHospitalTimingsTime2(d,
																									g, t, c)))
																			.getText();

																	gotindividualTime = 1;

																} catch (Exception e) {
																}

															}

															if (gotindividualTime == 0) {

																try {

																	time = wait
																			.until(ExpectedConditions
																					.visibilityOf(ProcessURLPOM
																							.docHospitalTimingsTime3(d,
																									g, t, c)))
																			.getText();

																	gotindividualTime = 1;

																} catch (Exception e) {
																}

															}

															if (gotindividualTime == 0) {

																try {

																	time = wait
																			.until(ExpectedConditions
																					.visibilityOf(ProcessURLPOM
																							.docHospitalTimingsTime4(d,
																									g, t, c)))
																			.getText();

																	gotindividualTime = 1;

																} catch (Exception e) {
																}

															}

															if (gotindividualTime == 0) {

																try {

																	time = wait
																			.until(ExpectedConditions
																					.visibilityOf(ProcessURLPOM
																							.docHospitalTimingsTime5(d,
																									g, t, c)))
																			.getText();

																} catch (Exception e) {
																	e.printStackTrace();
																}

															}

															String remarks = String.valueOf(i);
															try {

																System.out.println("Going to insert data in table");

																dbm.SetPractoURLData(DocURL, clinicDocName,
																		clinicDocURL, day, time, DocFeeInHospital,
																		isBookApp, remarks, ClinicAdd, ClinicGAdd,
																		tableName);

															} catch (SQLServerException e) {

																dbm.DBConnection(LiveDB_Path, Liveusename,
																		Livepassword);

																// e.printStackTrace();

															} catch (Exception e) {

																dbm.DBConnection(LiveDB_Path, Liveusename,
																		Livepassword);

																e.printStackTrace();

															}

														}

													}

												} catch (Exception e) {
												}

											}

										}

									} catch (Exception e) {
									}

								} catch (Exception e) {

								}

							}

						}

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

	public String sanitize(String inp) {

		String result = inp.replace("'", "");

		return result;
	}

}
