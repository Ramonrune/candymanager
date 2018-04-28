package com.candymanager.produtos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.candymanager.cliente.ClienteModel;
import com.candymanager.db.ClienteAjudante;
import com.candymanager.db.ClienteContrato;
import com.candymanager.db.ProdutoAjudante;
import com.candymanager.db.ProdutoContrato;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 25/04/2018.
 */

public class ProdutoDAO {

    private ProdutoAjudante produtoAjudante;

    private Context contexto;

    public void setContext(Context contexto){
        this.contexto = contexto;
    }

    public Context getContext(){
        return this.contexto;
    }

    public ProdutoDAO(Context context){
        produtoAjudante = new ProdutoAjudante(context);
        this.contexto = context;
    }

    public boolean cadastrar(ProdutoModel model){

        SQLiteDatabase db = produtoAjudante.getWritableDatabase();

        ContentValues values = new ContentValues();

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        String uuid = UUIDGenerator.uuid();

        values.put(ProdutoContrato.ProdutoEntrada.COLUNA_ID_PRODUTO, uuid);
        values.put(ProdutoContrato.ProdutoEntrada.COLUNA_NOME, model.getNome());
        values.put(ProdutoContrato.ProdutoEntrada.COLUNA_DESCRICAO, model.getDescricao());
        values.put(ProdutoContrato.ProdutoEntrada.COLUNA_FOTO, model.getImagem());
        values.put(ProdutoContrato.ProdutoEntrada.COLUNA_ID_USUARIO, loginSharedPreferences.getId());

        long newRowId = db.insert(ProdutoContrato.ProdutoEntrada.NOME_TABELA, null, values);

        if(newRowId == -1){
            return false;
        }

        return true;

    }

    public boolean alterar(ProdutoModel model){
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        SQLiteDatabase db = produtoAjudante.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ProdutoContrato.ProdutoEntrada.COLUNA_NOME, model.getNome());
        values.put(ProdutoContrato.ProdutoEntrada.COLUNA_DESCRICAO, model.getDescricao());
        values.put(ProdutoContrato.ProdutoEntrada.COLUNA_FOTO, model.getImagem());


        String selection =ProdutoContrato.ProdutoEntrada.COLUNA_ID_PRODUTO + " = ? AND " + ProdutoContrato.ProdutoEntrada.COLUNA_ID_USUARIO + " = ?";
        String[] selectionArgs = { model.getIdProduto(), loginSharedPreferences.getId() };


        long newRowId =  db.update(
                ProdutoContrato.ProdutoEntrada.NOME_TABELA,
                values,
                selection,
                selectionArgs);


        if(newRowId == -1){
            return false;
        }

        return true;

    }


    public boolean excluir(ProdutoModel model){
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        SQLiteDatabase db = produtoAjudante.getWritableDatabase();


        String selection = ProdutoContrato.ProdutoEntrada.COLUNA_ID_PRODUTO + " = ? AND " + ProdutoContrato.ProdutoEntrada.COLUNA_ID_USUARIO + " = ?";
        String[] selectionArgs = { model.getIdProduto(), loginSharedPreferences.getId() };


        long newRowId =  db.delete(
                ProdutoContrato.ProdutoEntrada.NOME_TABELA,
                selection,
                selectionArgs);


        if(newRowId == -1){
            return false;
        }

        return true;

    }

    public ArrayList<ProdutoModel> getLista(){
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);


        ArrayList<ProdutoModel> lista = new ArrayList<>();

        SQLiteDatabase db = produtoAjudante.getReadableDatabase();

        String[] projection = {
                ProdutoContrato.ProdutoEntrada.COLUNA_ID_PRODUTO,
                ProdutoContrato.ProdutoEntrada.COLUNA_ID_USUARIO,
                ProdutoContrato.ProdutoEntrada.COLUNA_NOME,
                ProdutoContrato.ProdutoEntrada.COLUNA_DESCRICAO,
                ProdutoContrato.ProdutoEntrada.COLUNA_FOTO
        };


        String selection =  ProdutoContrato.ProdutoEntrada.COLUNA_ID_USUARIO + " = ?";


        String[] selectionArgs = { loginSharedPreferences.getId()};
        String ordenarPor =
                ProdutoContrato.ProdutoEntrada.COLUNA_NOME+ " COLLATE NOCASE ASC";
        Cursor cursor = db.query(
                ProdutoContrato.ProdutoEntrada.NOME_TABELA,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                ordenarPor
        );



        if (cursor.moveToFirst()) {
            do {
                ProdutoModel produto = new ProdutoModel();

                System.out.println("---------" + cursor.getString(cursor.getColumnIndexOrThrow(ProdutoContrato.ProdutoEntrada.COLUNA_NOME)));
                System.out.println("---------" + cursor.getString(cursor.getColumnIndexOrThrow(ProdutoContrato.ProdutoEntrada.COLUNA_ID_PRODUTO)));

                produto.setNome(cursor.getString(cursor.getColumnIndexOrThrow(ProdutoContrato.ProdutoEntrada.COLUNA_NOME)));
                produto.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(ProdutoContrato.ProdutoEntrada.COLUNA_DESCRICAO)));
                produto.setIdProduto(cursor.getString(cursor.getColumnIndexOrThrow(ProdutoContrato.ProdutoEntrada.COLUNA_ID_PRODUTO)));
                produto.setImagem(cursor.getBlob(cursor.getColumnIndexOrThrow(ProdutoContrato.ProdutoEntrada.COLUNA_FOTO)));

                System.out.println("\n\n");

                lista.add(produto);
            } while (cursor.moveToNext());
        }
        db.close();





        return lista;

    }


}
