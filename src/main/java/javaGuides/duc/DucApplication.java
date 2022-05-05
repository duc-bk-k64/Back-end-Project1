package javaGuides.duc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@SpringBootApplication(scanBasePackages = "javaGuides.duc")
@EnableWebMvc
public class DucApplication {

	public static void main(String[] args) {
		SpringApplication.run(DucApplication.class, args);
	}

}
