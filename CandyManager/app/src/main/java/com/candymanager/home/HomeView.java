package com.candymanager.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.candymanager.R;

/**
 * Created by Usuario on 20/04/2018.
 */

public class HomeView {

    private LinearLayout produtosLayout;
    private LinearLayout pedidosLayout;
    private LinearLayout clientesLayout;
    private LinearLayout redeSocialLayout;

    public HomeView(View view) {


        produtosLayout = (LinearLayout) view.findViewById(R.id.produtosLayout);
        pedidosLayout = (LinearLayout) view.findViewById(R.id.pedidosLayout);
        clientesLayout = (LinearLayout) view.findViewById(R.id.clientesLayout);
        redeSocialLayout = (LinearLayout) view.findViewById(R.id.redesSociaisLayout);

    }


    public LinearLayout getProdutosLayout() {
        return produtosLayout;
    }

    public LinearLayout getPedidosLayout() {
        return pedidosLayout;
    }

    public LinearLayout getClientesLayout() {
        return clientesLayout;
    }

    public LinearLayout getRedeSocialLayout() {
        return redeSocialLayout;
    }


}
