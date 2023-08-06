package zoeq.parroticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "zoeq.parroticator")
@EnableJpaRepositories(basePackages = "zoeq.parroticator")
@ComponentScan(basePackages = "zoeq.parroticator")
@ConfigurationPropertiesScan(basePackages = "zoeq.parroticator")
public class ParroticatorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(ParroticatorApplication.class, args);
	}
}
