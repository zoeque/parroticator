package zoeque.parroticator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = "zoeque.parroticator")
@ConfigurationPropertiesScan(basePackages = "zoeque.parroticator")
class ParroticatorApplicationTests {

	@Test
	void contextLoads() {
	}

}
