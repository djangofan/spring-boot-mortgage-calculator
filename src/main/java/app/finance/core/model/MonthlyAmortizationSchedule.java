package app.finance.core.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonthlyAmortizationSchedule
{
    @DateTimeFormat(pattern = "MM/dd/yyyy") private Date startDate;
    private double initialBalance;
    private double interestRate;
    private int durationInMonths;
    private double futureValue;
    private int paymentType;
    private double monthlyPayment;
    private List<Payment> paymentList = new ArrayList<Payment>();

    public Date getStartDate()
    {
        return this.startDate;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public double getInitialBalance()
    {
        return this.initialBalance;
    }
    public void setInitialBalance(double initialBalance)
    {
        this.initialBalance = initialBalance;
    }

    public double getInterestRate()
    {
        return this.interestRate;
    }
    public void setInterestRate(double interestRate)
    {
        this.interestRate = interestRate;
    }

    public int getDurationInMonths()
    {
        return this.durationInMonths;
    }
    public void setDurationInMonths(int durationInMonths)
    {
        this.durationInMonths = durationInMonths;
    }

    public double getFutureValue()
    {
        return this.futureValue;
    }
    public void setFutureValue(double futureValue)
    {
        this.futureValue = futureValue;
    }

    public int getPaymentType()
    {
        return this.paymentType;
    }
    public void setPaymentType(int paymentType)
    {
        this.paymentType = paymentType;
    }

    public double getMonthlyPayment()
    {
        return this.monthlyPayment;
    }
    public void setMonthlyPayment(double monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    public List<Payment> getPaymentList() { return this.paymentList; }
    public void setPaymentList(List<Payment> paymentList) { this.paymentList = paymentList; }

    public void addAllPayments(List<Payment> paymentList) { this.paymentList.addAll(paymentList); }

    @Override
    public String toString()
    {
        return "[" + startDate + "," + initialBalance + "," + interestRate + "," + durationInMonths + "," + futureValue + "," + paymentType + "," + monthlyPayment + "]";
    }


}

