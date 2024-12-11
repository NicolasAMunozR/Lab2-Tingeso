package seguimientoservice.seguimiento_service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.client.RestTemplate;
import seguimientoservice.seguimiento_service.Models.NuevosUsuariosModel;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class SeguimientoService {
    @Autowired
    RestTemplate restTemplate;
    public List<NuevosUsuariosModel> findCreditByIdUser(Long id){
        List<NuevosUsuariosModel> credit = restTemplate.getForObject("http://nuevosUsuarios-service/nuevosUsuarios/credit/" + id, List.class);
        return credit;
    }
}
