package com.deepsenselab.automation;

/**
 * Created by Ashok K. Pant (ashokpant87@gmail.com) on 7/18/16.
 */

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * Search Google example.
 *
 * @author Ashok
 */
public class GoogleSearch {
    private String baseUrl;
    private WebDriver driver;
    private ScreenshotHelper screenshotHelper;

    @Before
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        baseUrl = "http://www.google.com";

        driver = new ChromeDriver();
        driver.get(baseUrl);
        screenshotHelper = new ScreenshotHelper();
    }

    @After
    public void saveScreenshotAndCloseBrowser() throws IOException, InterruptedException {
        screenshotHelper.saveScreenshot("screenshot.png");
        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    public void googleSearch() throws IOException {
        assertEquals("The page title should equal Google at the start of the test.", "Google", driver.getTitle());
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys("Hello World!");
        searchField.submit();
    }

    private class ScreenshotHelper {
        public void saveScreenshot(String screenshotFileName) throws IOException {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(screenshotFileName));
        }
    }
}
