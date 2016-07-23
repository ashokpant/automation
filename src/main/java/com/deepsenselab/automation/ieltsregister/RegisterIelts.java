package com.deepsenselab.automation.ieltsregister;

import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RegisterIelts {
    private WebDriver wd = null;
    ScreenRecorder screenRecorder = null;
    double time= 0;

    public void makeWebDriverAndGotoSite() {
        System.setProperty("webdriver.chrome.driver","/home/ashok/Projects/ashok/automation/automation/lib/chromedriver");

        /*HashMap<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.images", 1);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        DesiredCapabilities chromeCaps = DesiredCapabilities.chrome();
        chromeCaps.setCapability(ChromeOptions.CAPABILITY, options);*/
        time = System.currentTimeMillis();
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wd.get("https://ielts.britishcouncil.org/Default.aspx");


      /*  //Create a instance of GraphicsConfiguration to get the Graphics configuration
        //of the Screen. This is needed for ScreenRecorder class.
        GraphicsConfiguration gc = GraphicsEnvironment//
                .getLocalGraphicsEnvironment()//
                .getDefaultScreenDevice()//
                .getDefaultConfiguration();

        //Create a instance of ScreenRecorder with the required configurations
        try {
            screenRecorder = new ScreenRecorder(gc,
                    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            VideoFormatKeys.CompressorNameKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                            VideoFormatKeys.DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
                            VideoFormatKeys.QualityKey, 1.0f,
                            KeyFrameIntervalKey, (int) (15 * 60)),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,"black",
                            FrameRateKey, Rational.valueOf(30)),
                    null);
            screenRecorder.start();
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }*/
    }

    public void killWebDriver() {
        try {
            System.out.println("Total time: "+((System.currentTimeMillis()-time)/1000)+ " secs.");
            Thread.sleep(120000);
            wd.quit();
            //screenRecorder.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public double registerCore(CandidateDetails candidate) {
        makeWebDriverAndGotoSite();

        new DefaultPage(wd) {{
            yourCountryField().sendKeys(candidate.getBookingCountry());
            System.out.println("Booking country: "+candidate.getBookingCountry());
            System.out.println("Continue...");
            continueField().click();
        }};

        new FindTestDatePage(wd){{
            String date = candidate.getBookingDate();
            String townCity = candidate.getBookingTown();
            String module = candidate.getBookingModule();

            dateField().sendKeys(date);
            townField().sendKeys(townCity);
            moduleField().sendKeys(module);
            System.out.println("Booking month: "+date);
            System.out.println("Booking city: "+townCity);
            System.out.println("Booking module: "+module);
            System.out.println("Continue...");
            findField().click();
        }};

        new CheckAvailabilityPage(wd){{
            String date = candidate.getBookingExactDate();//"27 August 2016";
            System.out.println("Booking date: "+date);
            //System.out.println("header: "+headerField().getText());

            WebElement container = wd.findElement(By.id("ctl00_ContentPlaceHolder1_pnlTestResults"));

            String expr = "//*[boolean(number(substring-before(substring-after(@id, 'ctl00_ContentPlaceHolder1_rptVenue_ctl'), '_pnlHeader')))]";
           // String expr = "//*[matches(@id, 'ctl00_ContentPlaceHolder1_rptVenue_ctl\\d+_pnlHeader')]";
            //String expr = "//*[start-with(@id, 'ctl00_ContentPlaceHolder1_rptVenue_ctl') and end-with(@id, '_pnlHeader')]";
            List<WebElement> headers = container.findElements(By.xpath(expr));
            List<WebElement> townAndDates = new ArrayList<>();
            WebElement firstHeader = container.findElement(By.id("ctl00_ContentPlaceHolder1_rptVenue_ctl00_pnlHeader"));
            townAndDates.add(firstHeader);
            townAndDates.addAll(headers);

            for(WebElement header : townAndDates) {
                System.out.println("header: "+header.getText());
            }

            boolean foundDate = false;

            while(!foundDate) {
                List<FluentWebElement> rows = rowsField();
                for (FluentWebElement row : rows) {
                    System.out.println(row.divs().get(0).getText().toString() + " : " + row.divs().get(3).getText().toString());
                    if ((Objects.equals(row.divs().get(0).getText().toString(), date)) && (row.divs().get(3).getText().toString().contains("Available"))) {
                        System.out.println("Found date, applying!!!");
                        foundDate = true;
                        row.divs().get(4).click();
                        break;
                    }
                    System.out.println();
                }
                if(!foundDate) try {
                    Thread.sleep(30000);
                    wd.navigate().refresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }};


        new TermsAndConditionsPage(wd){{
            agreeField().click();
            System.out.println("Term and conditions accepted.");

            System.out.println("Continue...");
            continueField().click();
        }};

        new CandidateDetailsPage(wd){{
            System.out.println("Filling candidate details...");
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
            verifyCaptchaIfPresent();
            System.out.println("Continue...");
            continueField().click();
        }};

        new SummaryPage(wd){{
            if(candidate.getBookingCountry().equals("India")){
                paymentMethodField().sendKeys("Pay Later - using an alternative payment method"); //other option is "Pay Now - using Credit/Debit Card"}
            }
            //applyNowField().click();
            System.out.println("Done.");
        }};
        double t = ((System.currentTimeMillis()-time)/1000);
        return t;
    }

    public void register(CandidateDetails candidate) {
       registerCore(candidate);
        killWebDriver();
    }

    public static void main(String[] args) {
        String excelFilePath = "/home/ashok/Projects/ashok/automation/automation/data/candidates.xlsx";
        IO reader = new IO();
        List<CandidateDetails> candidates = null;
        RegisterIelts registerIelts = new RegisterIelts();
        try {
            candidates = reader.readCandidateDetailsFromExcelFile(excelFilePath);
            for(int i = 1; i <candidates.size(); ++i){
                CandidateDetails candidate = candidates.get(i);
                registerIelts.register(candidate);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
