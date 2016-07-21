package com.deepsenselab.automation.ieltsregister;

import org.apache.poi.hssf.record.chart.ObjectLinkRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ashok K. Pant (ashokpant87@gmail.com) on 7/21/16.
 */
public class IO {
    private Object getCellValue(Cell cell) {
        Object value = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value =  cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                break;
        }
        if(value != null){
            System.out.println(value);
            return ""+value;
        }
        else
            return null;
    }

    public List<CandidateDetails> readCandidateDetailsFromExcelFile(String excelFilePath) throws IOException {
        List<CandidateDetails> listCandidates = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            CandidateDetails aCandidate = new CandidateDetails();

            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();

                switch (columnIndex) {
                    /*case 1:
                        aCandidate.setTitle((String) getCellValue(nextCell));
                        break;
                    case 2:
                        aCandidate.setAuthor((String) getCellValue(nextCell));
                        break;
                    case 3:
                        aCandidate.setPrice((double) getCellValue(nextCell));
                        break;*/
                    case 1:
                        aCandidate.setBookingCountry((String) getCellValue(nextCell));
                        break;
                    case 2:
                        aCandidate.setBookingDate((String) getCellValue(nextCell));
                        break;
                    case 3:
                        aCandidate.setBookingExactDate((String) getCellValue(nextCell));
                        break;
                    case 4:
                        aCandidate.setBookingTown((String) getCellValue(nextCell));
                        break;
                    case 5:
                        aCandidate.setBookingModule((String) getCellValue(nextCell));
                        break;
                    case 6:
                        aCandidate.setTitle((String) getCellValue(nextCell));
                        break;
                    case 7:
                        aCandidate.setFirstName((String) getCellValue(nextCell));
                        break;
                    case 8:
                        aCandidate.setLastName((String) getCellValue(nextCell));
                        break;
                    case 9:
                        aCandidate.setFirstLanguage((String) getCellValue(nextCell));
                        break;
                    case 10:
                        aCandidate.setNationality((String) getCellValue(nextCell));
                        break;
                    case 11:
                        aCandidate.setEmail((String) getCellValue(nextCell));
                        break;
                    case 12:
                        aCandidate.setBirthDay((String) getCellValue(nextCell));
                        break;
                    case 13:
                        aCandidate.setBirthMonth((String) getCellValue(nextCell));
                        break;
                    case 14:
                        aCandidate.setBirthYear((String) getCellValue(nextCell));
                        break;
                    case 15:
                        aCandidate.setIdentificationDoc((String) getCellValue(nextCell));
                        break;
                    case 16:
                        aCandidate.setIdentificationDocNumber((String) getCellValue(nextCell));
                        break;
                    case 17:
                        aCandidate.setIdentificationDocExpiryDay((String) getCellValue(nextCell));
                        break;
                    case 18:
                        aCandidate.setIdentificationDocExpiryMonth((String) getCellValue(nextCell));
                        break;
                    case 19:
                        aCandidate.setIdentificationDocExpiryYear((String) getCellValue(nextCell));
                        break;
                    case 20:
                        aCandidate.setGender((String) getCellValue(nextCell));
                        break;
                    case 21:
                        aCandidate.setAddress((String) getCellValue(nextCell));
                        break;
                    case 22:
                        aCandidate.setCountry((String) getCellValue(nextCell));
                        break;
                    case 23:
                        aCandidate.setOccupationSector((String) getCellValue(nextCell));
                        break;
                    case 24:
                        aCandidate.setOccupationStatus((String) getCellValue(nextCell));
                        break;
                    case 25:
                        aCandidate.setReasonForTest((String) getCellValue(nextCell));
                        break;
                    case 26:
                        aCandidate.setDestinationCountry((String) getCellValue(nextCell));
                        break;
                    case 27:
                        aCandidate.setEducationLabel((String) getCellValue(nextCell));
                        break;
                    case 28:
                        aCandidate.setEnglishStudyYears((String) getCellValue(nextCell));
                        break;
                }


            }
            listCandidates.add(aCandidate);
        }

        workbook.close();
        inputStream.close();

        return listCandidates;
    }

    public static void main(String[] args) throws IOException {
        String excelFilePath = "/home/ashok/Projects/ashok/automation/automation/data/candidates.xlsx";
        IO reader = new IO();
        List<CandidateDetails> listCandidates = reader.readCandidateDetailsFromExcelFile(excelFilePath);
        for(CandidateDetails candidateDetail:listCandidates) {
            System.out.println(candidateDetail.toString("\n"));
            System.out.println();
        }
    }
}
