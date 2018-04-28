package com.candymanager.db;

import android.provider.BaseColumns;

/**
 * Created by Usuario on 18/04/2018.
 */

public class UsuarioContrato {

    private UsuarioContrato(){}


    public static class UsuarioEntrada implements BaseColumns{
        public static String NOME_TABELA = "Usuario";

        public static String COLUNA_ID_USUARIO = "id_usuario";

        public static String COLUNA_DATA_NASCIMENTO = "data_nascimento";

        public static String COLUNA_CEP = "cep";

        public static String COLUNA_ENDERECO = "endereco";

        public static String COLUNA_NUMERO = "numero";

        public static String COLUNA_BAIRRO = "bairro";

        public static String COLUNA_SENHA = "senha";

        public static String COLUNA_NOME = "nome";

        public static String COLUNA_EMAIL = "email";

        public static String COLUNA_TELEFONE = "telefone";

        public static String COLUNA_FOTO = "foto";
    }
}
