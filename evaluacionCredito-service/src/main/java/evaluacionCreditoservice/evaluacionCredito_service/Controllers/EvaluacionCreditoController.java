package evaluacionCreditoservice.evaluacionCredito_service.Controllers;

import evaluacionCreditoservice.evaluacionCredito_service.Models.SolicitudCreditoModel;
import evaluacionCreditoservice.evaluacionCredito_service.Services.EvaluacionCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/evaluacionCredito")
public class EvaluacionCreditoController {
    @Autowired
    private EvaluacionCreditoService evaluacionCreditoService;
    @Autowired
    RestTemplate restTemplate;

    /**
     * Method that allows evaluating a loan.
     * @param credit A CreditEntity with the data of the loan to evaluate.
     * @return A CreditEntity with the loan evaluated.
     */
    @PutMapping("/evaluateCredit/{id}")
    public ResponseEntity<SolicitudCreditoModel> evaluateCredit(@PathVariable Long id) {
        try{
            // The loan is searched in the database.
            SolicitudCreditoModel creditFound = evaluacionCreditoService.findById(id);
            if(creditFound != null){
                System.out.println("BIENNNNNNNNNNNNNNNNNNNNNNN");
            }
            System.out.println("PDF size (bytes): " + creditFound.getCreditHistory().length);
            // The loan is evaluated.
            SolicitudCreditoModel approved = evaluacionCreditoService.evaluateCredit(creditFound);
            System.out.println("asdsaffassa "+ approved);
            // The loan is returned.
            return ResponseEntity.ok(approved);
        }
        catch (Exception e) {
            // If there is an error, return null.
            return null;
        }
    }
    /**
     * Method that allows updating the status of a loan.
     * @param id A Long with the id of the loan to update.
     * @return A CreditEntity with the loan updated.
     */
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<SolicitudCreditoModel> updateStatus(@PathVariable Long id) {
        try{
            // The loan is searched in the database.
            SolicitudCreditoModel credit = evaluacionCreditoService.findById(id);
            // If the loan is pending documentation.
            if(credit.getApplicationStatus().equals("Pendiente de documentación")) {
                // The loan is in review.
                credit.setApplicationStatus("En evaluación");
            }
            // If the loan is under review.
            else if(credit.getApplicationStatus().equals("En evaluación")) {
                // The loan is approved.
                credit.setApplicationStatus("Pre-aprobado");
            }
            // If the loan is pre-approved.
            else if (credit.getApplicationStatus().equals("Pre-aprobado")) {
                // The loan is approved.
                credit.setApplicationStatus("Aprobación final");
            }
            // If the loan is finally approved.
            else if (credit.getApplicationStatus().equals("Aprobación final")) {
                // The loan is approved.
                credit.setApplicationStatus("Aprobada");
            }
            // If the loan is approved.
            else if (credit.getApplicationStatus().equals("Aprobada")) {
                // The loan is disbursed.
                credit.setApplicationStatus("Desembolso");
                // The loan is disbursed.
                evaluacionCreditoService.Disbursement(credit);
            }
            else {
                // If the loan is rejected.
                return null;
            }
            // The loan is saved in the database.
            SolicitudCreditoModel creditSaved = evaluacionCreditoService.saveCredit(credit);
            // The loan is returned.
            return ResponseEntity.ok(creditSaved);
        }
        catch (Exception e) {
            // If there is an error, return null.
            return null;
        }
    }
    /**
     * Method that allows updating the terms of a loan.
     * @param id A Long with the id of the loan to update.
     * @param lienInsurance A Double with the lien insurance of the loan.
     * @param administrationFee A Double with the administration fee of the loan.
     * @return A CreditEntity with the loan updated.
     */
    @PutMapping("/updateTerms")
    public ResponseEntity<SolicitudCreditoModel> updateTerms(@RequestParam("userId") Long id,
                                                    @RequestParam("lienInsurance") Double lienInsurance,
                                                    @RequestParam("administrationFee") Double administrationFee) {
        try{
            // The loan is searched in the database.
            SolicitudCreditoModel credit = evaluacionCreditoService.findById(id);
            // The loan is updated.
            credit.setLienInsurance(lienInsurance);
            // The loan is updated.
            credit.setAdministrationFee(administrationFee);
            // The total cost of the loan is calculated.
            evaluacionCreditoService.totalCost(credit);
            // The loan is saved in the database.
            SolicitudCreditoModel creditSaved = evaluacionCreditoService.saveCredit(credit);
            // The loan is returned.
            return ResponseEntity.ok(creditSaved);
        }
        catch (Exception e) {
            // If there is an error, return null.
            return null;
        }
    }
}
