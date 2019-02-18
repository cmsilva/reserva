package br.com.magluiza.reserva.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"br.com.magluiza.reserva", "net.kaczmarzyk"})
public class ModuleConfiguration {
}
