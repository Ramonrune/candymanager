package com.candymanager.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.candymanager.db.UsuarioAjudante;
import com.candymanager.db.UsuarioContrato;
import com.candymanager.util.Criptografia;

/**
 * Created by Usuario on 18/04/2018.
 */

public class LoginDAO {
    private UsuarioAjudante usuarioAjudante;
    private Context contexto;

    public LoginDAO(Context context) {
        usuarioAjudante = new UsuarioAjudante(context);
        this.contexto = context;
    }


    public boolean usuarioExiste(LoginModel model){
        SQLiteDatabase db = usuarioAjudante.getReadableDatabase();

        String[] projection = {
                UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO,
                UsuarioContrato.UsuarioEntrada.COLUNA_NOME,
                UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL,
                UsuarioContrato.UsuarioEntrada.COLUNA_SENHA

        };

        String selection = UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL + " = ? AND " + UsuarioContrato.UsuarioEntrada.COLUNA_SENHA + " = ?";
        String[] selectionArgs = { model.getEmail(), Criptografia.sha256(model.getSenha())};


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
            return false;
        }
        cursor.moveToFirst();
        String idUsuario = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO));
        String nomeUsuario = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_NOME));
        String emailUsuario = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL));

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        loginSharedPreferences.setUsuarioLogado(idUsuario, nomeUsuario, emailUsuario);
        db.close();
        return true;

    }
}
