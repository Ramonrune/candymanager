package com.candymanager.produtos.recycler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.candymanager.R;
import com.candymanager.cliente.recycler.ClienteAdapter;


public class ProdutoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView nomeProdutoTextView;
    private ImageView fotoProdutoImageView;

    public ProdutoViewHolder(View itemView) {
        super(itemView);

        nomeProdutoTextView = (TextView) itemView.findViewById(R.id.produtoNomeTextView);
        fotoProdutoImageView = (ImageView) itemView.findViewById(R.id.produtoFotoImageView);
        itemView.setOnClickListener(this);

    }

    public TextView getNomeClienteTextView() {
        return nomeProdutoTextView;
    }

    public ImageView getFotoClienteImageView() {
        return fotoProdutoImageView;
    }

    @Override
    public void onClick(View view) {
        if(ProdutoAdapter.itemClickListener != null) {
            ProdutoAdapter.itemClickListener.onItemClick(getAdapterPosition());
        }

    }
}
