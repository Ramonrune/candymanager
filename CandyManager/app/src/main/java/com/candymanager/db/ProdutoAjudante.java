package com.candymanager.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 18/04/2018.
 */

public class ProdutoAjudante extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Produto.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProdutoContrato.ProdutoEntrada.NOME_TABELA + " (" +
                    ProdutoContrato.ProdutoEntrada.COLUNA_ID_PRODUTO + " CHAR(36) PRIMARY KEY," +
                    ProdutoContrato.ProdutoEntrada.COLUNA_NOME + " VARCHAR(45) NOT NULL," +
                    ProdutoContrato.ProdutoEntrada.COLUNA_DESCRICAO + " TEXT," +
                    ProdutoContrato.ProdutoEntrada.COLUNA_FOTO + " BLOB " + "," +
                    ProdutoContrato.ProdutoEntrada.COLUNA_ID_USUARIO + " CHAR(36) NOT NULL " + "," +
                    "FOREIGN KEY ("+ ProdutoContrato.ProdutoEntrada.COLUNA_ID_USUARIO +") REFERENCES "+
                    UsuarioContrato.UsuarioEntrada.NOME_TABELA+"("+UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO+"))";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UsuarioContrato.UsuarioEntrada.NOME_TABELA;

    public ProdutoAjudante(Context context) {
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
