package PortfolioManager.Readers;

import PortfolioManager.Transactions.Transaction;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EtoroReaderClosedPositions {
  
    private static final String DIRECTORY = "C:\\Users\\Alec\\Documents\\GitHub\\PortfolioManager\\Excel\\";
    
    public static ArrayList<Transaction> read(ArrayList<Transaction> transactions) {
        try {
            File file = new File(DIRECTORY + "Etoro.xlsx");   //creating a new file instance  
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  

            //creating Workbook instance that refers to .xlsx file  
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(1);     //creating a Sheet object to retrieve object  

            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Transaction eTOpen = new Transaction();
                Transaction eTClose = new Transaction();
                XSSFRow row = ((XSSFRow) sheet.getRow(i));

                if (row != null) {

                    setVariables(eTOpen, eTClose, row);
                    transactions = addNonDuplicates(transactions, eTOpen);
                    transactions = addNonDuplicates(transactions, eTClose);
                } else {
                    System.out.println("Null row");
                }

            }

            System.out.println("Etoro Closed Positions spreadsheet read in.");

        } catch (IOException e) {
            System.out.println(e);
        }
        return transactions;
    }
    
    private static ArrayList<Transaction> addNonDuplicates(
            ArrayList<Transaction> transactions, Transaction transaction) {
        for (Transaction t : transactions) {
            if (transaction.getPositionID().equals(t.getPositionID()) 
                    && transaction.getType().equals(t.getType())){
                return transactions;
            }
        }
        transactions.add(transaction);
        return transactions;
    }
    
    private static void setVariables(Transaction eTOpen, Transaction eTClose, XSSFRow row){
        setPlatform(eTOpen, eTClose);
        setType(eTOpen, eTClose);
        readPositionID(eTOpen, eTClose, row);
        readAsset(eTOpen, eTClose, row);
        readAmount(eTOpen, eTClose, row);
        readUnits(eTOpen, eTClose, row);
        readDate(eTOpen, eTClose, row);
        readRate(eTOpen, eTClose, row);
        readDetails(eTOpen, eTClose, row);
    }
    
    private static void setPlatform(Transaction eTOpen, Transaction eTClose){
        eTOpen.setPlatform("Etoro");
        eTClose.setPlatform("Etoro");
    }
    
    private static void readPositionID(Transaction eTOpen, Transaction eTClose, XSSFRow row){
        eTOpen.setPositionID(row.getCell(0).getStringCellValue());
        eTClose.setPositionID(row.getCell(0).getStringCellValue());
    }
    
    
    private static void readAsset(Transaction eTOpen, Transaction eTClose, XSSFRow row){
        eTOpen.setAsset(row.getCell(1).getStringCellValue());
        eTClose.setAsset(row.getCell(1).getStringCellValue());
    }
    
    private static void readAmount(Transaction eTOpen, Transaction eTClose, XSSFRow row){
        eTOpen.setAmount(row.getCell(2).getNumericCellValue());
        eTClose.setAmount(row.getCell(2).getNumericCellValue() + row.getCell(8).getNumericCellValue());
    }
    
    private static void readUnits(Transaction eTOpen, Transaction eTClose, XSSFRow row){
        eTOpen.setUnits(Double.parseDouble(row.getCell(3).getStringCellValue()));
        eTClose.setUnits(Double.parseDouble(row.getCell(3).getStringCellValue()));
    }
    
    private static void readDate(Transaction eTOpen, Transaction eTClose, XSSFRow row){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        eTOpen.setDateTime(LocalDateTime.parse(row.getCell(4).getStringCellValue(), formatter));
        eTClose.setDateTime(LocalDateTime.parse(row.getCell(5).getStringCellValue(), formatter));
    }
    
    private static void readRate(Transaction eTOpen, Transaction eTClose, XSSFRow row){
        eTOpen.setRate(row.getCell(9).getNumericCellValue());
        eTClose.setRate(row.getCell(10).getNumericCellValue());
    }
    
    private static void setType(Transaction eTOpen, Transaction eTClose){
        eTOpen.setType("Buy");
        eTClose.setType("Sell");
    }
    
    private static void readDetails(Transaction eTOpen, Transaction eTClose, XSSFRow row){
        try {
            eTOpen.setDetails(row.getCell(17).getStringCellValue());
            eTClose.setDetails(row.getCell(17).getStringCellValue());
        } catch (NullPointerException e) {
            eTOpen.setDetails("");
            eTClose.setDetails("");
        }
    }
}  