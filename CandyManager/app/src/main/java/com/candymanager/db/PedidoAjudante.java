package com.candymanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PedidoAjudante extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pedido.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PedidoContrato.PedidoEntrada.NOME_TABELA + " (" +
                    PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO + " CHAR(36) PRIMARY KEY," +
                    PedidoContrato.PedidoEntrada.COLUNA_DATA + " LONG NOT NULL," +
                    PedidoContrato.PedidoEntrada.COLUNA_ENDERECO + " TEXT," +
                    PedidoContrato.PedidoEntrada.COLUNA_CEP + " TEXT " + "," +
                    PedidoContrato.PedidoEntrada.COLUNA_NUMERO + " TEXT " + "," +
                    PedidoContrato.PedidoEntrada.COLUNA_BAIRRO + " TEXT " + "," +
                    PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO + " CHAR(36) NOT NULL " + "," +
                    "FOREIGN KEY ("+ PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO +") REFERENCES "+
                    UsuarioContrato.UsuarioEntrada.NOME_TABELA+"("+UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO+"))";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UsuarioContrato.UsuarioEntrada.NOME_TABELA;

    public PedidoAjudante(Context context) {
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
