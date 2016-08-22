package com.deepsenselab.automation;

import com.deepsenselab.automation.ieltsregister.CandidateDetails;
import com.deepsenselab.automation.ieltsregister.Fun;
import com.deepsenselab.automation.ieltsregister.RegisterIeltsCore;
import org.junit.Test;

public class RegisterIeltsTest {
    @Test
    public void registerIeltsTest() {
        CandidateDetails candidate = Fun.getCandidateDetails();
        RegisterIeltsCore registerIelts = new RegisterIeltsCore();
        registerIelts.register(candidate);
    }
}
