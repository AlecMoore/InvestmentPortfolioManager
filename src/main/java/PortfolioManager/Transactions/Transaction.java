/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PortfolioManager.Transactions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Alec
 */
public class Transaction {
    
    private String platform;
    private LocalDateTime dateTime;
    private String type;
    private String details;
    private String asset;
    private double amount;
    private String positionID;
    private double units;
    private double rate;
    private double fees;
   
    
    public void EtoroTransaction(){
        this.platform = "";
        setDateTime(LocalDateTime.MIN);
        this.type = "";
        this.details = "";
        this.asset = "";
        this.amount = .0;            
        this.positionID = "";
        this.units = .0;
        this.rate = .0;
        this.fees = .0;
       
    }
    
    public void setPlatform(String platform){
        this.platform = platform;
    }
    
    public void setFees(double fees){
        this.fees = fees;
    }
    
    public void setDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setDetails(String details){
        this.details = details;   
    }

    public void setAmount(double amount){
        this.amount = amount;
    }
    
    public void setPositionID(String positionID){
        this.positionID = positionID;
    }
    
    public void setRate(double rate){
        this.rate = rate;
    }

    public void setUnits(double units) {
        this.units = units;
    }
    
    public void setAsset(String asset){

        if (asset != null) {
            int io = asset.indexOf(" ");
            this.asset = asset.substring(io+1);
        } else {
            this.asset = asset;
        }
    }

    public String getPlatform(){
        return this.platform;
    }
    
    public double getFees(){
        return this.fees;
    }
    
    public LocalDateTime getDate(){
        return this.dateTime;
    }
    
    public String getDateString(){
        return this.dateTime.toString();
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
    
    public String getPositionID(){
        return this.positionID;
    }
    
    public double getRate(){
        return this.rate;
    }

    public double getUnits() {
        return this.units;
    }  
    
    public String getAsset(){
        return this.asset;
    }
}
