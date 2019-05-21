package core;

import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.mongodb.DB;
import utilities.DBManager;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import pom.PractoPOM;

public class PractoDoctorsNonPrime {

	String baseURL = "https://www.practo.com/india?page=";

	String pageURL1 = "https://www.practo.com/";
	String pageURL2 = "/doctors";
	/*
	 * String LiveDB_Path =
	 * "jdbc:sqlserver://pbavg.etechaces.com:1433;DatabaseName=PBCroma"; private
	 * String Liveusename = "BackofficeSys"; private String Livepassword =
	 * "MT#123#C@re";
	 */

	String LiveDB_Path = "jdbc:sqlserver://automation-data.cma4hvr5eoya.ap-south-1.rds.amazonaws.com:1433;DatabaseName=PBCroma";
	private String Liveusename = "admin";
	private String Livepassword = "DBauto!#$asd";

	DBManager dbm = new DBManager();

	String apiURL = "http://qamatrixapi.policybazaar.com/Communication/Communication.svc/send";

	DB db = null;

	String tableName = "Automation.PractoDoctor";

	String downloadLocation = "c:\\eclipse\\MaxBupa\\";

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

	// 0. Get the url of each suggestion list and get index of - &page= - to
	// loop through all the pages

	// 1. Loop till 200 - Get the button with element 'Next' - get its attribute
	// - class - If class contains 'inactive' - break

