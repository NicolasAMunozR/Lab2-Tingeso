package evaluacionCreditoservice.evaluacionCredito_service.Models;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudCreditoModel {
    private Long userId;
    private String monthlyIncome;
    private Boolean creditsHistory;
    private int requestedAmount;
    private int loanTerm;
    private Double annualInterestRate;
    private Double lienInsurance;
    private Double administrationFee;
    @Lob
    private byte[] proofOfIncome;
    @Lob
    private byte[] appraisalCertificate;
    @Lob
    private byte[] creditHistory;
    @Lob
    private byte[] deedOfTheFirstHome;
    @Lob
    private byte[] financialStatusOfTheBusiness;
    @Lob
    private byte[] businessPlan;
    @Lob
    private byte[] remodelingBudget;
    @Lob
    private byte[] updatedAppraisalCertificate;
    private String applicationStatus;
    private int firstInstallment;
    private int remainingMonthlyInstallments;
    private String typeOfLoan;
    private String monthlyDebt;
    private int propertyAmount;
    private int totalAmount;
}
