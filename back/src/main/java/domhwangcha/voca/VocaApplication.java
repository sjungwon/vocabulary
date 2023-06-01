package domhwangcha.voca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VocaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VocaApplication.class, args);
	}

}
