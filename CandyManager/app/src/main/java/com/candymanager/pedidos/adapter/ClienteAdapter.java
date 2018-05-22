package com.candymanager.pedidos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.candymanager.R;
import com.candymanager.cliente.ClienteModel;
import com.candymanager.util.BitmapUtil;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/05/2018.
 */

public class ClienteAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<ClienteModel> clienteModelArrayList;

    public ClienteAdapter(Context context, ArrayList<ClienteModel> lista){
        this.context = context;
        clienteModelArrayList = lista;

    }
    @Override
    public int getCount() {
        return clienteModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return clienteModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.model_cliente, viewGroup, false);
        TextView nomeClienteTextView = (TextView) row.findViewById(R.id.clienteNomeTextView);
        TextView telefoneClienteTextView = (TextView) row.findViewById(R.id.clienteTelefoneTextView);
        TextView emailClienteTextView = (TextView) row.findViewById(R.id.clienteEmailTextView);
        ImageView fotoClienteImageView = (ImageView) row.findViewById(R.id.clienteFotoImageView);


        nomeClienteTextView.setText(clienteModelArrayList.get(i).getNome());
        telefoneClienteTextView.setText(clienteModelArrayList.get(i).getTelefone());
        emailClienteTextView.setText(clienteModelArrayList.get(i).getEmail());
        fotoClienteImageView.setImageBitmap(BitmapUtil.getImage(clienteModelArrayList.get(i).getImagem()));

        return row;
    }
}
