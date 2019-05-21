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

public class ProcessURLs3 {

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

			for (int o = 30001; o <= 45000; o++) {

				Excelurls.add(ExcelUtils.GetCellData(o, 0));

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

					Thread.sleep(4000);

					String docCountinClinic = "NA";

					try {

						docCountinClinic = d.findElement(By.xpath(".//*[@class='c-profile--qualification']/div[1]/h2"))
								.getText();

					} catch (Exception e) {

					}

					System.out.println("Doc Exp Raw: " + docCountinClinic);

					docCountinClinic = docCountinClinic.substring(0, 2);

					System.out.println("Doc Exp : " + docCountinClinic);

					String clinicDocName = "";
					String clinicDocURL = "";
					String day = "";
					String time = "";
					String DocFeeInHospital = "";
					String isBookApp = "";
					String remarks = "";
					String ClinicAdd = "";

					try {

						String ClinicGAdd = docCountinClinic;

						System.out.println("Going to insert data in table");

						dbm.SetPractoURLData(DocURL, clinicDocName, clinicDocURL, day, time, DocFeeInHospital,
								isBookApp, remarks, ClinicAdd, ClinicGAdd, tableName);

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
