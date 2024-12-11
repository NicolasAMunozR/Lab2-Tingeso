package evaluacionCreditoservice.evaluacionCredito_service.Services;

import evaluacionCreditoservice.evaluacionCredito_service.Models.NuevosUsuariosModel;
import evaluacionCreditoservice.evaluacionCredito_service.Models.SolicitudCreditoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
public class EvaluacionCreditoService {
    @Autowired
    RestTemplate restTemplate;
    /**
     * Search for a loan in the database.
     * @param userId A Long with the id of the client to search the loan.
     * @return A List with the loans found.
     */
    public SolicitudCreditoModel findById(Long id) {
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getAdministrationFee());
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getCreditHistory());
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getLoanTerm());
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getBusinessPlan());
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getAppraisalCertificate());
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getAnnualInterestRate());
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getMonthlyDebt());
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getMonthlyIncome());
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getUserId());
        System.out.println(restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class).getRemodelingBudget());

        return restTemplate.getForObject("http://solicitudCredito-service/solicitudCredito/buscar/" + id, SolicitudCreditoModel.class);
    }
    /**
     * Save a loan in the database.
     * @param credit A CreditEntity with the data of the loan to save.
     * @return A CreditEntity with the data of the loan saved.
     */
    public SolicitudCreditoModel saveCredit(SolicitudCreditoModel credit) {
        return restTemplate.postForObject("http://solicitudCredito-service/solicitudCredito/save", credit, SolicitudCreditoModel.class);
    }
    /**
     * Method that allows evaluating a loan.
     * @param creditFound A CreditEntity with the data of the loan to evaluate.
     * @return A CreditEntity with the loan evaluated.
     */
    public SolicitudCreditoModel evaluateCredit(SolicitudCreditoModel creditFound) {
        System.out.println("AAAAAA");
        if (R1(creditFound) && R2(creditFound) && R3(creditFound) && R4(creditFound) && R5(creditFound) && R6(creditFound)) {
            String status = R7(creditFound);
            if (status.contains("Aprobado")) {
                creditFound.setApplicationStatus("Pre-aprobado");
                System.out.println("SIIIIIIIIIIIIIIIIIIIIII1");
                System.out.println(restTemplate.postForObject("http://solicitudCredito-service/solicitudCredito/save", creditFound, SolicitudCreditoModel.class));
                return restTemplate.postForObject("http://solicitudCredito-service/solicitudCredito/save", creditFound, SolicitudCreditoModel.class);
            } else if (status.contains("Revisión")) {
                creditFound.setApplicationStatus("En evaluación");
                System.out.println("SIIIIIIIIIIIIIIIIIIIIII2");
                System.out.println(restTemplate.postForObject("http://solicitudCredito-service/solicitudCredito/save", creditFound, SolicitudCreditoModel.class));
                return restTemplate.postForObject("http://solicitudCredito-service/solicitudCredito/save", creditFound, SolicitudCreditoModel.class);
            } else {
                creditFound.setApplicationStatus("Rechazada");
                System.out.println("SIIIIIIIIIIIIIIIIIIIIII3");
                Map<String, Object> requestMap = new HashMap<>();
                requestMap.put("credito", creditFound);
                System.out.println(saveCredit(creditFound));
                return saveCredit(creditFound);
            }
        } else {
            creditFound.setApplicationStatus("Rechazada");
            System.out.println("NOOOOOOOOOOOOOOOOOOO");
            System.out.println(restTemplate.postForObject("http://solicitudCredito-service/solicitudCredito/save", creditFound, SolicitudCreditoModel.class));
            return restTemplate.postForObject("http://solicitudCredito-service/solicitudCredito/save", creditFound, SolicitudCreditoModel.class);
        }
    }
    /**
     * Method that calculates the average income of a client.
     * @param creditFound A CreditEntity with the data of the client to calculate the average income.
     * @return A double with the average income of the client.
     */
    public double averageIncome(SolicitudCreditoModel creditFound) {
        String monthlyIncome = creditFound.getMonthlyIncome();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] entries = monthlyIncome.split(",");
        Map<LocalDate, Integer> incomePerMonth = new HashMap<>();
        for (String entry : entries) {
            String[] parts = entry.split(" ");
            if (parts.length != 2) {
                System.err.println("Invalid format for entry: " + entry);
                continue;
            }
            String dateString = parts[0].trim();
            int amount = 0;
            try {
                amount = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing amount: " + parts[1] + " - " + e.getMessage());
                continue;
            }
            try {
                LocalDate date = LocalDate.parse(dateString, formatter);
                incomePerMonth.put(date, amount);
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date: " + dateString + " - " + e.getMessage());
            }
        }
        LocalDate currentDate = LocalDate.now();
        int totalIncome = 0;
        int countedMonths = 0;
        for (Map.Entry<LocalDate, Integer> entry : incomePerMonth.entrySet()) {
            LocalDate date = entry.getKey();
            if (!date.isBefore(currentDate.minusMonths(12))) {
                totalIncome += entry.getValue();
                countedMonths++;
            }
        }
        return countedMonths > 0 ? (double) totalIncome / countedMonths : 0;
    }
    /**
     * Method that allows evaluating a loan.
     * @param credit A CreditEntity with the data of the loan to evaluate.
     * @return A Boolean with the result of the loan evaluation.
     */
    public Boolean R1(SolicitudCreditoModel creditFound) {
        int amount1 = creditFound.getRequestedAmount();
        int term = creditFound.getLoanTerm();
        Double annualInterestRate = creditFound.getAnnualInterestRate();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("amount", amount1); // Valor numérico
        requestMap.put("term", term);
        requestMap.put("interestRate", annualInterestRate);
        int monthlyFee = restTemplate.postForObject("http://simulacionCredito-service/simulacionCredito/simulation", requestMap, int.class);
        System.out.println("asdasda "+ monthlyFee);
        double averageIncome = averageIncome(creditFound);
        double FeeIncomeRatio = ((double) monthlyFee / averageIncome) * 100;
        System.out.println("VAAAAAAAAAAAAAAA " + FeeIncomeRatio);
        if (FeeIncomeRatio <= 0.35) {
            return true;
        }
        return false;
    }

    /**
     * Method that allows evaluating a loan.
     * @param credit A CreditEntity with the data of the loan to evaluate.
     * @return A Boolean with the result of the loan evaluation.
     * * */
    public Boolean R2(SolicitudCreditoModel creditFound) {
        System.out.println("AKJSDHASJKD");
        return creditFound.getCreditsHistory() != null ? creditFound.getCreditsHistory() : false;
    }

    /**
     * Method that allows evaluating a loan.
     * @param credit A CreditEntity with the data of the loan to evaluate.
     * @return A Boolean with the result of the loan evaluation.
     */
    public Boolean R3(SolicitudCreditoModel creditFound) {
        NuevosUsuariosModel user = restTemplate.getForObject("http://nuevosUsuarios-service/nuevosUsuarios/" + creditFound.getUserId(), NuevosUsuariosModel.class);
        assert user != null;
        int jobSeniority = user.getJobSeniority();
        System.out.println("JABBBBBBBBBBB " + jobSeniority);
        if (jobSeniority >= 1) {
            return true;
        }
        return false;
    }

    /**
     * Method that allows evaluating a loan.
     * @param credit A CreditEntity with the data of the loan to evaluate.
     * @return A Boolean with the result of the loan evaluation.
     */
    public Boolean R4(SolicitudCreditoModel creditFound) {
        String debt = creditFound.getMonthlyDebt();
        if(debt != null){
            String[] parts = debt.split(",");
            int sum = 0;
            for (String part : parts) {
                sum += Integer.parseInt(part);
            }
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("amount", creditFound.getRequestedAmount()); // Valor numérico
            requestMap.put("term", creditFound.getLoanTerm());
            requestMap.put("interestRate", creditFound.getAnnualInterestRate());
            sum += restTemplate.postForObject("http://simulacionCredito-service/simulacionCredito/simulation", requestMap, int.class);
            System.out.println("VALOORORORORORO "+ sum);
            if (sum > (0.5 * averageIncome(creditFound))) {
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method that allows evaluating a loan.
     * @param credit A CreditEntity with the data of the loan to evaluate.
     * @return A Boolean with the result of the loan evaluation.
     */
    public Boolean R5(SolicitudCreditoModel creditFound) {
        String typeOfLoan = creditFound.getTypeOfLoan();
        int propertyAmount = creditFound.getPropertyAmount();
        int requestedAmount = creditFound.getRequestedAmount();
        System.out.println("typeoFloan "+ typeOfLoan);
        System.out.println("property "+ propertyAmount);
        System.out.println("request "+ requestedAmount);
        if (typeOfLoan.contains("Primera Vivienda")) {
            if (requestedAmount > (0.8 * propertyAmount)) {
                return false;
            } else {
                return true;
            }
        } else if (typeOfLoan.contains("Segunda Vivienda")) {
            if (requestedAmount > (0.7 * propertyAmount)) {
                return false;
            } else {
                return true;
            }
        } else if (typeOfLoan.contains("Propiedades Comerciales")) {
            if (requestedAmount > (0.6 * propertyAmount)) {
                return false;
            } else {
                return true;
            }
        } else if (typeOfLoan.contains("Remodelación")) {
            if (requestedAmount > (0.5 * propertyAmount)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that allows evaluating a loan.
     * @param credit A CreditEntity with the data of the loan to evaluate.
     * @return A Boolean with the result of the loan evaluation.
     */
    public Boolean R6(SolicitudCreditoModel creditFound) {
        NuevosUsuariosModel user = restTemplate.getForObject("http://nuevosUsuarios-service/nuevosUsuarios/" + creditFound.getUserId(), NuevosUsuariosModel.class);
        LocalDate currentDate = LocalDate.now();
        System.out.println("birthday "+ user.getBirthdate());
        int age = Period.between(user.getBirthdate(), currentDate).getYears();
        System.out.println("inttttttttttttttt "+ age);
        if (age >= 70) {
            return false;
        }
        return true;
    }

    /**
     * Method that allows evaluating a loan.
     * @param credit A CreditEntity with the data of the loan to evaluate.
     * @return A String with the result of the loan evaluation.
     */
    public String R7(SolicitudCreditoModel creditFound) {
        System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEeee");
        int countTrue = 0;
        if (R71(creditFound)) {
            countTrue++;
        }
        if (R72(creditFound)) {
            countTrue++;
        }
        if (R73(creditFound)) {
            countTrue++;
        }
        if (R74(creditFound)) {
            countTrue++;
        }
        if (R75(creditFound)) {
            countTrue++;
        }
        if (countTrue == 5) {
            System.out.println("sIIIIIIIIIIIIiiisiisisiisisisiisis");
            return "Aprobado";
        }
        else if (countTrue >= 3 && countTrue <= 4) {
            System.out.println("taltaltaltalrla");
            return "Revisión";
        }
        else {
            System.out.println("nnnnnnnnnnnnnnnnnnnnnnnooooooooooooooasosaosa");
            return "Rechazado";
        }
    }
    /**
     * Method that allows evaluating a saving.
     * @param credit A CreditEntity with the data of the saving to evaluate.
     * @return A Boolean with the result of the saving evaluation.
     */
    public Boolean R71(SolicitudCreditoModel creditFound) {
        NuevosUsuariosModel user = restTemplate.getForObject("http://nuevosUsuarios-service/nuevosUsuarios/" + creditFound.getUserId(), NuevosUsuariosModel.class);
        System.out.println("USEEEEEEEEEER " + user.getCurrentSavingsBalance());
        System.out.println("usudusu "+ creditFound.getRequestedAmount());
        assert user != null;
        int currentSavingsBalance = user.getCurrentSavingsBalance();
        int requestedAmount = creditFound.getRequestedAmount();
        if (currentSavingsBalance>=(0.1*requestedAmount)) {
            return true;
        }
        return false;
    }

    /**
     * Method that allows evaluating a saving.
     * @param credit A CreditEntity with the data of the saving to evaluate.
     * @return A Boolean with the result of the saving evaluation.
     */
    public Boolean R72(SolicitudCreditoModel creditFound) {
        NuevosUsuariosModel user = restTemplate.getForObject("http://nuevosUsuarios-service/nuevosUsuarios/" + creditFound.getUserId(), NuevosUsuariosModel.class);
        System.out.println("SavingACCOUNT "+ user.getSavingsAccountHistory());
        String savingsAccount = user.getSavingsAccountHistory();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] entries = savingsAccount.split(",");
        LocalDate currentDate = LocalDate.now();
        boolean foundValidEntry = false;
        int previousBalance = 0;
        boolean noSignificantWithdrawals = true;
        for (String entry : entries) {
            String[] parts = entry.split(" ");
            LocalDate date = LocalDate.parse(parts[0], formatter);
            int currentBalance = Integer.parseInt(parts[1]);
            if (!date.isBefore(currentDate.minusMonths(12))) {
                if (!foundValidEntry) {
                    previousBalance = currentBalance;
                    foundValidEntry = true;
                } else {
                    double threshold = previousBalance * 0.5;
                    if (currentBalance < threshold) {
                        noSignificantWithdrawals = false;
                        break;
                    }
                    previousBalance = currentBalance;
                }
            }
        }

        if (noSignificantWithdrawals && foundValidEntry) {
            return true;
        } else if (!foundValidEntry) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that allows evaluating a saving.
     * @param credit A CreditEntity with the data of the saving to evaluate.
     * @return A Boolean with the result of the saving evaluation.
     */
    public Boolean R73(SolicitudCreditoModel creditFound) {
        NuevosUsuariosModel user = restTemplate.getForObject("http://nuevosUsuarios-service/nuevosUsuarios/" + creditFound.getUserId(), NuevosUsuariosModel.class);
        assert user != null;
        String depositAccount = user.getDepositAccount();
        System.out.println("DEPOSIT "+ user.getDepositAccount());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] entries = depositAccount.split(",");
        LocalDate currentDate = LocalDate.now();
        double totalDeposits = 0;
        int depositCount = 0;
        for (String entry : entries) {
            String[] parts = entry.split(" ");
            LocalDate depositDate = LocalDate.parse(parts[0], formatter);
            int amount = Integer.parseInt(parts[1]);
            if (!depositDate.isBefore(currentDate.minusMonths(12))) {
                totalDeposits += amount;
                depositCount++;
            }
        }
        double minimumRequiredDeposit = 0.05 * averageIncome(creditFound);
        boolean areDepositsRegular = depositCount > 0 && (depositCount >= 12 || (depositCount >= 3 && totalDeposits >= minimumRequiredDeposit));
        System.out.println("oasnfknoafsknoafsknodfnjoasodn");
        if (areDepositsRegular && totalDeposits >= minimumRequiredDeposit) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that allows evaluating a saving.
     * @param credit A CreditEntity with the data of the saving to evaluate.
     * @return A Boolean with the result of the saving evaluation.
     */
    public Boolean R74(SolicitudCreditoModel creditFound) {
        NuevosUsuariosModel user = restTemplate.getForObject("http://nuevosUsuarios-service/nuevosUsuarios/" + creditFound.getUserId(), NuevosUsuariosModel.class);
        LocalDate creationDate = user.getCreationDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(creationDate, currentDate).getYears();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("amount", creditFound.getRequestedAmount()); // Valor numérico
        requestMap.put("term", creditFound.getLoanTerm());
        requestMap.put("interestRate", creditFound.getAnnualInterestRate());
        int valor = restTemplate.postForObject("http://simulacionCredito-service/simulacionCredito/simulation", requestMap, int.class);
        System.out.println("laksdnhffffffffffffff "+ valor);
        if (age < 2 && user.getCurrentSavingsBalance() >= (0.2 * valor)) {
            return true;
        }
        else if (age >= 2 && user.getCurrentSavingsBalance() >= (0.1 * valor)) {
            return true;
        }
        return false;
    }

    /**
     * Method that allows evaluating a saving.
     * @param credit A CreditEntity with the data of the saving to evaluate.
     * @return A Boolean with the result of the saving evaluation.
     */
    public Boolean R75(SolicitudCreditoModel creditFound) {
        NuevosUsuariosModel user = restTemplate.getForObject("http://nuevosUsuarios-service/nuevosUsuarios/" + creditFound.getUserId(), NuevosUsuariosModel.class);
        String withdrawalAccount = user.getWithdrawalAccount();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] entries = withdrawalAccount.split(",");
        LocalDate currentDate = LocalDate.now();
        boolean noLargeWithdrawals = true;
        int previousBalance = 0;
        for (String entry : entries) {
            String[] parts = entry.split(" ");
            LocalDate transactionDate = LocalDate.parse(parts[0], formatter);
            int currentBalance = Integer.parseInt(parts[1]);
            if (!transactionDate.isBefore(currentDate.minusMonths(6))) {
                if (previousBalance == 0) {
                    previousBalance = currentBalance;
                } else {
                    double threshold = previousBalance * 0.3;
                    if (previousBalance - currentBalance > threshold) {
                        noLargeWithdrawals = false;
                        break;
                    }
                    previousBalance = currentBalance;
                }
            }
        }
        System.out.println("tantantantasasdasd");
        if (noLargeWithdrawals) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Method that allows to make a disbursement.
     * @param credit A CreditEntity with the data of the loan to disburse.
     */
    public void Disbursement(SolicitudCreditoModel credit) {
        NuevosUsuariosModel user = restTemplate.getForObject("http://nuevosUsuarios-service/nuevosUsuarios/" + credit.getUserId(), NuevosUsuariosModel.class);
        int currentSavingsBalance = user.getCurrentSavingsBalance();
        int requestedAmount = credit.getRequestedAmount();
        user.setCurrentSavingsBalance(currentSavingsBalance + requestedAmount);
        String deposit = String.valueOf(requestedAmount);
        String dateDeposit = LocalDate.now().toString();
        String depositInitial = user.getDepositAccount();
        if (depositInitial.length() <= 0) {
            depositInitial =  dateDeposit + " " + deposit;
        } else {
            depositInitial = depositInitial + "," + dateDeposit + " " + deposit;
        }
        currentSavingsBalance += requestedAmount;
        String savingsAccountHistory = user.getSavingsAccountHistory();
        if (savingsAccountHistory.length() <= 0) {
            savingsAccountHistory = dateDeposit + " " + String.valueOf(currentSavingsBalance);
        } else {
            savingsAccountHistory = savingsAccountHistory + "," + dateDeposit + " " + String.valueOf(currentSavingsBalance);
        }
        user.setSavingsAccountHistory(savingsAccountHistory);
        user.setDepositAccount(depositInitial);
        restTemplate.postForObject("http://nuevosUsuarios-service/nuevosUsuarios/", user, NuevosUsuariosModel.class);
    }
    /**
     * Method that calculates the total cost of a loan.
     * @param credit A CreditEntity with the data of the loan to calculate the total cost.
     */
    public void totalCost(SolicitudCreditoModel credit) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("amount", credit.getRequestedAmount()); // Valor numérico
        requestMap.put("term", credit.getLoanTerm());
        requestMap.put("interestRate", credit.getAnnualInterestRate());
        int averageIncome = restTemplate.postForObject("http://simulacionCredito-service/simulacionCredito/simulation", requestMap, int.class);
        double lienInsurance = credit.getLienInsurance() * credit.getRequestedAmount();
        double administrationFee = credit.getAdministrationFee() * credit.getRequestedAmount();
        double monthlyCost = averageIncome + lienInsurance + 20000;
        credit.setRemainingMonthlyInstallments((int)monthlyCost);
        credit.setFirstInstallment((int)(monthlyCost + administrationFee));
        credit.setTotalAmount((int)(monthlyCost * credit.getLoanTerm() * 12 + administrationFee));
    }
}
