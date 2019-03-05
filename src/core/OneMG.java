package core;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class OneMG {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	  System.setProperty("webdriver.chrome.driver", "C://eclipse//chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUntitledTestCase() throws Exception {
    driver.get("https://www.1mg.com/drugs-listaz#");
    Thread.sleep(3000);
    //driver.findElement(By.xpath("//div[@id='content-container']/div/div/div/div/ul/li/a/span")).click();
    driver.navigate().refresh();
    Thread.sleep(1500);
   // Actions action = new Actions(driver);
    //action.sendKeys(Keys.ESCAPE);
    driver.findElement(By.linkText("A")).click();
    driver.findElement(By.id("brand-see-more")).click();
    driver.findElement(By.linkText("Augmentin")).click();
    driver.findElement(By.linkText("Augmentin 625 Duo Tablet")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | win_ser_1 | ]]
    
   

	String parentWin = driver.getWindowHandle();

	ArrayList<String> handles1 = new ArrayList<String>(driver.getWindowHandles());

	System.out.println("Tabs :" + handles1.size());

	if (handles1.size() > 1) {

		driver.switchTo().window(handles1.get(1));

		//driver.get(doctorNameURL);

	}

    String medname = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div/div/div/h1")).getText();
    System.out.println(medname);
    String companyname = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div/div/div[2]/div/a/div")).getText();
    System.out.println(companyname);
    String saltname = driver.findElement(By.xpath("//a[contains(text(),'Amoxicillin   (500mg) +   Potassium Clavulanate (125mg)')]")).getText();
    System.out.println(saltname);
    //driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div/div[2]")).click();
    String presc = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div/div/div[2]/div[2]")).getText();
    System.out.println(presc);
   // driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div/div")).click();
    String usedfor = driver.findElement(By.xpath("//*[@class='DrugInfo__uses___381Re']")).getText();
    System.out.println(usedfor);
    driver.findElement(By.xpath("//div[@id='container']/div/div[2]/div[2]")).click();
    String mrp = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div[2]/div/div/div[2]")).getText();
    System.out.println(mrp);
    //driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div")).click();
    String mrppertab = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div[2]/div/div/div[3]/div")).getText();
    System.out.println(mrppertab);
    //driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div")).click();
    String pack = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div[2]/div/div/div[4]")).getText();
    System.out.println(pack);
    driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div/div[2]")).click();
    driver.findElement(By.xpath("//div[@id='uses_0']/div[2]")).click();
    String uses = driver.findElement(By.xpath("//div[@id='uses_0']/div[2]")).getText();
    System.out.println(uses);
    driver.findElement(By.xpath("//div[@id='overview']/div/div")).click();
    String sideeffects = driver.findElement(By.xpath("//div[@id='side_effects_0']/div[2]/div/p")).getText();
    System.out.println(sideeffects);
    driver.findElement(By.xpath("//div[@id='overview']/div/div")).click();
    String howtouse = driver.findElement(By.xpath("//div[@id='how_to_use_0']/div[2]")).getText();
    System.out.println(howtouse);
    driver.findElement(By.xpath("//div[@id='overview']/div/div")).click();
    String howitworks = driver.findElement(By.xpath("//div[@id='how_it_works_0']/div")).getText();
    System.out.println(howitworks);
    driver.findElement(By.xpath("//div[@id='overview']/div/div")).click();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
