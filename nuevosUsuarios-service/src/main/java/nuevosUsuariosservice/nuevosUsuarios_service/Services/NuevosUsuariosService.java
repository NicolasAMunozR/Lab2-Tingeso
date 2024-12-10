package nuevosUsuariosservice.nuevosUsuarios_service.Services;

import nuevosUsuariosservice.nuevosUsuarios_service.Entities.NuevosUsuariosEntity;
import nuevosUsuariosservice.nuevosUsuarios_service.Repositories.NuevosUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NuevosUsuariosService {
    @Autowired
    private NuevosUsuariosRepository nuevosUsuariosRepository;
    /**
     * Save a client in the database.
     * @param user A UserEntity with the data of the client to save.
     * @return A UserEntity with the data of the client saved.
     */
    public NuevosUsuariosEntity saveUser(NuevosUsuariosEntity user) {
        return nuevosUsuariosRepository.save(user);
    }
    /**
     * Search for a client in the database.
     * @param email A String with the email of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public NuevosUsuariosEntity findUserByEmail(String email) {
        return nuevosUsuariosRepository.findByEmail(email);
    }
    /**
     * List all clients in the database.
     * @return An ArrayList with all the clients found.
     */
    public ArrayList<NuevosUsuariosEntity> findAll() {
        return (ArrayList<NuevosUsuariosEntity>) nuevosUsuariosRepository.findAll();
    }
    /**
     * Search for a client in the database.
     * @param email A String with the email of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public NuevosUsuariosEntity findByEmail(String email) {
        return nuevosUsuariosRepository.findByEmail(email);
    }
    /**
     * Search for a client in the database.
     * @param identifyingDocument A String with the identifying document of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public NuevosUsuariosEntity findByIdentifyingDocument(String identifyingDocument) {
        return nuevosUsuariosRepository.findByIdentifyingDocument(identifyingDocument);
    }
    /**
     * Search for a client in the database.
     * @param id A Long with the id of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public NuevosUsuariosEntity findById(Long id) {
        return nuevosUsuariosRepository.findById(id).orElse(null);
    }
    /**
     * Search for a client in the database.
     * @param id A Long with the id of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public NuevosUsuariosEntity findUserById(Long id) {
        return nuevosUsuariosRepository.findById(id).orElse(null);
    }
    /**
     * Delete a client from the database.
     * @param user A UserEntity with the data of the client to delete.
     */
    public void deleteUser(NuevosUsuariosEntity user) {
        nuevosUsuariosRepository.delete(user);

        //if (creditService.getAllCredits().size() > 0) {
        //    creditService.deleted(user);
        //}
    }
}
