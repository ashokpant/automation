package com.deepsenselab.automation.ieltsregister;

import java.io.IOException;
import java.util.List;

public class RegisterIeltsParallel implements Runnable {
    CandidateDetails candidate = null;
    public RegisterIeltsParallel(CandidateDetails candidate) {
        this.candidate = candidate;
    }

    @Override
    public void run() {
        RegisterIelts registerIelts = new RegisterIelts();
        System.out.println("candidate in run: "+this.candidate.toString(", "));
        registerIelts.registerCore(candidate);
        registerIelts.killWebDriver();
    }

    public static void main(String[] args) {
        String excelFilePath = "/home/ashok/Projects/ashok/automation/automation/data/candidates.xlsx";
        int numOfThreads = 3;

        try {
            IO reader = new IO();
            List<CandidateDetails> candidates = reader.readCandidateDetailsFromExcelFile(excelFilePath);
            for(int i = 1; i <candidates.size(); i = i+numOfThreads){
                for(int j=i; j < Math.min(i+numOfThreads,candidates.size()); ++j){
                    System.out.println("candidate: "+j);
                    CandidateDetails candidate = candidates.get(j);
                    System.out.println("\t"+candidate.toString(","));
                    Runnable job = new RegisterIeltsParallel(candidate);
                    Thread thread = new Thread(job);
                    thread.start();
                }
                System.out.println("---");
                Thread.sleep(60000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
