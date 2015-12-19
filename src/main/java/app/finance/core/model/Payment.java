package app.finance.core.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class Payment
{
    private int paymentNumber;
    @DateTimeFormat(pattern = "MM/dd/yyyy") private Date paymentDate;
    private double balance;
    private double principalPaid;
    private double interestPaid;
    private double accumulatedInterest;

    public Payment(int paymentNumber, Date paymentDate, double balance, double principalPaid, double interestPaid, double accumulatedInterest)
    {
        setPaymentNumber(paymentNumber);
        setPaymentDate(paymentDate);
        setBalance(balance);
        setPrincipalPaid(principalPaid);
        setInterestPaid(interestPaid);
        setAccumulatedInterest(accumulatedInterest);
    }

    public int getPaymentNumber() { return this.paymentNumber; }
    public void setPaymentNumber(int paymentNumber) { this.paymentNumber = paymentNumber; }

    public Date getPaymentDate()
    {
        return this.paymentDate;
    }
    public void setPaymentDate(Date paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public double getBalance()
    {
        return this.balance;
    }
    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public double getPrincipalPaid()
    {
        return this.principalPaid;
    }
    public void setPrincipalPaid(double principalPaid)
    {
        this.principalPaid = principalPaid;
    }

    public double getInterestPaid()
    {
        return this.interestPaid;
    }
    public void setInterestPaid(double interestPaid)
    {
        this.interestPaid = interestPaid;
    }

    public double getAccumulatedInterest()
    {
        return this.accumulatedInterest;
    }
    public void setAccumulatedInterest(double accumulatedInterest) { this.accumulatedInterest = accumulatedInterest; }

    @Override
    public String toString()
    {
        return "[" + paymentNumber + "," + paymentDate + "," + balance + "," + principalPaid + "," + interestPaid + "," + accumulatedInterest + "]";
    }

}
