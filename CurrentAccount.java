package assignment2;

public class CurrentAccount extends Account {
    private double overdraftLimit;
    
    // Constructor
    public CurrentAccount(String accountNumber, double initialBalance, double overdraftLimit) {
        super(accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }
    
    // Getter for overdraft limit
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    
    @Override
    public void withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdrawal amount must be positive");
        }
        
        // Check if withdrawal is within overdraft limit
        if (amount > balance + overdraftLimit) {
            throw new BankingException("Exceeds overdraft limit. Available funds: $" + 
                                        String.format("%.2f", (balance + overdraftLimit)));
        }
        
        balance -= amount;
    }
    
    @Override
    public String getAccountType() {
        return "Current Account";
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Overdraft Limit: $" + String.format("%.2f", overdraftLimit);
    }
}
