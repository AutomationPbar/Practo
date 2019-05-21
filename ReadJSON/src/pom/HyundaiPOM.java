package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HyundaiPOM {

	// Select by value - MASS
	public static WebElement dealerSelect(WebDriver d) {

		WebElement we = d.findElement(By.xpath("html/body/div[2]/div[3]/div/form/div[1]/select"));
		return we;

	}

	public static WebElement stateSelect(WebDriver d) {

		WebElement we = d.findElement(By.xpath("html/body/div[2]/div[3]/div/form/div[2]/select"));
		return we;

	}

	public static List<WebElement> stateCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath("html/body/div[2]/div[3]/div/form/div[2]/select/option"));
		return we;

	}

	public static WebElement chooseState(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath("html/body/div[2]/div[3]/div/form/div[2]/select/option[" + row + "]"));
		return we;

	}
	
	public static WebElement citySelect(WebDriver d) {

		WebElement we = d.findElement(By.xpath("html/body/div[2]/div[3]/div/form/div[3]/select"));
		return we;

	}

	public static List<WebElement> cityCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath("html/body/div[2]/div[3]/div/form/div[3]/select/option"));
		return we;

	}
	
	public static WebElement chooseCity(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath("html/body/div[2]/div[3]/div/form/div[3]/select/option[" + row + "]"));
		return we;

	}

	
	public static WebElement searchButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath("html/body/div[2]/div[3]/div/form/button"));
		return we;

	}

	// Dealer Results

	
	public static WebElement resultCountText(WebDriver d) {

		WebElement we = d.findElement(By.xpath("html/body/div[2]/div[4]/div/b[1]"));
		return we;

	}

	public static List<WebElement> resultCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='list']/div/div"));
		return we;

	}

	public static WebElement dealerName(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='list']/div/div[" + row + "]/div/a/div/div/h4/span/b"));
		return we;

	}

	public static WebElement dealerAddress1(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='list']/div/div[" + row + "]/div/div/div/a/div[1]"));
		return we;

	}

	public static WebElement dealerAddress2(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='list']/div/div[" + row + "]/div/div/div/a/div[2]"));
		return we;

	}

	
	public static List<WebElement> dealerNumbers(WebDriver d, int row) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='list']/div/div[" + row + "]/div/div/div/a/div[3]/span[not(contains(@class,'glyphicon-earphone'))]"));
		return we;

	}
	
	public static WebElement dealerNumbers(WebDriver d, int row, int col) {

		WebElement we = d.findElement(By.xpath(".//*[@id='list']/div/div[" + row + "]/div/div/div/a/div[3]/span[not(contains(@class,'glyphicon-earphone'))]["+col+"]"));
		return we;

	}

	public static WebElement dealerEmail(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='list']/div/div[" + row + "]/div/div/div/div[1]/a[2]"));
		return we;

	}
	
	// If noData text contains "No results found"
	public static WebElement noData(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='contentD']/span"));
		return we;

	}

	public static WebElement dealerGoogleAddress(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='data']/div[" + row + "]/span[5]/a"));
		return we;

	}

}
