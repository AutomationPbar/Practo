package core;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import pom.PractoPOM;
import pom.ProcessURLPOM;
import utilities.DBManager;
import utilities.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class practo {
	
	static String readExcel = "c:\\eclipse\\PractoURL.xls";
	static String inputsheetname = "Sheet1";

	static String userAgentExcel = "c:\\eclipse\\UserAgent.xls";

	String pageURL1 = "https://www.practo.com/";
	String pageURL2 = "/doctors";

	static String LiveDB_Path = "jdbc:sqlserver://automation-data.cma4hvr5eoya.ap-south-1.rds.amazonaws.com:1433;DatabaseName=PBCroma";
	private static String Liveusename = "admin";
	private static String Livepassword = "DBauto!#$asd";

	static DBManager dbm = new DBManager();

	String proxyAPIURL = "https://www.proxy-list.download/api/v1/get?type=http&anon=elite";

	String apiURL = "http://qamatrixapi.policybazaar.com/Communication/Communication.svc/send";

	String MastertableName = "Automation.DocData";
	String ClinicstableName = "Automation.LinkedClinics";
	String AwardstableName = "Automation.DocAwards";
	String EducationtableName = "Automation.DocEducation";
	String SpecializationtableName = "Automation.DocSpecialization";

	String downloadLocation = "c:\\eclipse\\MaxBupa\\";

	String Source = "Practo";
	String IsPrimeGlobal = "";
	

	public static WebDriver d;

	static WebDriverWait wait;

	ArrayList<String> proxies = new ArrayList<String>();
	ArrayList<String> userAgents = new ArrayList<String>();
	
//	int startRow = 1;
//	String lastProcessedDoc = "abc";
   
   @BeforeTest
     public static void setup() throws Exception{
	   FirefoxOptions options = new FirefoxOptions();
      
	   /*
       File torProfileDir = new File(
               "...\\Tor Browser\\Data\\Browser\\profile.default");
       FirefoxBinary binary = new FirefoxBinary(new File(
               "...\\Tor Browser\\Start Tor Browser.exe"));
       FirefoxProfile torProfile = new FirefoxProfile(torProfileDir);
       torProfile.setPreference("webdriver.load.strategy", "unstable");

       try {
           binary.startProfile(torProfile, torProfileDir, "");
       } catch (IOException e) {
           e.printStackTrace();
       }
        String profilePath = "C:\\Tor\\Browser\\TorBrowser\\Data\\Browser\\profile.default";
       */
       
       FirefoxProfile profile = new FirefoxProfile();
       
       profile.setPreference("webdriver.load.strategy", "unstable");
       profile.setPreference("network.proxy.type", 1);
       profile.setPreference("network.proxy.socks", "127.0.0.1");
       profile.setPreference("network.proxy.socks_port", 9050);
      
       options.setProfile(profile);
       options.setBinary("C:\\Tor\\Browser\\firefox.exe");
       System.setProperty("webdriver.gecko.driver", "C:\\Tor\\geckodriver.exe");
           
       d = new FirefoxDriver(options);
       
       wait = new WebDriverWait(d, 5);
       
       dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);
     //  d.manage().window().maximize();
                
   }
   
   
 	@Test(priority = 1)
 	public void searchData() {

 		try {
 		
 			try {

 				ExcelUtils.SetExcelFile(readExcel, inputsheetname);

 				int rowCount = ExcelUtils.GetRowCount();

 				System.out.println("Row Count = " + rowCount);

 				HashMap<String, String> Excelurls = new HashMap<String, String>();
 			
 				for (int o = 8001; o <= 10000; o++) {

 					Excelurls.put(ExcelUtils.GetCellData(o, 0), ExcelUtils.GetCellData(o, 1));

 				}

 				System.out.println("Array Size : " + Excelurls.size());

 				for (String key : Excelurls.keySet()) {

 					try {
 						
 						// random 0 inclusive, 10 exclusive
 						int rndPracto = 0;
 						
 						System.out.println("Going for DocURL :" +key);
 												
 						int i = 1;

 						String DocURL = key;

 						if (DocURL.toLowerCase().contains("clinic") || DocURL.toLowerCase().contains("hospital")) {

 							continue;
 						}

 						String docCity = Excelurls.get(key);

 						System.out.println(DocURL);
 						
 					//	try{
 					//	d.get("https://iplocation.com");
 				    	
 				    //	System.out.println("IP Captured : "+d.findElement(By.xpath("/html/body/div[1]/div[2]/div[3]/div/table/tbody/tr[1]/td/b")).getText());
 						 	
 					//	}catch(Exception e){
 							
 					//	}
 						
 						d.get(DocURL);

 						//d.navigate().to(DocURL);

 						Thread.sleep(3000);

 						try {

 							try {
 								handleLoginForm();
 							} catch (Exception e) {

 							}

 							String doctorNameURL = d.getCurrentUrl();

 							if (doctorNameURL.contains("?")) {

 								int indexOfQue = doctorNameURL.indexOf("?");

 								doctorNameURL = doctorNameURL.substring(0, indexOfQue);

 								System.out.println("doctorNameURL 2: " + doctorNameURL);

 							}

 							String DocOrClinic = "";
 							String DocImage = "";
 							String Name = "";
 							String address = "";
 							String docExperience = "";
 							String googleAddress = "";
 							String rating = "";
 							String ratingVotes = "";
 							String feedbackVotes = "";
 							String healthFeedCount = "";
 							String aboutText = "";
 							String modeOfPayment = "";
 							String Specialization = "";
 							String Service = "";
 							String Procedure = "";

 							String remarks = String.valueOf(i);

 							// Clinic / Hospital details
 							 							
 							Name = wait.until(ExpectedConditions.visibilityOf(PractoPOM.doctorName(d))).getText();
 							
 							System.out.println("name of doctor is " + Name);

 							int indexofDr = Name.indexOf("Dr.");

 							if (indexofDr != (-1)) {

 								Name = Name.substring((indexofDr + 4), Name.length());

 							}

 							String profileClaimed = "";

 							// Randomize
 							
 							rndPracto = getRandomVal(10, 1);
 							
 							if(rndPracto == 1 || rndPracto == 4 || rndPracto == 7 || rndPracto == 9){
 								
 								randomAction1();
 												
 							}
 							
 							int profileCm = 0;
 							// Thread.sleep(2000);
 							try {

 								Actions act = new Actions(d);

 								act.clickAndHold(PractoPOM.doctorProfileClaimed(d)).build().perform();

 								// Thread.sleep(2000);

 								profileClaimed = PractoPOM.doctorProfileClaimedText(d).getText();

 								System.out.println("Profile Claimed : " + profileClaimed);
 								
 								profileCm = 1;

 							} catch (Exception e) {
 								//e.printStackTrace();
 							}
 							
 							if(profileCm == 0){
 							try {

 								Actions act = new Actions(d);

 								act.clickAndHold(PractoPOM.doctorProfileClaimed2(d)).build().perform();

 								// Thread.sleep(2000);

 								profileClaimed = PractoPOM.doctorProfileClaimedText2(d).getText();

 								System.out.println("Profile Claimed : " + profileClaimed);
 							
 							} catch (Exception e) {
 								//e.printStackTrace();
 							}
 							
 						}

 							try {

 								DocImage = wait.until(ExpectedConditions.visibilityOf(PractoPOM.doctorImage(d)))
 										.getAttribute("src");

 								int indexOfSlash = DocImage.lastIndexOf("/thumbnail");

 								DocImage = DocImage.substring(0, indexOfSlash);

 								System.out.println("Doc Image URL : " + DocImage);

 							} catch (Exception e) {

 							}

 							try {

 								String docAddress = wait
 										.until(ExpectedConditions.visibilityOf(PractoPOM.docAddress(d))).getText();

 								address = sanitize(docAddress);

 							} catch (Exception e) {

 							}

 							try {

 								docExperience = wait.until(ExpectedConditions.visibilityOf(PractoPOM.docExperience(d)))
 										.getText();

 								docExperience = sanitize(docExperience);

 							} catch (Exception e) {

 							}

 							DocOrClinic = "Doctor";

 							Thread.sleep(500);

 							try {

 								googleAddress = wait
 										.until(ExpectedConditions.visibilityOf(PractoPOM.docGoogleLocation(d)))
 										.getAttribute("href");

 								googleAddress = sanitize(googleAddress);

 							} catch (Exception e) {

 							}

 							Thread.sleep(500);

 							int rat = 0;

 							try {

 								rating = wait.until(ExpectedConditions.visibilityOf(PractoPOM.docRating(d))).getText();

 								rat = 1;

 							} catch (Exception e) {
 								// e.printStackTrace();

 							}

 							if (rat == 0) {

 								try {
 									rating = wait.until(ExpectedConditions.visibilityOf(PractoPOM.docRating2(d)))
 											.getText();

 								} catch (Exception e) {
 									// e.printStackTrace();

 								}

 							}

 							int rat1 = 0;

 							try {

 								ratingVotes = wait.until(ExpectedConditions.visibilityOf(PractoPOM.docRatingCount(d)))
 										.getText();
 								rat1 = 1;

 							} catch (Exception e) {
 								// e.printStackTrace();

 							}

 							if (rat1 == 0) {
 								try {
 									
 									ratingVotes = wait
 											.until(ExpectedConditions.visibilityOf(PractoPOM.docRatingCount2(d)))
 											.getText();

 								} catch (Exception e) {
 									// e.printStackTrace();

 								}
 							}

 							if (rating.contains("Medical")) {
 								rating = null;
 								ratingVotes = null;
 							}

 							// Randomize
 														
 							if(rndPracto == 2 || rndPracto == 5 || rndPracto == 8 || rndPracto == 10){
 								
 								randomAction4();
 												
 							}
 							
 							try {

 								try {

 									wait.until(ExpectedConditions.visibilityOf(PractoPOM.aboutdoctorMoreButton(d)))
 											.click();

 								} catch (Exception e) {

 								}

 								aboutText = wait.until(ExpectedConditions.visibilityOf(PractoPOM.aboutDoctor(d)))
 										.getText();

 								aboutText = sanitize(aboutText);

 							} catch (Exception e) {

 							}

 							// Get Doc Procedures
 							
 							try{

								try {

									wait.until(ExpectedConditions.visibilityOf(PractoPOM.docProceduresDropButton(d)))
											.click();

								} catch (Exception e) {

								}

								int proceduresCount = 0;

								Thread.sleep(500);

								try {

									proceduresCount = PractoPOM.docProceduresCount(d).size();

								} catch (Exception e) {

								}

								System.out.println("Procedures Count : " + proceduresCount);

								if (proceduresCount >= 1) {

									for (int g = 1; g <= proceduresCount; g++) {

										try {

											String individualProcedure = wait
													.until(ExpectedConditions
															.visibilityOf(PractoPOM.docIndividualProcedure(d, g)))
													.getText();

											individualProcedure = sanitize(individualProcedure);

											System.out.println("Procedure " + g + ":" +	 individualProcedure);

											if (Procedure.isEmpty() || Procedure.equals(null)) {

												Procedure = Procedure + individualProcedure;

											} else {
												Procedure = Procedure + " , " + individualProcedure;
											}

										} catch (Exception e) {
											e.printStackTrace();
										}

									}

								}

							} catch (Exception e) {
								 e.printStackTrace();
							}
 							
 							
 							// Get doc Services

 							// Randomize
 							
 							if(rndPracto == 3 || rndPracto == 6){
 								
 								randomAction2();
 												
 							}
 							
 							try {

 								try {

 									wait.until(ExpectedConditions.visibilityOf(PractoPOM.docServicesMoreButton(d)))
 											.click();

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
 													.until(ExpectedConditions
 															.visibilityOf(PractoPOM.docIndividualService(d, g)))
 													.getText();

 											individualService = sanitize(individualService);

 											// System.out.println("Service
 											// " + g + ":" +
 											// individualService);

 											if (Service.isEmpty() || Service.equals(null)) {

 												Service = Service + individualService;

 											} else {
 												Service = Service + " , " + individualService;
 											}

 										} catch (Exception e) {

 										}

 									}

 								}

 							} catch (Exception e) {
 								// e.printStackTrace();
 							}

 							Thread.sleep(500);

 							// Get Doc Specializations

 							// Randomize
 							
 							if(rndPracto == 4 || rndPracto == 6 || rndPracto == 9 || rndPracto == 10){
 								
 								randomAction3();
 												
 							}
 							
 							ArrayList<String> specializationList = new ArrayList<String>();
 							try {

 								try {

 									wait.until(ExpectedConditions.visibilityOf(PractoPOM.docSpecMoreButton(d)))
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

 											String individualService = wait
 													.until(ExpectedConditions
 															.visibilityOf(PractoPOM.docIndividualSpec(d, g)))
 													.getText();

 											individualService = sanitize(individualService);

 											specializationList.add(individualService);

 											// System.out.println("Specs
 											// " + g + ":" +
 											// individualService);

 											if (Specialization.isEmpty() || Specialization.equals(null)) {

 												Specialization = Specialization + individualService;

 											} else {
 												Specialization = Specialization + " , " + individualService;
 											}

 										} catch (Exception e) {

 										}

 									}

 									// Insert Specialization data

 									for (int c = 0; c < specializationList.size(); c++) {

 										try {

 											dbm.SetSpecializationData(doctorNameURL, specializationList.get(c), remarks,
 													Source, SpecializationtableName);

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

 							Name = sanitize(Name);
 							address = sanitize(address);
 							aboutText = sanitize(aboutText);
 							modeOfPayment = sanitize(modeOfPayment);

 							// Get Doc Clinic Count

 							try {
 								handleLoginForm();
 							} catch (Exception e) {

 							}

 							try {

 								int docCountinClinic = 0;
 								
 								try {

 									ProcessURLPOM.docClinicMoreButton(d).click();

 								} catch (Exception e) {
 									//e.printStackTrace();
 								}

 								Thread.sleep(2000);
 								
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

 									if (extraframe.contains("clinics for") || extraframe.contains("clinic for")) {
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

 												clinicDocName = wait
 														.until(ExpectedConditions
 																.visibilityOf(ProcessURLPOM.docClinicName2(d, g)))
 														.getText();

 												docnameFound = 1;

 											} catch (Exception e) {
 												//e.printStackTrace();
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
 													e.printStackTrace();
 												}

 											}

 											if (docnameFound == 0) {

 												try {
 													clinicDocURL = wait
 															.until(ExpectedConditions
 																	.visibilityOf(ProcessURLPOM.docClinicName(d, g)))
 															.getAttribute("href");

 													clinicDocName = wait
 															.until(ExpectedConditions
 																	.visibilityOf(ProcessURLPOM.docClinicName(d, g)))
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

 											// Get Doc Address and
 											// Google Address

 											String ClinicAdd = "";

 											int clinicaddfound = 0;
 											try {

 												ClinicAdd = wait
 														.until(ExpectedConditions
 																.visibilityOf(ProcessURLPOM.docClinicAdd(d, g)))
 														.getText();

 												clinicaddfound = 1;

 											} catch (Exception e) {
 												// e.printStackTrace();
 											}

 											if (clinicaddfound == 0) {

 												try {

 													ClinicAdd = wait
 															.until(ExpectedConditions
 																	.visibilityOf(ProcessURLPOM.docClinicAdd2(d, g)))
 															.getText();

 													//System.out.println("Going for fee 4");
 												} catch (Exception e) {
 													//e.printStackTrace();
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
 												// e.printStackTrace();
 											}

 											if (clinicGaddfound == 0) {

 												try {

 													ClinicGAdd = wait
 															.until(ExpectedConditions.visibilityOf(
 																	ProcessURLPOM.docClinicGoogleAdd2(d, g)))
 															.getAttribute("href");

 												} catch (Exception e) {
 													// e.printStackTrace();
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

 												// System.out.println("Going
 												// for fee");
 												DocFeeInHospital = wait
 														.until(ExpectedConditions
 																.visibilityOf(ProcessURLPOM.docHospitalFee3(d, g)))
 														.getText();

 												docFeefound = 1;

 												// System.out.println("Going
 												// for fee
 												// 2");
 											} catch (Exception e) {
 												// e.printStackTrace();
 											}

 											if (docFeefound == 0) {

 												try {

 													// System.out.println("Going
 													// for fee
 													// 3");
 													DocFeeInHospital = wait
 															.until(ExpectedConditions
 																	.visibilityOf(ProcessURLPOM.docHospitalFee2(d, g)))
 															.getText();

 													docFeefound = 1;
 													// System.out.println("Going
 													// for fee
 													// 4");
 												} catch (Exception e) {
 													// e.printStackTrace();
 												}

 											}

 											if (docFeefound == 0) {

 												try {

 													// System.out.println("Going
 													// for fee
 													// 3");
 													DocFeeInHospital = wait
 															.until(ExpectedConditions
 																	.visibilityOf(ProcessURLPOM.docHospitalFee(d, g)))
 															.getText();

 													docFeefound = 1;
 													// System.out.println("Going
 													// for fee
 													// 4");
 												} catch (Exception e) {
 													// e.printStackTrace();
 												}

 											}

 											if (docFeefound == 0) {

 												try {

 													// System.out.println("Going
 													// for fee
 													// 3");
 													DocFeeInHospital = wait
 															.until(ExpectedConditions
 																	.visibilityOf(ProcessURLPOM.docHospitalFee4(d, g)))
 															.getText();

 												} catch (Exception e) {
 													//e.printStackTrace();
 												}

 											}

 											try {
 												DocFeeInHospital = sanitize(DocFeeInHospital);
 												DocFeeInHospital = sanitizeRemoveSymbol(DocFeeInHospital);
 											} catch (Exception e) {
 												e.printStackTrace();
 											}

 											System.out.println("Doc Fee : " + DocFeeInHospital);

 											// Get Doc Discounted Fee and discount
 											
 											String DocDiscountedFee = "";
 											String DocFeeDiscount = "";
 											
 											try {

													System.out.println("Going for discounted Fee");
													
													DocDiscountedFee = wait
															.until(ExpectedConditions
																	.visibilityOf(ProcessURLPOM.docHospitalDiscountedFee(d, g)))
															.getText();
													
													DocFeeDiscount = wait
															.until(ExpectedConditions
																	.visibilityOf(ProcessURLPOM.docHospitalDiscount(d, g)))
															.getText();

												} catch (Exception e) {
													//e.printStackTrace();
												}
 											
 											try {
 												DocDiscountedFee = sanitize(DocDiscountedFee);
 												DocFeeDiscount = sanitize(DocFeeDiscount);
 												DocDiscountedFee = sanitizeRemoveSymbol(DocDiscountedFee);
 												if(!DocFeeDiscount.contains("%")){
 												DocFeeDiscount = sanitizeRemoveSymbol(DocFeeDiscount);
 												}
 											} catch (Exception e) {
 												//e.printStackTrace();
 											}
 											
 											System.out.println("Doc Discounted Fee : " + DocDiscountedFee);
 											System.out.println("Doc Discount : " + DocFeeDiscount);
 											
 											// Get Doc Prime Status
 											
 											String primeText = "";
 											String IsPrime = "N"; 											
 																				
												try {

													// System.out.println("Going
													// for fee
													// 3");
													primeText = wait
															.until(ExpectedConditions
																	.visibilityOf(ProcessURLPOM.docHospitalIsPrime2(d, g)))
															.getText();

													IsPrime = "Y";
													
												} catch (Exception e) {
													// e.printStackTrace();
												}

											

											if (IsPrime.equals("N")) {

												try {
													
													primeText = wait
															.until(ExpectedConditions
																	.visibilityOf(ProcessURLPOM.docHospitalIsPrime1(d, g)))
															.getText();
													
													IsPrime = "Y";

												} catch (Exception e) {
													//e.printStackTrace();
												}

											}

											System.out.println("IS Prime : " + 	IsPrime);
 											
 											// Get Linked
 											// Doc/Clinic
 											// Booking

 											WebElement isBookApp1 = null;
 											int gotbookapp = 0;

 											try {

 												isBookApp1 = wait.until(ExpectedConditions
 														.visibilityOf(ProcessURLPOM.docBookingText(d, g)));

 												gotbookapp = 1;

 											} catch (Exception e) {

 												// e.printStackTrace();
 											}

 											if (gotbookapp == 0) {

 												try {
 													isBookApp1 = wait.until(ExpectedConditions
 															.visibilityOf(ProcessURLPOM.docBookingText2(d, g)));

 													gotbookapp = 1;

 												} catch (Exception e) {

 													// e.printStackTrace();
 												}
 											}

 											if (gotbookapp == 0) {

 												try {
 													isBookApp1 = wait.until(ExpectedConditions
 															.visibilityOf(ProcessURLPOM.docBookingText3(d, g)));

 													gotbookapp = 1;

 												} catch (Exception e) {

 													// e.printStackTrace();
 												}
 											}

 											if (gotbookapp == 0) {

 												try {
 													isBookApp1 = wait.until(ExpectedConditions
 															.visibilityOf(ProcessURLPOM.docBookingText4(d, g)));

 												} catch (Exception e) {

 													// e.printStackTrace();
 												}
 											}

 											String isBookApp = "";

 											try {

 												isBookApp = isBookApp1.getText();

 											} catch (Exception e) {

 											}

 											// Get Call Now Mobile No.

 											String clinicContact = "";

 											try {

 												if (isBookApp.contains("Call Now")) {

 													isBookApp1.click();

 													Thread.sleep(500);

 													int gotClinicNo = 0;

 													try {

 														clinicContact = wait
 																.until(ExpectedConditions.visibilityOf(
 																		ProcessURLPOM.docClinicNumber(d, g)))
 																.getText();

 														gotClinicNo = 1;

 													} catch (Exception e) {

 													}

 													if (gotClinicNo == 0) {

 														try {

 															clinicContact = wait
 																	.until(ExpectedConditions.visibilityOf(
 																			ProcessURLPOM.docClinicNumber2(d, g)))
 																	.getText();

 															gotClinicNo = 1;

 														} catch (Exception e) {

 														}

 													}

 													System.out.println("Clinic Contact No : " + clinicContact);
 												}

 											} catch (Exception e) {

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

 														timeCount = ProcessURLPOM.docHospitalTimingsCount3(d, g)
 																.size();

 													} catch (Exception e) {
 														e.printStackTrace();
 													}

 												}

 												if (timeCount == 0) {

 													try {

 														timeCount = ProcessURLPOM.docHospitalTimingsCount4(d, g)
 																.size();

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

 																day = wait.until(ExpectedConditions.visibilityOf(
 																		ProcessURLPOM.docHospitalTimingsDay3(d, g, t)))
 																		.getText();

 																getday = 1;

 																System.out.println("Day : " + day);

 															} catch (Exception e) {

 															}

 															if (getday == 0) {

 																try {

 																	day = wait.until(ExpectedConditions
 																			.visibilityOf(ProcessURLPOM
 																					.docHospitalTimingsDay2(d, g, t)))
 																			.getText();

 																	getday = 1;
 																	System.out.println("Day : " + day);

 																} catch (Exception e) {

 																}

 															}

 															if (getday == 0) {

 																try {

 																	day = wait.until(ExpectedConditions
 																			.visibilityOf(ProcessURLPOM
 																					.docHospitalTimingsDay4(d, g, t)))
 																			.getText();

 																	getday = 1;

 																	System.out.println("Day : " + day);

 																} catch (Exception e) {

 																}

 															}

 															if (getday == 0) {

 																try {

 																	day = wait.until(ExpectedConditions
 																			.visibilityOf(ProcessURLPOM
 																					.docHospitalTimingsDay(d, g, t)))
 																			.getText();

 																	getday = 1;

 																	System.out.println("Day : " + day);

 																} catch (Exception e) {

 																}

 															}

 															if (getday == 0) {

 																try {

 																	day = wait.until(ExpectedConditions
 																			.visibilityOf(ProcessURLPOM
 																					.docHospitalTimingsDay5(d, g, t)))
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
 																			.docHospitalPerDayTimingsCount4(d, g, t)
 																			.size();

 																	gottimingscount = 1;

 																} catch (Exception e) {
 																	e.printStackTrace();
 																}

 															}

 															if (perDayTimeCount == 0) {

 																try {

 																	perDayTimeCount = ProcessURLPOM
 																			.docHospitalPerDayTimingsCount2(d, g, t)
 																			.size();

 																	gottimingscount = 1;

 																} catch (Exception e) {
 																	e.printStackTrace();
 																}

 															}

 															if (perDayTimeCount == 0) {

 																try {

 																	perDayTimeCount = ProcessURLPOM
 																			.docHospitalPerDayTimingsCount(d, g, t)
 																			.size();

 																} catch (Exception e) {
 																}

 															}

 															System.out
 																	.println("Per Day time count : " + perDayTimeCount);

 															String time = "";

 															if (perDayTimeCount >= 1) {

 																for (int c = 1; c <= perDayTimeCount; c++) {

 																	int gotindividualTime = 0;

 																	try {

 																		time = wait
 																				.until(ExpectedConditions
 																						.visibilityOf(ProcessURLPOM
 																								.docHospitalTimingsTime1(
 																										d, g, t, c)))
 																				.getText();

 																		gotindividualTime = 1;

 																	} catch (Exception e) {
 																	}

 																	if (gotindividualTime == 0) {

 																		try {

 																			time = wait
 																					.until(ExpectedConditions
 																							.visibilityOf(ProcessURLPOM
 																									.docHospitalTimingsTime2(
 																											d, g, t, c)))
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
 																									.docHospitalTimingsTime3(
 																											d, g, t, c)))
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
 																									.docHospitalTimingsTime4(
 																											d, g, t, c)))
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
 																									.docHospitalTimingsTime5(
 																											d, g, t, c)))
 																					.getText();

 																		} catch (Exception e) {
 																			e.printStackTrace();
 																		}

 																	}

 																	try {

 																		System.out.println(
 																				"Going to insert data in table");

 																		dbm.SetLinkedClinicsData(doctorNameURL,
 																				clinicDocName, clinicDocURL, day, time,
 																				DocFeeInHospital, remarks, ClinicAdd,
 																				ClinicGAdd, clinicContact, IsPrime, DocDiscountedFee, DocFeeDiscount, Source,
 																				ClinicstableName);

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

 							}

 							Thread.sleep(500);

 							// Get Doc Awards
 							
 							// Randomize
 							
 							rndPracto = getRandomVal(10, 1);
 							
 							if(rndPracto == 2 || rndPracto == 3 || rndPracto == 4 || rndPracto == 10){
 								
 								randomAction6();
 												
 							}

 							ArrayList<String> awardsList = new ArrayList<String>();

 							try {
 								handleLoginForm();
 							} catch (Exception e) {

 							}

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

 											String individualService = wait
 													.until(ExpectedConditions
 															.visibilityOf(PractoPOM.docIndividualAward(d, g)))
 													.getText();

 											individualService = sanitize(individualService);

 											// System.out.println("Service
 											// " + g + ":" +
 											// individualService);

 											awardsList.add(individualService);

 										} catch (Exception e) {

 										}

 									}

 								}

 							} catch (Exception e) {
 								// e.printStackTrace();
 							}

 							// Get Doc Memberships

 							// Randomize
 							
 							rndPracto = getRandomVal(10, 1);
 							
 							if(rndPracto == 2 || rndPracto == 3 || rndPracto == 4 || rndPracto == 10){
 								
 								randomAction7();
 												
 							}
 							
 							ArrayList<String> membershipsList = new ArrayList<String>();

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
 													.until(ExpectedConditions
 															.visibilityOf(PractoPOM.docIndividualMembership(d, g)))
 													.getText();

 											individualService = sanitize(individualService);

 											// System.out.println("Membership
 											// " + g + ":" +
 											// individualService);

 											membershipsList.add(individualService);

 										} catch (Exception e) {

 										}

 									}

 								}

 							} catch (Exception e) {
 								// e.printStackTrace();
 							}

 							// Doc Registrations

 							ArrayList<String> registrationsList = new ArrayList<String>();

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
 													.until(ExpectedConditions
 															.visibilityOf(PractoPOM.docIndividualRegistration(d, g)))
 													.getText();

 											individualService = sanitize(individualService);

 											// System.out
 											// .println("Registration
 											// " + g + ":" +
 											// individualService);

 											registrationsList.add(individualService);

 										} catch (Exception e) {

 										}

 									}

 								}

 							} catch (Exception e) {
 								// e.printStackTrace();
 							}

 							// Insert Awards Data

 							if (registrationsList.size() >= 1) {

 								for (int p = 0; p < registrationsList.size(); p++) {

 									try {
 										if (membershipsList.size() >= 1) {

 											for (int q = 0; q < membershipsList.size(); q++) {

 												try {

 													if (awardsList.size() >= 1) {

 														for (int r = 0; r < awardsList.size(); r++) {

 															try {
 																System.out.println("Going for unique doc count : " + i);

 																System.out.println(
 																		"membership : " + membershipsList.get(q));

 																System.out
 																		.println("award : " + registrationsList.get(p));

 																System.out.println("award : " + awardsList.get(r));

 																try {

 																	dbm.SetAwardsData(doctorNameURL, awardsList.get(r),
 																			membershipsList.get(q),
 																			registrationsList.get(p), Source,
 																			AwardstableName);

 																} catch (SQLServerException e) {

 																	dbm.DBConnection(LiveDB_Path, Liveusename,
 																			Livepassword);

 																	// e.printStackTrace();

 																} catch (Exception e) {

 																	dbm.DBConnection(LiveDB_Path, Liveusename,
 																			Livepassword);

 																	e.printStackTrace();

 																}

 															} catch (Exception e) {

 															}
 														}

 													} else {

 														try {

 															dbm.SetAwardsData(doctorNameURL, null,
 																	membershipsList.get(q), registrationsList.get(p),
 																	Source, AwardstableName);

 														} catch (SQLServerException e) {

 															dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 															// e.printStackTrace();

 														} catch (Exception e) {

 															dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 															e.printStackTrace();

 														}

 													}

 												} catch (Exception e) {

 												}
 											}

 										} else {

 											if (awardsList.size() >= 1) {

 												for (int r = 0; r < awardsList.size(); r++) {

 													try {
 														System.out.println("award : " + registrationsList.get(p));

 														System.out.println("award : " + awardsList.get(r));

 														try {

 															dbm.SetAwardsData(doctorNameURL, awardsList.get(r), null,
 																	registrationsList.get(p), Source, AwardstableName);

 														} catch (SQLServerException e) {

 															dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 															// e.printStackTrace();

 														} catch (Exception e) {

 															dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 															e.printStackTrace();

 														}

 													} catch (Exception e) {

 													}
 												}

 											}

 										}
 									} catch (Exception e) {

 									}
 								}

 							} else {

 								if (membershipsList.size() >= 1) {

 									for (int q = 0; q < membershipsList.size(); q++) {

 										try {
 											if (awardsList.size() >= 1) {

 												for (int r = 0; r < awardsList.size(); r++) {
 													try {
 														System.out.println("Going for unique doc count : " + i);

 														System.out.println("membership : " + membershipsList.get(q));

 														System.out.println("award : " + awardsList.get(r));

 														try {

 															dbm.SetAwardsData(doctorNameURL, awardsList.get(r),
 																	membershipsList.get(q), null, Source,
 																	AwardstableName);

 														} catch (SQLServerException e) {

 															dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 															// e.printStackTrace();

 														} catch (Exception e) {

 															dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 															e.printStackTrace();

 														}

 													} catch (Exception e) {

 													}
 												}

 											} else {

 												try {

 													dbm.SetAwardsData(doctorNameURL, null, membershipsList.get(q), null,
 															Source, AwardstableName);

 												} catch (SQLServerException e) {

 													dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 													// e.printStackTrace();

 												} catch (Exception e) {

 													dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 													e.printStackTrace();

 												}

 											}
 										} catch (Exception e) {

 										}
 									}

 								} else {

 									if (awardsList.size() >= 1) {

 										for (int r = 0; r < awardsList.size(); r++) {

 											try {

 												dbm.SetAwardsData(doctorNameURL, awardsList.get(r), null, null, Source,
 														AwardstableName);

 											} catch (SQLServerException e) {

 												dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 												// e.printStackTrace();

 											} catch (Exception e) {

 												dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 												e.printStackTrace();

 											}

 										}

 									}

 								}

 							}

 							// Get Doc Education

 							// Randomize
 							
 							rndPracto = getRandomVal(10, 1);
 							
 							if(rndPracto == 2 || rndPracto == 3 || rndPracto == 4 || rndPracto == 10){
 								
 								randomAction1();
 												
 							}
 							
 							ArrayList<String> docEducation = new ArrayList<String>();

 							try {
 								handleLoginForm();
 							} catch (Exception e) {

 							}

 							try {

 								try {

 									wait.until(ExpectedConditions.visibilityOf(PractoPOM.docEduMoreButton(d))).click();

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

 											String individualService = wait.until(
 													ExpectedConditions.visibilityOf(PractoPOM.docIndividualEdu(d, g)))
 													.getText();

 											individualService = sanitize(individualService);

 											docEducation.add(individualService);

 										} catch (Exception e) {

 										}

 									}

 								}

 							} catch (Exception e) {
 								// e.printStackTrace();
 							}

 							// Get Doc Experience

 							// Randomize
 							
 							rndPracto = getRandomVal(10, 1);
 							
 							if(rndPracto == 2 || rndPracto == 3 || rndPracto == 4 || rndPracto == 10){
 								
 								randomAction1();
 												
 							}
 							
 							ArrayList<String> docExp = new ArrayList<String>();

 							try {

 								try {

 									wait.until(ExpectedConditions.visibilityOf(PractoPOM.docExpMoreButton(d))).click();

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

 											String individualService = wait.until(
 													ExpectedConditions.visibilityOf(PractoPOM.docIndividualExp(d, g)))
 													.getText();

 											individualService = sanitize(individualService);

 											docExp.add(individualService);

 										} catch (Exception e) {

 										}

 									}

 								}

 							} catch (Exception e) {
 								// e.printStackTrace();
 							}

 							// Insert Doc Edu and Exp

 							for (int s = 0; s < docEducation.size(); s++) {

 								try {
 									String individualService = docEducation.get(s);

 									int indexOfComma = individualService.lastIndexOf(",");

 									String Eduyear = individualService.substring((indexOfComma + 2),
 											individualService.length());

 									int indexOfHyphen = individualService.lastIndexOf("-");

 									String EduIns = individualService.substring((indexOfHyphen + 2), indexOfComma);

 									String EduQual = individualService.substring(0, indexOfHyphen);

 									EduIns = sanitize(EduIns);
 									EduQual = sanitize(EduQual);

 									if (docExp.size() >= 1) {

 										for (int t = 0; t < docExp.size(); t++) {

 											try {
 												String individualService1 = docExp.get(t);

 												int indexOfAt = individualService1.indexOf(" at ");

 												String Exploc = individualService1.substring((indexOfAt + 4),
 														individualService1.length());

 												Exploc = sanitize(Exploc);

 												String Expyears = individualService1.substring(0, 15);

 												String ExpRole = "";

 												if (!Expyears.contains("Present")) {

 													Expyears = individualService1.substring(0, 12);

 													ExpRole = individualService1.substring(12, (indexOfAt));

 												} else {

 													Expyears = individualService1.substring(0, 15);

 													ExpRole = individualService1.substring(15, (indexOfAt));

 												}

 												ExpRole = sanitize(ExpRole);

 												System.out.println("Exp Years : " + Expyears);

 												System.out.println("Going for unique doc count : " + i);

 												System.out.println("Experience : " + docExp.get(t));

 												System.out.println("Education : " + docEducation.get(s));

 												try {

 													dbm.SetEducationData(doctorNameURL, Eduyear, EduIns, EduQual,
 															Expyears, Exploc, ExpRole, remarks, Source,
 															EducationtableName);

 												} catch (SQLServerException e) {

 													dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 													// e.printStackTrace();

 												} catch (Exception e) {

 													dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 													e.printStackTrace();

 												}

 											} catch (Exception e) {

 											}
 										}

 									} else {

 										try {

 											dbm.SetEducationData(doctorNameURL, Eduyear, EduIns, EduQual, null, null,
 													null, remarks, Source, EducationtableName);

 										} catch (SQLServerException e) {

 											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 											// e.printStackTrace();

 										} catch (Exception e) {

 											dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

 											e.printStackTrace();

 										}
 									}
 								} catch (Exception e) {

 								}
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

 										tabName = wait.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, v)))
 												.getText();

 										String tabIDAttribute = wait
 												.until(ExpectedConditions.visibilityOf(PractoPOM.docTabs(d, v)))
 												.getAttribute("id");

 										int indexOfHyphen = tabIDAttribute.lastIndexOf("-");

 										String id1 = tabIDAttribute.substring(indexOfHyphen, tabIDAttribute.length());

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
 										.until(ExpectedConditions.visibilityOf(PractoPOM.docTabsCompleteText(d, 2)))
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
 								
 								// Randomize
 								
 								rndPracto = getRandomVal(10, 1);
 								
 								if(rndPracto == 2 || rndPracto == 3 || rndPracto == 4 || rndPracto == 10){
 									
 									randomAction4();
 													
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

 							System.out.println("Doctor / Clinic  : " + DocOrClinic);

 							System.out.println("Address : " + address);

 							System.out.println("Google Address  : " + googleAddress);

 							System.out.println("About : " + aboutText);

 							System.out.println("rating : " + rating);

 							System.out.println("ratingVotes : " + ratingVotes);

 							System.out.println("feedbackVotes : " + feedbackVotes);

 							System.out.println("healthFeedCount : " + healthFeedCount);

 							// Input Master data

 							try {

 								String Languages = "";

 								System.out.println("Going to insert data in table");
 								dbm.SetMasterData(docCity, doctorNameURL, Name, DocImage, googleAddress, aboutText,
 										Specialization, docExperience, Service, Languages, IsPrimeGlobal, rating, ratingVotes,
 										feedbackVotes, healthFeedCount, remarks, Procedure, Source, MastertableName);

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

 				}

 			} catch (

 			Exception e) {
 				e.printStackTrace();
 			}

 		} catch (Exception e) {
 			e.printStackTrace();
 		}

 	}

 	public String sanitize(String inp) {

 		String result = inp.replace("'", "");

 		return result;
 	}
 	
	public String sanitizeRemoveSymbol(String inp) {
		
			inp= inp.substring(1, inp.length());
							
 		String result = inp;

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
 	
 	public void randomAction1() throws InterruptedException{
 		
 		// Click in search box
 		
 		try{
 		
 		d.findElement(By.xpath(".//input[@data-qa-id='omni-searchbox-keyword']")).click();
 		
 		Thread.sleep(1100);
 		
 		d.findElement(By.xpath("//*[@id='container']/div[3]/div/div[2]/div[2]/div/div[1]/p/span")).click();
 		
 		Thread.sleep(900);
 		
 		}catch(Exception e){
 	 		
 	 	}
 		
 	}
 	
 	public void randomAction2() throws InterruptedException{
 		
 		// Click on Login button
 		
 		try{
 		
 		d.findElement(By.xpath(".//*[@name='Practo login']")).click();
 		
 		Thread.sleep(2100);
 		
 		d.navigate().back();
 		
 		Thread.sleep(2200);
 		
 		}catch(Exception e){
 	 		
 	 	}
 		
 	}
 	
 	public void randomAction3() throws InterruptedException{
 		
 		// Click the send app link text
 	try{
 		Thread.sleep(1100);
 		
 		d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[2]/div/div[1]/div[1]/input")).click();
 		
 		Thread.sleep(1700);
 	}catch(Exception e){
 		
 	}
 	}
 	
 	public void randomAction4() throws InterruptedException{
 		
 		// Scroll Randomly'
 		
 		try{
 		
 		int rndScroll = getRandomVal(100, 50);
 						
 		System.out.println("rndScroll :" +rndScroll);
 		
 		JavascriptExecutor jse = (JavascriptExecutor)d;
 		
 		Thread.sleep(1300);
 		
 		jse.executeScript("window.scrollBy(0," + rndScroll + " )", "");
 		
 		Thread.sleep(1800);
 		
 		jse.executeScript("window.scrollBy(0," + -rndScroll + " )", "");
 			
 		}catch(Exception e){
 	 		
 	 	}
 		
 	}
 	
 	public void randomAction5() throws InterruptedException{
 		
 		// Jst Randomly hover over links
 	
 		try{
 		Actions act = new Actions(d);
 		
 		act.moveToElement(d.findElement(By.xpath(".//a[@title='chat']/div[1]")));
 		
 		Thread.sleep(1300);
 		
 		act.moveToElement(d.findElement(By.xpath(".//a[@title='book']/div[1]")));
 		
 		Thread.sleep(1900);
 		
 		}catch(Exception e){
 	 		
 	 	}
 		
 	}
 	
 	public void randomAction6() throws InterruptedException{
 		
 		// Click the send app link text
 	
 		try{
 		Thread.sleep(1100);
 		
 		d.findElement(By.xpath(".//*[@class='surv-close']")).click();
 		
 		}catch(Exception e){
 			
 		}
 		
 		Thread.sleep(1200);
 		
 	}
 	
 	public void randomAction7() throws InterruptedException{
 		
 		// Randomly Hover Again
 		
 		try{
 		
 		Actions act = new Actions(d);
 		
 		act.moveToElement(d.findElement(By.xpath(".//*[@class='practo-logo']/a")));
 		
 		Thread.sleep(1300);
 		
 		act.moveToElement(d.findElement(By.xpath(".//*[@data-qa-id='omni-searchbox-locality']")));
 		
 		Thread.sleep(1900);
 		
 		}catch(Exception e){
 	 		
 	 	}
 		
 	}
 	
 	public int getRandomVal(int maxVal, int incrementBy){
 		
 		Random rn = new Random();
 		
 		int val = rn.nextInt(maxVal);
 		
 		val += incrementBy;
 		
 		return val;
 		
 	}

 	

 }
