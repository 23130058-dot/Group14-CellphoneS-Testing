package com.example.Assignment7;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class Tc01 {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private JavascriptExecutor js;

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testTc01() throws Exception {
    //ERROR: Caught exception [ERROR: Unsupported command [loadVars | TestData_Login_Smember.csv | ]]
    System.out.println("Đang test: ${Data_ID} - ${Test_Description}");
    driver.get("https://smember.com.vn/login");
    Thread.sleep(2000);
    driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']")).click();
    driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']")).clear();
    driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại của bạn']")).sendKeys("${Phone_Number}");
    driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']")).click();
    driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']")).clear();
    driver.findElement(By.xpath("//input[@placeholder='Nhập mật khẩu của bạn']")).sendKeys("${Password}");
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    Thread.sleep(2000);
    //ERROR: Caught exception [ERROR: Unsupported command [endLoadVars |  | ]]
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
