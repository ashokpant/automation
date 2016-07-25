package com.deepsenselab.automation.ieltsregister;

import com.deepsenselab.automation.BasePage;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Period;

import static org.openqa.selenium.By.id;

public class SummaryPage extends BasePage {
    public SummaryPage(WebDriver delegate) {
        super(delegate);
        url().within(Period.secs(60)).shouldContain("Confirmation");
    }

    protected FluentWebElement paymentMethodField(){return select(id("ddlPaymentMethod"));}
    protected FluentWebElement applyNowField(){
        return element(id("ctl00_ContentPlaceHolder1_ibtnBookNow"));
    }
}
