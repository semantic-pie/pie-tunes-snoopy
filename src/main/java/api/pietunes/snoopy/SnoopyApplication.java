package api.pietunes.snoopy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SnoopyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnoopyApplication.class, args);
	}
}
