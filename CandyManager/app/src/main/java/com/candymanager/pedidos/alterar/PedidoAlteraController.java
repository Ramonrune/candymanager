package com.candymanager.pedidos.alterar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;
import com.candymanager.pedidos.PedidoDAO;
import com.candymanager.pedidos.PedidoModel;
import com.candymanager.produtos.ProdutoDAO;
import com.candymanager.util.Validation;


/**
 * A simple {@link Fragment} subclass.
 */
public class PedidoAlteraController extends Fragment {

    private PedidoAlteraView pedidoAlteraView;
    private PedidoModel model;
    private PedidoDAO pedidoDAO;

    private View view;
    private Validation validacao = new Validation();

    public PedidoAlteraController() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_pedido_altera, container, false);;
        pedidoAlteraView = new PedidoAlteraView(view);
        pedidoDAO = new PedidoDAO(container.getContext());
        setHasOptionsMenu(true);
    }
}
