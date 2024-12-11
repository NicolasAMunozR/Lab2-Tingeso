package nuevosUsuariosservice.nuevosUsuarios_service.Controllers;

import nuevosUsuariosservice.nuevosUsuarios_service.Entities.NuevosUsuariosEntity;
import nuevosUsuariosservice.nuevosUsuarios_service.Models.SolicitudCreditoModel;
import nuevosUsuariosservice.nuevosUsuarios_service.Services.NuevosUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/nuevosUsuarios")
public class NuevosUsuariosController {
    @Autowired
    public NuevosUsuariosService nuevosUsuariosService;
    /**
     * Controller that allows obtaining a client by id.
     * @param id A Long with the client's id to search.
     * @return A UserEntity with the client's data found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NuevosUsuariosEntity> getUser(@PathVariable Long id) {
        try {
            // The user is searched in the database.
            NuevosUsuariosEntity user = nuevosUsuariosService.findUserById(id);
            // The user found is returned.
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // If the user is not found, return null.
            return null;
        }
    }
    /**
     * Controller that allows obtaining all the clients in the database.
     * @return A List with all the clients found.
     */
    @GetMapping("/all")
    public ResponseEntity<List<NuevosUsuariosEntity>> getAllUsers() {
        // All users are searched in the database.
        List<NuevosUsuariosEntity> users = nuevosUsuariosService.findAll();
        // The users found are returned.
        return ResponseEntity.ok(users);
    }
    /**
     * Controller that allows creating a new client.
     * @param body A Map with the client's data to save.
     * @return A UserEntity with the client's data saved.
     */
    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<NuevosUsuariosEntity> registerUser(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("identifyingDocument") String identifyingDocument,
                                                             @RequestParam("name") String name,
                                                             @RequestParam("email") String email,
                                                             @RequestParam("password") String password,
                                                             @RequestParam("jobSeniority") int jobSeniority,
                                                             @RequestParam("birthdate") LocalDate birthdate) {
        try {
            String filename = file.getOriginalFilename();
            // If the file is a PDF, the client is saved in the database.
            if (filename != null && filename.toLowerCase().endsWith(".pdf")) {
                // The file is converted to bytes.
                byte[] pdfBytes = file.getBytes();
                NuevosUsuariosEntity user = new NuevosUsuariosEntity(identifyingDocument, name, email, password, pdfBytes, jobSeniority, birthdate);
                user.setCreationDate(LocalDate.now());
                user.setCurrentSavingsBalance(0);
                user.setSavingsAccountHistory("");
                user.setDepositAccount("");
                user.setWithdrawalAccount("");
                nuevosUsuariosService.saveUser(user);
                // The user is saved in the database.
                return ResponseEntity.ok(user);
            } else {
                // If the file is not a PDF, return null.
                return null;
            }
        } catch (Exception e) {
            // If there is an error, return null.
            return null;
        }
    }
    /**
     * Controller that allows updating a client's data.
     * @param body A Map with the client's data to update.
     * @return A UserEntity with the client's data updated.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<NuevosUsuariosEntity> updateUser(
            @RequestParam("file") MultipartFile file,
            @RequestParam("email") String email,
            @PathVariable Long id) {
        try {
            String filename = file.getOriginalFilename();
            if (filename != null && filename.toLowerCase().endsWith(".pdf")) {
                byte[] pdfBytes = file.getBytes();
                NuevosUsuariosEntity user = nuevosUsuariosService.findById(id);
                user.setFile(pdfBytes);
                user.setEmail(email);
                nuevosUsuariosService.saveUser(user);
                return ResponseEntity.ok(user);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Controller that allows deleting a client.
     * @param id A Long with the client's id to delete.
     * @return A boolean that indicates if the client was deleted.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        try {
            // The user is searched in the database.
            NuevosUsuariosEntity user = nuevosUsuariosService.findUserById(id);
            // The user is deleted from the database.
            nuevosUsuariosService.deleteUser(user);
            // If the user is deleted, return true.
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // If the user is not found, return false.
            return ResponseEntity.ok(false);
        }
    }
    /**
     * Controller that allows finding a client's name by id.
     * @param id A Long with the client's id to find the name.
     * @return A String with the client's name found.
     */
    @GetMapping("/users/name/{id}")
    public ResponseEntity<String> findNameById(@PathVariable Long id) {
        try {
            // The user is searched in the database.
            NuevosUsuariosEntity user = nuevosUsuariosService.findUserById(id);
            // The user's name is returned.
            return ResponseEntity.ok(user.getName());
        } catch (Exception e) {
            // If the user is not found, return null.
            return null;
        }
    }
    @GetMapping("/credit/{userId}")
    public ResponseEntity<List<SolicitudCreditoModel>> getCredits(@PathVariable("userId") Long userId){
        NuevosUsuariosEntity usuarios = nuevosUsuariosService.findUserById(userId);
        if (usuarios == null){
            return ResponseEntity.notFound().build();
        }
        List<SolicitudCreditoModel> credits = nuevosUsuariosService.findCreditByIdUser(userId);
        return ResponseEntity.ok(credits);
    }
}
