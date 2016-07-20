package com.deepsenselab.automation.ieltsregister;

import com.deepsenselab.automation.BasePage;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import static org.openqa.selenium.By.id;

public class DefaultPage extends BasePage {
    public DefaultPage(WebDriver delegate) {
        super(delegate);
        url().shouldMatch(".*ielts.britishcouncil.org/Default.aspx");
    }

    protected FluentWebElement yourCountryField(){
        return select(id("ctl00_ContentPlaceHolder1_ddlCountry"));
    }
    protected FluentWebElement continueField(){
        return element(id("ctl00_ContentPlaceHolder1_imgbRegisterBtn"));
    }

}
