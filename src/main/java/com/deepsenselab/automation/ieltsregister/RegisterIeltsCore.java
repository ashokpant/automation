package com.deepsenselab.automation.ieltsregister;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RegisterIeltsCore {
    private WebDriver wd = null;
    double time = 0;

    public void makeWebDriverAndGotoSite() {
       if(System.getProperty("os.name").startsWith("Linux")){
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
       }else{
           System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
       }

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
    }

    public void killWebDriver() {
        try {
            System.out.println("Total time: " + ((System.currentTimeMillis() - time) / 1000) + " secs.");
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
            System.out.println("Booking country: " + candidate.getBookingCountry());
            System.out.println("Continue...");
            continueField().click();
        }};

        new FindTestDatePage(wd) {{
            String date = candidate.getBookingDate();
            String townCity = candidate.getBookingTown();
            String module = candidate.getBookingModule();

            dateField().sendKeys(date);
            townField().sendKeys(townCity);
            moduleField().sendKeys(module);
            System.out.println("Booking month: " + date);
            System.out.println("Booking city: " + townCity);
            System.out.println("Booking module: " + module);
            System.out.println("Continue...");
            findField().click();
        }};

        new CheckAvailabilityPage(wd) {{
            String date = candidate.getBookingExactDate();//"27 August 2016";
            System.out.println("Booking date: " + date);

            boolean foundDate = false;
            while (!foundDate) {
                List<Availability> availabilities = getAvailability();
                for (Availability availability : availabilities) {
                    System.out.println("Town: " + availability.getTown().getText());
                    if (availability.getTown().findElement(By.className("searchCriteriaBoxCriteriaValue")).getText().toLowerCase().contains(candidate.getBookingTown().toLowerCase())) {
                        for (AvailabilityDetail detail : availability.getDateDetails()) {
                            System.out.print(detail.getDate().getText() + " - " + detail.getFee().getText() + " - " + detail.getAvailability().getText());
                            if ((Objects.equals(detail.getDate().getText(), date)) && (detail.getAvailability().getText().contains("Available"))) {
                                System.out.print(" <= Found date, applying!!!");
                                foundDate = true;
                                detail.getStatus().click();
                                break;
                            }
                            System.out.println();
                        }
                    }
                    System.out.println();
                    if (foundDate) break;
                }
                if (!foundDate) try {
                    Thread.sleep(15000);
                    wd.navigate().refresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }};

        new TermsAndConditionsPage(wd) {{
            agreeField().click();
            System.out.println("Term and conditions accepted.");

            System.out.println("Continue...");
            continueField().click();
        }};

        new CandidateDetailsPage(wd) {{
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
            if ("Male".equals(candidate.getGender())) genderMaleField().click();
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

        new SummaryPage(wd) {{
            if (candidate.getBookingCountry().equals("India")) {
                paymentMethodField().sendKeys("Pay Later - using an alternative payment method"); //other option is "Pay Now - using Credit/Debit Card"}
            }
            //applyNowField().click();
            System.out.println("Done.");
        }};
        double t = ((System.currentTimeMillis() - time) / 1000);
        return t;
    }

    public void register(CandidateDetails candidate) {
        registerCore(candidate);
        killWebDriver();
    }

    public static void main(String[] args) {
        String excelFilePath = "data/candidates.xlsx";
        IO reader = new IO();
        List<CandidateDetails> candidates = null;
        RegisterIeltsCore registerIelts = new RegisterIeltsCore();
        try {
            candidates = reader.readCandidateDetailsFromExcelFile(excelFilePath);
            for (int i = 1; i < candidates.size(); ++i) {
                CandidateDetails candidate = candidates.get(i);
                registerIelts.register(candidate);
                Thread.sleep(60000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}