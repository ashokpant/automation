package com.deepsenselab.automation.ieltsregister;

import com.deepsenselab.automation.BasePage;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Period;

import static org.openqa.selenium.By.id;

public class TermsAndConditionsPage extends BasePage {
    public TermsAndConditionsPage(WebDriver delegate) {
        super(delegate);
        url().within(Period.secs(60)).shouldContain("TermsConditions");
    }

    protected FluentWebElement agreeField(){
        return element(id("ctl00_ContentPlaceHolder1_chkAccept"));
    }
    protected FluentWebElement continueField(){
        return element(id("ctl00_ContentPlaceHolder1_imgbContinue"));
    }
}
