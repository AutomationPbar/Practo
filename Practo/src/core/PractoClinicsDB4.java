package core;

import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mongodb.DB;
import utilities.DBManager;
import utilities.ExcelUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import pom.PractoLabPOM;
import pom.PractoPOM;

public class PractoClinicsDB4 {
	static String readExcel = "c:\\eclipse\\ClinicUrl.xls";
	static String inputsheetname = "Sheet1";

	String pageURL1 = "https://www.practo.com/";
	String pageURL2 = "/doctors";

	String LiveDB_Path = "jdbc:sqlserver://automation-data.cma4hvr5eoya.ap-south-1.rds.amazonaws.com:1433;DatabaseName=PBCroma";
	private String Liveusename = "admin";
	private String Livepassword = "DBauto!#$asd";

	DBManager dbm = new DBManager();

	String apiURL = "http://qamatrixapi.policybazaar.com/Communication/Communication.svc/send";

	DB db = null;

	String MastertableName = "Automation.ClinicData";
	String ServicestableName = "Automation.ClinicServices";
	String OtherCenterstableName = "Automation.ClinicOtherCenters";
	String LinkedDoctorstableName = "Automation.ClinicLinkedDoctors";
	String ClinicTimetableName = "Automation.ClinicOpenTime";

	String downloadLocation = "c:\\eclipse\\MaxBupa\\";

	String Source = "Practo";
	String IsPrime = "N";

	WebDriver d;

	WebDriverWait wait;

