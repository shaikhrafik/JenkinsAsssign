package assignment2;

import java.io.Serializable;

public abstract class Account implements Serializable {
    private String accountNumber;
    protected double balance;
    
    // Constructor
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double getBalance() {
        return balance;
    }
    
    // Deposit method
    public void deposit(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Deposit amount must be positive");
        }
        balance += amount;
    }
    
    // Abstract withdrawal method to be implemented by child classes
    public abstract void withdraw(double amount) throws BankingException;
    
    // Abstract method to get account type
    public abstract String getAccountType();
    
    @Override
    public String toString() {
        return "Account Type: " + getAccountType() + 
               ", Account Number: " + accountNumber + 
               ", Balance: $" + String.format("%.2f", balance);
    }
}

