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
            return value;
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
                nextCell.setCellType(Cell.CELL_TYPE_STRING);
                int columnIndex = nextCell.getColumnIndex();

                final int col = 0;
                switch (columnIndex) {
                    case col:
                        aCandidate.setBookingCountry((String) getCellValue(nextCell));
                        break;
                    case col+1:
                        aCandidate.setBookingDate((String) getCellValue(nextCell));
                        break;
                    case col+2:
                        aCandidate.setBookingExactDate((String) getCellValue(nextCell));
                        break;
                    case col+3:
                        aCandidate.setBookingTown((String) getCellValue(nextCell));
                        break;
                    case col+4:
                        aCandidate.setBookingModule((String) getCellValue(nextCell));
                        break;
                    case col+5:
                        aCandidate.setTitle((String) getCellValue(nextCell));
                        break;
                    case col+6:
                        aCandidate.setFirstName((String) getCellValue(nextCell));
                        break;
                    case col+7:
                        aCandidate.setLastName((String) getCellValue(nextCell));
                        break;
                    case col+8:
                        aCandidate.setFirstLanguage((String) getCellValue(nextCell));
                        break;
                    case col+9:
                        aCandidate.setNationality((String) getCellValue(nextCell));
                        break;
                    case col+10:
                        aCandidate.setEmail((String) getCellValue(nextCell));
                        break;
                    case col+11:
                        aCandidate.setBirthDay((String) getCellValue(nextCell));
                        break;
                    case col+12:
                        aCandidate.setBirthMonth((String) getCellValue(nextCell));
                        break;
                    case col+13:
                        aCandidate.setBirthYear((String) getCellValue(nextCell));
                        break;
                    case col+14:
                        aCandidate.setIdentificationDoc((String) getCellValue(nextCell));
                        break;
                    case col+15:
                        aCandidate.setIdentificationDocNumber((String) getCellValue(nextCell));
                        break;
                    case col+16:
                        aCandidate.setIdentificationDocExpiryDay((String) getCellValue(nextCell));
                        break;
                    case col+17:
                        aCandidate.setIdentificationDocExpiryMonth((String) getCellValue(nextCell));
                        break;
                    case col+18:
                        aCandidate.setIdentificationDocExpiryYear((String) getCellValue(nextCell));
                        break;
                    case col+19:
                        aCandidate.setGender((String) getCellValue(nextCell));
                        break;
                    case col+20:
                        aCandidate.setAddress((String) getCellValue(nextCell));
                        break;
                    case col+21:
                        aCandidate.setCountry((String) getCellValue(nextCell));
                        break;
                    case col+22:
                        aCandidate.setOccupationSector((String) getCellValue(nextCell));
                        break;
                    case col+23:
                        aCandidate.setOccupationStatus((String) getCellValue(nextCell));
                        break;
                    case col+24:
                        aCandidate.setReasonForTest((String) getCellValue(nextCell));
                        break;
                    case col+25:
                        aCandidate.setDestinationCountry((String) getCellValue(nextCell));
                        break;
                    case col+26:
                        aCandidate.setEducationLabel((String) getCellValue(nextCell));
                        break;
                    case col+27:
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
            System.out.println(candidateDetail.toString(", "));
            System.out.println();
        }
    }
}
