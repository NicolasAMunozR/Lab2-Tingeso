package solicitudCreditoservice.solicitudCredito_service.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import solicitudCreditoservice.solicitudCredito_service.Entities.SolicitudCreditoEntity;

@Repository
public interface SolicitudCreditoRepository extends JpaRepository<SolicitudCreditoEntity, Long> {
}