package com.candymanager.pedidos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.candymanager.cliente.ClienteDAO;
import com.candymanager.db.PedidoAjudante;
import com.candymanager.db.PedidoContrato;
import com.candymanager.db.ProdutoContrato;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.produtos.ProdutoModel;
import com.candymanager.util.UUIDGenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PedidoDAO {

    private PedidoAjudante pedidoAjudante;

    private Context contexto;

    public void setContext(Context contexto) {
        this.contexto = contexto;
    }

    public Context getContext() {
        return this.contexto;
    }

    private ClienteDAO clienteDAO;
    private ItemPedidoDAO itemPedidoDAO;

    public PedidoDAO(Context context) {
        pedidoAjudante = new PedidoAjudante(context);
        itemPedidoDAO = new ItemPedidoDAO(context);

        this.contexto = context;
        clienteDAO = new ClienteDAO(context);
    }

    public boolean cadastrar(PedidoModel model) {


        System.out.println(model.getCliente().getId() + "--------");

        SQLiteDatabase db = pedidoAjudante.getWritableDatabase();

        ContentValues values = new ContentValues();

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        String uuid = UUIDGenerator.uuid();

        values.put(PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO, uuid);
        values.put(PedidoContrato.PedidoEntrada.COLUNA_BAIRRO, model.getBairro());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_CEP, model.getCep());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_ENDERECO, model.getEndereco());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_NUMERO, model.getNumero());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_DATA, model.getData());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_ID_CLIENTE, model.getCliente().getId());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO, loginSharedPreferences.getId());
        long newRowId = db.insert(PedidoContrato.PedidoEntrada.NOME_TABELA, null, values);

        if (newRowId == -1) {
            return false;
        } else {

            boolean sucesso = true;
            for (ItemPedidoModel modelPedido : model.getListaItemsDePedido()) {
                if (!itemPedidoDAO.cadastrar(modelPedido, uuid)) {
                    sucesso = false;
                    break;
                }
            }

            if (!sucesso) {
                return false;
            }


        }

        return true;

    }

    public boolean alterar(PedidoModel model) {
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        SQLiteDatabase db = pedidoAjudante.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PedidoContrato.PedidoEntrada.COLUNA_BAIRRO, model.getBairro());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_CEP, model.getCep());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_ENDERECO, model.getEndereco());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_NUMERO, model.getNumero());
        values.put(PedidoContrato.PedidoEntrada.COLUNA_DATA, model.getData());

        String selection = PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO + " = ? AND " + PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO + " = ?";
        String[] selectionArgs = {model.getIdPedido(), loginSharedPreferences.getId()};


        long newRowId = db.update(
                PedidoContrato.PedidoEntrada.NOME_TABELA,
                values,
                selection,
                selectionArgs);


        if (newRowId == -1) {
            return false;
        }

        return true;

    }

    public boolean excluir(PedidoModel model) {

        SQLiteDatabase db = pedidoAjudante.getWritableDatabase();


        String selection = PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO + " = ?";
        String[] selectionArgs = {model.getIdPedido()};


        long newRowId = db.delete(
                PedidoContrato.PedidoEntrada.NOME_TABELA,
                selection,
                selectionArgs);


        if (newRowId == -1) {
            return false;
        }

        return true;

    }

    public ArrayList<PedidoModel> getLista() {
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);

        ArrayList<PedidoModel> lista = new ArrayList<>();

        SQLiteDatabase db = pedidoAjudante.getReadableDatabase();

        String[] projection = {
                PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO,
                PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO,
                PedidoContrato.PedidoEntrada.COLUNA_ID_CLIENTE,
                PedidoContrato.PedidoEntrada.COLUNA_BAIRRO,
                PedidoContrato.PedidoEntrada.COLUNA_NUMERO,
                PedidoContrato.PedidoEntrada.COLUNA_ENDERECO,
                PedidoContrato.PedidoEntrada.COLUNA_DATA,
                PedidoContrato.PedidoEntrada.COLUNA_CEP
        };


        String selection = PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO + " = ?";


        String[] selectionArgs = {loginSharedPreferences.getId()};
        String ordenarPor =
                PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO + " COLLATE NOCASE ASC";
        Cursor cursor = db.query(
                PedidoContrato.PedidoEntrada.NOME_TABELA,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                ordenarPor
        );


        if (cursor.moveToFirst()) {
            do {
                PedidoModel pedido = new PedidoModel();


                System.out.println("aquiasasjiasdijas " + itemPedidoDAO.getLista(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO))).size());

                pedido.setIdPedido(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO)));
                pedido.setBairro(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_BAIRRO)));
                pedido.setCep(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_CEP)));
                pedido.setData(cursor.getLong(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_DATA)));
                pedido.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_ENDERECO)));
                pedido.setNumero(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_NUMERO)));
                pedido.setCliente(clienteDAO.getCliente(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_ID_CLIENTE))));
                pedido.getListaItemsDePedido().addAll(itemPedidoDAO.getLista(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO))));


                lista.add(pedido);
            } while (cursor.moveToNext());
        }
        db.close();

        return lista;

    }

    public ArrayList<PedidoModel> getListaDiaDeHoje() {
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(contexto);
        ArrayList<PedidoModel> lista = new ArrayList<>();

        SQLiteDatabase db = pedidoAjudante.getReadableDatabase();

        String[] projection = {
                PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO,
                PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO,
                PedidoContrato.PedidoEntrada.COLUNA_ID_CLIENTE,
                PedidoContrato.PedidoEntrada.COLUNA_BAIRRO,
                PedidoContrato.PedidoEntrada.COLUNA_NUMERO,
                PedidoContrato.PedidoEntrada.COLUNA_ENDERECO,
                PedidoContrato.PedidoEntrada.COLUNA_DATA,
                PedidoContrato.PedidoEntrada.COLUNA_CEP
        };

        Calendar cal = Calendar.getInstance(); //current date and time
        cal.add(Calendar.DAY_OF_MONTH, 1); //add a day
        cal.set(Calendar.HOUR_OF_DAY, 23); //set hour to last hour
        cal.set(Calendar.MINUTE, 59); //set minutes to last minute
        cal.set(Calendar.SECOND, 59); //set seconds to last second
        cal.set(Calendar.MILLISECOND, 999); //set milliseconds to last millisecond
        long millis = cal.getTimeInMillis();

        String selection = PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO + " = ? AND " + PedidoContrato.PedidoEntrada.COLUNA_DATA + " BETWEEN ? AND ?";


        String[] selectionArgs = {loginSharedPreferences.getId(), String.valueOf(inicioDoDia()), String.valueOf(fimDoDia())};
        String ordenarPor =
                PedidoContrato.PedidoEntrada.COLUNA_ID_USUARIO + " COLLATE NOCASE ASC";
        Cursor cursor = db.query(
                PedidoContrato.PedidoEntrada.NOME_TABELA,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                ordenarPor
        );


        if (cursor.moveToFirst()) {
            do {
                PedidoModel pedido = new PedidoModel();


                pedido.setIdPedido(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO)));
                pedido.setBairro(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_BAIRRO)));
                pedido.setCep(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_CEP)));
                pedido.setData(cursor.getLong(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_DATA)));
                pedido.setEndereco(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_ENDERECO)));
                pedido.setNumero(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_NUMERO)));
                pedido.setCliente(clienteDAO.getCliente(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_ID_CLIENTE))));
                pedido.getListaItemsDePedido().addAll(itemPedidoDAO.getLista(cursor.getString(cursor.getColumnIndexOrThrow(PedidoContrato.PedidoEntrada.COLUNA_ID_PEDIDO))));


                lista.add(pedido);
            } while (cursor.moveToNext());
        }
        db.close();


        return lista;
    }

    private long fimDoDia() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    public long inicioDoDia() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

}
