package com.candymanager.produtos;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.candymanager.R;
import com.candymanager.cliente.recycler.ClienteAdapter;
import com.candymanager.produtos.recycler.ProdutoAdapter;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 25/04/2018.
 */

public class ProdutoListaView {

    private RecyclerView produtoRecyclerView;
    private FastScroller scrollerRapidoFastScroller;
    private FloatingActionButton novoProdutoFloatingActionButton;
    private ProdutoAdapter produtoAdapter;


    public ProdutoListaView(View view, ArrayList<ProdutoModel> lista) {

        produtoRecyclerView = (RecyclerView) view.findViewById(R.id.listaProdutoRecyclerView);
        scrollerRapidoFastScroller = (FastScroller) view.findViewById(R.id.produtoFastScroller);

        produtoRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        produtoAdapter =  new ProdutoAdapter(view.getContext(), lista);
        produtoRecyclerView.setAdapter(produtoAdapter);
        scrollerRapidoFastScroller.setRecyclerView(produtoRecyclerView);

        novoProdutoFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.novoProdutoFloatingActionButton);
    }

    public RecyclerView getProdutoRecyclerView() {
        return produtoRecyclerView;
    }

    public FloatingActionButton getNovoProdutoFloatingActionButton() {
        return novoProdutoFloatingActionButton;
    }

    public ProdutoAdapter getProdutoAdapter() {
        return produtoAdapter;
    }

}
