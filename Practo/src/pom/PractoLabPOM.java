package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PractoLabPOM {

	public static WebElement cityDropDownButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='o-flex']/div/div/div/div/span[2]"));
		return we;

	}

	public static List<WebElement> citySuggestionCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@class='c-search__suggestion']/div"));
		return we;

	}

	public static WebElement citySelect(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-search__suggestion']/div[" + row + "]"));
		return we;

	}

	public static WebElement labCountPerCity(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-filters']/div[1]/div[1]/h1/span[1]"));
		return we;

	}

	public static WebElement labCity(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-filters']/div[1]/div[1]/h1/span[2]"));
		return we;

	}

	
	public static List<WebElement> labCountPerPage(WebDriver d) {

		List<WebElement> we = d
				.findElements(By.xpath(".//*[@class='c-listing-lab-card__upper-details-section']/div/a[1]"));
		return we;

	}

	public static WebElement labNameSelect(WebDriver d, int row) {

		WebElement we = d.findElement(
				By.xpath(".//*[@class='dg-eight']/div[1]/div[2]/div[" + row + "]/div[1]/div[2]/div[1]/div[1]/a[1]"));
		return we;

	}
	
	public static WebElement nextButtonForLab(WebDriver d) {

		WebElement we = d
				.findElement(By.xpath(".//*[@class='c-pagination__next']"));
		return we;

	}
	
	public static WebElement getPageNo(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-paginator']/li[" + row + "]/a"));
		return we;

	}

	// Lab Details Page

	public static WebElement labLogo(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//div[@class='c-profile-lab-card__logo']/img"));
		return we;

	}
	
	public static WebElement labName(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='o-flex-order--1']/h1"));
		return we;

	}

	public static WebElement labAddress(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='o-flex-order--1']/div"));
		return we;

	}
	
	public static List<WebElement> labProfileCount(WebDriver d) {

		List<WebElement> we = d
				.findElements(By.xpath(".//*[@class='c-lab-profile-card__main-wrapper']/div[1]/div[3]/div"));
		return we;

	}
	
	public static WebElement labProfileData(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-lab-profile-card__main-wrapper']/div[1]/div[3]/div[" + row + "]"));
		return we;

	}

	
	public static WebElement labAccredition(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-lab-profile-card__main-wrapper']/div[1]/div[3]/div"));
		return we;

	}


	
	public static WebElement labGoogleLocation(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-profile-lab-card__location-wrapper']/div[2]"));
		return we;

	}

	public static WebElement labFacilities(WebDriver d) {

		WebElement we = d
				.findElement(By.xpath(".//*[@class='c-lab-profile-card__lower-section__facilites']/div[2]"));
		return we;

	}

	public static WebElement labTimingsHeading(WebDriver d) {

		WebElement we = d.findElement(By.xpath("html/body/div[17]/div/div/div/div[2]/div/div[1]"));
		return we;

	}

	public static WebElement labTimings(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-profile-lab-card__timings-component-wrapper']/div/div[2]"));
		return we;

	}

	public static List<WebElement> labOpenDayCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@class='rc-tooltip-white-inner']/div[1]/div/div[1]"));
		return we;

	}
	
	public static WebElement labOpenDay(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@class='rc-tooltip-white-inner']/div[1]/div[" + row + "]/div[1]/div"));
		return we;

	}

	public static WebElement labTimingsEachOpenDay(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@class='rc-tooltip-white-inner']/div[1]/div[" + row + "]/div[2]/div"));
		return we;

	}
	
	// Lab Tests

	public static List<WebElement> testCountPerPage(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@class='c-lab-profile-test-results__main-wrapper']/div"));
		return we;

	}

	// Next button disappears when we are on the last page. ElementnotFound
	// eg.Lucid Labs - 77 test pages - Next button disappears on 77th page
	public static WebElement nextButtonForTestsPagination(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-pagination__next']"));
		return we;

	}

	public static WebElement testName(WebDriver d, int row) {

		WebElement we = d.findElement(
				By.xpath(".//*[@class='c-lab-profile-test-results__main-wrapper']/div[" + row + "]/div[1]/div[1]"));
		return we;

	}

	public static WebElement testDetailsMoreButton(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(
				".//*[@class='c-lab-profile-test-results__main-wrapper']/div[" + row + "]/div[1]/div[2]/span[2]"));
		return we;

	}

	public static WebElement testDetails(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(
				".//*[@class='c-lab-profile-test-results__main-wrapper']/div[" + row + "]/div[1]/div[2]/span[1]"));
		return we;

	}

	public static WebElement testCharges(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(
				".//*[@class='c-lab-profile-test-results__main-wrapper']/div[" + row + "]/div[2]/div/div[1]/div[1]"));
		return we;

	}

	public static WebElement testSpecials(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(
				".//*[@class='c-lab-profile-test-results__main-wrapper']/div[" + row + "]/div[2]/div/div[2]/div[1]"));
		return we;

	}

	public static WebElement moreAboutLabTab(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-2']"));
		return we;

	}

	public static WebElement LabCompleteAddress(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-3']/div/div[2]/div[2]"));
		return we;

	}

	public static WebElement moreAboutLab(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-3']/div/div[1]/div"));
		return we;

	}

}
