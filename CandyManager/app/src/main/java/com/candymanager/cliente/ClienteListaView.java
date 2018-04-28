package com.candymanager.cliente;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;
import com.candymanager.cliente.recycler.ClienteAdapter;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;

/**
 * Created by Usuario on 20/04/2018.
 */

public class ClienteListaView {



    private RecyclerView clientesRecyclerView;
    private FastScroller scrollerRapidoFastScroller;
    private FloatingActionButton novoClienteFloatingActionButton;
    private ClienteAdapter clienteAdapter;


    public ClienteListaView(View view, ArrayList<ClienteModel> lista) {

        clientesRecyclerView = (RecyclerView) view.findViewById(R.id.listaClienteRecyclerView);
        scrollerRapidoFastScroller = (FastScroller) view.findViewById(R.id.fastscroll);

        clientesRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        clienteAdapter =  new ClienteAdapter(view.getContext(), lista);
        clientesRecyclerView.setAdapter(clienteAdapter);
        scrollerRapidoFastScroller.setRecyclerView(clientesRecyclerView);

        novoClienteFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.novoClienteFloatingActionButton);

    }

    public RecyclerView getClientesRecyclerView() {
        return clientesRecyclerView;
    }

    public ClienteAdapter getClienteAdapter() {
        return clienteAdapter;
    }

    public FloatingActionButton getNovoClienteFloatingActionButton() {
        return novoClienteFloatingActionButton;
    }


}
