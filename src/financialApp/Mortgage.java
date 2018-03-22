//Assignment:   mortgage
//Program:      Mortgage
//Created:      Nov 26, 2017
//By:           Tosh Roberts

package financialApp;

public class Mortgage {

    private int years;
    private double rate;
    private int months;
    private double loan;
    private double balance;
    private double monthlyRate;

    public Mortgage(int years, double rate, double loan) {
        this.years = years;
        this.rate = rate;
        this.loan = loan;
        this.balance = loan;
        this.monthlyRate = (rate/12)/100;
        convertYearsToMonths();
    }

    public Mortgage() {
        this.years = 0;
        this.rate = 0;
        this.loan = 0;
        this.balance = 0;
        convertYearsToMonths();
    }

    public int getMonths() {
        return months;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
        convertYearsToMonths();
    }

    private void convertYearsToMonths() {
        months = years * 12;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getLoan() {
        return loan;
    }

    public void setLoan(double loan) {
        this.loan = loan;
    }

    public double getBalance() {
        return balance;
    }

    public double monthlyPay() {
        return Math.round(((loan*monthlyRate) * ((Math.pow((1+monthlyRate), months))))/
                (Math.pow((1+monthlyRate), months)-1));
    }

    public double balance(int p) {
        if (p >= years)
            return 0;
        else
            return (loan*(Math.pow(1+monthlyRate,months)-Math.pow(1+monthlyRate,p*12)))/(Math.pow(1+monthlyRate,months)-1);
    }

    @Override
    public String toString() {
        return String.format(
                "Mortgage%n" +
                "Loan amount: $%.2f%n" +
                "Term: %d years%n" +
                "Rate: %.2f%%%n" +
                "Monthly Payments: $%.2f%n" +
                "Balance: $%.2f%n" +
                "--------------------------%n",
                loan, years, rate, monthlyPay(), balance);
    }
}
