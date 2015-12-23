package app.finance.core.controller;

import app.finance.core.service.AmortizationService;
import app.finance.core.model.MonthlyAmortizationSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MortgageCalculatorController
{
    @Autowired
    private AmortizationService amortizationService;

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
        amortizationService.initializeUnknownFields(monthlyAmortizationSchedule);
        model.addAttribute(monthlyAmortizationSchedule);

        return "schedule";
    }



}