package simulacionCreditoservice.simulacionCredito_service.Models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "credit")

public class SimulacionCreditoModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @Column(name = "user_id")
        private Long userId;
    
        @Column(name = "requested_amount")
        private int requestedAmount;
    
        @Column(name = "loan_term")
        private int loanTerm;
    
        @Column(name = "annual_interest_rate")
        private Double annualInterestRate;
    }
    
