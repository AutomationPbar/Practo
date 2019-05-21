package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PractoPOM {

	// row = page no
	public static List<WebElement> cityCount(WebDriver d, int row) {

		List<WebElement> we = d.findElements(
				By.xpath("html/body/div[4]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[" + row + "]/div/span/a"));
		return we;

	}

	public static WebElement citySelect(WebDriver d, int row, int col) {

		WebElement we = d.findElement(By.xpath(
				"html/body/div[4]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[" + row + "]/div/span[" + col + "]/a"));
		return we;

	}

	// Secondary Page - Same for Clinic/Hospital

	public static WebElement totalDocCountPerCity(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@class='u-clearfix']/div[1]/h4/span[1]"));
		return we;

	}

	public static List<WebElement> doctorCountPerPage(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@class='o-page']/div[2]/div/div[2]/div/div/div[1]"));
		return we;

	}

	public static WebElement doctorNameSelect(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(
				".//*[@class='o-page']/div[2]/div/div[2]/div[" + row + "]/div/div[1]/div[1]/div/div[2]/div[1]/a[1]"));
		return we;

	}

	public static WebElement getPageNo(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@class='c-paginator']/li[" + row + "]/a"));
		return we;

	}

	// Doctor / Clinic / Hospital Details Page

	public static WebElement doctorName(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@data-qa-id='doctor-name']"));
											
		return we;

	}

	public static WebElement doctorProfileClaimed(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[2]/div[1]/div/div/span"));
		return we;

	}

	public static WebElement doctorProfileClaimed2(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/span"));
		return we;

	}
	
	public static WebElement doctorProfileClaimedText(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[2]/div[1]/div/div/div/p"));
		return we;

	}
	
	public static WebElement doctorProfileClaimedText2(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[3]/div[1]/div[1]/div[2]/div[1]/div/div/div/p"));
		return we;

	}
	
	public static WebElement doctorImage(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[1]/img"));
		return we;

	}

	public static WebElement clinicName(WebDriver d) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div/div[2]/div[1]/h1"));
		return we;

	}

	public static WebElement hospitalName(WebDriver d) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div/div[2]/h1/span"));
		return we;

	}

	public static WebElement clinicAddress(WebDriver d) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div/div[2]/div[2]/p"));
		return we;

	}

	public static WebElement hospitalAddress(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[1]/p"));
		return we;

	}

	public static WebElement docAddress(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[1]/div[1]/div[1]/p[1]"));
		return we;

	}


	public static WebElement docExperience(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@data-qa-id = 'doctor-specializations']/h2"));
		return we;

	}

	
	public static WebElement clinicGoogleLocation(WebDriver d) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div/div[2]/div[2]/a"));
		return we;

	}

	public static WebElement docGoogleLocation(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[1]/div[1]/div[1]/p[2]/a"));
		return we;

	}

	public static WebElement hospitalGoogleLocation(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[1]/p/a"));
		return we;

	}

	public static WebElement docRating(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[2]/div[3]/p[2]/span[1]"));
		return we;

	}
	
	public static WebElement docRatingCount(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[2]/div[3]/p[2]/span[2]"));
		return we;

	}
	
	// In some cases, the element is different
	public static WebElement docRating2(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[2]/div[3]/p[1]/span[1]"));
		return we;

	}

	public static WebElement docRatingCount2(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[2]/div[3]/p[1]/span[2]"));
		return we;

	}
		
	public static WebElement feedbackVotes(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[2]/div[3]/p[2]/span[1]"));
		return we;

	}

	public static WebElement QALoadButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-5']/div/div/button"));
		return we;

	}
	
	public static List<WebElement> healthFeedCount(WebDriver d, int row) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='react-tabs-" + row + "']/div/div/h4"));
		return we;

	}
	
	
	// 0 - Overview, 2 - Doctors, 4- Feedback, 6 - Services
	public static WebElement docTabs(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-" + row + "']/span"));
		return we;

	}

	public static WebElement docTabsCompleteText(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-" + row + "']"));
		return we;

	}
	
	public static WebElement aboutClinicMoreButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[1]/div/p/span/span"));
		return we;

	}

	public static WebElement aboutClinic(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[1]/div"));
		return we;

	}

	public static WebElement aboutHospital(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[1]/div[1]"));
		return we;

	}

	public static WebElement aboutdoctorMoreButton(WebDriver d) {

		WebElement we = d.findElement(
				By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[2]/div[4]/p/span/span"));
		return we;

	}

	public static WebElement aboutDoctor(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='container']/div[3]/div/div[2]/div[1]/div[1]/div[2]/div[4]"));
		return we;

	}

	public static WebElement docTimings(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[1]/div/div/div/div"));
		return we;

	}

	public static List<WebElement> clinicTimingsCount(WebDriver d) {

		List<WebElement> we = d
				.findElements(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[1]/div/div/div/div/div"));
		return we;

	}

	public static WebElement clinicTimingsDay(WebDriver d, int row) {

		WebElement we = d.findElement(
				By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[1]/div/div/div/div/div[" + row + "]/p[1]"));
		return we;

	}

	public static WebElement clinicTimingsTime(WebDriver d, int row) {

		WebElement we = d.findElement(
				By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[1]/div/div/div/div/div[" + row + "]/p[2]"));
		return we;

	}

	public static List<WebElement> hospitalTimingsCount(WebDriver d) {

		List<WebElement> we = d
				.findElements(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[2]/div[1]/div/div/div/div"));
		return we;

	}

	public static WebElement hospitalTimingsDay(WebDriver d, int row) {

		WebElement we = d.findElement(
				By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[2]/div[1]/div/div/div/div[" + row + "]/p[1]"));
		return we;

	}

	public static WebElement hospitalTimingsTime(WebDriver d, int row) {

		WebElement we = d.findElement(
				By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[2]/div[1]/div/div/div/div[" + row + "]/p[2]"));
		return we;

	}

	// Doc Timings per linked Clinic

	public static List<WebElement> docHospitalTimingsCount(WebDriver d, int row) {

		List<WebElement> we = d.findElements(
				By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div[" + row + "]/div[1]/div[2]/div/div/div"));
		return we;

	}
	


	public static List<WebElement> docHospitalTimingsCount2(WebDriver d, int row) {

		List<WebElement> we = d.findElements(
				By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div[" + row + "]/div[1]/div[2]/div/div/div"));
		return we;

	}

	
	public static List<WebElement> docHospitalPerDayTimingsCount(WebDriver d, int row, int col) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div[" + row
				+ "]/div[1]/div[2]/div/div/div[" + col + "]/p[2]/span"));
		return we;

	}

	public static List<WebElement> docHospitalPerDayTimingsCount2(WebDriver d, int row) {

		List<WebElement> we = d.findElements(
				By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[2]/div[" + row + "]/div/div[1]/div[2]/div/div/div[1]/p[2]/span"));
		return we;

	}
	
	public static List<WebElement> docHospitalPerDayTimingsCount2(WebDriver d, int row, int col) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div[" + row + "]/div[1]/div[2]/div/div/div[" + col + "]/p[2]/span"));
		return we;

	}

	
	public static WebElement docHospitalTimingsDay(WebDriver d, int row, int col) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div[" + row
				+ "]/div[1]/div[2]/div/div/div[" + col + "]/p[1]"));
		return we;

	}

	
	public static WebElement docHospitalTimingsDay2(WebDriver d, int row, int col) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div[" + row + "]/div[1]/div[2]/div/div/div[" + col + "]/p[1]"));
		return we;

	}

	
	public static WebElement docHospitalTimingsTime(WebDriver d, int row, int col) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[" + row
				+ "]/div[1]/div[2]/div/div/div[" + col + "]/p[2]"));
		return we;

	}

	public static WebElement docHospitalTimingsTime2(WebDriver d, int row, int col) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[2]/div[" + row + "]/div[1]/div[2]/div/div/div[" + col + "]/p[2]"));
		return we;

	}

	
	public static WebElement docHospitalTimingsTime1(WebDriver d, int row, int col, int col2) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[2]/div[" + row
				+ "]/div[1]/div[2]/div/div/div[" + col + "]/p[2]/span[" + col2 + "]"));
		return we;

	}

	public static WebElement docHospitalTimingsTime12(WebDriver d, int row, int col, int col2) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[2]/div[" + row + "]/div[1]/div[2]/div/div/div[" + col + "]"));
		return we;

	}

	
	public static WebElement docHospitalFee(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div[" + row + "]/div[1]/div[3]/div[1]"));
		return we;

	}
	
	public static WebElement docHospitalFee2(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[2]/div[" + row + "]/div[1]/div[3]/div[1]"));
		return we;

	}

	// Doc Procedures
	
	public static WebElement docProceduresDropButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-select-2--value']/div[1]"));
		return we;

	}

	public static List<WebElement> docProceduresCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@class='Select-menu-outer']/div[1]/div"));
		return we;

	}

	public static WebElement docIndividualProcedure(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@class='Select-menu-outer']/div[1]/div[" + row + "]"));
		return we;

	}
	
	
	
	
	// Doc Services

	public static WebElement docServicesMoreButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='services']/div[1]/span/span"));
		return we;

	}

	public static List<WebElement> docServicesCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='services']/div[2]/div"));
		return we;

	}

	public static WebElement docIndividualService(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='services']/div[2]/div[" + row + "]/div/span/a"));
		return we;

	}

	// Doc Specializations

	public static WebElement docSpecMoreButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='specializations']/div[1]/span/span"));
		return we;

	}

	public static List<WebElement> docSpecCount(WebDriver d) {

		List<WebElement> we = d.findElements(
				By.xpath(".//*[@id='specializations']/div[2]/div/div/span[contains(@class,'item-title-label')]"));
		return we;

	}

	public static WebElement docIndividualSpec(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(
				".//*[@id='specializations']/div[2]/div[" + row + "]/div/span[contains(@class,'item-title-label')]"));
		return we;

	}

	// Doc Awards

	public static WebElement docAwardsMoreButton(WebDriver d) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='awards and recognitions']/div[1]/span/span"));
		return we;

	}

	
	public static List<WebElement> docAwardsCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='awards and recognitions']/div[2]/div"));
		return we;

	}

	public static WebElement docIndividualAward(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='awards and recognitions']/div[2]/div[" + row + "]/div/span/span"));
		return we;

	}

	// Doc Membership
	
	public static WebElement docMembershipMoreButton(WebDriver d) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='memberships']/div[1]/span/span"));
		return we;

	}

	public static List<WebElement> docMembershipCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='memberships']/div[2]/div"));
		return we;

	}

	public static WebElement docIndividualMembership(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='memberships']/div[2]/div[" + row + "]/div/span/span"));
		return we;

	}

	// Doc Registration

	public static List<WebElement> docRegistrationCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='registrations']/div[2]/div"));
		return we;

	}

	public static WebElement docIndividualRegistration(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='registrations']/div[2]/div[" + row + "]/div/span/span"));
		return we;

	}

	// Doc Experience

	public static WebElement docExpMoreButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='experience']/div[1]/span/span"));
		return we;

	}

	public static List<WebElement> docExpCount(WebDriver d) {

		List<WebElement> we = d.findElements(
				By.xpath(".//*[@id='experience']/div[2]/div"));
		return we;

	}

	public static WebElement docIndividualExp(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(
				".//*[@id='experience']/div[2]/div[" + row + "]/div/span/span"));
		return we;

	}

	// Doc Education

	public static WebElement docEduMoreButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='education']/div[1]/span/span"));
		return we;

	}

	public static List<WebElement> docEduCount(WebDriver d) {

		List<WebElement> we = d.findElements(
				By.xpath(".//*[@id='education']/div[2]/div"));
		return we;

	}

	public static WebElement docIndividualEdu(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(
				".//*[@id='education']/div[2]/div[" + row + "]/div/span/span"));
		return we;

	}

	// clinicDocCount

	public static WebElement clinicDocMoreButton(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-" + row + "']/div/div[2]/div/div[21]/button"));
		return we;

	}

	public static List<WebElement> clinicDocCount(WebDriver d, int row) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='react-tabs-" + row + "']/div/div[2]/div/div"));
		return we;

	}

	// To get unique doc URL href attrib - Remove specialization
	public static WebElement clinicDocName(WebDriver d, int row1, int row2) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-" + row1 + "']/div/div[2]/div/div[" + row2
				+ "]/div/div[1]/div[1]/div[1]/div[2]/div[1]/a"));
		return we;

	}

	// Doc Clinic Count

	public static List<WebElement> docClinicCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div"));
		return we;

	}

	public static WebElement docClinicCountExtraFrame(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[1]/p/span"));
		return we;

	}

	
	public static WebElement docClinicName(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div[" + row + "]/div[1]/div[1]/h2/a"));
		return we;						

	}

	public static WebElement docClinicName2(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div/div[" + row + "]/div[1]/div[1]/h2/a"));
		return we;						

	}
	
	// For Book Now, there is a separate xpath
	public static WebElement docBookingText(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[2]/div[" + row + "]/div/div[2]/div/button/span[2]"));
		return we;						

	}

	// For Call Now, there is a separate xpath
	public static WebElement docBookingText2(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div/div[2]/div[" + row + "]/div/div[2]/div/button/span/span"));
		return we;						

	}

	
	// Same for Hospital
	public static WebElement clinicServicesMoreButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='entity']/span/span"));
		return we;

	}

	public static List<WebElement> clinicServicesCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='entity']/div[2]/div"));
		return we;

	}

	// Same for Hospital
	public static WebElement clinicIndividualServices(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='entity']/div[2]/div[" + row + "]/div/span/*"));
		return we;

	}

	public static List<WebElement> clinicOtherCentersCount(WebDriver d, int row) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='react-tabs-" + row + "']/div/div"));
		return we;

	}

	// To get unique URL (href)
	public static WebElement clinicOtherCentersName(WebDriver d, int row1, int row2) {

		WebElement we = d.findElement(By
				.xpath(".//*[@id='react-tabs-" + row1 + "']/div/div[" + row2 + "]/div/div[1]/div[1]/div/div[2]/div/a"));
		return we;

	}

	public static WebElement hospitalModesOfPayment(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[3]/div/p"));
		return we;

	}

	public static WebElement hospitalReadMoreInfoButton(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[3]/div/span/span"));
		return we;

	}

	public static List<WebElement> hospitalAmenitiesCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[3]/div[1]/div/div"));
		return we;

	}

	public static WebElement hospitalAmenities(WebDriver d, int row) {

		WebElement we = d
				.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[3]/div[1]/div/div[" + row + "]/span"));
		return we;

	}

	public static WebElement hospitalEmergencyNo(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[3]/div[2]/p"));
		return we;

	}

	public static WebElement hospitalNoOfBeds(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[3]/h3/span[2]"));
		return we;

	}

	public static WebElement hospitalNoOfAmbulances(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='react-tabs-1']/div/div[1]/div[2]/div[3]/h3[2]/span[2]"));
		return we;

	}

	// Post clicking Services Tab
	public static WebElement viewAllSpecialitiesLink(WebDriver d) {

		WebElement we = d.findElement(By.xpath(".//*[@id='entity']/span/span"));
		return we;

	}

	public static List<WebElement> servicesCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='entity']/div[2]/div/div"));
		return we;

	}

	public static WebElement docServiceSelect(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(".//*[@id='entity']/div[2]/div[" + row + "]/div"));
		return we;

	}

	// Doctors count in case of Clinics / Hospitals

	// Post clicking Doctors tab
	public static List<WebElement> perClinicDocCount(WebDriver d) {

		List<WebElement> we = d.findElements(By.xpath(".//*[@id='react-tabs-3']/div/div[2]/div/div"));
		return we;

	}

	public static WebElement perClinicDocName(WebDriver d, int row) {

		WebElement we = d.findElement(By.xpath(
				".//*[@id='react-tabs-3']/div/div[2]/div/div[" + row + "]/div/div[1]/div[1]/div[1]/div[2]/div[1]"));
		return we;

	}

}
