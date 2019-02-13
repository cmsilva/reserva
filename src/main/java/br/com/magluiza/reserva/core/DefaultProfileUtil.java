package br.com.magluiza.reserva.core;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilit&aacute;rio para carregar um perfil do Spring para ser utilizado como padr&atilde;o,
 * quando n&atilde;o tiver um <code>spring.profiles.active</code> setado no contexto de execu&ccedil;&atilde;o
 * ou via argumento de linha de comando.
 * Se um valor n&atilde;o estiver dispon&iacute;vel no <code>application.yml</code> então o perfil <code>dev</code>
 * ser&aacute; utilizado como padr&atilde;o.
 */
public final class DefaultProfileUtil {

    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    private DefaultProfileUtil() {
        super();
    }

    /**
     * Seta o valor padr&atilde;o para ser utilizado quando n&atilde;o tem um perfil configurado.
     *
     * @param app a aplica&ccedil;&atilde;o Spring
     */
    public static void addDefaultProfile(SpringApplication app) {
        Map<String, Object> defProperties = new HashMap<>();
        defProperties.put(SPRING_PROFILE_DEFAULT, Constants.SPRING_PROFILE_DEVELOPMENT);
        app.setDefaultProperties(defProperties);
    }

    /**
     * Recupera os perfis que foram setados ou recupera os perfils padr&otilde;es.
     *
     * @param env contexto do Spring
     * @return perfis
     */
    public static String[] getActiveProfiles(Environment env) {
        String[] profiles = env.getActiveProfiles();
        if (profiles.length == 0) {
            return env.getDefaultProfiles();
        }
        return profiles;
    }
}
