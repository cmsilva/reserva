package br.com.magluiza.reserva.core;

/**
 * Valores padr&atilde.o para os par&acirc;metros de configura&ccedil;&atilde;o da aplica&ccdil.&atilde;o.
 */
public interface ApplicationDefaults {

    interface Http {
        ApplicationProperties.Http.Version version = ApplicationProperties.Http.Version.V_1_1;
    }

    interface Swagger {
        String title = "Application API";
        String description = "API documentation";
        String version = "0.0.1";
        String termsOfServiceUrl = null;
        String contactName = null;
        String contactUrl = null;
        String contactEmail = null;
        String license = null;
        String licenseUrl = null;
        String basePackage = "br.com.magluiza.reserva.web.rest";
        String host = null;
        String[] protocols = {};
        boolean useDefaultResponseMessages = true;
    }
}
