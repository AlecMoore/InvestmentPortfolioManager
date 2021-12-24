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

public class EtoroReaderAccountActivity {
  
    private static final String DIRECTORY = "C:\\Users\\Alec\\Documents\\GitHub\\PortfolioManager\\Excel\\";

    public static ArrayList<Transaction> read(ArrayList<Transaction> transactions) {
        try {
            File file = new File(DIRECTORY + "Etoro.xlsx");   //creating a new file instance  
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  

            //creating Workbook instance that refers to .xlsx file  
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(2);     //creating a Sheet object to retrieve object  

            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Transaction eT = new Transaction();
                XSSFRow row = ((XSSFRow) sheet.getRow(i));

                if (row != null) {
                    try {
                        switch (row.getCell(1).getStringCellValue()) {
                            case "Withdraw Request" -> {
                                setVariables(eT, row, "Withdrawal");

                                transactions = addNonDuplicates(transactions, eT);
                            }
                            case "Rollover Fee" -> {
                                if ("Payment caused by Dividend".equals(
                                        row.getCell(2).getStringCellValue())) {
                                    setVariables(eT, row, "Dividend");
                                } else {
                                    setVariables(eT, row, row.getCell(1).getStringCellValue());
                                }

                                transactions = addNonDuplicates(transactions, eT);
                            }
                            case "Open Position" -> {
                            }
                            case "Profit/Loss of Trade" -> {
                            }
                            default -> {
                                setVariables(eT, row, row.getCell(1).getStringCellValue());
                                transactions = addNonDuplicates(transactions, eT);
                            }
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Empty Cell");
                    }
                } else {
                    System.out.println("Null row");
                }
            }

            System.out.println("Etoro Account Activity spreadsheet read in.");

        } catch (IOException e) {
            System.out.println(e);
        }
        return transactions;
    }

    
    private static ArrayList<Transaction> addNonDuplicates(
            ArrayList<Transaction> transactions, Transaction transaction) {
        for (Transaction t : transactions) {
            if (transaction.getPositionID().equals(t.getPositionID())
                    && transaction.getType().equals(t.getType())) {
                return transactions;
            }
        }
        transactions.add(transaction);
        return transactions;
    }
        
    private static void setVariables(Transaction eT, XSSFRow row, String type) {
        setPlatform(eT);
        setType(eT, type);
        readPositionID(eT, row);
        readAmount(eT, row);
        readDate(eT, row);
        readDetails(eT, row);

    }
    
    private static void setPlatform(Transaction eT){
        eT.setPlatform("Etoro");
    }
    
    private static void readPositionID(Transaction eT, XSSFRow row){
        try{
            eT.setPositionID(row.getCell(7).getStringCellValue());
        } catch (NullPointerException e){
            eT.setPositionID("");
        }
    }
    
    private static void readAmount(Transaction eT, XSSFRow row){
        eT.setAmount(row.getCell(3).getNumericCellValue());
    }
    
    private static void readDate(Transaction eT, XSSFRow row){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        eT.setDateTime(LocalDateTime.parse(row.getCell(0).getStringCellValue(), formatter));
    }
    
    private static void setType(Transaction eT, String type){
        eT.setType(type);
    }
    
    private static void readDetails(Transaction eT, XSSFRow row){
        try {
            eT.setDetails(row.getCell(2).getStringCellValue());
        } catch (NullPointerException e) {
            eT.setDetails("");
        }
    }
}  