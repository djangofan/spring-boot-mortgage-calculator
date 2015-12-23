package app.finance.core.service;

// some basics:
// http://www.investopedia.com/articles/03/101503.asp
// http://www.tvmcalcs.com/index.php/tvm/formulas/regular_annuity_formulas
// http://www.experts-exchange.com/articles/1948/A-Guide-to-the-PMT-FV-IPMT-and-PPMT-Functions.html

import org.springframework.stereotype.Service;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Calculates payments and values of a loan amortization.
 */
@Service
public class PaymentService
{
    private final NumberFormat nfPercent;
    private final NumberFormat nfCurrency;

    PaymentService()
    {
        // establish percentage formatter.
        nfPercent = NumberFormat.getPercentInstance();
        nfPercent.setMinimumFractionDigits(2);
        nfPercent.setMaximumFractionDigits(4);

        // establish currency formatter.
        nfCurrency = NumberFormat.getCurrencyInstance();
        nfCurrency.setMinimumFractionDigits(2);
        nfCurrency.setMaximumFractionDigits(2);
    }

    /**
     * Format passed number value to appropriate monetary string for display.
     *
     * @param number
     * @return localized currency string (e.g., "$1,092.20").
     */
    public String formatCurrency(double number)
    {
        return nfCurrency.format(number);
    }

    /**
     * Format passed number value to percent string for display.
     *
     * @param number
     * @return percentage string (e.g., "7.00%").
     */
    public String formatPercent(double number)
    {
        return nfPercent.format(number);
    }

    /**
     * Convert passed string to numerical percent for use in calculations.
     *
     * @param s string as a percent, such as 7.00
     * @return <code>double</code> representing percentage as a decimal.
     * @throws ParseException if string is not a valid representation of a percent.
     */
    public double stringToPercent(String s) throws ParseException
    {
        return nfPercent.parse(s).doubleValue();
    }

    /**
     * Calculates interest rate on a monthly basis for compounding purposes.
     *
     * @param interestRate interest rate expresses as a whole number
     * @return a fraction of the yearly interest rate
     */
    public double getMonthlyInterestRate(double interestRate)
    {
        return interestRate / 100 / 12;
    }

    /**
     * Emulates PMT(interest_rate, number_payments, PV, FV, Type) function, which calculates
     * the mortgage or annuity payment/yield per period.
     *
     * @param r    periodic interest rate represented as a decimal.
     * @param nper number of total payments or periods.
     * @param pv   present value -- borrowed or invested principal.
     * @param fv   future value of loan or annuity.
     * @param type when payment is made: beginning of period is 1; end, 0.
     * @return <code>double</code> representing periodic payment amount.
     */
    public double pmt(double r, int nper, double pv, double fv, int type)
    {
        if (r == 0) {
            return -(pv + fv) / nper;
        }

        // i.e., pmt = r / ((1 + r)^N - 1) * -(pv * (1 + r)^N + fv)
        double pmt = r / (Math.pow(1 + r, nper) - 1) * -(pv * Math.pow(1 + r, nper) + fv);

        // account for payments at beginning of period versus end.
        if (type == 1) {
            pmt /= (1 + r);
        }

        // return results to caller.
        return pmt;
    }

    /**
     * Overloaded pmt() call omitting type.
     * <p/>
     * The 'type' defaults to 0, which is 'payments made at end of periods'.
     *
     * @return double
     * @see #pmt(double, int, double, double, int)
     */
    public double pmt(double r, int nper, double pv, double fv)
    {
        return pmt(r, nper, pv, fv, 0);
    }

    /**
     * Overloaded pmt() call omitting type and fv.
     * <p/>
     * The 'fv' defaults to 0, showing 'intent to finish annuity'.
     * See http://www.excelfunctions.net/Excel-Nper-Function.html
     *
     * @return double
     * @see #pmt(double, int, double, double, int)
     */
    public double pmt(double r, int nper, double pv)
    {
        return pmt(r, nper, pv, 0, 0);
    }

    /**
     * Emulates FV(interest_rate, number_payments, payment, PV, Type) function, which calculates
     * future value or principal at period N.
     *
     * @param r    periodic interest rate represented as a decimal.
     * @param nper number of total payments or periods.
     * @param c    periodic payment amount.
     * @param pv   present value -- borrowed or invested principal.
     * @param type when payment is made: beginning of period is 1; end, 0.
     * @return <code>double</code> representing future principal value.
     */
    public double fv(double r, int nper, double c, double pv, int type)
    {
        if (r == 0) return pv;

        // account for payments at beginning of period versus end.
        // since we are going in reverse, we multiply by 1 plus interest rate.
        if (type == 1) {
            c *= (1 + r);
        }

        // fv = -(((1 + r)^N - 1) / r * c + pv * (1 + r)^N);
        double fv = -((Math.pow(1 + r, nper) - 1) / r * c + pv * Math.pow(1 + r, nper));

        // return results to caller.
        return fv;
    }

    /**
     * Overloaded fv() call omitting type, which defaults to 0.
     *
     * @see #fv(double, int, double, double, int)
     */
    public double fv(double r, int nper, double c, double pv)
    {
        return fv(r, nper, c, pv, 0);
    }

    /**
     * Emulates IPMT(interest_rate, period, number_payments, PV, FV, Type) function, which calculates
     * the portion of the payment at a given period that is the interest on previous balance.
     *
     * @param r    periodic interest rate represented as a decimal.
     * @param per  period (payment number) to check value at.
     * @param nper number of total payments or periods.
     * @param pv   present value -- borrowed or invested principal.
     * @param fv   future value of loan or annuity.
     * @param type when payment is made: beginning of period is 1; end, 0.
     * @return <code>double</code> representing interest portion of payment.
     * @see #pmt(double, int, double, double, int)
     * @see #fv(double, int, double, double, int)
     */
    public double ipmt(double r, int per, int nper, double pv, double fv, int type)
    {
        // Prior period (i.e., per-1) balance times periodic interest rate.
        // i.e., ipmt = fv(r, per-1, c, pv, type) * r
        // where c = pmt(r, nper, pv, fv, type)
        double ipmt = fv(r, per - 1, pmt(r, nper, pv, fv, type), pv, type) * r;

        // account for payments at beginning of period versus end.
        if (type == 1) {
            ipmt /= (1 + r);
        }

        // return results to caller.
        return ipmt;
    }

    /**
     * Emulates PPMT(interest_rate, period, number_payments, PV, FV, Type) function, which calculates the
     * portion of the payment at a given period that will apply to principal.
     *
     * @param r    periodic interest rate represented as a decimal.
     * @param per  period (payment number) to check value at.
     * @param nper number of total payments / periods.
     * @param pv   present value -- borrowed or invested principal.
     * @param fv   future value of loan or annuity.
     * @param type when payment is made: beginning of period is 1; end, 0.
     * @return <code>double</code> representing principal portion of payment.
     * @see #pmt(double, int, double, double, int)
     * @see #ipmt(double, int, int, double, double, int)
     */
    public double ppmt(double r, int per, int nper, double pv, double fv, int type)
    {
        // Calculated payment per period minus interest portion of that period.
        // i.e., ppmt = c - i
        // where c = pmt(r, nper, pv, fv, type)
        // and i = ipmt(r, per, nper, pv, fv, type)
        return pmt(r, nper, pv, fv, type) - ipmt(r, per, nper, pv, fv, type);
    }

}