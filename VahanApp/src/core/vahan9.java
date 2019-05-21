package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
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
import org.json.JSONString;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

//import com.mongodb.util.JSON;

import pom.religareHomepage;
import pom.religareLogin;
import pom.religareProposalpage;
import utilities.DBManager;
import utilities.ExcelUtils;
import utilities.GetCaptcha;

public class vahan9 {

	WebDriver d;
	WebDriverWait wait;

	String baseUrl = "https://vahan.nic.in/nrservices/faces/user/jsp/SearchStatus.jsp";
	String pass = "abc@123";

	String activeLifeURL = "https://tractus.religarehealthinsurance.com/religarehrcrm/agent/active_life.php";
	String cdURL = "https://tractus.religarehealthinsurance.com/religarehrcrm/agent/cd_statement.php";
	String healthCardURL = "https://tractus.religarehealthinsurance.com/religarehrcrm/agent/employee_search.php";
	String claimURL = "https://tractus.religarehealthinsurance.com/religarehrcrm/agent/claim_search.php";

	String DBQuery = "SELECT LeadID, PlanName, PolicyNo, PolicyStartDate, PolicyEndDate, SupplierId, SupplierName FROM PBCROMA.CORP.BookingDetails (NOLOCK) WHERE SupplierId = 37 AND LeadID IS NOT NULL AND PolicyNo IS NOT NULL";

	String DBQuery1 = "select MakeID, ModelId, VariantID from productDB.ci.t_vehicledetails vd (nolock) where enquiryID = (select en.enquiryID from productDB.core.enquiry en (nolock) where matrixleadID = ";
	
	String DBQuery2 = "SELECT mk.MakeId, mk.MakeName, md.ModelId, md.ModelName, vr.VariantId, vr.VariantName, vr.SeatingCapacity, cc.CcRange, vr.CubicCapacity, ft.FuleType, ex.ExShowRoomPrice, vr.VehicleCode, mk.IsActive as 'IsMakeActive', md.IsActive AS 'IsModelActive', vr.IsActive AS 'IsVariantActive' FROM productDB.CI.M_Make MK WITH(NOLOCK) INNER JOIN productDB.ci.M_Model MD WITH(NOLOCK) ON mk.MakeId = md.MakeId INNER JOIN productDB.CI.M_Variant VR WITH(NOLOCK) ON md.ModelId = vr.ModelId left JOIN productDB.CI.M_FuelType FT WITH(NOLOCK) ON vr.FuelTypeId = FT.FuleTypeId left JOIN productDB.CI.M_CubicCapacity CC WITH(NOLOCK) ON vr.CubicCapacityId = cc.CubicCapacityId Left JOIN productDB.CI.M_VariantExShowRoom EX with(Nolock) ON vr.VariantId=ex.VariantId  and  ex.RegisterStateId=1 WHERE Mk.IsActive=1 AND MD.IsActive=1 AND vr.IsActive=1 AND Mk.MakeID = ";
	
	String DBQuery3 = "and MD.modelID = ";
	
	String DBQuery4 = " and vr.variantID = ";	
	
	String ReplDB_Path = "jdbc:sqlserver://pb-prod-sec1.etechaces.com:1433;DatabaseName=PBCROMA";
	private String Replusename = "PBLive";
	private String Replpassword = "PB123Live";

	String LiveDB_Path = "jdbc:sqlserver://PBAGL01.ETECHACES.COM:1433;DatabaseName=PBCroma";
	private String Liveusename = "BackofficeSys";
	private String Livepassword = "MT#123#C@re";

	ArrayList<String> leadId = new ArrayList<String>();
	ArrayList<String> planName = new ArrayList<String>();
	ArrayList<String> PolicyStartDate = new ArrayList<String>();
	ArrayList<String> policyNo = new ArrayList<String>();
	ArrayList<String> PolicyEndDate = new ArrayList<String>();
	ArrayList<String> SupplierName = new ArrayList<String>();
	ArrayList<String> supplierId = new ArrayList<String>();

	String basePath = "C:\\eclipse\\Vahan\\";

	String downloadLocation = basePath + "Downloads\\";

	String outputFile = basePath + "Input.xls";
	String masterFile = basePath + "Master.xls";

