package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

public class testperf {

	@Test
	public void setProp(){
	 // specify the path of the chromdriver binary that you have downloaded (see point 2)
		System.setProperty("webdriver.chrome.driver", "C:/eclipse/chromedriver.exe");
    ChromeOptions options = new ChromeOptions();
    // if you like to specify another profile
    options.addArguments("user-data-dir=/root/Downloads/aaa"); 
    options.addArguments("start-maximized");
    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    WebDriver driver = new ChromeDriver(capabilities);
    driver.get("http://www.google.com");
    String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
    String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
    
    System.out.println(netData);
	}
	
}
