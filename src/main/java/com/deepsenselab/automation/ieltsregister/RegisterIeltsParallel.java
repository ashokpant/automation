package com.deepsenselab.automation.ieltsregister;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.seleniumhq.selenium.fluent.FluentWebElement;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RegisterIeltsParallel implements Runnable {
    CandidateDetails candidate;

    public RegisterIeltsParallel(CandidateDetails candidate) {
        this.candidate = candidate;
    }

    public static void main(String[] args) {
        CandidateDetails candidate = Fun.getCandidateDetails();

        Runnable job1 = new RegisterIeltsParallel(candidate);
        Thread job1Thread = new Thread(job1);
        job1Thread.start();

        Runnable job2 = new RegisterIeltsParallel(candidate);
        Thread job2Thread = new Thread(job2);
        job2Thread.start();

        Runnable job3 = new RegisterIeltsParallel(candidate);
        Thread job3Thread = new Thread(job3);
        job3Thread.start();

        Runnable job4 = new RegisterIeltsParallel(candidate);
        Thread job4Thread = new Thread(job4);
        job4Thread.start();
    }

    @Override
    public void run() {
        RegisterIelts registerIelts = new RegisterIelts();
        registerIelts.register(candidate);
    }
}