	String outputDataFile = basePath + "Output9.xls";

	ArrayList<String> RegNos = new ArrayList<String>();
	ArrayList<String> LeadIDs = new ArrayList<String>();
	
	String apiURL = "http://matrixliveapi.policybazaar.com/Communication/Communication.svc/send";
	
	private final boolean chunks = true;
	String encodedFilePath = "C:\\eclipse\\Vahan\\output.txt";
	String excelPath3 = "C:\\eclipse\\Vahan\\output.txt";
	
	String fileName = "VahanReport";
	
	String emailFrom = "Automation@policybazaar.com";
	
	String emailTo1 = "neerajr@policybazaar.com";
	String emailTo2 = "";// "parveeny@policybazaar.com";
	
	String emailSubject = "Automation Vahan Report";
	String emailBody = "Hello Team, ";
	String emailBody1 = " PFA the Vahan Report ";
	String emailBody2 = "";
	String emailBody3 = "";
	String emailBody4 = "";
	String emailBody5 = "";
	String emailBody6 = "Regards, ";
	String emailBody7 = "Automation Team";
	String emailBody8 = "";
	String emailBody9 = "";

	
	String emailTo3 = "";
	String emailTo4 = "";
	String emailTo5 = "";
	String emailTo6 = "";
	String emailTo7 = "";
	String emailTo8 = "";
	String emailTo9 = "";
	String emailTo10 = "";
	String emailTo11 = "";
	String emailTo12 = "";
	
	String emailCC1 = "";
	String emailCC2 = "";
	String emailCC3 = "";
	String emailCC4 = "";
	
	// connect to mongoDB, IP and port number
	// Mongo mongo = new Mongo("10.0.8.62", 27017); //QA
	// Mongo mongo = new Mongo("10.34.83.10", 27017); // LIVE
	MongoClient mongoClient = null;

	// get database from MongoDB,
	// if database doesn't exists, mongoDB will create it automatically
	// DB db = mongo.getDB("InsurerAutomation");
	DB db = null;

	DBManager dbm = new DBManager();

	// get a single collection
	// DBCollection collection = db.getCollection("Religare");
	DBCollection collection = null;

	@BeforeTest

