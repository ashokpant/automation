package com.deepsenselab.automation.ieltsregister;

import com.deepsenselab.automation.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Period;

import java.util.List;

import static org.openqa.selenium.By.id;

public class CheckAvailabilityPage extends BasePage {
    public CheckAvailabilityPage(WebDriver delegate) {
        super(delegate);
        url().within(Period.secs(3)).shouldContain("CheckAvailability");
    }

    protected FluentWebElement dateField(){
        return select(id("ctl00_ContentPlaceHolder1_ddlDateMonthYear"));
    }
    protected FluentWebElement townField(){
        return select(id("ctl00_ContentPlaceHolder1_ddlTownCityVenue"));
    }
    protected FluentWebElement moduleField(){
        return select(id("ctl00_ContentPlaceHolder1_ddlModule"));
    }
    protected FluentWebElement findAgainField(){
        return element(id("ctl00_ContentPlaceHolder1_imgbSearchAgain"));
    }
    protected FluentWebElement availabilityRootField(){
        return element(id("ctl00_ContentPlaceHolder1_pnlTestResults"));
            }

    protected FluentWebElement selectedTownValueField(){
        return element(id("searchCriteriaBoxCriteriaValue"));
    }

    protected FluentWebElement headerField() {return element(By.id("ctl00_ContentPlaceHolder1_rptVenue_ctl00_pnlHeader"));}
   protected FluentWebElement tableField(){ return element(By.className("pnlBodyDetailRowBoxOuter"));}
    protected List<FluentWebElement> rowsField() {return tableField().elements(By.className("pnlBodyDetailRowBox"));}

}
