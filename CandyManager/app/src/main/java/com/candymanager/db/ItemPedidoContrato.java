package com.candymanager.db;

import android.provider.BaseColumns;

public class ItemPedidoContrato {

    public static class ItemPedidoEntrada implements BaseColumns {

        public static String NOME_TABELA = "item_pedido";

        public static String COLUNA_ID_ITEM_PEDIDO = "id_item_pedido";

        public static String COLUNA_QUANTIDADE = "quantidade";

        public static String COLUNA_VALOR_TOTAL_GASTO = "valo_total_gastos";

        public static String COLUNA_MARGEM_LUCRO = "margem_lucro";

        public static String COLUNA_PRODUTO_ID_PRODUTO = "produto_id_produto";

        public static String COLUNA_PRODUTO_ID_PEDIDO = "produto_id_pedido";

    }
}
