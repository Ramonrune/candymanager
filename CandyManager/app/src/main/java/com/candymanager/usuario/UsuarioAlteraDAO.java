package com.candymanager.usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.candymanager.R;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.usuario.UsuarioModel;
import com.candymanager.db.UsuarioAjudante;
import com.candymanager.db.UsuarioContrato;

import java.util.ArrayList;
import java.util.List;

public class UsuarioAlteraDAO {

    private UsuarioAjudante usuarioAjudante;

    private Context contexto;

    public void setContext(Context contexto) {
        this.contexto = contexto;
    }

    public Context getContext() {
        return this.contexto;
    }

    public UsuarioAlteraDAO(Context context) {
        usuarioAjudante = new UsuarioAjudante(context);
        this.contexto = context;
    }

    public boolean alterar(UsuarioModel model) {
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        SQLiteDatabase db = usuarioAjudante.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_NOME, model.getNome());
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL, model.getEmail());
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_CEP, model.getCep());
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_ENDERECO, model.getEndereco());
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_BAIRRO, model.getBairro());
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_NUMERO, model.getNumero());
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_FOTO, model.getFoto());

        String selection = UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO + " = ? " ;
        String[] selectionArgs = {loginSharedPreferences.getId()};

        long newRowId = db.update(
                UsuarioContrato.UsuarioEntrada.NOME_TABELA,
                values,
                selection,
                selectionArgs);

        System.out.println(newRowId);

        if (newRowId == -1) {
            return false;
        }

        return true;

    }

    private boolean usuarioJaExiste(String email){
        SQLiteDatabase db = usuarioAjudante.getReadableDatabase();

        String[] projection = {
                UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO,
                UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL
        };

        String selection = UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL + " = ?";
        String[] selectionArgs = { email };


        Cursor cursor = db.query(
                UsuarioContrato.UsuarioEntrada.NOME_TABELA,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.getCount() == 0){
            db.close();

            return false;
        }
        db.close();

        return true;

    }

    public UsuarioModel getUsuarioModel () {

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getContext());

        UsuarioModel usuarioModel = new UsuarioModel();

        SQLiteDatabase db = usuarioAjudante.getReadableDatabase();

        String[] projection = {
                UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO,
                UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL,
                UsuarioContrato.UsuarioEntrada.COLUNA_NOME,
                UsuarioContrato.UsuarioEntrada.COLUNA_FOTO,
                UsuarioContrato.UsuarioEntrada.COLUNA_BAIRRO,
                UsuarioContrato.UsuarioEntrada.COLUNA_NUMERO,
                UsuarioContrato.UsuarioEntrada.COLUNA_ENDERECO,
                UsuarioContrato.UsuarioEntrada.COLUNA_CEP
        };


        String selection = UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO + " = ?";

        String[] selectionArgs = {loginSharedPreferences.getId()};

        String ordenarPor =
                UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO + " COLLATE NOCASE ASC";
        Cursor cursor = db.query(
                UsuarioContrato.UsuarioEntrada.NOME_TABELA,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                ordenarPor
        );

        if (cursor.moveToFirst()) {
            do {

                usuarioModel.setFoto(cursor.getBlob(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_FOTO)));
                usuarioModel.setBairro(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_BAIRRO)));
                usuarioModel.setCep(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_CEP)));
                usuarioModel.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL)));
                usuarioModel.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_ENDERECO)));
                usuarioModel.setNumero(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_NUMERO)));
                usuarioModel.setNome(cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_NOME)));

            } while (cursor.moveToNext());
        }
        db.close();

        return usuarioModel;

    }

}
