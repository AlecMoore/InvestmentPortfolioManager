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

public class CoinbaseReader {
  
    private static final String DIRECTORY = "C:\\Users\\Alec\\Documents\\GitHub\\PortfolioManager\\Excel\\";
    
    public static ArrayList<Transaction> read(ArrayList<Transaction> transactions) {
        try {
            File file = new File(DIRECTORY + "Coinbase.xlsx");   //creating a new file instance  
            FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  

            //creating Workbook instance that refers to .xlsx file  
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  

            for (int i = 8; i < sheet.getLastRowNum(); i++) {
                Transaction cT = new Transaction();
                XSSFRow row = ((XSSFRow) sheet.getRow(i));

                if (row != null) {
                    switch (row.getCell(1).getStringCellValue()) {
                        case "Convert" -> {
                            Transaction cT1 = new Transaction();
                            setConvertVariables(cT, cT1, row);
                            transactions = addNonDuplicates(transactions, cT);
                            transactions = addNonDuplicates(transactions, cT1);
                        }
                        default -> {
                            setVariables(cT, row);
                            transactions = addNonDuplicates(transactions, cT);
                        }
                    }
                } else {
                    System.out.println("Null row");
                }
                
            }

            System.out.println("Coinbase spreadsheet read in.");
            fis.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return transactions;
    }
    
    
    private static ArrayList<Transaction> addNonDuplicates(
            ArrayList<Transaction> transactions, Transaction transaction) {
        for (Transaction t : transactions) {
            if (transaction.getDate().equals(t.getDate())
                    && transaction.getType().equals(t.getType())) {
                return transactions;
            }
        }
        transactions.add(transaction);
        return transactions;
    }
        
    private static void setVariables(Transaction cT, XSSFRow row){
        setPlatform(cT);
        
        if ("Rewards Income".equals(row.getCell(1).getStringCellValue())){
            setType(cT, "Staking");
        } else {
            readType(cT, row);
        }
        
        readDate(cT, row);
        readAsset(cT, row);
        readAmount(cT, row);
        readUnits(cT, row);
        readRate(cT, row);
        readDetails(cT, row);
        readFees(cT, row);
    }
    
    private static void setConvertVariables(Transaction cT, Transaction cT1, XSSFRow row){
        String details = row.getCell(9).getStringCellValue();
        int [] pos =  findPositions(details);
        
        setPlatform(cT);
        setPlatform(cT1);
        
        setType(cT, "Sell");
        setType(cT1, "Buy");
        
        readDate(cT, row);
        readDate(cT1, row);
        
        readAsset(cT, row);
        String asset = details.substring(pos[4]+1, details.length());
        setAsset(cT1, asset);
        
        
        readAmount(cT, row);
        readAmountConvert(cT1, row);
        
        readUnits(cT, row);
        String unitsStr = details.substring(pos[3]+1, pos[4]);
        double units = Double.parseDouble(unitsStr);
        setUnits(cT1, units);
        
        readRate(cT, row);
        setRate(cT1, row, units);
        
        readDetails(cT, row);
        readDetails(cT1, row);
        
        readFees(cT, row);
    }

    public static int [] findPositions(String details) {
        int[] pos = new int[5];
        int j = 0;
        for (int i = 0; i < details.length(); i++) {
            if (details.charAt(i) == ' ') {
                pos[j] = i;
                j++;
            }
        }
        return pos;
    }
    
    private static void setPlatform(Transaction cT){
        cT.setPlatform("Coinbase");
    }
    
    private static void setType(Transaction cT, String type){
        cT.setType(type);
    }
    
    private static void setAsset(Transaction cT, String asset){
        cT.setAsset(asset);
    }
    
    private static void setUnits(Transaction cT, double units){
        cT.setUnits(units);
    }
    
    private static void setRate(Transaction cT, XSSFRow row, double units){
        double rate = units*row.getCell(6).getNumericCellValue();
        cT.setRate(rate);
    }
    
    private static void readAmountConvert(Transaction cT, XSSFRow row){
        try {
            cT.setAmount(row.getCell(6).getNumericCellValue());
        } catch (NullPointerException e) {
        }
    }
    
    private static void readType(Transaction cT, XSSFRow row){
        try {
            cT.setType(row.getCell(1).getStringCellValue());            
        } catch (NullPointerException e) {
        }
    }
    
    private static void readDate(Transaction cT, XSSFRow row){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateTime = row.getCell(0).getStringCellValue().replace("T", " ");
            dateTime = dateTime.replace("Z", "");
            cT.setDateTime(LocalDateTime.parse(dateTime, formatter));
        } catch (NullPointerException e) {
        }
    }
    
    private static void readAsset(Transaction cT, XSSFRow row){
        try {
            cT.setAsset(row.getCell(2).getStringCellValue());            
        } catch (NullPointerException e) {
        }
    }
    
    private static void readAmount(Transaction cT, XSSFRow row){
        try {
            cT.setAmount(row.getCell(7).getNumericCellValue());            
        } catch (NullPointerException e) {
        }
    }
    
    private static void readUnits(Transaction cT, XSSFRow row){
        try {
            cT.setUnits(row.getCell(3).getNumericCellValue());
        } catch (NullPointerException e) {
        }
    }
    
    private static void readRate(Transaction cT, XSSFRow row){
        try {
            cT.setRate(row.getCell(5).getNumericCellValue());            
        } catch (NullPointerException e) {
        }
    }
    
    private static void readDetails(Transaction cT, XSSFRow row){
        try {
            cT.setDetails(row.getCell(9).getStringCellValue());
        } catch (NullPointerException e) {
        }
    }
    
    private static void readFees(Transaction cT, XSSFRow row){
        try {
            cT.setFees(row.getCell(8).getNumericCellValue());
        } catch (NullPointerException e) {
        }
    }
}  