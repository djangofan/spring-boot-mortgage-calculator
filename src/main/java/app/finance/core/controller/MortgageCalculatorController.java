package app.finance.core.controller;

import app.finance.core.service.FinanceService;
import app.finance.core.model.Payment;
import app.finance.core.model.MonthlyAmortizationSchedule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class MortgageCalculatorController
{
    /**
     * Default values are loaded into form from method argument 'monthlyAmortizationSchedule', loaded with default values.
     * @param monthlyAmortizationSchedule a MonthlyAmortizationSchedule object, auto-loaded by Spring MVC
     * @return a string identifier for which .jsp view to redirect to
     */
    @RequestMapping(path={"/index", "/"}, method = RequestMethod.GET)
    public String loadInitialFormInput(@ModelAttribute MonthlyAmortizationSchedule monthlyAmortizationSchedule) {
        return "form";
    }

    @RequestMapping(value = "/showSchedule", method = RequestMethod.POST)
    public String calculatePayments(@ModelAttribute MonthlyAmortizationSchedule monthlyAmortizationSchedule, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
            System.out.println( "There are errors! " + bindingResult.getAllErrors().toString() );
        }
        initMonthlyAmortizationSchedule(monthlyAmortizationSchedule);
        model.addAttribute(monthlyAmortizationSchedule);

        return "schedule";
    }

    private void initMonthlyAmortizationSchedule(MonthlyAmortizationSchedule monthlyAmortizationSchedule)
    {
        Date startDate = monthlyAmortizationSchedule.getStartDate();
        double initialBalance = monthlyAmortizationSchedule.getInitialBalance();
        double interestRate = monthlyAmortizationSchedule.getInterestRate();
        int durationInMonths = monthlyAmortizationSchedule.getDurationInMonths();
        double futureValue = monthlyAmortizationSchedule.getFutureValue();
        int paymentType = monthlyAmortizationSchedule.getPaymentType();

        double monthlyPayment = FinanceService.pmt(FinanceService.getMonthlyInterestRate(interestRate), durationInMonths, initialBalance, futureValue, paymentType);
        monthlyAmortizationSchedule.setMonthlyPayment(monthlyPayment);

        List<Payment> paymentList = calculatePaymentList(startDate, initialBalance, durationInMonths, paymentType, interestRate, futureValue);
        monthlyAmortizationSchedule.addAllPayments(paymentList);
    }

    public List<Payment> calculatePaymentList(Date startDate, double initialBalance, int durationInMonths, int paymentType, double interestRate, double futureValue)
    {
        List<Payment> paymentList = new ArrayList<Payment>();
        Date loopDate = startDate;
        double balance = initialBalance;
        double accumulatedInterest = 0;
        for (int paymentNumber = 1; paymentNumber <= durationInMonths; paymentNumber++)
        {
            if (paymentType == 0)
            {
                loopDate = addOneMonth(loopDate);
            }
            double principalPaid = FinanceService.ppmt(FinanceService.getMonthlyInterestRate(interestRate), paymentNumber, durationInMonths, initialBalance, futureValue, paymentType);
            double interestPaid = FinanceService.ipmt(FinanceService.getMonthlyInterestRate(interestRate), paymentNumber, durationInMonths, initialBalance, futureValue, paymentType);
            balance = balance + principalPaid;
            accumulatedInterest += interestPaid;

            Payment payment = new Payment(paymentNumber, loopDate, balance, principalPaid, interestPaid, accumulatedInterest);

            paymentList.add(payment);

            if (paymentType == 1)
            {
                loopDate = addOneMonth(loopDate);
            }
        }
        return paymentList;
    }

    private Date addOneMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

}