package PortfolioManager.Readers;

import PortfolioManager.Transactions.EtoroTransaction;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EtoroReader {
  
    private static final String DIRECTORY = "D:\\Projects\\NetBeansProjects\\PortfolioManager\\Excel\\";
    
    public static void read() {  
        try {
            File file = new File(DIRECTORY + "etoro.xlsx");   //creating a new file instance  
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  

            //creating Workbook instance that refers to .xlsx file  
            XSSFWorkbook wb = new XSSFWorkbook(fis);   
            XSSFSheet sheet = wb.getSheetAt(2);     //creating a Sheet object to retrieve object  

            ArrayList<EtoroTransaction> eTs = new ArrayList<>();
                
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                EtoroTransaction eT = new EtoroTransaction();
                XSSFRow row = ((XSSFRow) sheet.getRow(i));
                
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    if (row != null && row.getCell(j) != null) {
                        setVariable(j, row.getCell(j), eT);
                    } else {
                        setNullVariable(j, eT);
                    }
                }
                eTs.add(eT);
            }
                
                System.out.println("Etoro spreadsheet read in.");  
              
        }  
        catch(IOException e) {  
        }  
    }
    
    private static void setVariable(int i, Cell cell, EtoroTransaction eT){
        
        switch (i) {  
            case 0 -> {
                eT.setDateTime(cell.getStringCellValue());
            }
            case 1 -> {
                eT.setType(cell.getStringCellValue());
            }
            case 2 -> {
                eT.setDetails(cell.getStringCellValue());
            }
            case 3 -> {
                eT.setAmount(cell.getNumericCellValue());
            }
            case 4 -> {
                eT.setRealEquityChange(cell.getNumericCellValue());
            }
            case 5 -> {
                eT.setRealEquity(cell.getNumericCellValue());
            }
            case 6 -> {
                eT.setBalance(cell.getNumericCellValue());
            }
            case 7 -> {
                eT.setPositionID(cell.getStringCellValue());
            }
            case 8 -> {
                eT.setNWA(cell.getNumericCellValue());
            }
            default -> { 
            }
        }
    }
    
    private static void setNullVariable(int i, EtoroTransaction eT){
        
        switch (i) {  
            case 0 -> {
                eT.setDateTime(null);
            }
            case 1 -> {
                eT.setType(null);
            }
            case 2 -> {
                eT.setDetails(null);
            }
            case 3 -> {
                eT.setAmount(.0);
            }
            case 4 -> {
                eT.setRealEquityChange(.0);
            }
            case 5 -> {
                eT.setRealEquity(.0);
            }
            case 6 -> {
                eT.setBalance(.0);
            }
            case 7 -> {
                eT.setPositionID(null);
            }
            case 8 -> {
                eT.setNWA(.0);
            }
            default -> { 
            }
        }
    }
}  