package utilities;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class GetCaptcha {

	
	private final static boolean chunks = true;

	static String fileToEncode = "";
	static String encodedFilePath =  "";
	
	static String apiURL = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyDtlTuN4CAJRcAS6W30sqnnSCUFS3NvSmU";
	
	
	public static String captchatext(WebElement ct, WebDriver d, File report,int fx,int fy,int x,int y, String basePath) throws InterruptedException{
		
	
				
		try {
							
			Thread.sleep(7000);
			
			BufferedImage cp = null;
			try {
				cp = ImageIO.read(report);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			Point point = ct.getLocation();
			
			int height = ct.getSize().getHeight();
			int width = ct.getSize().getWidth();
			
	/*		System.out.println("height:" + height);
			System.out.println("width :" + width );
			System.out.println("PointX:" + point.getX());
			System.out.println("PointY:" + point.getY());
		*/	
			int Cx = point.getX()+fx+x;
			int Cy = point.getY() +fy+y;
			
			try{
			//FileUtils.deleteQuietly(new File("c:\\captcha\\captcha.png"));
			
			}catch(Exception e){
				
			}
			BufferedImage captcha = cp.getSubimage(Cx, Cy, width, height);
			
			ImageIO.write(captcha, "png", report);
			
			fileToEncode = basePath + "\\Captcha\\CaptchaSS.png";
			encodedFilePath = basePath + "\\Captcha\\encoded.txt";
			
			FileUtils.copyFile(report, new File(fileToEncode));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		String capCode = "";
		
		capCode = getCaptcha(fileToEncode);
/*		ITesseract tessInst = new Tesseract();
		tessInst.setDatapath("C:\\Users\\Avani\\workspace\\captchatest\\tessdata");
		
		String result= "";
		
		try {
            result= tessInst.doOCR(image);
            System.out.println(result);
            
            
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
		
		*/
		return capCode;
	}
	
	public static String getCaptcha(String cap) {

		String code = "";

		try {

			try {
				encode(fileToEncode, encodedFilePath, chunks);
			} catch (Exception e) {

				e.printStackTrace();
			}

			String Base64String = "";

			InputStream inputStream = new FileInputStream(encodedFilePath);
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				Base64String += line;
			}

			//System.out.println("String Created :" + Base64String);

			Date dt = new Date();

			SimpleDateFormat smdt = new SimpleDateFormat("dd.MM.yyyy");

			//String datee = smdt.format(dt);

			//System.out.println("Date : " + datee);

			String jSonDataString = "{'requests': [ { 'image': { 'content': '" + Base64String
					+ "'  }, 'features': [  { 'type': 'TEXT_DETECTION' } ] }]}";

			JSONObject jsonObject = new JSONObject(jSonDataString);

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

				String inputLine;

				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}

				in.close();

				//System.out.println(response.toString());

				JSONObject Response1 = new JSONObject(response.toString());

				JSONArray Response2 = Response1.getJSONArray("responses");

				JSONArray Annotations = Response2.getJSONObject(0).getJSONArray("textAnnotations");

				code = Annotations.getJSONObject(1).getString("description");

				System.out.println("Code- " + code);

			} catch (Exception e) {
				System.out.println("\nError while calling REST Service");
				// System.out.println(e);
				e.printStackTrace();
			}

			br.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return code;
	}

	private static void encode(String sourceFile, String targetFile, boolean isChunked) throws Exception {

		byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(sourceFile), isChunked);

		writeByteArraysToFile(targetFile, base64EncodedData);
	}

	public void decode(String sourceFile, String targetFile) throws Exception {

		byte[] decodedBytes = Base64.decodeBase64(loadFileAsBytesArray(sourceFile));

		writeByteArraysToFile(targetFile, decodedBytes);
	}

	public static byte[] loadFileAsBytesArray(String fileName) throws Exception {

		File file = new File(fileName);
		int length = (int) file.length();
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[length];
		reader.read(bytes, 0, length);
		reader.close();
		return bytes;

	}

	public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

		File file = new File(fileName);
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
		writer.write(content);
		writer.flush();
		writer.close();

	}

	
}
