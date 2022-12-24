package models;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Utils;

public class Staff {

	private static final By STAFF_ID = By.id("sn_staff");
	private static final By ADDEMPLOYEE_ID = By.id("act_primary");
	private static final By SAVEEMPLOYEE_ID = By.id("_as_save_multiple");
	private static final By ALLSTAFF_LTXT = By.partialLinkText("All Staff");
	private static final By EMPLOYEE_CLASS = By.className("StaffListTable-employee-row");
	private static final By EMPLOYEE_HREF = By.xpath("//*[@id='7']/a");
	private static final By EMPLOYEE_EDIT = By.linkText("Edit Details");
	private static final By EDIT_FNAME_ID = By.id("first_name");
	private static final By EDIT_PIC_ID = By.id("in-btn");
	private static final By EDIT_UPDATE_NAME = By.name("update");
	private static final String IMG_PATH = System.getProperty("user.dir") + "\\humanity_img\\mata.jpg";
	private static final String[] EMPLOYEE = {"Vitez", "Koja", "koja@gmail.com"};
	private static final String EDIT_NAME = "Mile";
	private static final String FILE_NAME = "humanity_data.xlsx";
	private static final String SHEET_NAME = "employees";

	private final List<WebElement> employees;
	
	public Staff(WebDriver driver) {
		employees = driver.findElements(EMPLOYEE_CLASS);
	}
	
	public static void addEmployee(WebDriver driver) {
		driver.findElement(ADDEMPLOYEE_ID).click();
		WebElement[] elements = new WebElement[3]; // firstName, lastName, email
		Utils.sleep(3);
		for (int i = 0; i < elements.length; i++) {
			elements[i] = driver.findElement(By.id("_as" + (i == 0 ? "f" : i == 1 ? "l" : "e") + 1));
			elements[i].click();
			elements[i].sendKeys(EMPLOYEE[i]);
		}
		driver.findElement(SAVEEMPLOYEE_ID).click();
	}

	public static int addEmployees(WebDriver driver) {
		driver.findElement(ADDEMPLOYEE_ID).click();
		List<String[]> listOfValues = Utils.readData(FILE_NAME, SHEET_NAME);
		for (int i = 0; i < listOfValues.size(); i++) {
			WebElement[] elements = new WebElement[3];
			Utils.sleep(3);
			for (int j = 0; j < elements.length; j++) {
				elements[j] = driver.findElement(By.id("_as" + (j == 0 ? "f" : j == 1 ? "l" : "e") + (i + 1)));
				elements[j].click();
				elements[j].sendKeys(listOfValues.get(i)[j]);
			}
		}
		driver.findElement(SAVEEMPLOYEE_ID).click();
		return listOfValues.size();
	}
	
	public static void editNameAndPic(WebDriver driver) {
		driver.findElement(EMPLOYEE_HREF).click();
		Utils.sleep(2);
		driver.findElement(EMPLOYEE_EDIT).click();
		Utils.sleep(2);
		WebElement fName = driver.findElement(EDIT_FNAME_ID);
		fName.click();
		fName.clear();
		fName.sendKeys(EDIT_NAME);
		uploadPic(driver);
		driver.findElement(EDIT_UPDATE_NAME).click();
		driver.findElement(STAFF_ID).click();
	}
	
	public static void uploadPic(WebDriver driver) {
		WebElement upload = driver.findElement(EDIT_PIC_ID);
		upload.click();
		Utils.uploadFile(IMG_PATH);
	}

	public static By getAllstaffLtxt() {
		return ALLSTAFF_LTXT;
	}

	public List<WebElement> getEmployees() {
		return employees;
	}

	public static By getStaffId() {
		return STAFF_ID;
	}
}
