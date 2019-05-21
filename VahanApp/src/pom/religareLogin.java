package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class religareLogin {

	public static WebElement username(WebDriver d) {

		WebElement u = d.findElement(By.name("username"));
		return u;

	}

	public static WebElement password(WebDriver d) {

		WebElement p = d.findElement(By.id("password"));
		return p;

	}

	public static WebElement captcha(WebDriver d) {

		WebElement we = d.findElement(By.id("siimage"));
		return we;

	}

	public static WebElement captchaInput(WebDriver d) {

		WebElement we = d.findElement(By.name("code"));
		return we;

	}

	public static WebElement termCheckBox(WebDriver d) {

		WebElement we = d.findElement(By.name("termandcondition"));
		return we;

	}

	public static WebElement loginbutton(WebDriver d) {

		WebElement l = d.findElement(By.xpath(".//*[@id='sign_in_btn']"));
		return l;

	}
	
	public static WebElement errorMsg(WebDriver d) {

		WebElement l = d.findElement(By.xpath(".//*[@id='sign_in_btn']"));
		return l;

	}

}
