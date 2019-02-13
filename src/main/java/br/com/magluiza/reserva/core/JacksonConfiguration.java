package br.com.magluiza.reserva.core;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    /**
     * Suporte para Java date and time API.
     *
     * @return o m&oacute;dulo do Jackson correspondente.
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /**
     * Suporte para tipos de dados do Hibernate no Jackson.
     */
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    /**
     * M&oacute;dulo Jackson Afterburner para acelerar o processo de serializa&ccedil;&atilde;o/deserializa&ccedil;&atilde;o.
     */
    @Bean
    public AfterburnerModule afterburnerModule() {
        return new AfterburnerModule();
    }
}
