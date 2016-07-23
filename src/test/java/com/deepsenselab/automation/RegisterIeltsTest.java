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
    @Test
    public void registerIeltsTest() {
        CandidateDetails candidate = Fun.getCandidateDetails();
        RegisterIelts registerIelts = new RegisterIelts();
        registerIelts.register(candidate);
    }
}
