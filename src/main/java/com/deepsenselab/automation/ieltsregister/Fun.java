package com.deepsenselab.automation.ieltsregister;

/**
 * Created by Ashok K. Pant (ashokpant87@gmail.com) on 7/20/16.
 */
public class Fun {
    public static CandidateDetails getCandidateDetails(){
        CandidateDetails details = new CandidateDetails();
        details.setBookingCountry("India");
        details.setBookingDate("August 2016");
        details.setBookingExactDate("26 November 2016");
        details.setBookingTown("Delhi");
        details.setBookingModule("Academic");

        details.setTitle("Mr");
        details.setFirstName("Test");
        details.setLastName("Test");
        details.setFirstLanguage("Nepali");
        details.setNationality("Nepal");
        details.setEmail("ashokpant87@gmail.com");
        details.setBirthDay("29");
        details.setBirthMonth("April");
        details.setBirthYear("1987");
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
}
