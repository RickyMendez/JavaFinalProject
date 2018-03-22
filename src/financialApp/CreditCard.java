/**
 * Author: Isaac Olsen
 * Date: 11.19.2017
 * Assignment: Group Project
 * File: CreditCard.java
 */
package financialApp;


/**
 * @author isaac
 *
 */
public class CreditCard {
	//********************Fields********************
    private int id;
	private String lender;
	private double balance;
	private double minimumPay;
	private double interestRate;
	
	//********************Constructors********************
	/**
	 * Constructor to initialize the lender to "Credit Card Default". Balance, minimum payment, and interest 
	 * rate defaults to 15 (based off of average credit card rates in the US).	
	 */
	public CreditCard (){
	    this.id = 0;
		this.lender = "Credit Card Default";
		this.balance = 0;
		this.minimumPay = 0;
		this.interestRate = 15;
	}
	
	/**
	 * @param lndr
	 * Constructor to initialize the lender name. Balance, minimum payment, and interest rate 
	 * defaults to 15 (based off of average credit card rates in the US).
	 */
	public CreditCard (int id, String lndr){
        this.id = id;
		this.lender = lndr;
		this.balance = 0;
		this.minimumPay = 0;
		this.interestRate = 15;		
	}
	
	/**
	 * @param lndr
	 * @param bal
	 * Constructor to initialize the lender name and balance. Minimum calls calcMinMonthPayment method 
	 * to calculate minimum payment. Interest rate defaults to 15 (based off of average credit card rates in the US).
	 */
	public CreditCard (int id, String lndr, double bal) {
        this.id = id;
		this.lender = lndr;
		this.balance = bal;
		this.interestRate = 15;
		this.minimumPay = calcMinMonthPayment();
	}
	
	/**
	 * @param lndr
	 * @param bal
	 * @param min
	 * Constructor to initialize the lender name, balance, and minimum payment field. Interest rate 
	 * defaults to 15 (based off of average credit card rates in the US).
	 */
	public CreditCard (int id, String lndr, double bal, double min) {
        this.id = id;
		this.lender = lndr;
		this.balance = bal;
		this.minimumPay = min;
		this.interestRate = 15;
	}
	
	/**
	 * @param lndr
	 * @param bal
	 * @param min
	 * @param intRat
	 * Constructor to initialize each value
	 */
	public CreditCard (int id, String lndr, double bal, double min, double intRat) {
        this.id = id;
		this.lender = lndr;
		this.balance = bal;
		this.interestRate = intRat;
        if (min == 0){
            this.minimumPay = calcMinMonthPayment();
        } else {
            this.minimumPay = min;
        }
    }

	//********************Getters and Setters********************

    public int getId() {
        return id;
    }

    /**
	 * @return the lender
	 */
	public String getLender() {
		return lender;
	}

	/**
	 * @param lndr the lender to set
	 */
	public void setLender(String lndr) {
		this.lender = lndr;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param bal the balance to set
	 */
	public void setBalance(double bal) {
	    this.balance = bal;
        this.minimumPay = calcMinMonthPayment();
	}

	/**
	 * @return the minimumPay
	 */
	public double getMinimumPay() {
		return minimumPay;
	}

	/**
	 * @param min the minimumPay to set
	 */
	public void setMinimumPay(double min) {
		this.minimumPay = min;
	}

	/**
	 * @return the interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * @param intRat the interestRate to set
	 */
	public void setInterestRate(double intRat) {
		this.interestRate = intRat;
		this.minimumPay = calcMinMonthPayment();
	}
	
	//********************Methods********************
	/**
	 * @return the minimum interest payment for the next month. 
	 */
	private double interestOnlyPayment(){
		return ((interestRate/100)*balance)/12;
	}
	
	/**
	 * @return a minimum payment that rounds to the nearest $10 amount that still pays off some balance.
	 */
	private double calcMinMonthPayment(){
		double payment; 
		
		if (balance <= 0) {
			payment = 0;
		}
		else {
			if (interestOnlyPayment() % 10 == 0) {
				payment = Math.round(interestOnlyPayment());
//				System.out.println("mod 10: "+payment);
			}
			else {
				payment = Math.ceil(interestOnlyPayment());
//				System.out.println(payment);
			}
		}

		return payment;
	}


	//********************Overridden Methods********************
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(interestRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lender == null) ? 0 : lender.hashCode());
		temp = Double.doubleToLongBits(minimumPay);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (Double.doubleToLongBits(interestRate) != Double.doubleToLongBits(other.interestRate))
			return false;
		if (lender == null) {
			if (other.lender != null)
				return false;
		} else if (!lender.equals(other.lender))
			return false;
		if (Double.doubleToLongBits(minimumPay) != Double.doubleToLongBits(other.minimumPay))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
		        "Card #: %d%n" +
                "Lender: %s%n" +
                "Balance: $%.2f%n" +
                "Minimum Payment: $%.2f%n" +
                "Interest Rate: %.2f%%%n" +
                "--------------------------%n",
				id,lender, balance, minimumPay, interestRate);
	}
	
	
}