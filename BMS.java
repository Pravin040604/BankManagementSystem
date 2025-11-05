package main;

import java.util.*;

// ✅ Custom Exception: Invalid Amount
class InvalidAmountException extends Exception {
    public InvalidAmountException(String msg) {
        super(msg);
    }
}

// ✅ Custom Exception: Insufficient Balance
class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String msg) {
        super(msg);
    }
}

// ✅ Abstract Base Class
abstract class BankAccount {
    protected String accNo;
    protected String name;
    protected double balance;
    protected String accountType;

    public BankAccount(String accNo, String name, double balance) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
    }

    public String getAccNo() {
        return accNo;
    }

    public String getAccountType() {
        return accountType;
    }

    // Deposit
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive!");
        }
        balance += amount;
        System.out.println("✅ Successfully deposited ₹" + amount);
    }

    // Withdraw
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        String otp = OTPService.generateOTP();
        System.out.println("Your OTP : " + otp);

        if (!OTPService.verifyOTP(otp)) {
            System.out.println("❌ OTP verification failed. Transaction cancelled!");
            return;
        }

        if (amount <= 0)
            throw new InvalidAmountException("Withdraw amount must be positive!");
        if (balance < amount)
            throw new InsufficientBalanceException("❌ Insufficient balance!");

        balance -= amount;
        System.out.println("✅ Withdraw successful! New balance = " + balance);
    }

    // Display
    public void display() {
        System.out.println("Account No: " + accNo);
        System.out.println("Name: " + name);
        System.out.println("Balance: ₹" + balance);
        System.out.println("Account Type: " + accountType);
    }

    public abstract void calculateInterest();
}

// ✅ Savings Account
class SavingsAccount extends BankAccount {
    private double interestRate = 0.05;

    public SavingsAccount(String accNo, String name, double balance) {
        super(accNo, name, balance);
        this.accountType = "savings";
    }

    @Override
    public void calculateInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("💰 Interest of ₹" + interest + " added to your Savings Account.");
    }
}

// ✅ Current Account
class CurrentAccount extends BankAccount {
    public CurrentAccount(String accNo, String name, double balance) {
        super(accNo, name, balance);
        this.accountType = "current";
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        String otp = OTPService.generateOTP();
        System.out.println("Your OTP : " + otp);

        if (!OTPService.verifyOTP(otp)) {
            System.out.println("❌ OTP verification failed. Transaction cancelled!");
            return;
        }

        if (amount > balance) {
            System.out.println("❌ Insufficient balance!");
            return;
        }

        balance -= amount;
        System.out.println("✅ Withdraw successful! New balance = " + balance);
    }

    @Override
    public void calculateInterest() {
        System.out.println("❌ No interest for Current Accounts.");
    }
}

// ✅ OTP Service
class OTPService {

    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public static boolean verifyOTP(String generatedOTP) {
        Scanner sc = new Scanner(System.in);

        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Enter OTP: ");
            String entered = sc.nextLine();

            if (generatedOTP.equals(entered)) {
                return true;
            }

            attempts--;
            System.out.println("❌ Invalid OTP! Remaining attempts: " + attempts);
        }

        return false;
    }
}

// ✅ Bank Class
class Bank {
    private ArrayList<BankAccount> accounts = new ArrayList<>();

    // ✅ Check if account already exists (same accNo + type)
    public boolean isexists(String accNo, String accType) {
        for (BankAccount acc : accounts) {
            if (acc.getAccNo().equals(accNo) &&
                acc.getAccountType().equalsIgnoreCase(accType)) {
                System.out.println("❌ Account already exists with same type!");
                return false;
            }
        }
        return true;
    }

    // ✅ Create
    public void createAccount(String type, String accNo, String name, double balance) {
        BankAccount acc = null;

        if (type.equalsIgnoreCase("savings")) {
            acc = new SavingsAccount(accNo, name, balance);
        } else if (type.equalsIgnoreCase("current")) {
            acc = new CurrentAccount(accNo, name, balance);
        } else {
            System.out.println("❌ Invalid account type!");
            return;
        }

        accounts.add(acc);
        System.out.println("✅ Account created successfully for " + name);
    }

    // ✅ Find
    public BankAccount findAccount(String accNo, String type) {
        for (BankAccount acc : accounts) {
            if (acc.getAccNo().equals(accNo) &&
                acc.getAccountType().equalsIgnoreCase(type)) {
                return acc;
            }
        }
        return null;
    }

