package br.com.magluiza.reserva.core;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 * Utilit&aacute;rio para configurar o H2 database.
 */
public final class H2ConfigurationHelper {

    private H2ConfigurationHelper() {
        super();
    }

    public static Object createServer(String port) throws SQLException {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> serverClass = Class.forName("org.h2.tools.Server", true, loader);
            Method createServer = serverClass.getMethod("createTcpServer", String[].class);
            return createServer.invoke(null, new Object[]{new String[]{"-tcp", "-tcpAllowOthers", "-tcpPort", port}});

        } catch (ClassNotFoundException | LinkageError e) {
            throw new RuntimeException("Falha ao carregar e inicializar o org.h2.tools.Server", e);

        } catch (SecurityException | NoSuchMethodException e) {
            throw new RuntimeException("Falha ao recuperar o método org.h2.tools.Server.createTcpServer()", e);

        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException("Falha ao invocar o método org.h2.tools.Server.createTcpServer()", e);

        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t instanceof SQLException) {
                throw (SQLException) t;
            }
            throw new RuntimeException("Erro inesperado, org.h2.tools.Server.createTcpServer()", t);
        }
    }

    public static void initH2Console(ServletContext servletContext) {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> servletClass = Class.forName("org.h2.server.web.WebServlet", true, loader);
            Servlet servlet = (Servlet) servletClass.newInstance();

            ServletRegistration.Dynamic h2ConsoleServlet = servletContext.addServlet("H2Console", servlet);
            h2ConsoleServlet.addMapping("/h2-console/*");
            h2ConsoleServlet.setInitParameter("-properties", "src/main/resources/");
            h2ConsoleServlet.setLoadOnStartup(1);

        } catch (ClassNotFoundException | LinkageError e) {
            throw new RuntimeException("Falha ao carregar e inicializar  org.h2.server.web.WebServlet", e);

        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Falha ao instanciar org.h2.server.web.WebServlet", e);
        }
    }
}

