package simulacionCreditoservice.simulacionCredito_service.Controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import simulacionCreditoservice.simulacionCredito_service.Models.SimulacionCreditoModel;
import simulacionCreditoservice.simulacionCredito_service.Services.SimulacionCreditoService;

@RequestMapping("/api/simulacion-credito")
public class SimulacionCreditoController {
    
    private final SimulacionCreditoService simulacionCreditoService;
    
    public SimulacionCreditoController(SimulacionCreditoService simulacionCreditoService) {
        this.simulacionCreditoService = simulacionCreditoService;
    }
    
    @PostMapping("/simular")
    public ResponseEntity<SimulacionCreditoModel> simularCredito(@RequestBody SimulacionCreditoModel request) {
        SimulacionCreditoModel simulacion = simulacionCreditoService.simularCredito(
            request.getRequestedAmount(),
            request.getLoanTerm(),
            request.getAnnualInterestRate()
        );
         return ResponseEntity.ok(simulacion);
    }
}
    
    
