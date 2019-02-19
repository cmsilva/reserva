package br.com.magluiza.reserva.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Propriedades espec&iacute;ficas da aplica&ccedil;&atilde;o.
 * <p>
 * As propriedades est&atilde;o parametrizadas no arquivo application.yml
 * contendo o prefixo "application".
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Http http = new Http();
    private final CorsConfiguration cors = new CorsConfiguration();

    public Http getHttp() {
        return http;
    }

    public CorsConfiguration getCors() {
        return cors;
    }

    public static class Http {
        public enum Version {V_1_1, V_2_0}

        /**
         * Vers&atilde;o do HTTP, deve ser "V_1_1" (para HTTP/1.1) ou V_2_0 (para (HTTP/2)
         */
        public Http.Version version = ApplicationDefaults.Http.version;

        public Http.Version getVersion() {
            return version;
        }

        public void setVersion(Http.Version version) {
            this.version = version;
        }
    }
}
