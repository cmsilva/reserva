package br.com.magluiza.reserva.core;


import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

/**
 * Corre&ccedil;&atilde;o para o problema de convers&atilde;o do tipo de dado "real" no H2
 * https://hibernate.atlassian.net/browse/HHH-9861
 */
public class FixedH2Dialect extends H2Dialect {

    public FixedH2Dialect() {
        super();
        registerColumnType(Types.FLOAT, "real");
    }
}

