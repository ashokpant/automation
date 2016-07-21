package com.deepsenselab.automation;

import com.deepsenselab.automation.ieltsregister.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RegisterIeltsTest {
    private WebDriver wd;
    WebDriverWait wait;
    private Monitor.Timer bizOperationTiming;
    private int claimedUnitedPrice;
    private String hipmunkWindowHandle;
    double time= 0;

    @Before
    public void makeWebDriverAndGotoSite() {
        System.setProperty("webdriver.chrome.driver","/home/ashok/Projects/ashok/automation/automation/lib/chromedriver");

        HashMap<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.images", 1);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        DesiredCapabilities chromeCaps = DesiredCapabilities.chrome();
        chromeCaps.setCapability(ChromeOptions.CAPABILITY, options);

        wd = new ChromeDriver(chromeCaps);
        time = System.currentTimeMillis();
        //wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wd.get("https://ielts.britishcouncil.org/Default.aspx");
    }

    @After
    public void killWebDriver() {
        try {
            System.out.println("Total time: "+((System.currentTimeMillis()-time)/1000)+ " secs.");
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wd.quit();
    }

    @Test
    public void registerIeltsTest() {
        CandidateDetails candidate = Fun.getCandidateDetails();
        new DefaultPage(wd) {{
            yourCountryField().sendKeys(candidate.getBookingCountry());
            System.out.println("Booking country: "+candidate.getBookingCountry());
            System.out.println("Continue...");
            continueField().click();
        }};

        new FindTestDatePage(wd){{
            String date = candidate.getBookingDate(); //01/08/216
            String townCity = candidate.getBookingTown();
            String module = candidate.getBookingModule();

            dateField().sendKeys(date);
            townField().sendKeys(townCity);
            moduleField().sendKeys(module);
            System.out.println("Booking month: "+date);
            System.out.println("Booking city: "+townCity);
            System.out.println("Booking module: "+module);
            System.out.println("Continue...");
            findField().click();
        }};

        new CheckAvailabilityPage(wd){{
            String date = candidate.getBookingExactDate();//"27 August 2016";
            System.out.println("Booking date: "+date);
            //System.out.println("header: "+headerField().getText());
            List<FluentWebElement> rows = rowsField();

            for(FluentWebElement row : rows) {
                System.out.println(row.divs().get(0).getText().toString()+ " : "+row.divs().get(3).getText().toString());
                if((Objects.equals(row.divs().get(0).getText().toString(), date)) && (row.divs().get(3).getText().toString().contains("Available"))){
                    System.out.println("Found date, applying!!!");
                    row.divs().get(4).click();
                    break;
                }
                System.out.println();
            }
        }};

        new TermsAndConditionsPage(wd){{
            agreeField().click();
            System.out.println("Term and conditions accepted.");

            System.out.println("Continue...");
            continueField().click();
        }};

        new CandidateDetailsPage(wd){{
            System.out.println("Filling candidate details...");
            titleField().sendKeys(candidate.getTitle());
            firstNameField().sendKeys(candidate.getFirstName());
            lastNameField().sendKeys(candidate.getLastName());
            firstLanguageField().sendKeys(candidate.getFirstLanguage());
            nationalityField().sendKeys(candidate.getNationality());
            emailField().sendKeys(candidate.getEmail());
            confirmEmailField().sendKeys(candidate.getEmail());
            birthDayField().sendKeys(candidate.getBirthDay());
            birthMonthField().sendKeys(candidate.getBirthMonth());
            birthYearField().sendKeys(candidate.getBirthYear());
            identificationDocField().sendKeys(candidate.getIdentificationDoc());
            identificationDocNumberField().sendKeys(candidate.getIdentificationDocNumber());
            identificationDocExpiryDayField().sendKeys(candidate.getIdentificationDocExpiryDay());
            identificationDocExpiryMonthField().sendKeys(candidate.getIdentificationDocExpiryMonth());
            identificationDocExpiryYearField().sendKeys(candidate.getIdentificationDocExpiryYear());
            if("Male".equals(candidate.getGender()))genderMaleField().click();
            else genderFemaleField().click();
            addressField().sendKeys(candidate.getAddress());
            countryField().sendKeys(candidate.getCountry());
            occupationSectorField().sendKeys(candidate.getOccupationSector());
            occupationStatusField().sendKeys(candidate.getOccupationStatus());
            reasonForTestField().sendKeys(candidate.getReasonForTest());
            destinationCountryField().sendKeys(candidate.getDestinationCountry());
            educationLabelField().sendKeys(candidate.getEducationLabel());
            englishStudyYearsField().sendKeys(candidate.getEnglishStudyYears());

            if(isElementPresent(By.id("ctl00_ContentPlaceHolder1_divIsEnabledRecapsha"))){
                wd.switchTo().defaultContent();
                wd.switchTo().frame(0);

                if(isElementPresent(By.id("recaptcha-anchor"))){
                    System.out.println("Verify captcha.");
                    WebElement captchaCheckBox =  wd.findElement(By.id("recaptcha-anchor"));
                    captchaCheckBox.click();

                    WebDriverWait wait = new WebDriverWait(wd,300,2000);

                    wait.until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver) {
                            WebElement span =  driver.findElement(By.id("recaptcha-anchor"));
                            String enabled = span.getAttribute("aria-checked");
                            if(enabled.equals("true")){
                                System.out.println("Captcha verified!");
                                return true;
                            }else{
                                return false;
                            }
                        }
                    });
                }
                wd.switchTo().defaultContent();
            }else{
                System.out.println("Captcha not present.");
            }

            System.out.println("Continue...");
            continueField().click();
        }};

        new SummaryPage(wd){{
            if(candidate.getBookingCountry().equals("India")){
                paymentMethodField().sendKeys("Pay Later - using an alternative payment method"); //other option is "Pay Now - using Credit/Debit Card"}
            }
            //applyNowField().click();
            System.out.println("Done.");
        }};

    }

    private void changeWebDriverWindow(WebDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        for (String popupHandle : handles) {
            if (!popupHandle.contains(hipmunkWindowHandle)) {
                driver.switchTo().window(popupHandle);
            }
        }
    }

    private void timeBizOperation(String description) {
        bizOperationTiming = WholeSuiteListener.codahaleMetricsMonitor.start(description + " (End User Experience)");
    }

}
