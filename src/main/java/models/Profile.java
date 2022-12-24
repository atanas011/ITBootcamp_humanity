package models;

import utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Profile {

    private static final By AVATAR_ID = By.id("tr_avatar");
    private static final By SETTINGS = By.cssSelector("#userm a[href*='edit']");
    private static final By LANG = By.id("language");
    private static final By SAVE_BTN = By.name("update");
    private static final String LETTER = "s";

    public static void changeLang(WebDriver driver) {
        driver.findElement(AVATAR_ID).click();
        Utils.sleep(3);
        driver.findElement(SETTINGS).click();
        Utils.sleep(3);
        driver.findElement(LANG).sendKeys(LETTER);
        driver.findElement(SAVE_BTN).click();
        driver.navigate().refresh();
    }
}
