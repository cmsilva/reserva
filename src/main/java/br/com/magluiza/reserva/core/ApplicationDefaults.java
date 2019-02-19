package br.com.magluiza.reserva.core;

/**
 * Valores padr&atilde.o para os par&acirc;metros de configura&ccedil;&atilde;o da aplica&ccdil.&atilde;o.
 */
public interface ApplicationDefaults {

    interface Http {
        ApplicationProperties.Http.Version version = ApplicationProperties.Http.Version.V_1_1;
    }
}
