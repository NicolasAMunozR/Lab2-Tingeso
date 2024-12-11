package simulacionCreditoservice.simulacionCredito_service.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simulacionCreditoservice.simulacionCredito_service.Services.SimulacionCreditoService;

import java.util.Map;


@RestController
@RequestMapping("/simulacionCredito")
public class SimulacionCreditoController {
    @Autowired
    private SimulacionCreditoService simulacionCreditoService;
    /**
     * Controller that allows simulating a mortgage credit for a client.
     * @param body A Map with the data of the mortgage credit to simulate.
     * @return An Integer with the monthly payment of the mortgage credit.
     */
    @PostMapping("/simulation")
    public ResponseEntity<Integer> simulation(@RequestBody Map<String, String> body) {
        try {
            // The amount of the loan is obtained.
            int amount = Integer.parseInt(body.get("amount"));
            // The term of the loan is obtained.
            int term = Integer.parseInt(body.get("term"));
            // The interest rate of the loan is obtained.
            double interestRate = Double.parseDouble(body.get("interestRate"));
            // The monthly payment of the loan is calculated.
            int monthlyPayment = simulacionCreditoService.simulation(amount, term, interestRate);
            // The monthly payment of the loan is returned.
            return ResponseEntity.ok(monthlyPayment);
        } catch (Exception e) {
            // If there is an error, return 0.
            return null;
        }
    }
}
    
    
