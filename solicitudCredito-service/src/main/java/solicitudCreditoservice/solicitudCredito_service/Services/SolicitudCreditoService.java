package solicitudCreditoservice.solicitudCredito_service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import solicitudCreditoservice.solicitudCredito_service.Entities.SolicitudCreditoEntity;
import solicitudCreditoservice.solicitudCredito_service.Repositories.SolicitudCreditoRepository;

import java.util.ArrayList;

@SpringBootApplication
@EnableDiscoveryClient
public class SolicitudCreditoService {
    @Autowired
    private SolicitudCreditoRepository solicitudCreditoRepository;
    /**
     * Save a loan in the database.
     * @param credit A CreditEntity with the data of the loan to save.
     * @return A CreditEntity with the data of the loan saved.
     */
    public SolicitudCreditoEntity saveCredit(SolicitudCreditoEntity credit) {
        return solicitudCreditoRepository.save(credit);
    }
    /**
     * Method that allows to know the status of the loan application.
     * @param credit A CreditEntity with the data of the loan to know the status of the application.
     * @return A CreditEntity with the status of the loan application.
     */
    public SolicitudCreditoEntity applicationStatus(SolicitudCreditoEntity credit) {
        String typeOfLoan = credit.getTypeOfLoan();
        if (typeOfLoan.contains("Primera Vivienda")) {
            if (credit.getProofOfIncome() != null && credit.getAppraisalCertificate() != null && credit.getCreditHistory() != null) {
                credit.setApplicationStatus("En revisión");
            } else {
                credit.setApplicationStatus("Pendiente de documentación");
            }
        } else if (typeOfLoan.contains("Segunda Vivienda")) {
            if (credit.getProofOfIncome() != null && credit.getAppraisalCertificate() != null && credit.getDeedOfTheFirstHome() != null && credit.getCreditHistory() != null) {
                credit.setApplicationStatus("En revisión");
            } else {
                credit.setApplicationStatus("Pendiente de documentación");
            }
        } else if (typeOfLoan.contains("Propiedades Comerciales")) {
            if (credit.getProofOfIncome() != null && credit.getAppraisalCertificate() != null && credit.getFinancialStatusOfTheBusiness() != null && credit.getBusinessPlan() != null) {
                credit.setApplicationStatus("En revisión");
            } else {
                credit.setApplicationStatus("Pendiente de documentación");
            }
        } else if (typeOfLoan.contains("Remodelación")) {
            if (credit.getProofOfIncome() != null && credit.getUpdatedAppraisalCertificate() != null && credit.getRemodelingBudget() != null) {
                credit.setApplicationStatus("En revisión");
            } else {
                credit.setApplicationStatus("Pendiente de documentación");
            }
        } else {
            return null;
        }
        return solicitudCreditoRepository.save(credit);
    }
    /**
     * Search for a loan in the database.
     * @param userId A Long with the id of the client to search the loan.
     * @return A List with the loans found.
     */
    public SolicitudCreditoEntity findById(Long id) {
        return solicitudCreditoRepository.findById(id).orElse(null);
    }
    /**
     * Method that allows to obtain all the loans.
     * @return A List with all the loans.
     */
    public ArrayList<SolicitudCreditoEntity> getAllCredits() {
        return (ArrayList<SolicitudCreditoEntity>) solicitudCreditoRepository.findAll();
    }
    /**
     * Search for a loan in the database.
     * @param user_id A Long with the id of the client to search the loan.
     * @return A List with the loans found.
     */
    public ArrayList<SolicitudCreditoEntity> findByIdUser(Long id) {
        return (ArrayList<SolicitudCreditoEntity>) solicitudCreditoRepository.findByUserId(id);
    }

}
