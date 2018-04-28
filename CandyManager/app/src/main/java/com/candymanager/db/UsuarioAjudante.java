package com.candymanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 18/04/2018.
 */

public class UsuarioAjudante extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Usuario.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UsuarioContrato.UsuarioEntrada.NOME_TABELA + " (" +
                    UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO + " CHAR(36) PRIMARY KEY," +
                    UsuarioContrato.UsuarioEntrada.COLUNA_NOME + " VARCHAR(45) NOT NULL," +
                    UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL + " VARCHAR(120)," +
                    UsuarioContrato.UsuarioEntrada.COLUNA_DATA_NASCIMENTO + " INTEGER," +
                    UsuarioContrato.UsuarioEntrada.COLUNA_CEP + " CHAR(8)," +
                    UsuarioContrato.UsuarioEntrada.COLUNA_ENDERECO + " VARCHAR(150)," +
                    UsuarioContrato.UsuarioEntrada.COLUNA_BAIRRO + " VARCHAR(45)," +
                    UsuarioContrato.UsuarioEntrada.COLUNA_NUMERO + " VARCHAR(10)," +
                    UsuarioContrato.UsuarioEntrada.COLUNA_SENHA + " VARCHAR(64)," +
                    UsuarioContrato.UsuarioEntrada.COLUNA_FOTO + " VARCHAR(250)" + ");";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UsuarioContrato.UsuarioEntrada.NOME_TABELA;

    public UsuarioAjudante(Context context) {
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
