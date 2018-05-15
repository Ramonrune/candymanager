package com.candymanager.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;
import com.candymanager.cliente.ClienteController;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.pedidos.PedidoController;
import com.candymanager.produtos.ProdutoController;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeController extends Fragment {
    private HomeView homeView;

    public HomeController() {
        // Required empty public constructor
    }


    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializaRecursos(inflater, container);

        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Home");

        homeView.getClientesLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MenuPrincipal) getActivity())
                        .setCheckedItem(R.id.nav_clientes);
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();

                ClienteController clienteController = new ClienteController();

                manager.beginTransaction().replace(R.id.relative_layout_fragmento, clienteController, clienteController.getTag()).addToBackStack(null).commit();
            }
        });



        homeView.getProdutosLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((MenuPrincipal) getActivity())
                        .setCheckedItem(R.id.nav_produtos);
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();

                ProdutoController produtoController = new ProdutoController();

                manager.beginTransaction().replace(R.id.relative_layout_fragmento, produtoController, produtoController.getTag()).addToBackStack(null).commit();
            }
        });


        homeView.getPedidosLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuPrincipal) getActivity())
                        .setCheckedItem(R.id.nav_pedidos);

                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();

                PedidoController pedidoController = new PedidoController();
                manager.beginTransaction().replace(R.id.relative_layout_fragmento, pedidoController, pedidoController.getTag()).addToBackStack(null).commit();


            }
        });
        return view;
    }

    private void inicializaRecursos(LayoutInflater inflate, ViewGroup container) {
        view = inflate.inflate(R.layout.fragment_home, container, false);

        homeView = new HomeView(view);
    }

}
