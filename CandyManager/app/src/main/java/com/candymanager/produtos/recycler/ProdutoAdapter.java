package com.candymanager.produtos.recycler;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;
import com.candymanager.produtos.ProdutoModel;
import com.candymanager.util.BitmapUtil;
import com.futuremind.recyclerviewfastscroll.SectionTitleProvider;

import java.util.ArrayList;
import java.util.List;



public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoViewHolder> implements SectionTitleProvider {

    private Context contexto;
    private List<ProdutoModel> listaProdutos;
    protected static ItemClickListener itemClickListener;


    public ProdutoAdapter(Context contexto, ArrayList<ProdutoModel> listaProdutos){
        this.contexto = contexto;
        this.listaProdutos = listaProdutos;
    }

    @Override
    public ProdutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.model_produto, parent,false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProdutoViewHolder holder, int position) {

        ProdutoModel produto = listaProdutos.get(position);

        holder.getNomeClienteTextView().setText(produto.getNome());
        holder.getFotoClienteImageView().setImageBitmap(BitmapUtil.getImage(produto.getImagem()));

    }

    public ProdutoModel getItem(int posicao){
        return listaProdutos.get(posicao);
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public String getSectionTitle(int position) {
        return getItem(position).getNome().substring(0, 1).toUpperCase();
    }
}
