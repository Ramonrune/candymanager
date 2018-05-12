package com.candymanager.db;

import android.provider.BaseColumns;

public class PedidoContrato {

    public static class PedidoEntrada implements BaseColumns {

        public static String NOME_TABELA = "pedido";

        public static String COLUNA_ID_PEDIDO = "id_pedido";

        public static String COLUNA_DATA = "data";

        public static String COLUNA_CEP = "cep";

        public static String COLUNA_ENDERECO = "endereco";

        public static String COLUNA_NUMERO = "numero";

        public static String COLUNA_BAIRRO = "bairro";

        public static String COLUNA_ID_USUARIO = "id_usuario";

    }
}
