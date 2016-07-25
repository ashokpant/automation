package com.deepsenselab.automation.ieltsregister;

import com.deepsenselab.automation.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Period;

import static org.openqa.selenium.By.id;

public class CandidateDetailsPage extends BasePage {
    public CandidateDetailsPage(WebDriver delegate) {
        super(delegate);
        url().within(Period.secs(60)).shouldContain("CandidateDetails");
    }
    protected FluentWebElement titleField(){
        return select(id("ctl00_ContentPlaceHolder1_ddlTitle"));
    }
    protected FluentWebElement firstNameField(){
        return input(id("ctl00_ContentPlaceHolder1_txtOtherNames"));
    }
    protected FluentWebElement lastNameField(){return input(id("ctl00_ContentPlaceHolder1_txtFamilyName"));}
    protected FluentWebElement firstLanguageField(){return select(id("ctl00_ContentPlaceHolder1_ddlFirstLanguage"));}
    protected FluentWebElement nationalityField(){return select(id("ctl00_ContentPlaceHolder1_ddlCountryRegion"));}
    protected FluentWebElement emailField(){return input(id("ctl00_ContentPlaceHolder1_txtEmail"));}
    protected FluentWebElement confirmEmailField(){return input(id("ctl00_ContentPlaceHolder1_txtEmailConfirm"));}
    protected FluentWebElement birthDayField(){return select(id("ctl00_ContentPlaceHolder1_ddlDays"));}
    protected FluentWebElement birthMonthField(){return select(id("ctl00_ContentPlaceHolder1_ddlMonths"));}
    protected FluentWebElement birthYearField(){return select(id("ctl00_ContentPlaceHolder1_ddlYears"));}
    protected FluentWebElement identificationDocField(){return select(id("ctl00_ContentPlaceHolder1_ddlIdDocument"));}
    protected FluentWebElement identificationDocNumberField(){return input(id("ctl00_ContentPlaceHolder1_txtIdDocumentNumber"));}
    protected FluentWebElement identificationDocExpiryDayField(){return select(id("ctl00_ContentPlaceHolder1_ddlDocIdDay"));}
    protected FluentWebElement identificationDocExpiryMonthField(){return select(id("ctl00_ContentPlaceHolder1_ddlDocIdMonth"));}
    protected FluentWebElement identificationDocExpiryYearField(){return select(id("ctl00_ContentPlaceHolder1_ddlDocIdYear"));}
    protected FluentWebElement genderMaleField(){return element(id("ctl00_ContentPlaceHolder1_radGenderMale"));}
    protected FluentWebElement genderFemaleField(){return element(id("ctl00_ContentPlaceHolder1_radGenderFemale"));}
    protected FluentWebElement addressField(){return input(id("ctl00_ContentPlaceHolder1_txtAddr1"));}
    protected FluentWebElement countryField(){return select(id("ctl00_ContentPlaceHolder1_ddlAddrCountry"));}
    protected FluentWebElement occupationSectorField(){return select(id("ctl00_ContentPlaceHolder1_ddlOccupationSector"));}
    protected FluentWebElement occupationStatusField(){return select(id("ctl00_ContentPlaceHolder1_ddlOccupationStatus"));}
    protected FluentWebElement reasonForTestField(){return select(id("ctl00_ContentPlaceHolder1_ddlReasonForTest"));}
    protected FluentWebElement destinationCountryField(){return select(id("ctl00_ContentPlaceHolder1_ddlDestinationCountry"));}
    protected FluentWebElement educationLabelField(){return select(id("ctl00_ContentPlaceHolder1_ddlEducationLevel"));}
    protected FluentWebElement englishStudyYearsField(){return select(id("ctl00_ContentPlaceHolder1_ddlEnglishStudyInYears"));}
    protected FluentWebElement continueField(){return element(id("ctl00_ContentPlaceHolder1_ibtnContinue"));}

    final String CAPTCHA_DIV_ID= "ctl00_ContentPlaceHolder1_divIsEnabledRecapsha";
    final String CAPTCHA_CHECKBOX_ID= "recaptcha-anchor";
    int maxWaitSeconds = 600;
    void verifyCaptchaIfPresent() {
        if (isElementPresent(By.id(CAPTCHA_DIV_ID))) {
            delegate.switchTo().defaultContent();
            delegate.switchTo().frame(0);

            if (isElementPresent(By.id(CAPTCHA_CHECKBOX_ID))) {
                System.out.println("Verify captcha.");
                WebElement captchaCheckBox = delegate.findElement(By.id(CAPTCHA_CHECKBOX_ID));
                captchaCheckBox.click();

                WebDriverWait wait = new WebDriverWait(delegate, maxWaitSeconds, 2000);

                wait.until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        WebElement span = driver.findElement(By.id(CAPTCHA_CHECKBOX_ID));
                        String enabled = span.getAttribute("aria-checked");
                        if (enabled.equals("true")) {
                            System.out.println("Captcha verified!");
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
            }
            delegate.switchTo().defaultContent();
        } else {
            System.out.println("Captcha not present.");
        }
    }
}
