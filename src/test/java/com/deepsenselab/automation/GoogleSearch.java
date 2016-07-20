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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
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
            //baseUrl = System.getProperty("webdriver.base.url");
            System.setProperty("webdriver.chrome.driver","/home/ashok/Projects/ashok/automation/automation/lib/chromedriver");
            baseUrl = "http://www.google.com";

            driver = new ChromeDriver();
            driver.get(baseUrl);
            screenshotHelper = new ScreenshotHelper();
        }

        @After
        public void saveScreenshotAndCloseBrowser() throws IOException, InterruptedException {
            screenshotHelper.saveScreenshot("screenshot.png");
            Thread.sleep(10000);
            driver.quit();
        }

        @Test
        public void pageTitleAfterSearchShouldBeginWithDrupal() throws IOException {
            assertEquals("The page title should equal Google at the start of the test.", "Google", driver.getTitle());
            WebElement searchField = driver.findElement(By.name("q"));
            searchField.sendKeys("Hello World!");
            searchField.submit();
            assertTrue("The page title should start with the search string after the search.",
                    (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver d) {
                            return d.getTitle().toLowerCase().startsWith("Hello world!");
                        }
                    })
            );

        }

        private class ScreenshotHelper {

            public void saveScreenshot(String screenshotFileName) throws IOException {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot, new File(screenshotFileName));
            }
        }
    }
