# 🏦 Bank Management System (Java)

✨ Overview

A console-based **Bank Management System** implemented in Java for users to perform core banking tasks such as creating accounts, checking balances, depositing/withdrawing money, and securely deleting accounts with OTP confirmation.
Built with OOPS principles, Collections, and Custom Exceptions to ensure robustness and scalability.

## ✅ Features

* Create Savings / Current account
* Deposit money
* Withdraw money (with OTP verification)
* Display account details
* Calculate interest (Savings only)
* View all accounts owned by a person
* Remove account (with confirmation + OTP verification)
* Exception handling for invalid operations

---

## ✅ Technologies Used

* **Java** (Core)
* **Collections (ArrayList)**
* **OOP Concepts**

  * Abstraction
  * Inheritance
  * Polymorphism
* **Custom Exceptions**
* **OTP Security Implementation**

---

## 📦 Project Structure

```
main.java
│
├── BankAccount (abstract)
│   ├── SavingsAccount
│   └── CurrentAccount
│
├── Bank
├── OTPService
├── InvalidAmountException
└── InsufficientBalanceException
```

---

## 🔐 OTP System

Used during:
✅ Withdrawal
✅ Account Deletion

* Auto generates a 6‑digit OTP
* User gets 3 attempts to verify
* Failure → Action cancelled

---

## 📘 How to Run

1. Compile the project:

```
javac main.java
```

2. Run the program:

```
java main
```

---

## 📋 Menu Options

```
1. Create Account
2. Deposit
3. Withdraw
4. Display Account
5. Calculate Interest
6. Show All Accounts for a Person
7. Exit
8. Remove Account
```

---

## 🧾 Example Output (Account Deletion)

```
Enter Account No: 101
Enter Type: savings
⚠️ Are you sure you want to DELETE this account? (yes/no): yes
Your OTP: 123456
Enter OTP: 123456
✅ Account removed successfully!
```

---

## ✅ OOP Concepts Used

| Concept       | Usage                                  |
| ------------- | -------------------------------------- |
| Abstraction   | Abstract BankAccount class             |
| Inheritance   | SavingsAccount & CurrentAccount        |
| Polymorphism  | Overridden withdraw & interest methods |
| Encapsulation | Private fields & methods               |

---

## ⚠ Exceptions

* InvalidAmountException
* InsufficientBalanceException

Handled when:

* Deposit is ≤ 0
* Withdrawal exceeds balance

---

## ⭐ Future Enhancements

* Persistent storage using files / DB
* GUI (JavaFX / Swing)
* Transaction history
* Online banking authentication

---

## 👨‍💻 Author

Pravin

SQL | JAVA | Web Dev | JDBC

pravinselvakumar39@gmail.com
---

## ✅ License

Free to use & modify.
