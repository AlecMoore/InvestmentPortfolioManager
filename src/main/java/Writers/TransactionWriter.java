/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Writers;

import PortfolioManager.Transactions.Transaction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Alec
 */
public class TransactionWriter {
    
    public static void write(ArrayList<Transaction> eTs) throws FileNotFoundException, IOException{
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(" Transactions ");
        
        Row row = spreadsheet.createRow(0);
        for (int i = 0; i < 10; i++) {
                Cell cell = row.createCell(i);
                                switch(i){
                case 0 -> cell.setCellValue("Platform");
                case 1 -> cell.setCellValue("Type");
                case 2 -> cell.setCellValue("Date");
                case 3 -> cell.setCellValue("Asset");
                case 4 -> cell.setCellValue("Amount/USD");
                case 5 -> cell.setCellValue("Units");
                case 6 -> cell.setCellValue("Rate/USD");
                case 7 -> cell.setCellValue("Fees/USD");
                case 8 -> cell.setCellValue("PositionID");
                case 9 -> cell.setCellValue("Details");
                }
        }
        
        
        int rownum = 1;
        for (Transaction eT: eTs) {
            row = spreadsheet.createRow(rownum);
            
            for (int i = 0; i < 10; i++) {
                Cell cell = row.createCell(i);
                
                switch(i){
                case 0 -> cell.setCellValue(eT.getPlatform());
                case 1 -> cell.setCellValue(eT.getType());
                case 2 -> cell.setCellValue(eT.getDateString());
                case 3 -> cell.setCellValue(eT.getAsset());
                case 4 -> cell.setCellValue(eT.getAmount());
                case 5 -> cell.setCellValue(eT.getUnits());
                case 6 -> cell.setCellValue(eT.getRate());
                case 7 -> cell.setCellValue(eT.getFees());
                case 8 -> cell.setCellValue(eT.getPositionID());
                case 9 -> cell.setCellValue(eT.getDetails());
                }
            }
            rownum++;
        }

        try (
                FileOutputStream out = new FileOutputStream(
                        new File("C:\\Users\\Alec\\Documents\\GitHub\\"
                                + "PortfolioManager\\Excel\\Transactions.xlsx"))) {
            workbook.write(out);
            System.out.println("Transactions written to spreadsheet.");
        }
    }
}
