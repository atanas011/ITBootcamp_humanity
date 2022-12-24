package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

import models.Dashboard;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Utils {

    private static final By LINKS_CLASS = By.className("lft");
    private static final By ITEMS_CLASS = By.className("primNavQtip__itemName");
    private static final String IMG_PATH = "./humanity_img/screenshot.jpg";

    public static void checkLinks(WebDriver driver) {
        String homePage = Dashboard.getUrl();
        List<WebElement> links = driver.findElements(LINKS_CLASS);
        List<WebElement> items = driver.findElements(ITEMS_CLASS);

        for (int i = 0; i < links.size(); i++) {
            String url = links.get(i).getAttribute("href");
            String itemName = items.get(i).getAttribute("innerText");

            if (url == null || url.isEmpty()) {
                System.out.println("URL is either not configured for anchor tag or it is empty.");
                continue;
            }
            if (!url.startsWith(homePage)) {
                System.out.println("URL belongs to another domain, skipping it.");
                continue;
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
                conn.setRequestMethod("HEAD");
                conn.connect();
                int resCode = conn.getResponseCode();
                System.out.println(itemName + ": " + url + " is a " +
                        ((resCode > 399) ? "broken" : "valid ") + "link.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void explicitlyWait(WebDriver driver, int sec, By locator) { // old
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }

//    public static String getDate() { // old
//        GregorianCalendar date = new GregorianCalendar();
//        String year = "" + date.get(Calendar.YEAR);
//        String month = "" + (date.get(Calendar.MONTH) + 1);
//        String yesterday = "" + (date.get(Calendar.DATE) - 1);
//        return year + "-" + month + "-" + yesterday;
//    }

//    public static void mouseOver(WebDriver driver, WebElement element) { // old
//        Actions builder = new Actions(driver);
//        Action mouseOver = builder.moveToElement(element).build();
//        mouseOver.perform();
//    }

    public static List<String[]> readData(String fileName, String sheetName) {
        File file = new File(fileName);
        try {
            InputStream in = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(in);
            Sheet sheet = wb.getSheet(sheetName);

            int lastRow = sheet.getLastRowNum();
            int lastCol = sheet.getRow(0).getLastCellNum();

            String[] values = new String[lastRow];
            List<String[]> listOfValues = new ArrayList<>();

            for (int i = 1; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < lastCol; j++) {
                    values[j] = row.getCell(j).toString();
                }
                listOfValues.add(values);
                values = new String[values.length];
            }
            wb.close();
            return listOfValues;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void switchTab(WebDriver driver) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public static void takeScreenshot(WebDriver driver, int ms) {
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies
                .viewportPasting(ms)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(), "jpg", new File(IMG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void takeSnapShot(WebDriver driver) {
//        TakesScreenshot scrShot = ((TakesScreenshot) driver);
//        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
//        File destFile = new File(IMG_PATH);
//        try {
//            FileUtils.copyFile(srcFile, destFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void uploadFile(String filePath) {
        StringSelection strSelect = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strSelect, null);
        try {
            Robot robot = new Robot();
            robot.delay(10000); // 300 doesn't work !!!
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(200);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

//    public static void zoomOut() { // to show whole navbar (old)
//        try {
//            Robot robot = new Robot();
//            robot.keyPress(KeyEvent.VK_CONTROL);
//            robot.keyPress(KeyEvent.VK_MINUS);
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }
}