    // ✅ Show all accounts for same name
    public void showAccountsByName(String name) {
        boolean found = false;
        for (BankAccount acc : accounts) {
            if (acc.name.equalsIgnoreCase(name)) {
                acc.display();
                System.out.println("----------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("❌ No accounts found for name: " + name);
        }
    }

    // ✅ ✅ REMOVE Account with Confirmation + OTP
    public boolean removeAccount(String accNo, String type) {

        Iterator<BankAccount> itr = accounts.iterator();

        while (itr.hasNext()) {
            BankAccount acc = itr.next();
            if (acc.getAccNo().equals(accNo) &&
                acc.getAccountType().equalsIgnoreCase(type)) {

                Scanner sc = new Scanner(System.in);
                System.out.print("⚠️ Are you sure you want to DELETE this account? (yes/no): ");
                String confirm = sc.next();

                if (!confirm.equalsIgnoreCase("yes")) {
                    System.out.println("❌ Account deletion cancelled!");
                    return false;
                }

                // ✅ OTP
                String otp = OTPService.generateOTP();
                System.out.println("Your OTP : " + otp);

                if (!OTPService.verifyOTP(otp)) {
                    System.out.println("❌ OTP verification failed. Account NOT deleted!");
                    return false;
                }

                itr.remove();
                System.out.println("✅ Account removed successfully!");
                return true;
            }
        }

        System.out.println("❌ Account not found!");
        return false;
    }
}


// ✅ Main
public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("\n===== 🏦 BANK MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display Account");
            System.out.println("5. Calculate Interest");
            System.out.println("6. Show All Accounts for a Person");
            System.out.println("7. Exit");
            System.out.println("8. Remove Account");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            try {
                switch (ch) {

                    case 1:
                        System.out.print("Enter Type (Savings/Current): ");
                        String type = sc.next();
                        System.out.print("Enter Account No: ");
                        String accNo = sc.next();

                        if (bank.isexists(accNo, type)) {
                            System.out.print("Enter Name: ");
                            String name = sc.next();
                            System.out.print("Enter Initial Balance: ");
                            double bal = sc.nextDouble();
                            bank.createAccount(type, accNo, name, bal);
                        }
                        break;

                    case 2:
                        System.out.print("Enter Account No: ");
                        accNo = sc.next();
                        System.out.print("Enter Type: ");
                        type = sc.next();

                        BankAccount acc1 = bank.findAccount(accNo, type);
                        if (acc1 == null) {
                            System.out.println("❌ Account not found!");
                            break;
                        }

                        System.out.print("Enter deposit amount: ");
                        double dep = sc.nextDouble();
                        acc1.deposit(dep);
                        break;

                    case 3:
                        System.out.print("Enter Account No: ");
                        accNo = sc.next();
                        System.out.print("Enter Type: ");
                        type = sc.next();

                        BankAccount acc2 = bank.findAccount(accNo, type);
                        if (acc2 == null) {
                            System.out.println("❌ Account not found!");
                            break;
                        }

                        System.out.print("Enter withdraw amount: ");
                        double wd = sc.nextDouble();
                        acc2.withdraw(wd);
                        break;

                    case 4:
                        System.out.print("Enter Account No: ");
                        accNo = sc.next();
                        System.out.print("Enter Type: ");
                        type = sc.next();

                        BankAccount acc3 = bank.findAccount(accNo, type);
                        if (acc3 != null)
                            acc3.display();
                        else
                            System.out.println("❌ Account not found!");
                        break;

                    case 5:
                        System.out.print("Enter Account No: ");
                        accNo = sc.next();
                        System.out.print("Enter Type: ");
                        type = sc.next();

                        BankAccount acc4 = bank.findAccount(accNo, type);
                        if (acc4 != null)
                            acc4.calculateInterest();
                        else
                            System.out.println("❌ Account not found!");
                        break;

                    case 6:
                        System.out.print("Enter Name: ");
                        String nm = sc.next();
                        bank.showAccountsByName(nm);
                        break;

                    case 7:
                        System.out.println("👋 Thank you for using Bank Management System!");
                        System.exit(0);

                    // ✅ Account Removal
                    case 8:
                        System.out.print("Enter Account No: ");
                        accNo = sc.next();
                        System.out.print("Enter Type: ");
                        type = sc.next();
                        bank.removeAccount(accNo, type);
                        break;

                    default:
                        System.out.println("❌ Invalid choice!");
                }

            } catch (InvalidAmountException | InsufficientBalanceException e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠️ Unexpected error: " + e.getMessage());
            }
        }
    }
}
