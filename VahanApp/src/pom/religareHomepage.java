package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class religareHomepage {

	public static WebElement viewproposal(WebDriver d){
		
				WebElement vp = d.findElement(By.xpath(".//*[@class='graf_details_container']/div[4]/button"));

		return vp;
	}
	
	
	
}
