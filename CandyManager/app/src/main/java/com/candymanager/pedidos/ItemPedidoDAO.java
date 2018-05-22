package com.candymanager.pedidos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.candymanager.cliente.ClienteDAO;
import com.candymanager.db.ItemPedidoAjudante;
import com.candymanager.db.ItemPedidoContrato;
import com.candymanager.db.PedidoAjudante;
import com.candymanager.db.PedidoContrato;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.produtos.ProdutoDAO;
import com.candymanager.util.UUIDGenerator;

import java.util.ArrayList;

public class ItemPedidoDAO {

    private ItemPedidoAjudante itemPedidoAjudante;

    private Context contexto;

    public void setContext(Context contexto) {
        this.contexto = contexto;
    }

    public Context getContext() {
        return this.contexto;
    }

    private ProdutoDAO produtoDAO;


    public ItemPedidoDAO(Context context) {
        itemPedidoAjudante = new ItemPedidoAjudante(context);
        produtoDAO = new ProdutoDAO(context);

        this.contexto = context;
    }

    public boolean cadastrar(ItemPedidoModel model, String uuid) {

        SQLiteDatabase db = itemPedidoAjudante.getWritableDatabase();

        ContentValues values = new ContentValues();

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        String id = UUIDGenerator.uuid();

        values.put(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_ID_ITEM_PEDIDO, id);
        values.put(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_MARGEM_LUCRO, model.getMargemLucro());
        values.put(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PEDIDO, uuid);
        values.put(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PRODUTO, model.getDoce().getIdProduto());
        values.put(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_QUANTIDADE, model.getQuantidade());
        values.put(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_VALOR_TOTAL_GASTO, model.getValorTotalGasto());

        long newRowId = db.insert(ItemPedidoContrato.ItemPedidoEntrada.NOME_TABELA, null, values);

        if (newRowId == -1) {
            return false;
        }


        return true;

    }


    public ArrayList<ItemPedidoModel> getLista(String uuid){

        ArrayList<ItemPedidoModel> lista = new ArrayList<>();

        SQLiteDatabase db = itemPedidoAjudante.getReadableDatabase();

        String[] projection = {
                ItemPedidoContrato.ItemPedidoEntrada.COLUNA_ID_ITEM_PEDIDO,
                ItemPedidoContrato.ItemPedidoEntrada.COLUNA_MARGEM_LUCRO,
                ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PEDIDO,
                ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PRODUTO,
                ItemPedidoContrato.ItemPedidoEntrada.COLUNA_QUANTIDADE,
                ItemPedidoContrato.ItemPedidoEntrada.COLUNA_VALOR_TOTAL_GASTO

        };


        String selection =  ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PEDIDO + " = ?";


        String[] selectionArgs = { uuid};
        String ordenarPor =
                ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PEDIDO + " COLLATE NOCASE ASC";
        Cursor cursor = db.query(
                ItemPedidoContrato.ItemPedidoEntrada.NOME_TABELA,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                ordenarPor
        );



        if (cursor.moveToFirst()) {
            do {
                ItemPedidoModel itemPedidoModel = new ItemPedidoModel();

                itemPedidoModel.setQuantidade(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_QUANTIDADE))));
                itemPedidoModel.setIdItemPedido(cursor.getString(cursor.getColumnIndexOrThrow(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_ID_ITEM_PEDIDO)));
                itemPedidoModel.setMargemLucro(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_MARGEM_LUCRO))));
                itemPedidoModel.setDoce(produtoDAO.getProduto(cursor.getString(cursor.getColumnIndexOrThrow(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_PRODUTO_ID_PRODUTO))));
                itemPedidoModel.setValorTotalGasto(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ItemPedidoContrato.ItemPedidoEntrada.COLUNA_VALOR_TOTAL_GASTO))));



                lista.add(itemPedidoModel);
            } while (cursor.moveToNext());
        }
        db.close();

        return lista;

    }



}