	public void getAppno() {

		try {
			System.setProperty("webdriver.chrome.driver", "C:/eclipse/chromedriver.exe");
			HashMap<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("download.default_directory", downloadLocation);

			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			prefs.put("download.prompt_for_download", false);

			d = new ChromeDriver(cap);

			wait = new WebDriverWait(d, 10);

			FileUtils.cleanDirectory(new File(downloadLocation));
			
			dbm.DBConnection(ReplDB_Path, Replusename, Replpassword);

			int totalRows = ExcelUtils.GetRowCount(outputFile, "Sheet1");

			System.out.println("TotalRows : " + totalRows);

			ExcelUtils.SetExcelFile(outputFile, "Sheet1");

			for (int j = 1; j <= totalRows; j++) {

				try {

					String regNo = "";
					String custID = "";
					String leadID = "";

					custID = ExcelUtils.GetCellData(j, 0);
					leadID = ExcelUtils.GetCellData(j, 1);
					regNo = ExcelUtils.GetCellData(j, 2);

					RegNos.add(regNo); 
					LeadIDs.add(leadID);
					
					System.out.println("Reg No -" + regNo + " Lead ID -" + leadID);

				} catch (Exception e) {

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// @Test(priority = 1)
	public void login(String username) throws InterruptedException {

		try {

			d.navigate().to(baseUrl);

			Thread.sleep(2000);

			String capText = getCaptchaCode();

			System.out.println("Captcha Text Raw - " + capText);

			capText = capText.replace(" ", "");

			capText = capText.replace("\n", "");

			System.out.println("Captcha Text Final - " + capText);

			Thread.sleep(2000);

			wait.until(ExpectedConditions
					.visibilityOf(d.findElement(By.xpath(".//*[@id='vehiclesearchstatus:regn_no1_exact']"))))
					.sendKeys(username);

			// wait.until(ExpectedConditions.visibilityOf(religareLogin.password(d))).sendKeys(pass);

			wait.until(ExpectedConditions
					.visibilityOf(d.findElement(By.xpath(".//*[@id='vehiclesearchstatus:txt_ALPHA_NUMERIC']"))))
					.sendKeys(capText);

			// wait.until(ExpectedConditions.visibilityOf(religareLogin.termCheckBox(d))).click();

			wait.until(ExpectedConditions
					.visibilityOf(d.findElement(By.xpath("//*[@id='vehiclesearchstatus:j_id_jsp_664471437_27']/input")))).click();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(priority = 1)
	public void capturedata() throws ParseException, InterruptedException {

		// Login

		for (int n = 350; n < 400; n++) {

			System.out.println("Going for reg no : " + RegNos.get(n));

			
			// Get Vehicle make , model IDs from DB
			
			String makeID = "";
			String modelID = "";
			String variantID = "";
			
			
			try{
				
				System.out.println("Going for Lead ID - " + LeadIDs.get(n));
				
				String DBQuery5 = "";
				
				DBQuery5 = DBQuery1 + "'" + LeadIDs.get(n) + "')";
				
				System.out.println("Final DBQuery 1 : " + DBQuery5);

				ResultSet resultSet = dbm.GetResultSet(DBQuery5);
				int resultSetSize = 0;

				while (resultSet.next()) {
					resultSet.beforeFirst();
					resultSet.last();
					resultSetSize = resultSet.getRow();
				}

				System.out.println("Result Count :" + resultSetSize);
				int resultCounter = 0;
				resultSet.first();
				if (resultSetSize > 0) {

					do {

						resultCounter = resultCounter + 1;

						System.out.println("Lead ID : " + resultSet.getString(1));

						makeID = resultSet.getString(1);
						modelID = resultSet.getString(2);
						variantID = resultSet.getString(3);
						

					} while (resultSet.next());

				}
				
			}catch(Exception e){
				
			}
			
			// Get Vehicle make , model names from DB
			
			String makeName = "";
			String modelName = "";
			String variantName = "";
			
			try{
				
				String DBQuery6 = "";
				
				DBQuery6 = DBQuery2 + "'" + makeID + "'" + DBQuery3 + "'" + modelID + "'" + DBQuery4 + "'" + variantID + "'";
				
				System.out.println("Final DBQuery 2 : " + DBQuery6);

				// Get rest of the fields data


				ResultSet resultSet = dbm.GetResultSet(DBQuery6);
				int resultSetSize = 0;

				while (resultSet.next()) {
					resultSet.beforeFirst();
					resultSet.last();
					resultSetSize = resultSet.getRow();
				}

				System.out.println("Result Count :" + resultSetSize);
				int resultCounter = 0;
				resultSet.first();
				if (resultSetSize > 0) {

					do {

						resultCounter = resultCounter + 1;

						System.out.println("Lead ID : " + resultSet.getString(1));

						makeName = resultSet.getString(2);
						modelName = resultSet.getString(4);
						variantName = resultSet.getString(6);
						

					} while (resultSet.next());

				}
				
			}catch(Exception e){
				
			}
	
			
			System.out.println("DB Values : Make " + makeName + " Model - " + modelName + "Variant - " + variantName);
			
			// Login
			
			login(RegNos.get(n));
			
			try {

				try {

					Thread.sleep(2000);
					// Handle incorrect captcha error
					List<WebElement> dataFound = d.findElements(By.xpath(
							".//*[@id='vehiclesearchstatus:pn_grd1']/tbody/tr[2]/td[1]/div[1]/div[1]/table/tbody/tr[1]/td[1]"));

					System.out.println("Data Found : " + dataFound.size());

					if (!(dataFound.size() > 0)) {
						
						try{
							
						
						String message = wait.until(ExpectedConditions.visibilityOf(d.findElement(By.xpath(".//*[@id='vehiclesearchstatus:msg']")))).getText();

						if(message.contains("Vehicle No is wrong") || message.contains("not digitized")){
							continue;
						}
						
						}catch(Exception e){
							
						}
						
						login(RegNos.get(n));

						Thread.sleep(2000);

						for (int i = 1; i <= 10; i++) {

							System.out.println("Loop : " + i);

							List<WebElement> dataFound2 = d.findElements(By.xpath(
									".//*[@id='vehiclesearchstatus:pn_grd1']/tbody/tr[2]/td[1]/div[1]/div[1]/table/tbody/tr[1]/td[1]"));

							System.out.println("Data Found New : " + dataFound2.size());

							if (!(dataFound2.size() > 0)) {

								System.out.println("Going for Login again " + i);

								login(RegNos.get(n));

								Thread.sleep(2000);
							}

							else {
								break;
							}
						}
					}

				} catch (Exception e) {

				}

				String regDate = d.findElement(By.xpath("//*[@id='vehiclesearchstatus:j_id_jsp_664471437_37']"))
						.getText();
				String owner = d.findElement(By.xpath("//*[@id='vehiclesearchstatus:j_id_jsp_664471437_45']"))
						.getText();
				String expDate = d.findElement(By.xpath("//*[@id='vehiclesearchstatus:j_id_jsp_664471437_58']"))
						.getText();
				String carClass = d.findElement(By.xpath("//*[@id='vehiclesearchstatus:j_id_jsp_664471437_48']"))
						.getText();
				String fuel = d.findElement(By.xpath(".//*[@id='vehiclesearchstatus:j_id_jsp_664471437_50']"))
						.getText();
				String model = d.findElement(By.xpath(".//*[@id='vehiclesearchstatus:j_id_jsp_664471437_53']"))
						.getText();
				String fitnessUpto = d.findElement(By.xpath(".//*[@id='vehiclesearchstatus:j_id_jsp_664471437_56']"))
						.getText();
				String pollutionNorms = d
						.findElement(By.xpath(".//*[@id='vehiclesearchstatus:j_id_jsp_664471437_61']")).getText();
				String status = d.findElement(By.xpath(".//*[@id='vehiclesearchstatus:j_id_jsp_664471437_63']"))
						.getText();
				

				String make = model.substring(0, model.indexOf("/") - 1);

				model = model.substring(model.indexOf("/") + 2, model.length());
				
				System.out.println("Website Model : " + model);
				System.out.println("regDate : " + regDate);
				System.out.println("owner : " + owner);
				System.out.println("expDate : " + expDate);
				System.out.println("status : " + status);
				
				
				String makeFinal = make.substring(0, make.indexOf(" ")).trim();
				
				makeFinal = makeFinal.toLowerCase();

				String modelFinal = "";
				String variantFinal = "";

				System.out.println("Trimmed Make : " + makeFinal);

					try {

							if (model.toLowerCase().contains(makeName.toLowerCase())) {
								
								String modelRaw = model.substring(model.toUpperCase().indexOf(makeName), model.length());
								
								System.out.println("Model Raw : " + modelRaw);
								
								if(modelRaw.toLowerCase().contains(modelName.toLowerCase())){
									
									modelFinal = modelName;
									variantFinal = modelRaw.substring(modelRaw.indexOf(modelName), modelRaw.length());
									
								} else {
									
									modelFinal = modelRaw.substring(0, modelRaw.indexOf(" "));
									variantFinal = modelRaw.substring(modelRaw.indexOf(" "), modelRaw.length());
									
								}
							
								System.out.println("modelFinal : " + modelFinal);
								System.out.println("variantFinal : " + variantFinal);
									
							}else{
								
								if(model.toLowerCase().contains(modelName.toLowerCase())){
									
									modelFinal = modelName;
									variantFinal = model.substring(model.indexOf(modelName), model.length());
									
								} else {
									
									modelFinal = model.substring(0, model.indexOf(" "));
									variantFinal = model.substring(model.indexOf(" "), model.length());
									
								}
							
							
								System.out.println("modelFinal 2 : " + modelFinal);
								System.out.println("variantFinal 2 : " + variantFinal);
								
							}


					} catch (Exception e) {

					}
				
				

				String[] siteData = new String[16];

				siteData[0] = regDate;
				siteData[1] = owner;
				siteData[2] = expDate;
				siteData[3] = carClass;
				siteData[4] = fuel;
				siteData[5] = make;
				siteData[6] = model;
				siteData[7] = fitnessUpto;
				siteData[8] = pollutionNorms;
				siteData[9] = status;
				siteData[10] = RegNos.get(n);
				siteData[11] = makeName;
				siteData[12] = modelName;
				siteData[13] = variantName;
				siteData[14] = modelFinal;
				siteData[15] = variantFinal;

				ExcelUtils.SetInputData1(outputDataFile, "Sheet1", siteData, n + 1);

				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		try {

			encodeReport(excelPath3, encodedFilePath);
			sendEmail(encodedFilePath, fileName, emailFrom, emailSubject, emailBody, emailBody1, emailBody2, emailBody3,
					emailBody4, emailBody5, emailBody6, emailBody7, emailBody8, emailBody9, emailTo1, emailTo2,
					emailTo3, emailTo4, emailTo5, emailTo6, emailTo7, emailTo8, emailTo9, emailTo10, emailTo11,
					emailTo12, emailCC1, emailCC2, emailCC3, emailCC4);

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// @AfterClass
	public void tearDown() {
		d.close();
		d.quit();
		mongoClient.close();
	}

	public String getCaptchaCode() throws InterruptedException {

		WebElement ele = wait.until(ExpectedConditions.visibilityOf(d.findElement(
				By.xpath(".//*[@id='vehiclesearchstatus:imageProcessBankNewImage']/preceding-sibling::img"))));

		File screenshot = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);
		// Get the location of element on the page

		String capText = GetCaptcha.captchatext(ele, d, screenshot, 0, 0, 0, 0, basePath);

		return capText;

	}

	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
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
	
	public void encodeReport(String excelPath, String encodedFilePath) {

		try {
			encode(excelPath, encodedFilePath, chunks);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void sendEmail(String encodedFilePath, String fileName, String emailFrom, String subject, String emailBody,
			String emailBody1, String emailBody2, String emailBody3, String emailBody4, String emailBody5,
			String emailBody6, String emailBody7, String emailBody8, String emailBody9, String emailTo1,
			String emailTo2, String emailTo3, String emailTo4, String emailTo5, String emailTo6, String emailTo7,
			String emailTo8, String emailTo9, String emailTo10, String emailTo11, String emailTo12, String emailCC1,
			String emailCC2, String emailCC3, String emailCC4) {

		try {

			String Base64String = "";

			InputStream inputStream = new FileInputStream(encodedFilePath);
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				Base64String += line;
			}

			Date dt = new Date();

			SimpleDateFormat smdt = new SimpleDateFormat("dd.MM.yyyy");

			String datee = smdt.format(dt);

			System.out.println("Date : " + datee);

			String jSonDataString = "";

			

				jSonDataString = "{'CommunicationDetails':{'LeadID':25618644,'Conversations':[{'From':'" + emailFrom
						+ "', 'ToReceipent':['" + emailTo1 + "','" + emailTo2 + "','" + emailTo3 + "','" + emailTo4
						+ "','" + emailTo5 + "','" + emailTo6 + "','" + emailTo7 + "','" + emailTo8 + "','" + emailTo9
						+ "','" + emailTo10 + "','" + emailTo11 + "','" + emailTo12 + "','"
						+ "'], 'BccEmail':[],'CCEmail':['" + emailCC1 + "','" + emailCC2 + "','" + emailCC3 + "','"
						+ emailCC4 + "'], 'Body':'" + emailBody + "<BR>" + "<BR>" + emailBody1 + "<BR>" + "<BR>"
						+ emailBody6 + "<BR>" + emailBody7 + "<BR>" + "<BR>" + "', 'Subject':'" + subject + " " + datee
						+ " ', 'MailAttachments': [ {'FileName': '" + fileName + "_" + datee
						+ ".xls', 'AttachemntContent': '" + Base64String
						+ "', 'AttachmentURL': '' }], 'CreatedBy':'Tanuja','UserID':124,'AutoTemplate':true}], 'CommunicationType':1} }";

			

			JSONObject jsonObject = new JSONObject(jSonDataString);

			System.out.println(jSonDataString);

			System.out.println(jsonObject);

			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL(apiURL);
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				while (in.readLine() != null) {
				}
				System.out.println("\n REST Service Invoked Successfully..");
				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling REST Service");
				System.out.println(e);
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private void encode(String sourceFile, String targetFile, boolean isChunked) throws Exception {

		byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(sourceFile), isChunked);

		writeByteArraysToFile(targetFile, base64EncodedData);
	}



}
