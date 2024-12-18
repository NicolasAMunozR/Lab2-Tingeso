package nuevosUsuariosservice.nuevosUsuarios_service.Entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NuevosUsuariosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identifying_document")
    private String identifyingDocument;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Lob
    private byte[] file;

    @Column(name = "job_seniority")
    private int jobSeniority;

    @Column(name = "current_savings_balance")
    private int currentSavingsBalance;

    @Column(name = "savings_account_history")
    private String savingsAccountHistory;

    // Getter y setter para creationDate
    @Getter
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "deposit_account")
    private String depositAccount;

    @Column(name = "withdrawal account")
    private String withdrawalAccount;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    public NuevosUsuariosEntity(String identifyingDocument, String name, String email, String password, byte[] file, int jobSeniority, LocalDate birthdate) {
        this.identifyingDocument = identifyingDocument;
        this.name = name;
        this.email = email;
        this.password = password;
        this.file = file;
        this.jobSeniority = jobSeniority;
        this.birthdate = birthdate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public void setCurrentSavingsBalance(int currentSavingsBalance){
        this.currentSavingsBalance = currentSavingsBalance;
    }
    public void setSavingsAccountHistory(String savingsAccountHistory){
        this.savingsAccountHistory = savingsAccountHistory;
    }
    public void setDepositAccount(String depositAccount){
        this.depositAccount = depositAccount;
    }
    public void setWithdrawalAccount(String withdrawalAccount){
        this.withdrawalAccount = withdrawalAccount;
    }
    public void setFile(byte[] file){
        this.file = file;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
}