	@Test(priority = 1)
	public void searchData() throws InterruptedException {

		try {

			ArrayList<String> suggestions = new ArrayList<String>();

		//	suggestions.add("Bangalore");
		//	suggestions.add("Chennai");
			suggestions.add("Delhi");
		/*	suggestions.add("Hyderabad");
			suggestions.add("Mumbai");
			suggestions.add("Pune");
			suggestions.add("Kolkata");

			for (int i = 1; i <= 2; i++) {

				baseURL = "https://www.practo.com/india?page=";

				try {

					baseURL = baseURL + i;

					d.get(baseURL);

				} catch (Exception e) {

				}

				d.manage().window().maximize();

				Thread.sleep(3000);

				int suggestionCount = 0;

				try {

					suggestionCount = PractoPOM.cityCount(d, i).size();

					for (int n = 1; n <= suggestionCount; n++) {

						String currSuggestion = wait
								.until(ExpectedConditions.visibilityOf(PractoPOM.citySelect(d, i, n))).getText();

						suggestions.add(currSuggestion);

					}

				} catch (Exception e) {

				}

			}  

			for (int n = 0; n < 1; n++) {

				System.out.println("Suggestion : " + n + " - " + suggestions.get(n));

			}
*/
			d.manage().window().maximize();
			
			for (int i = 0; i < 1; i++) {

				try {

					System.out.println("Going for Suggestion : " + (i));

					String docCity = suggestions.get(i);

					String pageURL = pageURL1 + docCity + pageURL2;

					System.out.println("Going for URL : " + pageURL);

					d.navigate().to(pageURL);

					Thread.sleep(3000);

					String totalDocCount = "";

					try {

						totalDocCount = wait.until(ExpectedConditions.visibilityOf(PractoPOM.totalDocCountPerCity(d)))
								.getText();

					} catch (Exception e) {
						// e.printStackTrace();
					}

					int docsPerPage1 = 10;

				/*	try {

						docsPerPage1 = PractoPOM.doctorCountPerPage(d).size();

					} catch (Exception e) {
						// e.printStackTrace();
					}  */

					System.out.println("Total doc Count in City : " + totalDocCount);
					System.out.println("Doc count per page : " + docsPerPage1);

					int DocPageCount = (Integer.parseInt(totalDocCount) / docsPerPage1);

					DocPageCount = DocPageCount + 1;

					System.out.println("DocPageCount : " + DocPageCount);

					String currURL = d.getCurrentUrl();

					for (int k = 1; k <= DocPageCount; k++) {

						try {

							String navigateToURL = "";
							
							try {

								if (!currURL.contains("?page")) {

									navigateToURL = currURL + "?page=" + (k) + "&program_type=practo_non_prime";

								} else {

									navigateToURL = currURL + (k) + "&program_type=practo_non_prime";

								}

								System.out.println("Updated URL : " + navigateToURL);

								d.navigate().to(navigateToURL);

							} catch (Exception e) {

							}
							
							System.out.println("Page : " + k);

							int docsPerPage = 0;

							try {

								docsPerPage = PractoPOM.doctorCountPerPage(d).size();

							} catch (Exception e) {
								// e.printStackTrace();
							}

							System.out.println("Docs per page : " + docsPerPage);

							if (docsPerPage >= 1) {

								for (int l = 1; l <= (docsPerPage + 1); l++) {

									try {

										System.out.println("Page No : " + k + "Doc : " + l);

										Thread.sleep(500);

										String doctorNameURL = wait
												.until(ExpectedConditions
														.visibilityOf(PractoPOM.doctorNameSelect(d, l)))
												.getAttribute("href");

										String differentiator = doctorNameURL.substring((docCity.length() + 24),
												(docCity.length() + 32));

										System.out.println("doctorNameURL : " + doctorNameURL);

										System.out.println("differentiator : " + differentiator);

										if (doctorNameURL.contains("?")) {

											int indexOfQue = doctorNameURL.indexOf("?");

											doctorNameURL = doctorNameURL.substring(0, indexOfQue);

											System.out.println("doctorNameURL 2: " + doctorNameURL);

										}

										((JavascriptExecutor) d).executeScript("window.open()");

										// Thread.sleep(1500);

										String parentWin = d.getWindowHandle();

										ArrayList<String> handles1 = new ArrayList<String>(d.getWindowHandles());

										System.out.println("Tabs :" + handles1.size());

										if (handles1.size() > 1) {

											d.switchTo().window(handles1.get(1));

											d.get(doctorNameURL);

										}

										Thread.sleep(2500);

										String DocOrClinic = "";
										String DocImage = "";
										String Name = "";
										String address = "";
										String googleAddress = "";
										String rating = "";
										String ratingVotes = "";
										String feedback = "";
										String feedbackVotes = "";
										String healthFeedCount = "";
										String consultQACount = "";
										String consultAnsCount = "";
										String aboutText = "";
										String modeOfPayment = "";
										JSONObject WeeklyOpenTime = new JSONObject();
										JSONObject LinkedDoctors = new JSONObject();
										JSONObject Services = new JSONObject();
										JSONObject Specializations = new JSONObject();
										JSONObject Awards = new JSONObject();
										JSONObject Memberships = new JSONObject();
										JSONObject Registrations = new JSONObject();
										JSONObject Education = new JSONObject();
										JSONObject Experience = new JSONObject();

										// Clinic / Hospital details

										Name = wait.until(ExpectedConditions.visibilityOf(PractoPOM.doctorName(d)))
												.getText();

										int indexofDr = Name.indexOf("Dr.");

										if (indexofDr != (-1)) {

											Name = Name.substring((indexofDr + 4), Name.length());

										}

										String profileClaimed = "";

										// Thread.sleep(2000);
										try {

											Actions act = new Actions(d);

											act.clickAndHold(PractoPOM.doctorProfileClaimed(d)).build().perform();

											// Thread.sleep(2000);

											profileClaimed = PractoPOM.doctorProfileClaimedText(d).getText();

											System.out.println("Profile Claimed : " + profileClaimed);

										} catch (Exception e) {
											e.printStackTrace();
										}

										try {

											DocImage = wait
													.until(ExpectedConditions.visibilityOf(PractoPOM.doctorImage(d)))
													.getAttribute("src");

											int indexOfSlash = DocImage.lastIndexOf("/thumbnail");

											DocImage = DocImage.substring(0, indexOfSlash);

											System.out.println("Doc Image URL : " + DocImage);

										} catch (Exception e) {

										}

										try {

											String docAddress = wait
													.until(ExpectedConditions.visibilityOf(PractoPOM.docAddress(d)))
													.getText();

											address = sanitize(docAddress);

										} catch (Exception e) {

										}

										DocOrClinic = "Doctor";

										Thread.sleep(500);

										try {

											googleAddress = wait
													.until(ExpectedConditions
															.visibilityOf(PractoPOM.docGoogleLocation(d)))
													.getAttribute("href");

											googleAddress = sanitize(googleAddress);
											
										} catch (Exception e) {

										}

										Thread.sleep(500);

										try {

											rating = wait.until(ExpectedConditions.visibilityOf(PractoPOM.docRating(d)))
													.getText();

										} catch (NoSuchElementException e) {
											
											rating = wait.until(ExpectedConditions.visibilityOf(PractoPOM.docRating2(d)))
													.getText();
											
										} catch (Exception e) {
											e.printStackTrace();

										}

										try {

											ratingVotes = wait
													.until(ExpectedConditions.visibilityOf(PractoPOM.docRatingCount(d)))
													.getText();

										} catch (NoSuchElementException e) {
											ratingVotes = wait
													.until(ExpectedConditions.visibilityOf(PractoPOM.docRatingCount2(d)))
													.getText();

										} catch (Exception e) {
											e.printStackTrace();

										}

										try {

											try {

												wait.until(ExpectedConditions
														.visibilityOf(PractoPOM.aboutdoctorMoreButton(d))).click();

											} catch (Exception e) {

											}

											aboutText = wait
													.until(ExpectedConditions.visibilityOf(PractoPOM.aboutDoctor(d)))
													.getText();

											aboutText = sanitize(aboutText);
											
										} catch (Exception e) {

										}

										// Get Doc Clinic Count

										try {

											int docCountinClinic = 0;

											try {

												docCountinClinic = PractoPOM.docClinicCount(d).size();

											} catch (Exception e) {
												e.printStackTrace();
											}

											System.out.println("Clinic Count for Doc : " + docCountinClinic);

											String extraframe = "";

											int posFrame = 0;

											Thread.sleep(500);

											try {

												extraframe = PractoPOM.docClinicCountExtraFrame(d).getText();

												posFrame = 1;

											} catch (Exception e) {
												//e.printStackTrace();
											}

											System.out.println("posFrame : " + posFrame);

											if (posFrame == 1) {

												if (extraframe.contains("clinics for")) {
													posFrame = 2;

													try {

														docCountinClinic = d
																.findElements(By
																		.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[2]/div"))
																.size();

													} catch (Exception e) {

													}

												}

											}

											System.out.println("Final Docs Count : " + docCountinClinic);
											System.out.println("posFramee : " + posFrame);

											if (docCountinClinic >= 1) {

												if (posFrame != 2) {

													System.out.println("posFrameee : " + posFrame);

													for (int g = 1; g <= docCountinClinic; g++) {

														try {

															String clinicDocURL = wait
																	.until(ExpectedConditions.visibilityOf(
																			PractoPOM.docClinicName(d, g)))
																	.getAttribute("href");

															String clinicDocName = wait.until(ExpectedConditions
																	.visibilityOf(PractoPOM.docClinicName(d, g)))
																	.getText();

															System.out.println("Clinic Name : " + clinicDocName);
															System.out.println("Clinic Doctor URL 1: " + clinicDocURL);

															if (clinicDocURL.contains("?")) {

																int indexOfParam = clinicDocURL.indexOf("?");

																clinicDocURL = clinicDocURL.substring(0, indexOfParam);

																System.out
																		.println("Clinic Doctor URL : " + clinicDocURL);

															}

															clinicDocName = sanitize(clinicDocName);
															
															System.out.println("Final Clinic Name : " + clinicDocName);
															// Get Linked
															// Doc/Clinic
															// Booking

															String isBookApp = "";

															try {

																isBookApp = wait
																		.until(ExpectedConditions.visibilityOf(
																				PractoPOM.docBookingText(d, g)))
																		.getText();

																

															} catch (NoSuchElementException e) {

																isBookApp = wait
																		.until(ExpectedConditions.visibilityOf(
																				PractoPOM.docBookingText2(d, g)))
																		.getText();

															} catch (Exception e) {

																e.printStackTrace();
															}

															isBookApp = sanitize(isBookApp);
															
															System.out.println("Is Booked : " + isBookApp);
															
															// Get Linked
															// Doc/Clinic
															// Timings
															
															try {

																int timeCount = 0;

																try {

																	timeCount = PractoPOM.docHospitalTimingsCount(d, g)
																			.size();

																} catch (Exception e) {

																}

																System.out.println("Time Count : " + timeCount);

																if (timeCount >= 1) {

																	for (int t = 1; t <= timeCount; t++) {

																		try {

																			String day = wait
																					.until(ExpectedConditions
																							.visibilityOf(PractoPOM
																									.docHospitalTimingsDay(
																											d, g, t)))
																					.getText();

																			System.out.println("Day : " + day);

																			int perDayTimeCount = 0;

																			try {

																				perDayTimeCount = PractoPOM
																						.docHospitalPerDayTimingsCount(
																								d, g, t)
																						.size();

																			} catch (Exception e) {

																			}

																			System.out.println("Per Day time count : "
																					+ perDayTimeCount);

																			String time = "";
																			JSONArray timesArray = new JSONArray();

																			if (perDayTimeCount > 1) {

																				for (int c = 1; c <= perDayTimeCount; c++) {

																					try {
																						time = wait
																								.until(ExpectedConditions
																										.visibilityOf(
																												PractoPOM
																														.docHospitalTimingsTime1(
																																d,
																																g,
																																t,
																																c)))
																								.getText();

																						timesArray.put(time);

																					} catch (Exception e) {

																					}

																				}

																				System.out.println("Timings : " + day
																						+ ":" + time);

																			} else {

																				try {

																					time = wait.until(ExpectedConditions
																							.visibilityOf(PractoPOM
																									.docHospitalTimingsTime(
																											d, g, t)))
																							.getText();

																					timesArray.put(time);

																				} catch (Exception e) {

																				}

																			}

																			WeeklyOpenTime.put(day, timesArray);

																		} catch (Exception e) {

																		}

																	}

																}

															} catch (Exception e) {
																e.printStackTrace();
															}

															String DocFeeInHospital = "";

															try {

																DocFeeInHospital = wait
																		.until(ExpectedConditions.visibilityOf(
																				PractoPOM.docHospitalFee(d, g)))
																		.getText();

															} catch (Exception e) {
																e.printStackTrace();
															}

															DocFeeInHospital = sanitize(DocFeeInHospital);
															
															try {

																JSONObject Time = new JSONObject();

																Time.put("Visiting Days", WeeklyOpenTime);

																JSONObject fee = new JSONObject();

																JSONObject URL = new JSONObject();

																JSONObject isBook = new JSONObject();

																fee.put("Fee", DocFeeInHospital);

																URL.put("Clinic URL", clinicDocURL);

																isBook.put("Book Appointment", isBookApp);

																JSONArray feeNTime = new JSONArray();

																feeNTime.put(Time);
																feeNTime.put(fee);
																feeNTime.put(URL);
																feeNTime.put(isBook);

																// JSONObject
																// clinicDetails
																// =
																// new
																// JSONObject();

																// clinicDetails.put("Clinic
																// Details",
																// feeNTime);

																LinkedDoctors.putOpt(clinicDocName, feeNTime);

															} catch (Exception e) {
																e.printStackTrace();
															}

														} catch (Exception e) {
															e.printStackTrace();
														}

													}

												} else {

													for (int f = 1; f <= docCountinClinic; f++) {

														try {

															int g = f + 1;

															String clinicDocURL = wait
																	.until(ExpectedConditions.visibilityOf(
																			PractoPOM.docClinicName2(d, g)))
																	.getAttribute("href");

															String clinicDocName = wait.until(ExpectedConditions
																	.visibilityOf(PractoPOM.docClinicName2(d, g)))
																	.getText();

															System.out.println("Clinic Name : " + clinicDocName);
															System.out.println("Clinic Doctor URL 1: " + clinicDocURL);

															if (clinicDocURL.contains("?")) {

																int indexOfParam = clinicDocURL.indexOf("?");

																clinicDocURL = clinicDocURL.substring(0, indexOfParam);

																System.out
																		.println("Clinic Doctor URL : " + clinicDocURL);

															}
															
															clinicDocName = sanitize(clinicDocName);
															
															System.out.println("Final Clinic Namee : " + clinicDocName);
															
															// Get Timings
															try {

																int timeCount = 0;

																try {

																	timeCount = PractoPOM.docHospitalTimingsCount2(d, g)
																			.size();

																} catch (Exception e) {

																}

																System.out.println("Time Count : " + timeCount);

																if (timeCount >= 1) {

																	for (int t = 1; t <= timeCount; t++) {

																		try {

																			String day = wait
																					.until(ExpectedConditions
																							.visibilityOf(PractoPOM
																									.docHospitalTimingsDay2(
																											d, g, t)))
																					.getText();

																			System.out.println("Day : " + day);

																			int perDayTimeCount = 0;

																			try {

																				perDayTimeCount = PractoPOM
																						.docHospitalPerDayTimingsCount2(
																								d, g, t)
																						.size();

																			} catch (Exception e) {
																			}

																			System.out.println("Per Day time count : "
																					+ perDayTimeCount);

																			String time = "";
																			JSONArray timesArray = new JSONArray();

																			if (perDayTimeCount > 1) {

																				for (int c = 1; c <= perDayTimeCount; c++) {

																					try {
																						time = wait
																								.until(ExpectedConditions
																										.visibilityOf(
																												PractoPOM
																														.docHospitalTimingsTime12(
																																d,
																																g,
																																t,
																																c)))
																								.getText();

																						timesArray.put(time);

																					} catch (Exception e) {
																					}

																				}

																				System.out.println("Timings : " + day
																						+ ":" + time);

																			} else {

																				try {

																					time = wait.until(ExpectedConditions
																							.visibilityOf(PractoPOM
																									.docHospitalTimingsTime2(
																											d, g, t)))
																							.getText();

																					timesArray.put(time);

																				} catch (Exception e) {
																				}

																			}

																			WeeklyOpenTime.put(day, timesArray);

																		} catch (Exception e) {
																		}

																	}

																}

															} catch (Exception e) {
															}

															String DocFeeInHospital = "";

															try {

																DocFeeInHospital = wait
																		.until(ExpectedConditions.visibilityOf(
																				PractoPOM.docHospitalFee2(d, g)))
																		.getText();

															} catch (Exception e) {
															}

															try {

																JSONObject Time = new JSONObject();

																Time.put("Visiting Days", WeeklyOpenTime);

																JSONObject fee = new JSONObject();

																JSONObject URL = new JSONObject();

																fee.put("Fee", DocFeeInHospital);

																URL.put("Clinic URL", clinicDocURL);

																JSONArray feeNTime = new JSONArray();

																feeNTime.put(Time);
																feeNTime.put(fee);
																feeNTime.put(URL);

																LinkedDoctors.putOpt(clinicDocName, feeNTime);

															} catch (Exception e) {

															}

														} catch (Exception e) {

														}

													}

												}

											}
										} catch (Exception e) {

										}

										Thread.sleep(500);

										// Get doc Services

										try {

											try {

												wait.until(ExpectedConditions
														.visibilityOf(PractoPOM.docServicesMoreButton(d))).click();

											} catch (Exception e) {

											}

											int servicesCount = 0;

											Thread.sleep(500);

											try {

												servicesCount = PractoPOM.docServicesCount(d).size();

											} catch (Exception e) {

											}

											System.out.println("Services Count : " + servicesCount);

											if (servicesCount >= 1) {

												for (int g = 1; g <= servicesCount; g++) {

													try {

														String individualService = wait
																.until(ExpectedConditions.visibilityOf(
																		PractoPOM.docIndividualService(d, g)))
																.getText();

														individualService = sanitize(individualService);

														// System.out.println("Service
														// " + g + ":" +
														// individualService);

														Services.put(String.valueOf(g), individualService);

													} catch (Exception e) {

													}

												}

											}

										} catch (Exception e) {
											// e.printStackTrace();
										}

										Thread.sleep(500);

										// Get Doc Specializations
										try {

											try {

												wait.until(
														ExpectedConditions.visibilityOf(PractoPOM.docSpecMoreButton(d)))
														.click();

											} catch (Exception e) {

											}

											int servicesCount = 0;

											Thread.sleep(500);

											try {

												servicesCount = PractoPOM.docSpecCount(d).size();

											} catch (Exception e) {

											}

											System.out.println("Specialization Count : " + servicesCount);

											if (servicesCount >= 1) {

												for (int g = 1; g <= servicesCount; g++) {

													try {

														String individualService = wait.until(ExpectedConditions
																.visibilityOf(PractoPOM.docIndividualSpec(d, g)))
																.getText();

														individualService = sanitize(individualService);

														// System.out.println("Specs
														// " + g + ":" +
														// individualService);

														Specializations.put(String.valueOf(g), individualService);

													} catch (Exception e) {

													}

												}

											}

										} catch (Exception e) {
											// e.printStackTrace();
										}

										// Get Doc Awards

										try {

											try {

												PractoPOM.docAwardsMoreButton(d).click();

											} catch (Exception e) {

											}

											int servicesCount = 0;

											Thread.sleep(500);

											try {

												servicesCount = PractoPOM.docAwardsCount(d).size();

											} catch (Exception e) {

											}

											// System.out.println("Awards Count
											// : " + servicesCount);

											if (servicesCount >= 1) {

												for (int g = 1; g <= servicesCount; g++) {

													try {

														String individualService = wait.until(ExpectedConditions
																.visibilityOf(PractoPOM.docIndividualAward(d, g)))
																.getText();

														individualService = sanitize(individualService);

														// System.out.println("Service
														// " + g + ":" +
														// individualService);

														Awards.put(String.valueOf(g), individualService);

													} catch (Exception e) {

													}

												}

											}

										} catch (Exception e) {
											// e.printStackTrace();
										}

										// Get Doc Memberships

										try {

											try {

												PractoPOM.docMembershipMoreButton(d).click();

											} catch (Exception e) {

											}

											int servicesCount = 0;

											Thread.sleep(500);

											try {

												servicesCount = PractoPOM.docMembershipCount(d).size();

											} catch (Exception e) {

											}

											// System.out.println("Memberships
											// Count : " + servicesCount);

											if (servicesCount >= 1) {

												for (int g = 1; g <= servicesCount; g++) {

													try {

														String individualService = wait
																.until(ExpectedConditions.visibilityOf(
																		PractoPOM.docIndividualMembership(d, g)))
																.getText();

														individualService = sanitize(individualService);

														// System.out.println("Membership
														// " + g + ":" +
														// individualService);

														Memberships.put(String.valueOf(g), individualService);

													} catch (Exception e) {

													}

												}

											}

										} catch (Exception e) {
											// e.printStackTrace();
										}

										// Doc Registrations
										try {

											int servicesCount = 0;

											Thread.sleep(500);

											try {

												servicesCount = PractoPOM.docRegistrationCount(d).size();

											} catch (Exception e) {

											}

											// System.out.println("Registration
											// Count : " + servicesCount);

											if (servicesCount >= 1) {

												for (int g = 1; g <= servicesCount; g++) {

													try {

														String individualService = wait
																.until(ExpectedConditions.visibilityOf(
																		PractoPOM.docIndividualRegistration(d, g)))
																.getText();

														individualService = sanitize(individualService);

														// System.out
														// .println("Registration
														// " + g + ":" +
														// individualService);

														Registrations.put(String.valueOf(g), individualService);

													} catch (Exception e) {

													}

												}

											}

										} catch (Exception e) {
											// e.printStackTrace();
										}

										// Get Doc Education
										try {

											try {

												wait.until(
														ExpectedConditions.visibilityOf(PractoPOM.docEduMoreButton(d)))
														.click();

											} catch (Exception e) {

											}

											int servicesCount = 0;

											Thread.sleep(500);

											try {

												servicesCount = PractoPOM.docEduCount(d).size();

											} catch (Exception e) {

											}

											System.out.println("Specialization Count : " + servicesCount);

											if (servicesCount >= 1) {

												for (int g = 1; g <= servicesCount; g++) {

													try {

														String individualService = wait
																.until(ExpectedConditions
																		.visibilityOf(PractoPOM.docIndividualEdu(d, g)))
																.getText();

														int indexOfComma = individualService.lastIndexOf(",");

														String year = individualService.substring((indexOfComma + 2),
																individualService.length());

														int indexOfHyphen = individualService.lastIndexOf("-");

														String Ins = individualService.substring((indexOfHyphen + 2),
																indexOfComma);

														String Qual = individualService.substring(0, indexOfHyphen);

														individualService = sanitize(individualService);
														Ins = sanitize(Ins);
														Qual = sanitize(Qual);

														// System.out.println("Service
														// Edu" + g + ":" +
														// individualService);

														JSONObject edu = new JSONObject();

														edu.put("Year", year);
														edu.put("Institute", Ins);
														edu.put("Qualification", Qual);

														Education.put(String.valueOf(g), edu);

													} catch (Exception e) {

													}

												}

											}

										} catch (Exception e) {
											// e.printStackTrace();
										}

										// Get Doc Experience
										try {

											try {

												wait.until(
														ExpectedConditions.visibilityOf(PractoPOM.docExpMoreButton(d)))
														.click();

											} catch (Exception e) {

											}

											int servicesCount = 0;

											Thread.sleep(500);

											try {

												servicesCount = PractoPOM.docExpCount(d).size();

											} catch (Exception e) {

											}

											System.out.println("Specialization Count : " + servicesCount);

											if (servicesCount >= 1) {

												for (int g = 1; g <= servicesCount; g++) {

													try {

														String individualService = wait
																.until(ExpectedConditions
																		.visibilityOf(PractoPOM.docIndividualExp(d, g)))
																.getText();

														individualService = sanitize(individualService);
														
														int indexOfAt = individualService.indexOf(" at ");

														String loc = individualService.substring((indexOfAt + 4),
																individualService.length());

														loc = sanitize(loc);
														
														String years = individualService.substring(0, 15);

														String Role = "";

														if (!years.contains("Present")) {

															years = individualService.substring(0, 12);

															Role = individualService.substring(12, (indexOfAt));

														} else {

															years = individualService.substring(0, 15);

															Role = individualService.substring(15, (indexOfAt));

														}

														Role = sanitize(Role);
														
														System.out.println("Exp Years : " + years);

														individualService = sanitize(individualService);

														// System.out.println("Service
														// Exp" + g + ":" +
														// individualService);

														JSONObject exp = new JSONObject();

														exp.put("Year", years);
														exp.put("Location", loc);
														exp.put("Role", Role);

														Experience.put(String.valueOf(g), exp);

													} catch (Exception e) {

													}

												}

											}

										} catch (Exception e) {
											// e.printStackTrace();
										}

										// Get Tab IDs

										int feedbackTab = 0;
										int QATab = 0;
										int healthFeedTab = 0;

										try {

											for (int v = 0; v <= 10; v++) {

												String tabName = "";
												int tabID = 0;

												try {

													tabName = wait.until(
															ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, v)))
															.getText();

													String tabIDAttribute = wait
															.until(ExpectedConditions
																	.visibilityOf(PractoPOM.docTabs(d, v)))
															.getAttribute("id");

													int indexOfHyphen = tabIDAttribute.lastIndexOf("-");

													String id1 = tabIDAttribute.substring(indexOfHyphen,
															tabIDAttribute.length());

													tabID = Integer.parseInt(id1);

													// System.out.println("Index
													// ID :" +id1);

													// System.out.println("Index
													// ID :" +tabID);

												} catch (Exception e) {

												}

												if (tabName.contains("Feedback")) {

													feedbackTab = tabID;

												}
												if (tabName.contains("Consult")) {

													QATab = tabID;

												}
												if (tabName.contains("Healthfeed")) {

													healthFeedTab = tabID;

												}

											}

										} catch (Exception e) {

										}

										// System.out.println("Feedback Tab : "
										// + feedbackTab);
										// System.out.println("Consult Tab : " +
										// QATab);
										// System.out.println("HealthFeed Tab :
										// " + healthFeedTab);

										try {

											feedbackVotes = wait
													.until(ExpectedConditions
															.visibilityOf(PractoPOM.docTabsCompleteText(d, 2)))
													.getText();

										} catch (Exception e) {
											e.printStackTrace();

										}

										// Click the healthFeed Tab
										try {
											String crURL = d.getCurrentUrl();

											if (crURL.contains("?")) {

												int indexOfParam = crURL.indexOf("?");

												crURL = crURL.substring(0, indexOfParam);

											}

											String healthFeedUrl = crURL + "/healthfeed";
											d.navigate().to(healthFeedUrl);

											Thread.sleep(1500);

											int healthcount = PractoPOM.healthFeedCount(d, 7).size();

											System.out.println("Health Count : " + healthcount);

											healthFeedCount = String.valueOf(PractoPOM.healthFeedCount(d, 7).size());

										} catch (Exception e) {
											e.printStackTrace();
										}

										// Thread.sleep(500);

										System.out.println("Name : " + l + " - " + Name);

										System.out.println("Doctor / Clinic  : " + DocOrClinic);

										System.out.println("Address : " + address);

										System.out.println("Google Address  : " + googleAddress);

										System.out.println("About : " + aboutText);

										System.out.println("rating : " + rating);

										System.out.println("ratingVotes : " + ratingVotes);

										System.out.println("feedbackVotes : " + feedbackVotes);

										System.out.println("healthFeedCount : " + healthFeedCount);

										d.close();

										d.switchTo().window(parentWin);

										Name = sanitize(Name);
										address = sanitize(address);
										aboutText = sanitize(aboutText);
										modeOfPayment = sanitize(modeOfPayment);

										JSONObject ClinicData = new JSONObject();
										ClinicData.put("Name", Name);
										ClinicData.put("URL", doctorNameURL);
										ClinicData.put("DocImageURL", DocImage);
										ClinicData.put("ProfileClaimed", profileClaimed);
										ClinicData.put("Address", address);
										ClinicData.put("GoogleAddress", googleAddress);
										ClinicData.put("LinkedClinics", LinkedDoctors);
										ClinicData.put("Services", Services);
										ClinicData.put("Specializations", Specializations);
										ClinicData.put("About", aboutText);
										ClinicData.put("Rating", rating);
										ClinicData.put("RatingVotes", ratingVotes);
										ClinicData.put("FeedbackVotes", feedbackVotes);
										ClinicData.put("HealthFeedCount", healthFeedCount);

										// For Hospital
										ClinicData.put("Awards", Awards);
										ClinicData.put("Memberships", Memberships);
										ClinicData.put("Registrations", Registrations);
										ClinicData.put("Education", Education);
										ClinicData.put("Experience", Experience);

										String jsonData = ClinicData.toString();

										System.out.println("JSON Data : " + jsonData);

										String remarks = String.valueOf(k);

										String isPrime =  "N";
										try {

											System.out.println("Going to insert data in table");
											dbm.SetPractoLabData(docCity, Name, jsonData, remarks, doctorNameURL, rating, ratingVotes, isPrime, tableName);

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

								}

							}

						} catch (Exception e) {

							e.printStackTrace();
						}
						

						try {

							ArrayList<String> handles2 = new ArrayList<String>(d.getWindowHandles());

							System.out.println("Handles 2 Size : " + handles2.size());

							if (handles2.size() > 1) {

								for (int h = 1; h < handles2.size(); h++) {

									d.switchTo().window(handles2.get(h));
									d.close();

								}

							}

							d.switchTo().window(handles2.get(0));

						} catch (Exception e) {

						}
	

						Thread.sleep(2000);

					}

				} catch (

				Exception e) {
					e.printStackTrace();
				}

				d.navigate().to(baseURL);
				Thread.sleep(3000);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String sanitize(String inp) {

		String result = inp.replace("'", "");

		return result;
	}

	public String GetFileUrl(String FileName, String pfilePath, String appNo, String type)
			throws IOException, URISyntaxException {
		String content = "";
		JSONObject docObj = new JSONObject();
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			StringBuilder payLoad = new StringBuilder("{").append("\"ApplicationNo\":\"").append(appNo).append("\"}");
			String encoSt = URLEncoder.encode(payLoad.toString(), "UTF-8");
			String url = "http://api.policybazaar.com/cs/repo/uploadInsurerPortalDoc?payloadJSON=" + encoSt;
			HttpPost post = new HttpPost(url);

			File file = new File(pfilePath);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			if (type.equalsIgnoreCase("Image")) {
				builder.addBinaryBody("file", file, ContentType.create("application/jpg"), pfilePath);
			} else {
				builder.addBinaryBody("file", file, ContentType.create("application/pdf"), pfilePath);
			}
			HttpEntity entity = builder.build();
			post.setEntity(entity);

			HttpResponse response = client.execute(post);
			content = EntityUtils.toString(response.getEntity());
			docObj = new JSONObject(content);
			System.out.println("output URL:" + content.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return docObj.getString("docUrl");
	}

}
