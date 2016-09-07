package com.deepsenselab.automation.ieltsregister;

import java.io.IOException;
import java.util.List;

public class RegisterIelts implements Runnable {
    CandidateDetails candidate = null;

    public RegisterIelts(CandidateDetails candidate) {
        this.candidate = candidate;
    }

    public RegisterIelts() {
    }

    public static void main(String[] args) {
        boolean production = false;
        String excelFilePath;
        int numOfThreads = 1;
        int sleepSecs = 120;

        if (production) {
            if (args.length == 3) {
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
            }
        } else {
            excelFilePath = "/home/ashok/Projects/ashok/automation/automation/data/candidates_oct_nov.xlsx";
        }

        RegisterIelts registerIelts = new RegisterIelts();
        String msg = registerIelts.register(excelFilePath, numOfThreads, sleepSecs);
        if (msg == null) {
            System.out.println("Done.");
        } else {
            System.out.println(msg);
        }
    }

    @Override
    public void run() {
        RegisterIeltsCore registerIelts = new RegisterIeltsCore();
        System.out.println("Candidate in run: " + this.candidate.toString(", "));
        registerIelts.registerCore(candidate);
        registerIelts.killWebDriver();
    }

    public String register( List<CandidateDetails> candidates, int numOfThreads, int sleepSecs){
        try {
            if (candidates.size() == 0) return "No candidate to register.";
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
                if (sleepSecs > 0) {
                    Thread.sleep(sleepSecs * 1000);
                }
            }
        } catch (InterruptedException | IllegalArgumentException e) {
            e.printStackTrace();
            return e.toString();
        }
        return "Success";
    }

    public String register(String filePath, int numOfThreads, int sleepSecs) {
        try {
            List<CandidateDetails> candidates = read(filePath);
           return register(candidates,numOfThreads,sleepSecs);
        }  catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    public  List<CandidateDetails> read(String filePath) throws Exception{
        try {
            IO reader = new IO();
            return reader.readCandidateDetailsFromExcelFile(filePath);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

}
