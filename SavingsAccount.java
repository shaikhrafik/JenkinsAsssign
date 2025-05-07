package assignment2;

public class SavingsAccount extends Account {
    private double interestRate;
    
    // Constructor
    public SavingsAccount(String accountNumber, double initialBalance, double interestRate) {
        super(accountNumber, initialBalance);
        this.interestRate = interestRate;
    }
    
    

	// Getter for interest rate
    public double getInterestRate() {
        return interestRate;
    }
    
    // Method to calculate interest
    public double calculateInterest() {
        double interest = balance * interestRate / 100;
        return interest;
    }
    
    // Method to add interest to the balance
    public void addInterest() {
        double interest = calculateInterest();
        balance += interest;
    }
    
    @Override
    public void withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdrawal amount must be positive");
        }
        
        if (amount > balance) {
            throw new BankingException("Insufficient funds. Current balance: $" + 
                                        String.format("%.2f", balance));
        }
        
        balance -= amount;
    }
    
    @Override
    public String getAccountType() {
        return "Savings Account";
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Interest Rate: " + interestRate + "%";
    }
}

