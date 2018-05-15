package com.candymanager.pedidos;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.candymanager.R;
import com.candymanager.pedidos.recycler.PedidoAdapter;
import com.candymanager.produtos.ProdutoModel;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;

public class PedidoListaView {

    private RecyclerView pedidoRecyclerView;
    private FastScroller scrollerRapidoFastScroller;
    private FloatingActionButton novoPedidoFloatingActionButton;
    private PedidoAdapter pedidoAdapter;


    public PedidoListaView(View view, ArrayList<ProdutoModel> lista) {

        pedidoRecyclerView = (RecyclerView) view.findViewById(R.id.listaProdutoRecyclerView);
        scrollerRapidoFastScroller = (FastScroller) view.findViewById(R.id.produtoFastScroller);

        pedidoRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        pedidoAdapter =  new PedidoAdapter(view.getContext(), lista);
        pedidoRecyclerView.setAdapter(pedidoAdapter);
        scrollerRapidoFastScroller.setRecyclerView(pedidoRecyclerView);

        novoPedidoFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.novoProdutoFloatingActionButton);
    }

    public RecyclerView getPedidoRecyclerView() {
        return pedidoRecyclerView;
    }

    public FloatingActionButton getNovoPedidooFloatingActionButton() {
        return novoPedidoFloatingActionButton;
    }

    public PedidoAdapter getPedidoAdapter() {
        return pedidoAdapter;
    }


}
