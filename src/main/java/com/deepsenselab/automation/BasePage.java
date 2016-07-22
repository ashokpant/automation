package com.deepsenselab.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.FluentWebDriver;
import org.seleniumhq.selenium.fluent.monitors.CompositeMonitor;
import org.seleniumhq.selenium.fluent.monitors.HighlightOnError;
import org.seleniumhq.selenium.fluent.monitors.ScreenShotOnError;

public class BasePage extends FluentWebDriver {

    public BasePage(WebDriver delegate) {
        super(delegate,
                new CompositeMonitor(WholeSuiteListener.codahaleMetricsMonitor,
                new HighlightOnError(delegate),
                new ScreenShotOnError.WithUnitTestFrameWorkContext((TakesScreenshot) delegate, BasePage.class, "test-classes", "surefire-reports")));
    }

    protected boolean isElementPresent(By by){
        try {
            if(element(by).isDisplayed().value()) return true;
        }
        catch (org.openqa.selenium.NoSuchElementException e){
            return false;
        }
        return false;
    }
}
