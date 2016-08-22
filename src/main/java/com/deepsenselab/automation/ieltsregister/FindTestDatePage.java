package com.deepsenselab.automation.ieltsregister;

import com.deepsenselab.automation.BasePage;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Period;

import static org.openqa.selenium.By.id;

public class FindTestDatePage extends BasePage {
    public FindTestDatePage(WebDriver delegate) {
        super(delegate);
        url().within(Period.secs(60)).shouldContain("CountryExamSearch");
    }

    protected FluentWebElement dateField() {
        return select(id("ctl00_ContentPlaceHolder1_ddlDateMonthYear"));
    }

    protected FluentWebElement townField() {
        return select(id("ctl00_ContentPlaceHolder1_ddlTownCityVenue"));
    }

    protected FluentWebElement moduleField() {
        return select(id("ctl00_ContentPlaceHolder1_ddlModule"));
    }

    protected FluentWebElement findField() {
        return element(id("ctl00_ContentPlaceHolder1_imgbSearch"));
    }

}
