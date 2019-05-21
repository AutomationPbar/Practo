package core;

import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import com.mongodb.DB;
import utilities.DBManager;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import pom.PractoLabPOM;

public class PractoLab {

	String baseURL = "https://www.practo.com/tests/search";

	String getURL = "https://www.practo.com";

	String GoogleAPI = "https://maps.google.com/maps/api/geocode/json?sensor=false&key=AIzaSyAgxghwzg5dI4vZ-3gL2UYrk7WJYLvGT08&address=";

	String LiveDB_Path = "jdbc:sqlserver://10.0.10.42:1433;DatabaseName=PBCroma";
	private String Liveusename = "PBLIVE";
	private String Livepassword = "PB123Live";

	DBManager dbm = new DBManager();

	DB db = null;

	String tableName = "Automation.PractoLab";

	String downloadLocation = "c:\\eclipse\\Practo\\";

	String Income = "";
	String MaritalStatus = "";
	String Remarks = "";

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
			
			FileUtils.cleanDirectory(new File(downloadLocation));

		} catch (Exception e) {

		}
	}

	@Test(priority = 1)
	public void searchData() throws InterruptedException {

		try {

			ArrayList<String> suggestions = new ArrayList<String>();

			try {

				d.get(baseURL);

			} catch (Exception e) {

			}

			d.manage().window().maximize();

			Thread.sleep(3000);

			// Secondary Page

			wait.until(ExpectedConditions.visibilityOf(PractoLabPOM.cityDropDownButton(d))).click();

			Thread.sleep(1500);

			int suggestionCount = 0;

			try {

				suggestionCount = PractoLabPOM.citySuggestionCount(d).size();

				for (int n = 1; n <= suggestionCount; n++) {

					String currSuggestion = wait.until(ExpectedConditions.visibilityOf(PractoLabPOM.citySelect(d, (n))))
							.getText();

					// int indexOfspace = currmake.indexOf(" ");

					suggestions.add(currSuggestion);

					System.out.println("Suggestion : " + n + " - " + suggestions.get(n - 1));

				}

			} catch (Exception e) {

			}

			wait.until(ExpectedConditions.visibilityOf(PractoLabPOM.cityDropDownButton(d))).click();

			for (int i = 0; i < suggestions.size(); i++) {

				try {

					System.out.println("Going for Suggestion : " + (i + 1));

					wait.until(ExpectedConditions.visibilityOf(PractoLabPOM.cityDropDownButton(d))).click();

					wait.until(ExpectedConditions.visibilityOf(PractoLabPOM.citySelect(d, (i + 1)))).click();

					Thread.sleep(3000);

					int labCountOnPage = 1;

					String labCountInCity = wait.until(ExpectedConditions.visibilityOf(PractoLabPOM.labCountPerCity(d)))
							.getText();

					String labCity = wait.until(ExpectedConditions.visibilityOf(PractoLabPOM.labCity(d))).getText();

					try {

						labCountOnPage = PractoLabPOM.labCountPerPage(d).size();

					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println("Total Lab Count in City : " + labCountInCity);
					System.out.println("Lab count per page : " + labCountOnPage);

					int LabPageCount = (Integer.parseInt(labCountInCity) / labCountOnPage);

					LabPageCount = LabPageCount + 1;

					System.out.println("LabPageCount : " + LabPageCount);

					for (int k = 1; k <= LabPageCount; k++) {

						String currURL = d.getCurrentUrl();

						if (currURL.contains("&page=")) {

							int indexOfPage = currURL.lastIndexOf("=");

							currURL = currURL.substring(0, (indexOfPage + 1));

							System.out.println("Current URL : " + currURL);

						} else {

							System.out.println("Else Current URL : " + currURL);
						}

						try {

							System.out.println("Page : " + k);

							for (int l = 1; l <= labCountOnPage; l++) {

								try {

									try {
										FileUtils.cleanDirectory(new File(downloadLocation));
									} catch (Exception e) {

									}

									String labNameURL = wait
											.until(ExpectedConditions.visibilityOf(PractoLabPOM.labNameSelect(d, l)))
											.getAttribute("href");

									System.out.println("Lab Name URL : " + labNameURL);

									((JavascriptExecutor) d).executeScript("window.open()");

									Thread.sleep(2000);

									String parentWin = d.getWindowHandle();

									ArrayList<String> handles1 = new ArrayList<String>(d.getWindowHandles());

									System.out.println("Tabs :" + handles1.size());

									if (handles1.size() > 1) {

										if (handles1.size() == 3) {

											d.switchTo().window(handles1.get(2));

											d.close();
										}

										d.switchTo().window(handles1.get(1));

										d.get(labNameURL);

									}

									// FileUtils.cleanDirectory(new
									// File(downloadLocation));

									Thread.sleep(3000);

									String DocOrClinic = "";
									String Name = "";
									String address = "";

									String googleAddress = "";
									String aboutText = "";
									String openDay = "";
									String openTime = "";
									String facilities = "";

									String lat = "";
									String lng = "";
									String logoURL = "";

									// Lab Profile Variables
									String accredition = "";
									String scans = "";
									String homeSample = "";
									JSONObject weeklyOpenTime = new JSONObject();

									// Lab details

									Name = wait.until(ExpectedConditions.visibilityOf(PractoLabPOM.labName(d)))
											.getText();

									try {

										Thread.sleep(2000);

										String logoSRC = wait
												.until(ExpectedConditions.visibilityOf(PractoLabPOM.labLogo(d)))
												.getAttribute("src");

										URL imageURL = new URL(logoSRC);

										BufferedImage saveImage = ImageIO.read(imageURL.openStream());

										String imageNamePath = downloadLocation + l + ".png";

										ImageIO.write(saveImage, "png", new File(imageNamePath));

										Thread.sleep(2000);

										logoURL = GetFileUrl("test", imageNamePath, ("LogoImage" + l), "Image");

										// FileUtils.deleteQuietly(new
										// File(imageNamePath));

									} catch (Exception e) {

									}

									try {

										address = wait
												.until(ExpectedConditions.visibilityOf(PractoLabPOM.labAddress(d)))
												.getText();

									} catch (Exception e) {

									}

									DocOrClinic = "Lab";

									int labProfileCount = 0;

									try {

										labProfileCount = PractoLabPOM.labProfileCount(d).size();

										for (int s = 1; s <= labProfileCount; s++) {

											try {

												String classText = wait
														.until(ExpectedConditions
																.visibilityOf(PractoLabPOM.labProfileData(d, s)))
														.getAttribute("class");

												String currData = wait
														.until(ExpectedConditions
																.visibilityOf(PractoLabPOM.labProfileData(d, s)))
														.getText();

												System.out.println("Current Profile Text : " + currData);

												if (classText.contains("home-sample-only")) {

													homeSample = currData;

												}

												if (classText.contains("home-sample-only")) {

													homeSample = currData;

												}

												if (classText.contains("accred")) {

													accredition = currData;

												}

												if (classText.contains("scans-label")) {

													if (s != labProfileCount) {

														scans = scans + currData + ",";

													} else {

														scans = scans + currData;

													}

												}

											} catch (Exception e) {

											}

										}

									} catch (Exception e) {
										e.printStackTrace();
									}

									System.out.println("homeSample : " + homeSample);
									System.out.println("scans : " + scans);

									Thread.sleep(1500);

									try {

										Actions act = new Actions(d);

										act.clickAndHold(PractoLabPOM.labTimings(d)).build().perform();

										Thread.sleep(500);

										int dayCount = PractoLabPOM.labOpenDayCount(d).size();

										System.out.println("Lab Days Count : " + dayCount);

										if (dayCount >= 1) {

											for (int p = 1; p <= dayCount; p++) {

												try {

													openDay = PractoLabPOM.labOpenDay(d, (p + 1)).getText();

													openTime = PractoLabPOM.labTimingsEachOpenDay(d, (p + 1)).getText();

												} catch (Exception e) {
													// e.printStackTrace();
												}

												System.out.println(" Lab Open Day : " + openDay + " " + openTime);

												weeklyOpenTime.put(openDay, openTime);
											}
										}

									} catch (Exception e) {

										// e.printStackTrace();

									}

									Thread.sleep(500);

									try {

										facilities = wait
												.until(ExpectedConditions.visibilityOf(PractoLabPOM.labFacilities(d)))
												.getText();

									} catch (Exception e) {

									}

									Thread.sleep(500);

									try {

										wait.until(ExpectedConditions.visibilityOf(PractoLabPOM.moreAboutLabTab(d)))
												.click();

									} catch (Exception e) {

									}

									try {

										googleAddress = wait.until(
												ExpectedConditions.visibilityOf(PractoLabPOM.LabCompleteAddress(d)))
												.getText();

										String latLongAddress = GoogleAPI + URLEncoder.encode(googleAddress, "UTF-8");
										URL url = new URL(latLongAddress);

										System.out.println("API URL : " + latLongAddress);

										Scanner scan = new Scanner(url.openStream());
										String str = new String();
										while (scan.hasNext())
											str += scan.nextLine();
										scan.close();

										JSONObject obj = new JSONObject(str);

										// get the first result
										JSONObject res = obj.getJSONArray("results").getJSONObject(0);
										System.out.println(res.getString("formatted_address"));
										JSONObject loc = res.getJSONObject("geometry").getJSONObject("location");
										System.out.println(
												"lat: " + loc.getDouble("lat") + ", lng: " + loc.getDouble("lng"));

										lat = String.valueOf(loc.getDouble("lat"));
										lng = String.valueOf(loc.getDouble("lng"));

									} catch (Exception e) {

									}

									try {

										aboutText = wait
												.until(ExpectedConditions.visibilityOf(PractoLabPOM.moreAboutLab(d)))
												.getText();

										aboutText = aboutText.replace("'","''");
										
									} catch (Exception e) {

									}

									System.out.println("Name : " + l + " - " + Name);

									System.out.println("Address : " + address);

									System.out.println("Google Address  : " + googleAddress);

									System.out.println("Accredition : " + accredition);

									System.out.println("Facilities : " + facilities);

									// System.out.println("About : " +
									// aboutText);

									Thread.sleep(2000);

									d.close();

									d.switchTo().window(handles1.get(0));

									Thread.sleep(2000);

									JSONObject LabData = new JSONObject();
									LabData.put("Name", Name);
									LabData.put("Address", address);
									LabData.put("GoogleAddress", googleAddress);
									LabData.put("Latitude", lat);
									LabData.put("Longitude", lng);
									LabData.put("Facilities", facilities);
									LabData.put("OpeningTime", weeklyOpenTime);
									LabData.put("Accredition", accredition);
									LabData.put("Scans", scans);
									LabData.put("HomeSample", homeSample);
									LabData.put("LogoURL", logoURL);
									LabData.put("About", aboutText);

									String jsonData = LabData.toString();

									System.out.println("JSON Data : " + jsonData);

									String remarks = "";

									try {

										System.out.println("Going to insert data in table");
										dbm.SetPractoLabData(labCity, Name, jsonData, remarks, null, null, null, null, tableName);

									} catch (Exception e) {

										e.printStackTrace();
										// System.out.println(e);
									}

								} catch (Exception e) {

									e.printStackTrace();
								}

							}

						} catch (Exception e) {

							e.printStackTrace();
						}

						String navigateToURL = "";

						ArrayList<String> handles2 = new ArrayList<String>(d.getWindowHandles());

						System.out.println("Handles 2 Size : " + handles2.size());

						if (handles2.size() > 1) {

							for (int h = 1; h < handles2.size(); h++) {

								d.switchTo().window(handles2.get(h));
								d.close();

							}

						}

						d.switchTo().window(handles2.get(0));

						if (!currURL.contains("&page")) {

							navigateToURL = currURL + "&page=" + (k + 1);

						} else {

							navigateToURL = currURL + (k + 1);

						}

						System.out.println("Updated URL : " + navigateToURL);

						d.navigate().to(navigateToURL);
						Thread.sleep(3000);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				d.navigate().to(baseURL);
				Thread.sleep(3000);

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

	public String GetPostAPIResponse(URL URL, ByteArrayOutputStream file, String FileName, String urlParameters)
			throws IOException, URISyntaxException {
		// Dynamic DataResult = null;
		String FileExt = "";
		FileExt = FilenameUtils.getExtension(FileName);
		/*
		 * if (FileExt.contains(".")) { FileExt = FileExt.(0, 1); }
		 */

		FileExt = GetContentType(FileExt);
		String boundary = ("---------------------------" + java.time.LocalDateTime.now());
		// byte[] boundarybytes = (("\r\n--" + (boundary + "\r\n"))).getBytes();
		// dynamic PayloadData = "{\"file \" :" + data + ", \"remarks \" :" +
		// FileName + " }"; ;
		// HttpWebRequest request = (HttpWebRequest)WebRequest.Create(URL);

		System.out.println("uri url: " + URL.toURI().toString());
		URL obj = new URL(URL.toURI().toString());

		HttpURLConnection request = ((HttpURLConnection) obj.openConnection());
		request.setRequestMethod("POST");
		request.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		request.setRequestProperty("Content-Length", "" + Integer.toString(obj.toString().getBytes().length));

		request.setRequestProperty("Content-Language", "en-US");

		request.setUseCaches(false);
		request.setDoOutput(true);

		request.setReadTimeout(6000);
		// Get the request stream.
		DataOutputStream wr = new DataOutputStream(request.getOutputStream());
		// Write the data to the request stream.
		// dataStream.Write(byteArray, 0, byteArray.length);
		// wr.write(boundarybytes, 0, 0);
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		// Close the Stream object.

		// request.KeepAlive = true;
		/*
		 * var postData = request.GetRequestStream(); String headerTemplate =
		 * "Content-Disposition: form-data; name=\"{0}\"; filename=\"{1}\"\r\nContent-Type: {2}\r\n\r\n"
		 * ; String header = String.Format(headerTemplate, "file", FileName,
		 * FileExt); postData.Write(boundarybytes, 0, boundarybytes.Length);
		 * byte[] headerbytes = System.Text.Encoding.UTF8.GetBytes(header);
		 * postData.Write(headerbytes, 0, headerbytes.Length); byte[] buffer =
		 * new byte[4096]; int bytesRead = 0; while ((file.Read(buffer, 0,
		 * buffer.Length) != 0)) { postData.Write(buffer, 0, bytesRead); }
		 * 
		 * byte[] trailer = System.Text.Encoding.ASCII.GetBytes(("\r\n--" +
		 * (boundary + "--\r\n"))); postData.Write(trailer, 0, trailer.Length);
		 * postData.Close(); try { WebResponse webResponse =
		 * request.GetResponse(); Stream webStream =
		 * webResponse.GetResponseStream(); if ((webStream != null)) {
		 * StreamReader responseReader = new StreamReader(webStream); String
		 * strREsp = responseReader.ReadToEnd(); DataResult =
		 * JsonConvert.DeserializeObject<dynamic>(strREsp); }
		 * 
		 * } catch (Exception ex) {
		 * 
		 * }
		 */

		InputStream is = request.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder(); // or StringBuffer if Java
		// version 5+
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');

			System.out.println("Response :" + response.toString());
		}

		rd.close();

		return response.toString();
	}

	public static String GetContentType(String Ext) {
		Ext = Ext.toLowerCase();
		String mimeType = "";
		if (Ext == "png") {
			mimeType = "image/png";
		} else if (Ext == "bmp") {
			mimeType = "image/bmp";
		} else if (Ext == "emf") {
			mimeType = "image/x-emf";
		} else if (Ext == "exif") {
			mimeType = "image/jpeg";
		} else if (Ext == "gif") {
			mimeType = "image/gif";
		} else if (Ext == "icon") {
			mimeType = "image/ico";
		} else if (Ext == "jpeg") {
			mimeType = "image/jpeg";
		} else if (Ext == "jpg") {
			mimeType = "image/jpeg";
		} else if (Ext == "memorybmp") {
			mimeType = "image/bmp";
		} else if (Ext == "tiff") {
			mimeType = "image/tiff";
		} else if (Ext == "wmf") {
			mimeType = "image/wmf";
		} else if (Ext == "doc") {
			mimeType = "application/msword";
		} else if (Ext == "docx") {
			mimeType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else if (Ext == "pdf") {
			mimeType = "application/pdf";
		} else {
			mimeType = "application/pdf";
		}
		return mimeType;
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

}