	@BeforeTest
	public void setProperties() {

		try {

			System.setProperty("webdriver.chrome.driver", "C:/eclipse/chromedriver.exe");

			HashMap<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("download.default_directory", downloadLocation);
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

	@Test(priority = 1)
	public void searchData() throws InterruptedException {

		try {

			ExcelUtils.SetExcelFile(readExcel, inputsheetname);

			int rowCount = ExcelUtils.GetRowCount();

			System.out.println("Row Count = " + rowCount);

			HashMap<String, String> Excelurls = new HashMap<String, String>();

			for (int o = 57000; o <= 60000; o++) {

				Excelurls.put(ExcelUtils.GetCellData(o, 0), ExcelUtils.GetCellData(o, 1));

			}

			System.out.println("Array Size : " + Excelurls.size());

			d.manage().window().maximize();

			int i = 1;

			for (String key : Excelurls.keySet()) {

				try {

					String DocURL = key;

					String remarks = String.valueOf(i);

					String docCity = Excelurls.get(key);

					System.out.println(DocURL);

					d.navigate().to(DocURL);

					Thread.sleep(3000);

					try {

						try {
							handleLoginForm();
						} catch (Exception e) {

						}

						Thread.sleep(500);

						String doctorNameURL = DocURL;

						String differentiator = doctorNameURL.substring((docCity.length() + 24),
								(docCity.length() + 32));

						System.out.println("doctorNameURL : " + doctorNameURL);

						System.out.println("differentiator : " + differentiator);

						Thread.sleep(3000);

						String DocOrClinic = "";
						String Name = "";
						String address = "";
						String googleAddress = "";
						String aboutText = "";
						String modeOfPayment = "";
						String noOfBeds = "";
						String noOfAmbulances = "";
						String emergencyNo = "";
						String amenitiesList = "";
						JSONObject WeeklyOpenTime = new JSONObject();
						JSONObject LinkedDoctors = new JSONObject();
						JSONObject Services = new JSONObject();
						JSONObject OtherCenters = new JSONObject();
						JSONObject Amenities = new JSONObject();

						// Clinic / Hospital details

						if (differentiator.contains("clinic")) {

							Name = wait.until(ExpectedConditions.visibilityOf(PractoPOM.clinicName(d))).getText();

							String clinicAdd1 = "";

							try {

								clinicAdd1 = wait.until(ExpectedConditions.visibilityOf(PractoPOM.clinicAddress(d)))
										.getText();

							} catch (Exception e) {

							}

							address = clinicAdd1;

							DocOrClinic = "Clinic";

							Thread.sleep(1500);

							try {

								googleAddress = wait
										.until(ExpectedConditions.visibilityOf(PractoPOM.clinicGoogleLocation(d)))
										.getAttribute("href");

							} catch (Exception e) {

							}

							try {

								try {

									wait.until(ExpectedConditions.visibilityOf(PractoPOM.aboutClinicMoreButton(d)))
											.click();

								} catch (Exception e) {

								}

								aboutText = wait.until(ExpectedConditions.visibilityOf(PractoPOM.aboutClinic(d)))
										.getText();

							} catch (Exception e) {

							}

							// Get Timings
							try {

								int timeCount = 0;

								try {

									timeCount = PractoPOM.clinicTimingsCount(d).size();

								} catch (Exception e) {

								}

								if (timeCount >= 1) {

									for (int g = 1; g <= timeCount; g++) {

										String day = wait.until(
												ExpectedConditions.visibilityOf(PractoPOM.clinicTimingsDay(d, g)))
												.getText();
										String time = wait.until(
												ExpectedConditions.visibilityOf(PractoPOM.clinicTimingsTime(d, g)))
												.getText();

										System.out.println("Timings : " + day + ":" + time);

										try {

											dbm.SetClinicsTimeData(doctorNameURL, day, time, remarks, Source,
													ClinicTimetableName);

										} catch (SQLServerException e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											// e.printStackTrace();

										} catch (Exception e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											e.printStackTrace();

										}

										WeeklyOpenTime.put(day, time);

									}

								}

							} catch (Exception e) {

							}

							// Get Tab IDs

							int doctorsTab = 0;
							int servicesTab = 0;
							int otherCentersTab = 0;

							try {

								for (int v = 0; v <= 10; v++) {

									String tabName = "";

									try {

										tabName = wait.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, v)))
												.getText();

									} catch (Exception e) {

									}

									if (tabName.contains("Doctor")) {

										doctorsTab = v;

									}
									if (tabName.contains("Service")) {

										servicesTab = v;

									}
									if (tabName.contains("Centers")) {

										otherCentersTab = v;

									}

								}

							} catch (Exception e) {

							}

							System.out.println("Docs Tab : " + doctorsTab);
							System.out.println("Services Tab : " + servicesTab);
							System.out.println("Centers Tab : " + otherCentersTab);

							// Click the Doctors Tab

							try {

								wait.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, doctorsTab))).click();

							} catch (Exception e) {

							}

							Thread.sleep(1500);

							try {

								int docCountinClinic = 0;

								try {

									docCountinClinic = PractoPOM.clinicDocCount(d, (doctorsTab + 1)).size();

								} catch (Exception e) {

								}

								if (docCountinClinic >= 1) {

									for (int g = 1; g <= docCountinClinic; g++) {

										String clinicDocURL = wait
												.until(ExpectedConditions
														.visibilityOf(PractoPOM.clinicDocName(d, (doctorsTab + 1), g)))
												.getAttribute("href");

										String clinicDocName = wait
												.until(ExpectedConditions
														.visibilityOf(PractoPOM.clinicDocName(d, (doctorsTab + 1), g)))
												.getText();

										if (clinicDocURL.contains("?")) {

											int indexOfParam = clinicDocURL.indexOf("?");

											clinicDocURL = clinicDocURL.substring(0, indexOfParam);

											System.out.println("Clinic Doctor URL : " + clinicDocURL);

											LinkedDoctors.put(clinicDocName, clinicDocURL);
										}

										try {

											dbm.SetLinkedDoctorData(doctorNameURL, clinicDocName, clinicDocURL, remarks,
													Source, LinkedDoctorstableName);

										} catch (SQLServerException e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											// e.printStackTrace();

										} catch (Exception e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											e.printStackTrace();

										}

									}

								}

							} catch (Exception e) {

							}

							Thread.sleep(500);

							// Go to Services Tab

							ArrayList<String> servicesList = new ArrayList<String>();

							try {

								wait.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, servicesTab))).click();

							} catch (Exception e) {

							}

							Thread.sleep(1500);

							try {

								try {

									wait.until(ExpectedConditions.visibilityOf(PractoPOM.clinicServicesMoreButton(d)))
											.click();

								} catch (Exception e) {

								}

								int servicesCount = 0;

								Thread.sleep(500);

								try {

									servicesCount = PractoPOM.clinicServicesCount(d).size();

								} catch (Exception e) {

								}

								System.out.println("Services Count : " + servicesCount);

								if (servicesCount >= 1) {

									for (int g = 1; g <= servicesCount; g++) {

										String individualService = wait
												.until(ExpectedConditions
														.visibilityOf(PractoPOM.clinicIndividualServices(d, g)))
												.getText();

										individualService = sanitize(individualService);

										servicesList.add(individualService);

										System.out.println("Service " + g + ":" + individualService);

										Services.put(String.valueOf(g), individualService);

									}

									for (int c = 0; c < servicesList.size(); c++) {

										try {

											dbm.SetSpecializationData(doctorNameURL, servicesList.get(c), remarks,
													Source, ServicestableName);

										} catch (SQLServerException e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											// e.printStackTrace();

										} catch (Exception e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											e.printStackTrace();

										}

									}

								}

							} catch (Exception e) {
								// e.printStackTrace();
							}

							Thread.sleep(500);

							// Go to Other Centers Tab

							ArrayList<String> centersList = new ArrayList<String>();

							try {

								wait.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, otherCentersTab)))
										.click();

							} catch (Exception e) {

							}

							Thread.sleep(2000);

							try {

								int otherCentersCount = 0;

								try {

									otherCentersCount = PractoPOM.clinicOtherCentersCount(d, (otherCentersTab + 1))
											.size();

								} catch (Exception e) {

								}

								System.out.println("Other centers : " + otherCentersCount);

								if (otherCentersCount >= 1) {

									for (int g = 1; g <= otherCentersCount; g++) {

										String individualCenter = wait
												.until(ExpectedConditions.visibilityOf(
														PractoPOM.clinicOtherCentersName(d, (otherCentersTab + 1), g)))
												.getAttribute("href");

										System.out.println("Center URL " + g + ":" + individualCenter);

										centersList.add(individualCenter);

										OtherCenters.put(String.valueOf(g), individualCenter);

									}

									for (int c = 0; c < centersList.size(); c++) {

										try {

											dbm.SetSpecializationData(doctorNameURL, centersList.get(c), remarks,
													Source, OtherCenterstableName);

										} catch (SQLServerException e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											// e.printStackTrace();

										} catch (Exception e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											e.printStackTrace();

										}

									}

								}

							} catch (Exception e) {
								// e.printStackTrace();

							}

						} else if (differentiator.contains("hospital")) {

							Name = wait.until(ExpectedConditions.visibilityOf(PractoPOM.hospitalName(d))).getText();

							String clinicAdd1 = wait
									.until(ExpectedConditions.visibilityOf(PractoPOM.hospitalAddress(d))).getText();

							address = clinicAdd1;

							DocOrClinic = "Hospital";

							Thread.sleep(1500);

							try {

								googleAddress = wait
										.until(ExpectedConditions.visibilityOf(PractoPOM.hospitalGoogleLocation(d)))
										.getAttribute("href");

							} catch (Exception e) {

							}

							try {

								try {

									wait.until(ExpectedConditions.visibilityOf(PractoPOM.aboutClinicMoreButton(d)))
											.click();

								} catch (Exception e) {

								}

								aboutText = wait.until(ExpectedConditions.visibilityOf(PractoPOM.aboutHospital(d)))
										.getText();

							} catch (Exception e) {

							}

							// Get Timings
							try {

								int timeCount = 0;

								try {

									timeCount = PractoPOM.hospitalTimingsCount(d).size();

								} catch (Exception e) {

								}

								if (timeCount >= 1) {

									for (int g = 1; g <= timeCount; g++) {

										String day = wait.until(
												ExpectedConditions.visibilityOf(PractoPOM.hospitalTimingsDay(d, g)))
												.getText();
										String time = wait.until(
												ExpectedConditions.visibilityOf(PractoPOM.hospitalTimingsTime(d, g)))
												.getText();

										System.out.println("Timings : " + day + ":" + time);

										try {

											dbm.SetClinicsTimeData(doctorNameURL, day, time, remarks, Source,
													ClinicTimetableName);

										} catch (SQLServerException e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											// e.printStackTrace();

										} catch (Exception e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											e.printStackTrace();

										}

										WeeklyOpenTime.put(day, time);

									}

								}

							} catch (Exception e) {

							}

							// Get Hospital Mode of Payment
							try {

								modeOfPayment = wait
										.until(ExpectedConditions.visibilityOf(PractoPOM.hospitalModesOfPayment(d)))
										.getText();

							} catch (Exception e) {

							}

							// Get Hospital No. of beds
							try {

								noOfBeds = wait.until(ExpectedConditions.visibilityOf(PractoPOM.hospitalNoOfBeds(d)))
										.getText();

							} catch (Exception e) {

							}

							// Get Hospital No. of Ambulances
							try {

								noOfAmbulances = wait
										.until(ExpectedConditions.visibilityOf(PractoPOM.hospitalNoOfAmbulances(d)))
										.getText();

							} catch (Exception e) {

							}

							// Click Hospital Read more Info
							// Button // Get Hospital Emergency
							// No // Get Hospital Amenities

							try {

								wait.until(ExpectedConditions.visibilityOf(PractoPOM.hospitalReadMoreInfoButton(d)))
										.click();

								Thread.sleep(500);

								try {

									emergencyNo = wait
											.until(ExpectedConditions.visibilityOf(PractoPOM.hospitalEmergencyNo(d)))
											.getText();

								} catch (Exception e) {

								}

								try {

									int amenitiesCount = 0;

									Thread.sleep(500);

									try {

										amenitiesCount = PractoPOM.hospitalAmenitiesCount(d).size();

									} catch (Exception e) {

									}

									System.out.println("Amenities Count : " + amenitiesCount);

									if (amenitiesCount >= 1) {

										for (int g = 1; g <= amenitiesCount; g++) {

											String individualAmenity = wait.until(
													ExpectedConditions.visibilityOf(PractoPOM.hospitalAmenities(d, g)))
													.getText();

											individualAmenity = sanitize(individualAmenity);

											if (amenitiesList.isEmpty() || amenitiesList.equals(null)) {

												amenitiesList = individualAmenity;

											} else {

												amenitiesList = amenitiesList + "' " + individualAmenity;

											}

											System.out.println("Hospital Amenity " + g + ":" + individualAmenity);

											Amenities.put(String.valueOf(g), individualAmenity);

										}

									}

								} catch (Exception e) {
									// e.printStackTrace();
								}

							} catch (Exception e) {

							}
							// Get Tab IDs

							int doctorsTab = 0;
							int servicesTab = 0;
							int otherCentersTab = 0;

							try {

								for (int v = 0; v <= 10; v++) {

									String tabName = "";

									try {

										tabName = wait.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, v)))
												.getText();

									} catch (Exception e) {

									}

									if (tabName.contains("Doctor")) {

										doctorsTab = v;

									}
									if (tabName.contains("Service")) {

										servicesTab = v;

									}
									if (tabName.contains("Centers")) {

										otherCentersTab = v;

									}

								}

							} catch (Exception e) {

							}

							System.out.println("Docs Tab : " + doctorsTab);
							System.out.println("Services Tab : " + servicesTab);
							System.out.println("Centers Tab : " + otherCentersTab);

							// Click the Doctors Tab

							ArrayList<String> linkedDocsList = new ArrayList<String>();

							try {

								wait.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, doctorsTab))).click();

							} catch (Exception e) {

							}

							Thread.sleep(1500);

							try {

								try {

									wait.until(ExpectedConditions
											.visibilityOf(PractoPOM.clinicDocMoreButton(d, (doctorsTab + 1)))).click();

								} catch (Exception e) {

								}

								Thread.sleep(1500);

								int docCountinClinic = 0;

								try {

									docCountinClinic = PractoPOM.clinicDocCount(d, (doctorsTab + 1)).size();

								} catch (Exception e) {

								}

								if (docCountinClinic >= 1) {

									for (int g = 1; g <= docCountinClinic; g++) {

										String clinicDocURL = wait
												.until(ExpectedConditions
														.visibilityOf(PractoPOM.clinicDocName(d, (doctorsTab + 1), g)))
												.getAttribute("href");

										String clinicDocName = wait
												.until(ExpectedConditions
														.visibilityOf(PractoPOM.clinicDocName(d, (doctorsTab + 1), g)))
												.getText();

										if (clinicDocURL.contains("?")) {

											int indexOfParam = clinicDocURL.indexOf("?");

											clinicDocURL = clinicDocURL.substring(0, indexOfParam);

											System.out.println("Clinic Doctor URL : " + clinicDocURL);

											linkedDocsList.add(clinicDocURL);

											LinkedDoctors.put(clinicDocName, clinicDocURL);
										}

										try {

											dbm.SetLinkedDoctorData(doctorNameURL, clinicDocName, clinicDocURL, remarks,
													Source, LinkedDoctorstableName);

										} catch (SQLServerException e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											// e.printStackTrace();

										} catch (Exception e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											e.printStackTrace();

										}

									}

								}

							} catch (Exception e) {

							}

							Thread.sleep(500);

							// Go to Services Tab

							ArrayList<String> servicesList = new ArrayList<String>();
							try {

								wait.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, servicesTab))).click();

							} catch (Exception e) {

							}

							Thread.sleep(2000);

							try {

								try {

									wait.until(ExpectedConditions.visibilityOf(PractoPOM.clinicServicesMoreButton(d)))
											.click();

								} catch (Exception e) {

								}

								int servicesCount = 0;

								Thread.sleep(500);

								try {

									servicesCount = PractoPOM.clinicServicesCount(d).size();

								} catch (Exception e) {

								}

								System.out.println("Services Count : " + servicesCount);

								if (servicesCount >= 1) {

									for (int g = 1; g <= servicesCount; g++) {

										String individualService = wait
												.until(ExpectedConditions
														.visibilityOf(PractoPOM.clinicIndividualServices(d, g)))
												.getText();

										individualService = sanitize(individualService);

										System.out.println("Service " + g + ":" + individualService);

										servicesList.add(individualService);

										Services.put(String.valueOf(g), individualService);

									}

									for (int c = 0; c < servicesList.size(); c++) {

										try {

											dbm.SetSpecializationData(doctorNameURL, servicesList.get(c), remarks,
													Source, ServicestableName);

										} catch (SQLServerException e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											// e.printStackTrace();

										} catch (Exception e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											e.printStackTrace();

										}

									}

								}

							} catch (Exception e) {
								// e.printStackTrace();
							}

							Thread.sleep(500);

							// Go to Other Centers Tab
							ArrayList<String> centersList = new ArrayList<String>();

							try {

								wait.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, otherCentersTab)))
										.click();

							} catch (Exception e) {

							}

							Thread.sleep(2000);

							try {

								int otherCentersCount = 0;

								try {

									otherCentersCount = PractoPOM.clinicOtherCentersCount(d, (otherCentersTab + 1))
											.size();

								} catch (Exception e) {

								}

								System.out.println("Other centers : " + otherCentersCount);

								if (otherCentersCount >= 1) {

									for (int g = 1; g <= otherCentersCount; g++) {

										String individualCenter = wait
												.until(ExpectedConditions.visibilityOf(
														PractoPOM.clinicOtherCentersName(d, (otherCentersTab + 1), g)))
												.getAttribute("href");

										System.out.println("Center URL " + g + ":" + individualCenter);

										centersList.add(individualCenter);

										OtherCenters.put(String.valueOf(g), individualCenter);

									}

									for (int c = 0; c < centersList.size(); c++) {

										try {

											dbm.SetSpecializationData(doctorNameURL, centersList.get(c), remarks,
													Source, OtherCenterstableName);

										} catch (SQLServerException e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											// e.printStackTrace();

										} catch (Exception e) {

											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

											e.printStackTrace();

										}

									}

								}

							} catch (Exception e) {
								// e.printStackTrace();

							}

						}

						System.out.println("Doctor / Clinic  : " + DocOrClinic);

						System.out.println("Address : " + address);

						System.out.println("Google Address  : " + googleAddress);

						System.out.println("About : " + aboutText);

						Name = sanitize(Name);
						address = sanitize(address);
						aboutText = sanitize(aboutText);
						modeOfPayment = sanitize(modeOfPayment);

						JSONObject ClinicData = new JSONObject();
						ClinicData.put("Name", Name);
						ClinicData.put("ClinicOrHospital", DocOrClinic);
						ClinicData.put("Address", address);
						ClinicData.put("GoogleAddress", googleAddress);
						ClinicData.put("WeeklyOpenTime", WeeklyOpenTime);
						ClinicData.put("LinkedDoctors", LinkedDoctors);
						ClinicData.put("Services", Services);
						ClinicData.put("OtherCenters", OtherCenters);
						ClinicData.put("About", aboutText);

						// For Hospital
						ClinicData.put("ModeOfPayment", modeOfPayment);
						ClinicData.put("NoOfBeds", noOfBeds);
						ClinicData.put("NoOfAmbulances", noOfAmbulances);
						ClinicData.put("EmergencyNo", emergencyNo);
						ClinicData.put("Amenities", Amenities);

						String jsonData = ClinicData.toString();

						System.out.println("JSON Data : " + jsonData);

						Thread.sleep(2000);

						// Input Master data

						try {

							String Languages = "";

							System.out.println("Going to insert data in table");
							dbm.SetMasterData(docCity, doctorNameURL, Name, null, googleAddress, aboutText, address,
									modeOfPayment, noOfBeds, Languages, "N", noOfAmbulances, emergencyNo, amenitiesList,
									DocOrClinic, remarks, Source, MastertableName);

						} catch (SQLServerException e) {

							dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

							// e.printStackTrace();

						} catch (Exception e) {

							dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

							e.printStackTrace();

						}

					} catch (Exception e) {

						e.printStackTrace();

					}

				} catch (Exception e) {

					e.printStackTrace();
				}
				i = i + 1;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String sanitize(String inp) {

		String result = inp.replace("'", "");

		return result;
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

	public void handleLoginForm() {

		try {

			Thread.sleep(500);

			String parentwin = d.getWindowHandle();

			d.switchTo().frame("login-iframe-form");

			Thread.sleep(500);

			d.findElement(By.xpath(".//*[@id='close']")).click();

			d.switchTo().window(parentwin);

			Thread.sleep(500);

		} catch (Exception e) {

		}

	}

}
