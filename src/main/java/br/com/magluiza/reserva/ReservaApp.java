package br.com.magluiza.reserva;

import br.com.magluiza.reserva.core.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableConfigurationProperties({ApplicationProperties.class})
public class ReservaApp {

    private static final Logger log = LoggerFactory.getLogger(ReservaApp.class);
    private final Environment env;

    public ReservaApp(Environment env) {
        this.env = env;
    }

    /**
     * Inicializa a aplica&ccedil;&atilde;o de Reservas de Sala.
     * <p>
     * Os perfis do Spring podem ser configurados via argumento para o programa --spring.profiles.active=your-active-profile
     * <p>
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
            log.error("Você deve ter configurado erroneamente a aplicação! Isto não deveria acontecer," +
                    "ambos os perfis de 'dev' e 'prod' não podem ser ativados ao mesmo tempo.");
        }
    }

    /**
     * M&eacute;todo principal, utilizado para iniciar a aplica&ccedil;&atilde;o.
     *
     * @param args os argumentos da linha de comando
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(
                ReservaApp.class,
                ModuleConfiguration.class,
                WebConfigurer.class,
                JacksonConfiguration.class,
                DateTimeFormatConfiguration.class,
                SpecificationResolverConfiguration.class,
                DatabaseConfiguration.class,
                SwaggerConfiguration.class
        );
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("O nome do host não pode ser identificado, usando `localhost` como alternativa");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Aplicação '{}' está no ar! Acesse as URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "Externa: \t{}://{}:{}{}\n\t" +
                        "Perfil(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles());
    }
}
