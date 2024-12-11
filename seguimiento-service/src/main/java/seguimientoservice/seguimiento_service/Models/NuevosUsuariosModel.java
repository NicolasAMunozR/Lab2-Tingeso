package seguimientoservice.seguimiento_service.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevosUsuariosModel {
    private String identifyingDocument;
    private String name;
    private String email;
    private String password;
    @Lob
    private byte[] file;
    private int jobSeniority;
    private int currentSavingsBalance;
    private String savingsAccountHistory;
    private LocalDate creationDate;
    private String depositAccount;
    private String withdrawalAccount;
    private LocalDate birthdate;
}
