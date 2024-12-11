package simulacionCreditoservice.simulacionCredito_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SimulacionCreditoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulacionCreditoServiceApplication.class, args);
	}

}
