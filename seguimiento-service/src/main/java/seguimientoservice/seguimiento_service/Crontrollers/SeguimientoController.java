package seguimientoservice.seguimiento_service.Crontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seguimientoservice.seguimiento_service.Models.NuevosUsuariosModel;
import seguimientoservice.seguimiento_service.Services.SeguimientoService;

import java.util.List;

@RestController
@RequestMapping("/seguimiento")
public class SeguimientoController {
    @Autowired
    private SeguimientoService seguimientoService;
    @GetMapping("/credit/{userId}")
    public ResponseEntity<List<NuevosUsuariosModel>> getCredits(@PathVariable("userId") Long userId){
        List<NuevosUsuariosModel> credits = seguimientoService.findCreditByIdUser(userId);
        return ResponseEntity.ok(credits);
    }
}
