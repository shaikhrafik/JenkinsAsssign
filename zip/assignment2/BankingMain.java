package assignment2;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingMain {
    private static List<Account> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        boolean exit = false;
        
        while (!exit) {
            printMainMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        depositMoney();
                        break;
                    case 3:
                        withdrawMoney();
                        break;
                    case 4:
                        checkBalance();
                        break;
                    case 5:
                        addInterestToSavings();
                        break;
                    case 6:
                        listAllAccounts();
                        break;
                    case 7:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        System.out.println("Thank you for using the Banking System!");
    }
    
    private static void printMainMenu() {
        System.out.println("\n---- BANKING SYSTEM ----");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Check Balance");
        System.out.println("5. Add Interest to Savings Accounts");
        System.out.println("6. List All Accounts");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void createAccount() {
        System.out.println("\n---- CREATE ACCOUNT ----");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.print("Enter account type: ");
        
        try {
            int accountType = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Enter account number: ");
            String accountNumber = scanner.nextLine().trim();
            
            // Check if account number already exists
            for (Account acc : accounts) {
                if (acc.getAccountNumber().equals(accountNumber)) {
                    System.out.println("Account with this number already exists!");
                    return;
                }
            }
            
            System.out.print("Enter initial balance: $");
            double initialBalance = Double.parseDouble(scanner.nextLine().trim());
            
            if (initialBalance < 0) {
                System.out.println("Initial balance cannot be negative.");
                return;
            }
            
            switch (accountType) {
                case 1: // Savings Account
                    System.out.print("Enter interest rate (%): ");
                    double interestRate = Double.parseDouble(scanner.nextLine().trim());
                    
                    if (interestRate < 0) {
                        System.out.println("Interest rate cannot be negative.");
                        return;
                    }
                    
                    accounts.add(new SavingsAccount(accountNumber, initialBalance, interestRate));
                    System.out.println("Savings Account created successfully.");
                    break;
                    
                case 2: // Current Account
                    System.out.print("Enter overdraft limit: $");
                    double overdraftLimit = Double.parseDouble(scanner.nextLine().trim());
                    
                    if (overdraftLimit < 0) {
                        System.out.println("Overdraft limit cannot be negative.");
                        return;
                    }
                    
                    accounts.add(new CurrentAccount(accountNumber, initialBalance, overdraftLimit));
                    System.out.println("Current Account created successfully.");
                    break;
                    
                default:
                    System.out.println("Invalid account type selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric values.");
        }
    }
    
    private static Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    
    private static void depositMoney() {
        System.out.print("\nEnter account number: ");
        String accountNumber = scanner.nextLine().trim();
        
        Account account = findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        
        System.out.print("Enter deposit amount: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            account.deposit(amount);
            System.out.println("Deposit successful. New balance: $" + 
                                String.format("%.2f", account.getBalance()));
        } catch (BankingException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount.");
        }
    }
    
    private static void withdrawMoney() {
        System.out.print("\nEnter account number: ");
        String accountNumber = scanner.nextLine().trim();
        
        Account account = findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        
        System.out.print("Enter withdrawal amount: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            account.withdraw(amount);
            System.out.println("Withdrawal successful. New balance: $" + 
                                String.format("%.2f", account.getBalance()));
        } catch (BankingException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount.");
        }
    }
    
    private static void checkBalance() {
        System.out.print("\nEnter account number: ");
        String accountNumber = scanner.nextLine().trim();
        
        Account account = findAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }
        
        System.out.println(account);
    }
    
    private static void addInterestToSavings() {
        boolean interestAdded = false;
        
        for (Account account : accounts) {
            if (account instanceof SavingsAccount) {
                SavingsAccount savingsAccount = (SavingsAccount) account;
                double interest = savingsAccount.calculateInterest();
                savingsAccount.addInterest();
                System.out.println("Added $" + String.format("%.2f", interest) + 
                                    " interest to account " + savingsAccount.getAccountNumber() + 
                                    ". New balance: $" + String.format("%.2f", savingsAccount.getBalance()));
                interestAdded = true;
            }
        }
    
        if (!interestAdded) {
            System.out.println("No savings accounts found.");
        }
    }
    
    private static void listAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
    } 
       
    }