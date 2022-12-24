package tests;

import utils.models.*;
import models.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import utils.Utils;

/**
 * Zavrsni projekat pise se postujuci POM (page object model).
 * Testovi se pisu samo za "happy path" (pozitivne scenarije).
 * Testira se sajt www.humanity.com.
 * Kao pripremu za izradu projekta potrebno je napraviti lazni nalog na tom sajtu
 * i zapoceti besplatnu probnu verziju (30 dana). Ne ostavljati prave e-mail adrese.
 * Kredencijale izmisliti ili koristiti neki sajt za generisanje
 * i sacuvati ih radi kasnijeg koriscenja.
 * Full Name: Mata Zamlata
 * Email: mata@yahoo.com
 * Company name: city
 * Company size: 0-100
 * Industry: Non-profit
 * Functional role: Other
 * Phone number: 123456
 * Password: mata123
 * Save password
 * ZADACI:
 * 1) Homepage
 * a) Doci do stranice About Us i napraviti screenshot strane.
 * b) Proveriti da li postojeci user moze uspesno da se uloguje.
 * 2) Dashboard
 * a) Proveriti sve elemente iz gornjeg menija - da li naziv iz menija
 * odgovara strani na koju sajt redirektuje prilikom kliktanja na dugme.
 * b) Dodati novog zaposlenog i proveriti uspesnost dodavanja.
 * c) Zaposlenom promeniti ime.
 * d) Zaposlenom dodati sliku
 * e) Koristeci apachePOI (ucitavanjem iz excel tabele), dodati minimum 5 novih zaposlenih
 * i proveriti uspesnost dodavanja. Sami kreirate tabelu sa potrebnim podacima.
 * 3) Settings
 * b) Odcekirati (disable) notifikacije.
 * 4) Profile
 * a) Promeniti jezik.
 */

public class TestHumanity {

    private WebDriver driver;

    @BeforeTest
    public void createDriver() {
        driver = new EdgeDriver();
    }

    @BeforeClass
    public void setUp() {
        driver.get(Home.getUrl());
        driver.manage().window().maximize();
    }

    @Test
//    @Ignore
    public void testAbout() {
        Home.openAbout(driver);
        Utils.takeScreenshot(driver, 300);
    }

    @Test(priority = 1)
    public void testLogin() {
        Home.openLogin(driver);
        Utils.switchTab(driver);
        Login.login(driver);
        Utils.sleep(2);
        String act = driver.getCurrentUrl();
        String exp = Dashboard.getDashUrl();
        Assert.assertEquals(act, exp);
    }

    @Test(priority = 2)
    public void testNavLinks() {
        Utils.checkLinks(driver);
    }

    @Test(priority = 3)
    // delete added employee before running this (only works with unique email)
    public void testAddEmployee() {
        driver.findElement(Staff.getStaffId()).click();
        Utils.sleep(2);
        Staff oldStaff = new Staff(driver);
        Staff.addEmployee(driver);
        Utils.sleep(4);
        driver.findElement(Staff.getAllstaffLtxt()).click();
        Utils.sleep(2);
        Staff newStaff = new Staff(driver);
        Assert.assertEquals(oldStaff.getEmployees().size() + 1,
                newStaff.getEmployees().size());
    }

    @Test(priority = 4)
    public void testEditNameAndPic() {
        driver.findElement(Staff.getStaffId()).click();
        Utils.sleep(2);
//        System.out.print("img path:" + Staff.IMG_PATH);
        Staff.editNameAndPic(driver);
    }

    @Test(priority = 5)
    // bulk delete added employees before running this (only works with unique emails)
    public void testAddEmployees() {
        driver.findElement(Staff.getStaffId()).click();
        Utils.sleep(2);
        Staff oldStaff = new Staff(driver);
        System.out.println("Old staff: " + oldStaff.getEmployees().size());
        int num = Staff.addEmployees(driver);
        Utils.sleep(4);
        driver.findElement(Staff.getAllstaffLtxt()).click();
        Utils.sleep(2);
        Staff newStaff = new Staff(driver);
        System.out.println("New staff: " + newStaff.getEmployees().size());
        Assert.assertEquals(oldStaff.getEmployees().size() + num,
                newStaff.getEmployees().size());
    }

    @Test(priority = 6)
    public void testUncheckNots() {
        Settings.uncheckNots(driver);
    }

    @Test(priority = 7)
    public void testChangeLang() {
        Profile.changeLang(driver);
    }

    @AfterTest
    public void tearDown() {
        Utils.sleep(1);
        driver.quit();
    }
}
