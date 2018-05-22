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
import com.candymanager.produtos.ProdutoModel;
import com.candymanager.util.BitmapUtil;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/05/2018.
 */

public class ProdutoAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<ProdutoModel> produtoModelArrayList;

    public ProdutoAdapter(Context context, ArrayList<ProdutoModel> lista){
        this.context = context;
        produtoModelArrayList = lista;

    }
    @Override
    public int getCount() {
        return produtoModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return produtoModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.model_produto, viewGroup, false);
        TextView nomeProdutoTextView = (TextView) row.findViewById(R.id.produtoNomeTextView);
        ImageView fotoProdutoImageView = (ImageView) row.findViewById(R.id.produtoFotoImageView);


        nomeProdutoTextView.setText(produtoModelArrayList.get(i).getNome());
        fotoProdutoImageView.setImageBitmap(BitmapUtil.getImage(produtoModelArrayList.get(i).getImagem()));

        return row;
    }
}
