package th.co.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@ComponentScan("th.co.bookstore")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class})
@PropertySources({
	@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
})
public class BookStoreWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreWebAppApplication.class, args);
	}
}
