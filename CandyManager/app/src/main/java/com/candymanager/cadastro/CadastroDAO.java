package com.candymanager.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.candymanager.db.UsuarioAjudante;
import com.candymanager.db.UsuarioContrato;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.util.Criptografia;
import com.candymanager.util.UUIDGenerator;

/**
 * Created by Usuario on 18/04/2018.
 */

public class CadastroDAO {

    private UsuarioAjudante usuarioAjudante;

    private Context contexto;

    public CadastroDAO(Context context){
        usuarioAjudante = new UsuarioAjudante(context);
        this.contexto = context;
    }

    public RespostaCadastro cadastrar(CadastroModel model){

        if(usuarioJaExiste(model.getEmail())) {
            return RespostaCadastro.EMAIL_ALREADY_EXIST;
        }

        SQLiteDatabase db = usuarioAjudante.getWritableDatabase();

        ContentValues values = new ContentValues();

        String uuid = UUIDGenerator.uuid();

        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_ID_USUARIO, uuid);
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_EMAIL, model.getEmail());
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_NOME, model.getNome());
        values.put(UsuarioContrato.UsuarioEntrada.COLUNA_SENHA, Criptografia.sha256(model.getSenha()));

        long newRowId = db.insert(UsuarioContrato.UsuarioEntrada.NOME_TABELA, null, values);

        db.close();
        if(newRowId == -1){
            return RespostaCadastro.EMAIL_ERROR;
        }
        else{

            LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

            loginSharedPreferences.setUsuarioLogado(uuid, model.getNome(), model.getEmail());
            return RespostaCadastro.EMAIL_SUCCESS;
        }

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


}
