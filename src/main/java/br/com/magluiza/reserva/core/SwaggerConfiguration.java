package br.com.magluiza.reserva.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile(Constants.SPRING_PROFILE_SWAGGER)
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final ApplicationProperties.Swagger swagger;

    public SwaggerConfiguration(ApplicationProperties applicationProperties) {
        this.swagger = applicationProperties.getSwagger();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant(swagger.getDefaultIncludePattern()))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swagger.getTitle())
                .description(swagger.getDescription())
                .version(swagger.getVersion())
                .termsOfServiceUrl(swagger.getTermsOfServiceUrl())
                .contact(new Contact(swagger.getContactName(), swagger.getContactUrl(), swagger.getContactEmail()))
                .license(swagger.getLicense())
                .licenseUrl(swagger.getLicenseUrl())
                .build();
    }
}
