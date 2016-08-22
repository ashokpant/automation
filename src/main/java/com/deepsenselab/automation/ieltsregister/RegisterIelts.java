package com.deepsenselab.automation.ieltsregister;

import java.io.IOException;
import java.util.List;

public class RegisterIelts implements Runnable {
    CandidateDetails candidate = null;

    public RegisterIelts(CandidateDetails candidate) {
        this.candidate = candidate;
    }

    @Override
    public void run() {
        RegisterIeltsCore registerIelts = new RegisterIeltsCore();
        System.out.println("Candidate in run: " + this.candidate.toString(", "));
        registerIelts.registerCore(candidate);
        registerIelts.killWebDriver();
    }

    public static void main(String[] args) {
        String excelFilePath; //=  "/home/ashok/Projects/ashok/automation/automation/data/candidates_sept.xlsx";
        int numOfThreads = 3;
        int sleepSecs = 60;

       /* if (args.length == 3) {
            excelFilePath = args[0];
            numOfThreads = Integer.parseInt(args[1]);
            sleepSecs = Integer.parseInt(args[2]);
        } else if (args.length == 2) {
            excelFilePath = args[0];
            numOfThreads = Integer.parseInt(args[1]);
        } else if (args.length == 1) {
            excelFilePath = args[0];
        } else {
            System.out.println("Input the candidate list excel file.");
            return;
        }*/
        excelFilePath = "/home/ashok/Projects/ashok/automation/automation/data/candidates_sept.xlsx";


        try {
            IO reader = new IO();
            List<CandidateDetails> candidates = reader.readCandidateDetailsFromExcelFile(excelFilePath);

            for (int i = 1; i < candidates.size(); i = i + numOfThreads) {
                for (int j = i; j < Math.min(i + numOfThreads, candidates.size()); ++j) {
                    System.out.println("candidate: " + j);
                    CandidateDetails candidate = candidates.get(j);
                    System.out.println("\t" + candidate.toString(","));
                    Runnable job = new RegisterIelts(candidate);
                    Thread thread = new Thread(job);
                    thread.start();
                }
                System.out.println("---");
                Thread.sleep(sleepSecs * 1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
