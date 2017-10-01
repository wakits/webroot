package webroot.websrv;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import webroot.websrv.auth.entity.User;
import webroot.websrv.auth.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "webroot", considerNestedRepositories = true)
public class WebsrvApplication implements CommandLineRunner {

	@Autowired
    DataSource dataSource;

    @Autowired
    UserRepository userRepository;
    
	public static void main(String[] args) {
		SpringApplication.run(WebsrvApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("DATASOURCE = " + dataSource);

        System.out.println("\n1.findAll()...");
        for (User user : userRepository.findAll()) {
            System.out.println(user.getEmail());
        }
	}
}
