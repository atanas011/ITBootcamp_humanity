package models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.Utils;

public class Settings {
	
	public static final By SETTINGS_ID = By.id("sn_admin");
	public static final By CHECK1_NAME = By.name("pref_email");
	public static final By CHECK2_NAME = By.name("pref_sms");
	public static final By CHECK3_NAME = By.name("pref_mobile_push");
	public static final By SAVEBTN_ID = By.id("_save_settings_button");

	public static void uncheckNots(WebDriver driver) {
		driver.findElement(SETTINGS_ID).click();
		Utils.sleep(1);
		driver.findElement(CHECK1_NAME).click();
		driver.findElement(CHECK2_NAME).click();
		driver.findElement(CHECK3_NAME).click();
		driver.findElement(SAVEBTN_ID).click();
		Utils.sleep(3);
	}
}
