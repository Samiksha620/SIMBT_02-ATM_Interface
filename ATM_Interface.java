import java.util.ArrayList;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String accountNumber;
    private String pin;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account(String accountNumber, String pin) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePin(String pinToCheck) {
        return pin.equals(pinToCheck);
    }

    public double getBalance() {
        return balance;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawn $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            System.out.println("Deposited $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getAccountNumber(), amount));
            System.out.println("Transferred $" + amount + " to " + recipient.getAccountNumber() + ".");
        } else {
            System.out.println("Invalid transfer amount or insufficient funds.");
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }
}

class ATM {
    private ArrayList<Account> accounts;
    private Scanner scanner;

    public ATM() {
        accounts = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void createAccount(String accountNumber, String pin) {
        Account account = new Account(accountNumber, pin);
        accounts.add(account);
        System.out.println("Account created successfully.");
    }

    public Account login() {
        System.out.print("Enter your account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber) && account.validatePin(pin)) {
                System.out.println("Login successful.");
                return account;
            }
        }

        System.out.println("Authentication failed. Please check your account number and PIN.");
        return null;
    }

    public void run() {
        while (true) {
            System.out.println("Welcome to the ATM!");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Quit");
            System.out.print("Select an option (1/2/3): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice == 1) {
                System.out.print("Enter your new account number: ");
                String accountNumber = scanner.nextLine();
                System.out.print("Set your PIN: ");
                String pin = scanner.nextLine();
                createAccount(accountNumber, pin);
            } else if (choice == 2) {
                Account loggedInAccount = login();
                if (loggedInAccount != null) {
                    loggedInAccountActions(loggedInAccount);
                }
            } else if (choice == 3) {
                System.out.println("Thank you for using the ATM. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void loggedInAccountActions(Account account) {
        while (true) {
            System.out.println("Welcome, " + account.getAccountNumber() + "!");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Transaction History");
            System.out.println("6. Logout");
            System.out.print("Select an option (1/2/3/4/5/6): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: $" + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter the withdrawal amount: $");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter the deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character
                    account.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter the recipient's account number: ");
                    String recipientAccountNumber = scanner.nextLine();
                    Account recipient = findAccountByNumber(recipientAccountNumber);
                    if (recipient != null) {
                        System.out.print("Enter the transfer amount: $");
                        double transferAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume the newline character
                        account.transfer(recipient, transferAmount);
                    } else {
                        System.out.println("Recipient account not found.");
                    }
                    break;
                case 5:
                    account.displayTransactionHistory();
                    break;
                case 6:
                    System.out.println("Logged out. Thank you for using the ATM.");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private Account findAccountByNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.run();
    }
}

public class ATM_Interface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.run();
    }
}