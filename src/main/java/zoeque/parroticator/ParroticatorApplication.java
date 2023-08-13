package zoeque.parroticator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "zoeque.parroticator")
@ComponentScan(basePackages = "zoeque.parroticator")
@ConfigurationPropertiesScan(basePackages = "zoeque.parroticator")
public class ParroticatorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(ParroticatorApplication.class, args);
	}
}
