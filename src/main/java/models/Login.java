package models;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class Login {

    private final static By EMAIL_ID = By.id("email");
    private final static By PASS_ID = By.id("password");
    private final static By LOGIN_NAME = By.name("login");
    private final static String EMAIL = "mata@yahoo.com";
    private final static String PASS = "mata123";

    public static void login(WebDriver driver) {
        driver.findElement(EMAIL_ID).sendKeys(EMAIL);
        driver.findElement(PASS_ID).sendKeys(PASS);
        driver.findElement(LOGIN_NAME).sendKeys(Keys.ENTER);
    }
}
