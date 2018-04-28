package com.candymanager.db;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 21/04/2018.
 */

public class ClienteContrato {

    private ClienteContrato(){}


    public static class ClienteEntrada implements BaseColumns {
        public static String NOME_TABELA = "Cliente";

        public static String COLUNA_ID_CLIENTE = "id_cliente";

        public static String COLUNA_NOME = "nome";

        public static String COLUNA_EMAIL = "email";

        public static String COLUNA_TELEFONE = "telefone";

        public static String COLUNA_FOTO = "foto";

        public static String COLUNA_ID_USUARIO = "id_usuario";






    }
}
