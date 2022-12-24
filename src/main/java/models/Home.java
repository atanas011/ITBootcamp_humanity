package models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Home {

    private static final String HOME_URL = "https://www.humanity.com";
    private static final By ABOUT_CSS = By.cssSelector("#navbarSupportedContent a[href*='about']");
    private static final By LOGIN_CSS = By.linkText("Login");

    public static String getUrl() {
        return HOME_URL;
    }

    public static void openAbout(WebDriver driver) {
        driver.findElement(ABOUT_CSS).click();
    }

    public static void openLogin(WebDriver driver) {
        driver.findElement(LOGIN_CSS).click();
    }
}
