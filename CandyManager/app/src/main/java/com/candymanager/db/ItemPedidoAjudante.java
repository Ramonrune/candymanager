package com.candymanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemPedidoAjudante extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ItemPedido.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ItemPedidoContrato.ItemPedidoEntrada.NOME_TABELA + " (" +
                    ItemPedidoContrato.ItemPedidoEntrada.COLUNA_ID_ITEM_PEDIDO + " CHAR(36) PRIMARY KEY NOT NULL," +
                    ItemPedidoContrato.ItemPedidoEntrada.COLUNA_MARGEM_LUCRO + " INT," +
                    ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PEDIDO + " CHAR(36)  NOT NULL," +
                    ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PRODUTO + " CHAR(36)  NOT NULL," +
                    ItemPedidoContrato.ItemPedidoEntrada.COLUNA_QUANTIDADE + " INT," +
                    ItemPedidoContrato.ItemPedidoEntrada.COLUNA_VALOR_TOTAL_GASTO + " FLOAT," +
                    "FOREIGN KEY ("+ ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PEDIDO +") REFERENCES "+
                    PedidoContrato.PedidoEntrada.NOME_TABELA+"("+PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO+") ON DELETE CASCADE)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PedidoContrato.PedidoEntrada.NOME_TABELA;

    public ItemPedidoAjudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

