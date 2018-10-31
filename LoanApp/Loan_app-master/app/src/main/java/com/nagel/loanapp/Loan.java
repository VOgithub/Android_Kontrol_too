package com.nagel.loanapp;

public class Loan {

    static Loan instance = null;
    private double principal = 0;     // the amount borrowed inc. costs   // issue 5.1
    private double interestRate = 0;  // interest rate as a number between 0 and 1 that indicates the rate for 1 period  //issue 5.2
    private int periods = 0;          // the number of periods and thus number of payments

    public Loan() {
    }

    public Loan getInstance() {
        if(instance == null)
        {
            synchronized (Loan.class)
            {
                if(instance == null) instance = new Loan();
            }
        }
        return instance;
    }

    public double payment()
    {
        return principal * interestRate / (1 - Math.pow(1 + interestRate, -periods));
    }

    public double outstanding(int n)
    {
        return principal * Math.pow(1 + interestRate, n) - payment() * (Math.pow(1 + interestRate, n) - 1) / interestRate;
    }

    public double interest(int n)  // issue 5.3
    {
        return outstanding(n - 1) * interestRate;
    }

    public double repayment(int n)
    {
        return payment() - interest(n);
    }
}
