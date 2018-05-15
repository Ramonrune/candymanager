package com.candymanager.pedidos.cadastrar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;


public class PedidoAdicionaController extends Fragment {


    public PedidoAdicionaController() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pedido_adiciona, container, false);
    }

}
