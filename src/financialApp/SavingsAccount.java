package financialApp;

/**
 * @author Zach
 * A class for a savings account
 *
 */
public class SavingsAccount {
    private int accountNumber;
    private double balance;


    /**
     * CONSTRUCTOR SavingsAccount
     * @param accountNumber
     * @param balance
     */
    public SavingsAccount(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    /**
     *
     * Method getAccountNumber
     * @return accountNumber
     */
    public int getAccountNumber() {
        return accountNumber;
    }
    /**
     *
     * Method getBalance
     * @return balance
     */
    public double getBalance() {
        return balance;
    }
    /**
     *
     * Method deposit
     * @return transaction
     */
    public void deposit(double transaction) {
        this.balance += transaction;
    }
    /**
     *
     * Method withdraw
     * @return transaction
     */
    public void withdraw(double transaction) {
        this.balance -= transaction;
    }
    /**
     *
     * Method toString
     * @return accountNumber, balance
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("Account Number: %d%n" +
                "Balance: %.2f%n" +
                "--------------------%n", accountNumber, balance);
    }
    /**
     *
     * Method printBalance
     * @return balance
     */
    public String printBalance() {
        return String.format("Balance: %.2f\n", balance);
    }
}