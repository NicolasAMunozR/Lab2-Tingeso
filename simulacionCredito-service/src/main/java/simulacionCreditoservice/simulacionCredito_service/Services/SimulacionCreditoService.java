package simulacionCreditoservice.simulacionCredito_service.Services;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SimulacionCreditoService {
    /**
     * Simulate a loan.
     * @param amount An int with the amount of the loan.
     * @param type A String with the type of loan.
     * @param term An int with the term of the loan.
     * @param interestRate A double with the interest rate of the loan.
     * @return An int with the monthly payment of the loan.
     */
    public int simulation(int amount, int term, double interestRate) {
        if (term == 0) {
            return amount;
        }
        double monthlyInterestRate = interestRate / 100 / 12;
        int termInMonths = term * 12;
        double monthlyPayment = amount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, termInMonths)) /
                (Math.pow(1 + monthlyInterestRate, termInMonths) - 1);
        return (int) Math.round(monthlyPayment);
    }
}
    
    
