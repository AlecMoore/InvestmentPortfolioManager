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

public class TransactionsReader {
  
    private static final String DIRECTORY = "C:\\Users\\Alec\\Documents\\GitHub\\PortfolioManager\\Excel\\";
    
    public static ArrayList<Transaction> read() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            File file = new File(DIRECTORY + "Transactions.xlsx");   //creating a new file instance  
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  

            //creating Workbook instance that refers to .xlsx file  
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  

            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Transaction transaction = new Transaction();
                XSSFRow row = ((XSSFRow) sheet.getRow(i));

                if (row != null) {

                    setVariables(transaction, row);

                    transactions.add(transaction);
                } else {
                    System.out.println("Null row");
                }
                
            }

            System.out.println("Transactions spreadsheet read in.");
            fis.close();
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return transactions;
    }
    
    private static void setVariables(Transaction transaction, XSSFRow row){
        readPositionID(transaction, row);
        readType(transaction, row);
        readDate(transaction, row);
        readAsset(transaction, row);
        readAmount(transaction, row);
        readUnits(transaction, row);
        readRate(transaction, row);
        readDetails(transaction, row);
    }
    
    private static void readPositionID(Transaction transaction, XSSFRow row){
        try {
            transaction.setPositionID(row.getCell(0).getStringCellValue());
        } catch (NullPointerException e) {
        }
    }
    
    private static void readType(Transaction transaction, XSSFRow row){
        try {
            transaction.setType(row.getCell(1).getStringCellValue());            
        } catch (NullPointerException e) {
        }
    }
    
    private static void readDate(Transaction transaction, XSSFRow row){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTime = row.getCell(2).getStringCellValue().replace("T", " ");
            transaction.setDateTime(LocalDateTime.parse(dateTime, formatter));
        } catch (NullPointerException e) {
        }
    }
    
    private static void readAsset(Transaction transaction, XSSFRow row){
        try {
            transaction.setAsset(row.getCell(3).getStringCellValue());            
        } catch (NullPointerException e) {
        }
    }
    
    private static void readAmount(Transaction transaction, XSSFRow row){
        try {
            transaction.setAmount(row.getCell(4).getNumericCellValue());            
        } catch (NullPointerException e) {
        }
    }
    
    private static void readUnits(Transaction transaction, XSSFRow row){
        try {
            transaction.setUnits(row.getCell(5).getNumericCellValue());
        } catch (NullPointerException e) {
        }
    }
    
    private static void readRate(Transaction transaction, XSSFRow row){
        try {
            transaction.setRate(row.getCell(6).getNumericCellValue());            
        } catch (NullPointerException e) {
        }
    }
    
    private static void readDetails(Transaction transaction, XSSFRow row){
        try {
            transaction.setDetails(row.getCell(9).getStringCellValue());
        } catch (NullPointerException e) {
        }
    }
}  