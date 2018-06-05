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
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_BAIRRO, model.getNumero());
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
}
