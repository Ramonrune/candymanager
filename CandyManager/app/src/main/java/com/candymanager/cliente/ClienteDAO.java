package com.candymanager.cliente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.candymanager.R;
import com.candymanager.cadastro.CadastroModel;
import com.candymanager.cadastro.RespostaCadastro;
import com.candymanager.db.ClienteAjudante;
import com.candymanager.db.ClienteContrato;
import com.candymanager.db.UsuarioAjudante;
import com.candymanager.db.UsuarioContrato;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.util.Criptografia;
import com.candymanager.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 18/04/2018.
 */

public class ClienteDAO {

    private ClienteAjudante clienteAjudante;

    private Context contexto;

    public void setContext(Context contexto) {
        this.contexto = contexto;
    }

    public Context getContext() {
        return this.contexto;
    }

    public ClienteDAO(Context context) {
        clienteAjudante = new ClienteAjudante(context);
        this.contexto = context;
    }

    public boolean cadastrar(ClienteModel model) {

        SQLiteDatabase db = clienteAjudante.getWritableDatabase();

        ContentValues values = new ContentValues();

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        String uuid = UUIDGenerator.uuid();

        values.put(ClienteContrato.ClienteEntrada.COLUNA_ID_CLIENTE, uuid);
        values.put(ClienteContrato.ClienteEntrada.COLUNA_NOME, model.getNome());
        values.put(ClienteContrato.ClienteEntrada.COLUNA_EMAIL, model.getEmail());
        values.put(ClienteContrato.ClienteEntrada.COLUNA_TELEFONE, model.getTelefone());
        values.put(ClienteContrato.ClienteEntrada.COLUNA_FOTO, model.getImagem());
        values.put(ClienteContrato.ClienteEntrada.COLUNA_ID_USUARIO, loginSharedPreferences.getId());

        long newRowId = db.insert(ClienteContrato.ClienteEntrada.NOME_TABELA, null, values);

        if (newRowId == -1) {
            return false;
        }

        return true;

    }

    public boolean alterar(ClienteModel model) {
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        SQLiteDatabase db = clienteAjudante.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ClienteContrato.ClienteEntrada.COLUNA_NOME, model.getNome());
        values.put(ClienteContrato.ClienteEntrada.COLUNA_EMAIL, model.getEmail());
        values.put(ClienteContrato.ClienteEntrada.COLUNA_TELEFONE, model.getTelefone());
        values.put(ClienteContrato.ClienteEntrada.COLUNA_FOTO, model.getImagem());


        String selection = ClienteContrato.ClienteEntrada.COLUNA_ID_CLIENTE + " = ? AND " + ClienteContrato.ClienteEntrada.COLUNA_ID_USUARIO + " = ?";
        String[] selectionArgs = {model.getId(), loginSharedPreferences.getId()};


        long newRowId = db.update(
                ClienteContrato.ClienteEntrada.NOME_TABELA,
                values,
                selection,
                selectionArgs);

        System.out.println(newRowId);

        if (newRowId == -1) {
            return false;
        }

        return true;

    }


    public boolean excluir(ClienteModel model) {
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        SQLiteDatabase db = clienteAjudante.getWritableDatabase();


        String selection = ClienteContrato.ClienteEntrada.COLUNA_ID_CLIENTE + " = ? AND " + ClienteContrato.ClienteEntrada.COLUNA_ID_USUARIO + " = ?";
        String[] selectionArgs = {model.getId(), loginSharedPreferences.getId()};


        long newRowId = db.delete(
                ClienteContrato.ClienteEntrada.NOME_TABELA,
                selection,
                selectionArgs);


        if (newRowId == -1) {
            return false;
        }

        return true;

    }

    public ArrayList<ClienteModel> getLista() {
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);


        ArrayList<ClienteModel> lista = new ArrayList<>();

        SQLiteDatabase db = clienteAjudante.getReadableDatabase();

        String[] projection = {
                ClienteContrato.ClienteEntrada.COLUNA_ID_CLIENTE,
                ClienteContrato.ClienteEntrada.COLUNA_ID_USUARIO,
                ClienteContrato.ClienteEntrada.COLUNA_NOME,
                ClienteContrato.ClienteEntrada.COLUNA_EMAIL,
                ClienteContrato.ClienteEntrada.COLUNA_TELEFONE,
                ClienteContrato.ClienteEntrada.COLUNA_FOTO

        };


        String selection = ClienteContrato.ClienteEntrada.COLUNA_ID_USUARIO + " = ?";


        String[] selectionArgs = {loginSharedPreferences.getId()};
        String ordenarPor =
                ClienteContrato.ClienteEntrada.COLUNA_NOME + " COLLATE NOCASE ASC";
        Cursor cursor = db.query(
                ClienteContrato.ClienteEntrada.NOME_TABELA,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                ordenarPor
        );


        if (cursor.moveToFirst()) {
            do {
                ClienteModel cliente = new ClienteModel();
                cliente.setNome(cursor.getString(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_NOME)));
                cliente.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_EMAIL)));
                cliente.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_TELEFONE)));
                cliente.setId(cursor.getString(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_ID_CLIENTE)));
                cliente.setImagem(cursor.getBlob(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_FOTO)));


                lista.add(cliente);
            } while (cursor.moveToNext());
        }
        db.close();


        return lista;

    }


    public ClienteModel getCliente(String clienteId) {


        SQLiteDatabase db = clienteAjudante.getReadableDatabase();

        String[] projection = {
                ClienteContrato.ClienteEntrada.COLUNA_ID_CLIENTE,
                ClienteContrato.ClienteEntrada.COLUNA_ID_USUARIO,
                ClienteContrato.ClienteEntrada.COLUNA_NOME,
                ClienteContrato.ClienteEntrada.COLUNA_EMAIL,
                ClienteContrato.ClienteEntrada.COLUNA_TELEFONE,
                ClienteContrato.ClienteEntrada.COLUNA_FOTO

        };


        String selection = ClienteContrato.ClienteEntrada.COLUNA_ID_CLIENTE + " = ?";


        String[] selectionArgs = {clienteId};
        String ordenarPor =
                ClienteContrato.ClienteEntrada.COLUNA_NOME + " COLLATE NOCASE ASC";
        Cursor cursor = db.query(
                ClienteContrato.ClienteEntrada.NOME_TABELA,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                ordenarPor
        );


        ClienteModel cliente = new ClienteModel();

        if (cursor.moveToFirst()) {

            cliente.setNome(cursor.getString(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_NOME)));
            cliente.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_EMAIL)));
            cliente.setTelefone(cursor.getString(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_TELEFONE)));
            cliente.setId(cursor.getString(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_ID_CLIENTE)));
            cliente.setImagem(cursor.getBlob(cursor.getColumnIndexOrThrow(ClienteContrato.ClienteEntrada.COLUNA_FOTO)));


        }
        db.close();


        return cliente;

    }


}
