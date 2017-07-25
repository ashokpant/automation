package com.deepsenselab.automation;

import com.deepsenselab.automation.ieltsregister.CandidateDetails;
import com.deepsenselab.automation.ieltsregister.RegisterIeltsCore;
import org.junit.Test;

public class RegisterIeltsTest {
    public CandidateDetails getDummyCandidate() {
        CandidateDetails details = new CandidateDetails();
        details.setBookingCountry("India");
        details.setBookingDate("August 2016");
        details.setBookingExactDate("21 October 2017");
        details.setBookingTown("Delhi");
        details.setBookingModule("Academic");

        details.setTitle("Mr");
        details.setFirstName("Test");
        details.setLastName("Test");
        details.setFirstLanguage("Nepali");
        details.setNationality("Nepal");
        details.setEmail("test@gmail.com");
        details.setBirthDay("29");
        details.setBirthMonth("April");
        details.setBirthYear("1990");
        details.setIdentificationDoc("Passport");
        details.setIdentificationDocNumber("1234526");
        details.setIdentificationDocExpiryDay("21");
        details.setIdentificationDocExpiryMonth("November");
        details.setIdentificationDocExpiryYear("2025");
        details.setGender("Female");
        details.setAddress("Kathmandu, Nepal");
        details.setCountry("Nepal");
        details.setOccupationSector("Education");
        details.setOccupationStatus("Student");
        details.setReasonForTest("Higher education extended course (3 months or more)");
        details.setDestinationCountry("Australia");
        details.setEducationLabel("Secondary (upto 16 years)");
        details.setEnglishStudyYears("9 (or more)");
        return details;
    }

    @Test
    public void registerIeltsTest() {
        CandidateDetails candidate = getDummyCandidate();
        RegisterIeltsCore registerIelts = new RegisterIeltsCore();
        registerIelts.register(candidate);
    }
}
