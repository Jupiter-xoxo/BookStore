package th.co.bookstore.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiModelReader;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import th.co.bookstore.config.security.documentation.FormLoginOperations;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)   
          .apiInfo(getApiInfo())
          .select()          
          .paths(PathSelectors.ant("/**"))                          
          .build();                                           
    }
    
    private ApiInfo getApiInfo() {
		return new ApiInfo("REST Api Documentation",
			"REST Api Documentation Description",
			"1.0",
			"",
			new Contact("", "", ""),
			"", "",
			Lists.newArrayList());
	}
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");
     
        registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    @Primary
	@Bean
	public ApiListingScanner addExtraOperations(
			ApiDescriptionReader apiDescriptionReader,
			ApiModelReader apiModelReader,
			DocumentationPluginsManager pluginsManager,
			TypeResolver typeResolver) {
		return new FormLoginOperations(apiDescriptionReader, apiModelReader, pluginsManager, typeResolver);
	}
}