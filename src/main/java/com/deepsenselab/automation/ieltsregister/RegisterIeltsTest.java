package com.deepsenselab.automation.ieltsregister;

import com.deepsenselab.automation.WholeSuiteListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Monitor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.assertThat;

public class RegisterIeltsTest {
    private WebDriver wd;
    private Monitor.Timer bizOperationTiming;
    private int claimedUnitedPrice;
    private String hipmunkWindowHandle;

    @Before
    public void makeWebDriverAndGotoSite() {
        System.setProperty("webdriver.chrome.driver","/home/ashok/Projects/ashok/automation/automation/lib/chromedriver");

        /*HashMap<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.images", 2);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        DesiredCapabilities chromeCaps = DesiredCapabilities.chrome();
        chromeCaps.setCapability(ChromeOptions.CAPABILITY, options);

        wd = new ChromeDriver(chromeCaps);*/

        wd = new ChromeDriver();
        wd.get("https://ielts.britishcouncil.org/Default.aspx");
    }

    @After
    public void killWebDriver() {
        try {
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
            continueField().click();
        }};

        new FindTestDatePage(wd){{
            String date = candidate.getBookingDate(); //01/08/216
            String townCity = candidate.getBookingTown();
            String module = candidate.getBookingModule();
            dateField().sendKeys(date);
            townField().sendKeys(townCity);
            moduleField().sendKeys(module);
            findField().click();
        }};

        new CheckAvailabilityPage(wd){{
            String date = candidate.getBookingExactDate();//"27 August 2016";

            System.out.println("header: "+headerField().getText());
            List<FluentWebElement> rows = rowsField();

            for(FluentWebElement row : rows) {
                System.out.println(row.divs().get(0).getText().toString()+ " : "+row.divs().get(3).getText().toString());
                if((Objects.equals(row.divs().get(0).getText().toString(), date)) && (Objects.equals(row.divs().get(3).getText().toString(), "Available"))){
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

            continueField().click();
            System.out.println("Continue...");
        }};

        new CandidateDetailsPage(wd){{
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
            continueField().click();
        }};

        new SummaryPage(wd){{
            if(candidate.getBookingCountry().equals("India")){
                paymentMethodField().sendKeys("Pay Later - using an alternative payment method"); //other option is "Pay Now - using Credit/Debit Card"}
            }
            applyNowField().click();
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
