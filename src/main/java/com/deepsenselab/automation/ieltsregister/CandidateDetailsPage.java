package com.deepsenselab.automation.ieltsregister;

import com.deepsenselab.automation.BasePage;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Period;

import static org.openqa.selenium.By.id;

public class CandidateDetailsPage extends BasePage {
    public CandidateDetailsPage(WebDriver delegate) {
        super(delegate);
        url().within(Period.secs(2)).shouldContain("CandidateDetails");
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

}
