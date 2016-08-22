package com.deepsenselab.automation.ieltsregister;

import com.deepsenselab.automation.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.seleniumhq.selenium.fluent.Period;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.By.id;

public class CheckAvailabilityPage extends BasePage {
    public CheckAvailabilityPage(WebDriver delegate) {
        super(delegate);
        url().within(Period.secs(60)).shouldContain("CheckAvailability");
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

    protected FluentWebElement findAgainField() {
        return element(id("ctl00_ContentPlaceHolder1_imgbSearchAgain"));
    }

    protected FluentWebElement availabilityRootField() {
        return element(id("ctl00_ContentPlaceHolder1_pnlTestResults"));
    }

    protected FluentWebElement selectedTownValueField() {
        return element(id("searchCriteriaBoxCriteriaValue"));
    }

    protected FluentWebElement headerField() {
        return element(By.id("ctl00_ContentPlaceHolder1_rptVenue_ctl00_pnlHeader"));
    }

    protected FluentWebElement tableField() {
        return element(By.className("pnlBodyDetailRowBoxOuter"));
    }

    protected List<FluentWebElement> rowsField() {
        return tableField().elements(By.className("pnlBodyDetailRowBox"));
    }

    protected List<Availability> getAvailability() {
        List<Availability> availabilities = new ArrayList<>();

        List<WebElement> townAndDates = new ArrayList<>();
        WebElement container = delegate.findElement(By.id("ctl00_ContentPlaceHolder1_pnlTestResults"));
        if (isElementPresent(container, By.id("ctl00_ContentPlaceHolder1_rptVenue_ctl00_pnlHeader"))) {
            WebElement firstHeader = container.findElement(By.id("ctl00_ContentPlaceHolder1_rptVenue_ctl00_pnlHeader"));
            townAndDates.add(firstHeader);
        }

        String expr = "//*[boolean(number(substring-before(substring-after(@id, 'ctl00_ContentPlaceHolder1_rptVenue_ctl'), '_pnlHeader')))]";
        List<WebElement> headers = container.findElements(By.xpath(expr));
        townAndDates.addAll(headers);

        for (WebElement header : townAndDates) {
            Availability availability = new Availability();
            availability.setTown(header);
            //System.out.println("header: "+header.getText());
            List<AvailabilityDetail> availabilityDetails = new ArrayList<>();
            WebElement rowContainer = header.findElement(By.xpath("following-sibling::div"));
            for (WebElement row : rowContainer.findElements(By.className("pnlBodyDetailRowBox"))) {
                List<WebElement> elements = row.findElements(By.className("pnlBodyDetailRowBoxLeft"));
                WebElement apply = row.findElement(By.className("pnlBodyDetailRowBoxRight"));

                AvailabilityDetail dateDetail = new AvailabilityDetail();
                dateDetail.setDate(elements.get(0));
                dateDetail.setModule(elements.get(1));
                dateDetail.setFee(elements.get(2));
                dateDetail.setAvailability(elements.get(3));
                dateDetail.setStatus(apply);
                //System.out.println(elements.get(0).getText() + " : " +elements.get(1).getText() + " : "+elements.get(2).getText() + " : "+ elements.get(3).getText()+" = "+apply.getText());
                availabilityDetails.add(dateDetail);
            }
            availability.setDateDetails(availabilityDetails);
            availabilities.add(availability);
        }
        return availabilities;
    }

    class Availability {
        WebElement town;
        List<AvailabilityDetail> dateDetails;

        public WebElement getTown() {
            return town;
        }

        public void setTown(WebElement town) {
            this.town = town;
        }

        public List<AvailabilityDetail> getDateDetails() {
            return dateDetails;
        }

        public void setDateDetails(List<AvailabilityDetail> dateDetails) {
            this.dateDetails = dateDetails;
        }
    }

    class AvailabilityDetail {
        WebElement date;
        WebElement module;
        WebElement fee;
        WebElement availability;
        WebElement status;

        public WebElement getDate() {
            return date;
        }

        public void setDate(WebElement date) {
            this.date = date;
        }

        public WebElement getModule() {
            return module;
        }

        public void setModule(WebElement module) {
            this.module = module;
        }

        public WebElement getFee() {
            return fee;
        }

        public void setFee(WebElement fee) {
            this.fee = fee;
        }

        public WebElement getAvailability() {
            return availability;
        }

        public void setAvailability(WebElement availability) {
            this.availability = availability;
        }

        public WebElement getStatus() {
            return status;
        }

        public void setStatus(WebElement status) {
            this.status = status;
        }
    }
}
