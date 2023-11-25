
import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private ArrayList<String> transactionHistory;

    public BankAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposit: +" + amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrawal: -" + amount);
        } else {
            System.out.println("Insufficient funds!");
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transfer to " + recipient.getAccountHolder() + ": -" + amount);
        } else {
            System.out.println("Insufficient funds for transfer!");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History for Account " + accountNumber);
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
        System.out.println("Current Balance: " + balance);
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}

public class OBS {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<BankAccount> accounts = new ArrayList<>();

        while (true) {
            System.out.println("\n----- Online Bank System -----");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.next();
                    System.out.print("Enter account holder name: ");
                    String accountHolder = scanner.next();
                    BankAccount newAccount = new BankAccount(accountNumber, accountHolder);
                    accounts.add(newAccount);
                    System.out.println("Account created successfully!");
                    break;
                case 2:
                    performTransaction(accounts, "Deposit");
                    break;
                case 3:
                    performTransaction(accounts, "Withdraw");
                    break;
                case 4:
                    performTransfer(accounts);
                    break;
                case 5:
                    viewTransactionHistory(accounts);
                    break;
                case 6:
                    System.out.println("Exiting Online Bank System. Thank you!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void performTransaction(ArrayList<BankAccount> accounts, String transactionType) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        BankAccount account = findAccount(accounts, accountNumber);

        if (account != null) {
            System.out.print("Enter amount to " + transactionType.toLowerCase() + ": ");
            double amount = scanner.nextDouble();

            switch (transactionType) {
                case "Deposit":
                    account.deposit(amount);
                    break;
                case "Withdraw":
                    account.withdraw(amount);
                    break;
            }

            System.out.println(transactionType + " completed successfully!");
        } else {
            System.out.println("Account not found!");
        }
    }

    private static void performTransfer(ArrayList<BankAccount> accounts) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter sender account name: ");
        String senderAccountNumber = scanner.next();
        BankAccount senderAccount = findAccount(accounts, senderAccountNumber);

        System.out.print("Enter recipient account name: ");
        String recipientAccountNumber = scanner.next();
        BankAccount recipientAccount = findAccount(accounts, recipientAccountNumber);

        if (senderAccount != null && recipientAccount != null) {
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            senderAccount.transfer(recipientAccount, amount);
            System.out.println("Transfer completed successfully!");
        } else {
            System.out.println("Sender or recipient account not found!");
        }
    }

    private static void viewTransactionHistory(ArrayList<BankAccount> accounts) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        BankAccount account = findAccount(accounts, accountNumber);

        if (account != null) {
            account.printTransactionHistory();
        } else {
            System.out.println("Account not found!");
        }
    }

    private static BankAccount findAccount(ArrayList<BankAccount> accounts, String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountHolder().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}

