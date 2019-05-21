package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class religareProposalpage {

	public static WebElement selectPolicyButton(WebDriver d){
		
		WebElement sb = d.findElement(By.xpath("//*[@id = 'policyNumber']"));
		
		return sb;
		
	}
	
	public static WebElement activeLivesSubmit(WebDriver d){
		
		WebElement sb = d.findElement(By.xpath("//*[@id = 'claim_search']/div[2]/div[1]/a/img"));
		
		return sb;
		
	}
	
public static WebElement downloadActiveLives(WebDriver d){
		
		WebElement pn = d.findElement(By.linkText("Download"));
		
		return pn;
		
	}
	
	
	public static WebElement clientID(WebDriver d){
		
		WebElement sf = d.findElement(By.xpath("//*[@id ='customerId']"));
		
		return sf;
		
	}
	
public static WebElement submitButton(WebDriver d){
		
		WebElement pn = d.findElement(By.xpath(".//*[@class='SummaryCountLeft']/a"));
		
		return pn;
		
	}












public static WebElement search(WebDriver d){
	
	WebElement s = d.findElement(By.xpath("//*[@class='search_input_box_mobile_view']/div[1]/div[1]/i"));
	
	return s;
	
}


public static List<WebElement> statusRowcount(WebDriver d){
	
	List<WebElement> w = d.findElements(By.xpath("//*[@class='col-md-12 col-sm-12 col-xs-12 Commission_date_amount_details Fit_center ng-scope']"));
	
	return w;
	
}

public static WebElement proposalStatus(WebDriver d){
	
	WebElement ps = d.findElement(By.xpath("//*[@class='glyphicon sprite search_icon_png form-control-feedback search-icon']"));
	
	return ps;
	
}

public static WebElement tablefield(WebDriver d,int row,int col){
	
	WebElement f = d.findElement(By.xpath("//*[@ng-table='proposalDetailsTable']/tbody/tr["+row+"]/td["+col+"]"));
	
	return f;
	
}


public static WebElement policydownload(WebDriver d,int row,int col){
	
	WebElement dl = d.findElement(By.xpath("//*[@class='sprite mail_box_png']"));
	
	return dl;
	
}

public static List<WebElement> insuredDetailcount(WebDriver d){
	
	List<WebElement> c = d.findElements(By.xpath("//*[@ng-repeat='teleqdetails in partyDOList']"));
	
	return c;
	
}

public static WebElement insuredDetailsData(WebDriver d, int row, int col) {

	WebElement we = d
			.findElement(By.xpath(".//*[@id='renewal_form']/div/div/div[2]/div[2]/div/div/div/table/tbody/tr[" + row
					+ "]/td[" + col + "]"));
	return we;

}


public static List<WebElement> medicalDetailsDataCount(WebDriver d) {

	List<WebElement> we = d
			.findElements(By.xpath(".//*[@id='medical_details_block']/div[2]/div/div/div/table/tbody/tr/td[1]"));
	return we;

}

public static WebElement medicalDetailsData(WebDriver d, int row, int col) {

	WebElement we = d.findElement(By.xpath(
			".//*[@id='medical_details_block']/div[2]/div/div/div/table/tbody/tr[" + row + "]/td[" + col + "]"));
	return we;

}

public static WebElement noMedicalData(WebDriver d) {

	WebElement we = d.findElement(By.xpath(".//*[@id='medical_details_block']"));
	return we;

}

public static List<WebElement> dispatchDetailsDataCount(WebDriver d) {

	List<WebElement> we = d
			.findElements(By.xpath(".//*[@id='dispatch_details_block']/div[2]/div/div/div/table/tbody/tr/td[1]"));
	return we;
	
}


public static WebElement dispatchDetailsData(WebDriver d, int row, int col) {

	WebElement we = d.findElement(By.xpath(
			".//*[@id='dispatch_details_block']/div[2]/div/div/div/table/tbody/tr[" + row + "]/td[" + col + "]"));
	return we;

}

public static WebElement nodispatchData(WebDriver d) {

	WebElement we = d.findElement(By.xpath(".//*[@id='dispatch_details_block']"));
	return we;

}

}

