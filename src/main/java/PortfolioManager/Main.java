package PortfolioManager;

import PortfolioManager.Readers.CoinbaseReader;
import PortfolioManager.Readers.EtoroReaderAccountActivity;
import PortfolioManager.Readers.EtoroReaderClosedPositions;
import PortfolioManager.Readers.TransactionsReader;
import PortfolioManager.Transactions.Transaction;
import Writers.TransactionWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alec
 */
public class Main {
    
    
    
    public static void main(String[] args) throws IOException {
        
        System.out.println("heelo");
        
        ArrayList<Transaction> Transactions = TransactionsReader.read();
        Transactions = EtoroReaderClosedPositions.read(Transactions);
        Transactions = EtoroReaderAccountActivity.read(Transactions);
        Transactions = CoinbaseReader.read(Transactions);
        TransactionWriter.write(Transactions);
    }
}
