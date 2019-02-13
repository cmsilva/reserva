package br.com.magluiza.reserva.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

@Configuration
@EnableJpaRepositories("br.com.magluiza.reserva.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);
    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

    /**
     * Abre uma porta TCP para o H2 database.
     *
     * @return Servidor TCP do H2 database
     * @throws SQLException se o servidor falhar na inicialliza&ccedil;&atilde;o
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Object h2TCPServer() throws SQLException {
        log.info("Iniciando servidor H2 database");

        String port = getValidPortForH2();
        Object server = H2ConfigurationHelper.createServer(port);
        log.info("H2 database está disponível na porta {}", port);
        return server;
    }

    private String getValidPortForH2() {
        int port = Integer.parseInt(env.getProperty("server.port"));
        if (port < 10000) {
            port = 10000 + port;
        } else {
            if (port < 63536) {
                port = port + 2000;
            } else {
                port = port - 2000;
            }
        }
        return String.valueOf(port);
    }
}
