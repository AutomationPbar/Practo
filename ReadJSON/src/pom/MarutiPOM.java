package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MarutiPOM {

	// Select by value - MASS
	public static WebElement dealerSelect(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='dltypeDLpage']"));
		return we;

	}

	public static WebElement stateSelect(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='dealerstate-locate']"));
		return we;

	}

	public static List<WebElement> stateCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='dealerstate-locate']/option"));
		return we;

	}

	public static WebElement chooseState(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='dealerstate-locate']/option[" + row + "]"));
		return we;

	}

	
	public static WebElement citySelect(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='dealercity-locate']"));
		return we;

	}

	public static List<WebElement> cityCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='dealercity-locate']/option"));
		return we;

	}
	
	public static WebElement chooseCity(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='dealercity-locate']/option[" + row + "]"));
		return we;

	}

	
	public static WebElement searchButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='frmloc']/div[4]/button"));
		return we;

	}

	// Dealer Results

	public static List<WebElement> resultCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@class='grey_cont']"));
		return we;

	}

	public static WebElement dealerName(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='data']/div[" + row + "]/span[1]"));
		return we;

	}

	public static WebElement dealerAddress(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='data']/div[" + row + "]/span[2]"));
		return we;

	}

	public static WebElement dealerNumbers(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='data']/div[" + row + "]/span[3]"));
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
