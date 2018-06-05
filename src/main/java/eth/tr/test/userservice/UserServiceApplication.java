package eth.tr.test.userservice;

import eth.tr.test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"eth.tr.test"})
@EntityScan(basePackages={"eth.tr.test.model"})
@EnableJpaRepositories(basePackages={"eth.tr.test.repository"})
public class UserServiceApplication {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}


}
