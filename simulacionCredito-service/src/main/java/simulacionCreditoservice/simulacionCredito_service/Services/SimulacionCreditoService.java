package simulacionCreditoservice.simulacionCredito_service.Services;

import org.springframework.stereotype.Service;
import simulacionCreditoservice.simulacionCredito_service.Models.SimulacionCreditoModel;

@Service
public class SimulacionCreditoService {
    
    public SimulacionCreditoModel simularCredito(int requestedAmount, int loanTerm, double annualInterestRate) {
         // Fórmulas para calcular la cuota mensual y otros detalles
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        double monthlyPayment = requestedAmount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -loanTerm));
        return new SimulacionCreditoModel(monthlyPayment, requestedAmount * loanTerm);
    }
}
    
    
