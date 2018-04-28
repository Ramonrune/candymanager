package com.candymanager.cliente.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.candymanager.R;

/**
 * Created by Usuario on 20/04/2018.
 */

public class ClienteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView nomeClienteTextView;
    private TextView telefoneClienteTextView;
    private TextView emailClienteTextView;
    private ImageView fotoClienteImageView;

    public ClienteViewHolder(View itemView) {
        super(itemView);

        nomeClienteTextView = (TextView) itemView.findViewById(R.id.clienteNomeTextView);
        telefoneClienteTextView = (TextView) itemView.findViewById(R.id.clienteTelefoneTextView);
        emailClienteTextView = (TextView) itemView.findViewById(R.id.clienteEmailTextView);
        fotoClienteImageView = (ImageView) itemView.findViewById(R.id.clienteFotoImageView);
        itemView.setOnClickListener(this);

    }

    public TextView getNomeClienteTextView() {
        return nomeClienteTextView;
    }

    public TextView getTelefoneClienteTextView() {
        return telefoneClienteTextView;
    }

    public TextView getEmailClienteTextView() {
        return emailClienteTextView;
    }

    public ImageView getFotoClienteImageView() {
        return fotoClienteImageView;
    }

    @Override
    public void onClick(View view) {
        if(ClienteAdapter.itemClickListener != null) {
            ClienteAdapter.itemClickListener.onItemClick(getAdapterPosition());
        }
        System.out.println(getAdapterPosition() + "-----------");

    }
}
