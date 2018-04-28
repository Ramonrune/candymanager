package com.candymanager.cliente.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;
import com.candymanager.cliente.ClienteModel;
import com.candymanager.util.BitmapUtil;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 20/04/2018.
 */

public class ClienteAdapter extends RecyclerView.Adapter<ClienteViewHolder> implements SectionTitleProvider {

    private Context contexto;
    private List<ClienteModel> listaClientes;
    protected static ItemClickListener itemClickListener;


    public ClienteAdapter(Context contexto, ArrayList<ClienteModel> listaClientes){
        this.contexto = contexto;
        this.listaClientes = listaClientes;
    }

    @Override
    public ClienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.model_cliente, parent,false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClienteViewHolder holder, int position) {

        ClienteModel cliente = listaClientes.get(position);

        holder.getNomeClienteTextView().setText(cliente.getNome());
        holder.getEmailClienteTextView().setText(cliente.getEmail());
        holder.getTelefoneClienteTextView().setText(cliente.getTelefone());
        holder.getFotoClienteImageView().setImageBitmap(BitmapUtil.getImage(cliente.getImagem()));

    }

    public ClienteModel getItem(int posicao){
        return listaClientes.get(posicao);
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public String getSectionTitle(int position) {
        return getItem(position).getNome().substring(0, 1).toUpperCase();
    }
}
