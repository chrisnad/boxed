package com.pubstack.homework.ws.boxed;

import com.google.common.base.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pubstack.homework.ws.boxed.api", "com.pubstack.homework.ws.boxed.api.delegate", "com.pubstack.homework.ws.boxed.conf", "com.pubstack.homework.ws.boxed.model", "com.pubstack.homework.ws.boxed.conf"})
public class BoxedApplication implements CommandLineRunner {

    /** The log. */
    private static final Logger log = LoggerFactory.getLogger(BoxedApplication.class);

    @Override
    public void run(final String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(final String[] args) throws Exception {
        new SpringApplication(BoxedApplication.class).run(args);
    }

    /**
     * Custom implementation.
     *
     * @param basePath the base path
     * @return the docket
     */
    @Bean
    public Docket customImplementation(@Value("${openapi.simpleREST.base-path:/}") final String basePath) {
        return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).select()
                .apis(RequestHandlerSelectors.basePackage("com.pubstack.homework.ws.boxed.api"))
                .paths(Predicates.not(PathSelectors.regex("/error.*"))).build()
                .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class).apiInfo(apiInfo());
    }

    /**
     * Api info.
     *
     * @return the api info
     */
    ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("BOXED").description("Simple API")
                .license("").licenseUrl("http://unlicense.org").termsOfServiceUrl("").version("1.0.0")
                .contact(new Contact("chris", "", "christiannader@hotmail.com")).build();
    }

    static class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }

}
