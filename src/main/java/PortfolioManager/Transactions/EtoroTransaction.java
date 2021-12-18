/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PortfolioManager.Transactions;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Alec
 */
public class EtoroTransaction {
    
    private LocalDateTime dateTime;
    private String type;
    private String details;
    private String asset;
    private double amount;
    private double realEquityChange;
    private double realEquity;
    private double balance;
    private String positionID;
    private double nwa;
    
    public void EtoroTransaction(){
        setDateTime("01/01/2001 00:00:00");
        this.type = null;
        this.details = null;
        this.asset = null;
        this.amount = .0;        
        this.realEquityChange = .0;  
        this.realEquity = .0;  
        this.balance = .0;       
        this.positionID = null;
        this.nwa  = .0;  
    }
    
    public void setDateTime(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dateTime = LocalDateTime.parse(dateTime, formatter);
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setDetails(String details){
        if (details != null) {
            int iend = details.indexOf("/");
            if (iend != -1) {
                this.asset = details.substring(0, iend);
            } else {
                this.asset = "";
            }
        }

        this.details = details;
        System.out.println(asset);
        System.out.println(details);
        
    }

    public void setAmount(double amount){
        this.amount = amount;
    }
    
    public void setRealEquityChange(double realEquityChange){
        this.realEquityChange = realEquityChange;
    }
    
    public void setRealEquity(double realEquity){
        this.realEquity = realEquity;
    }
    
    public void setBalance(double balance){
        this.balance = balance;
    }
    
    public void setPositionID(String positionID){
        this.positionID = positionID;
    }
    
    public void setNWA(double nwa){
        this.nwa = nwa;
    }

    public LocalDateTime getDate(){
        return this.dateTime;
    }
    
    public String getType(){
        return this.type;
    }
    
    public String getDetails(){
        return this.details;
    }

    public double getAmount(){
        return this.amount;
    }
    
    public double getRealEquityChange(){
        return this.realEquityChange;
    }
    
    public double getRealEquity(){
        return this.realEquity;
    }
    
    public double getBalance(){
        return this.balance;
    }
    
    public String getPositionID(){
        return this.positionID;
    }
    
    public double getNWA(){
        return this.nwa;
    }
}
