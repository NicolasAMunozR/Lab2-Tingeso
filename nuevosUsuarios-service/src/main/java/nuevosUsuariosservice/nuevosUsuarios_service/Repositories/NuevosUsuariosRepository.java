package nuevosUsuariosservice.nuevosUsuarios_service.Repositories;

import nuevosUsuariosservice.nuevosUsuarios_service.Entities.NuevosUsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NuevosUsuariosRepository extends JpaRepository<NuevosUsuariosEntity, Long> {
    NuevosUsuariosEntity findByEmail(String email);
    NuevosUsuariosEntity findByIdentifyingDocument(String identifyingDocument);
}
