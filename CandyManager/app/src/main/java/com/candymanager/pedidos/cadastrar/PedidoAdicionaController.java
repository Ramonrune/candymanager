package com.candymanager.pedidos.cadastrar;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.candymanager.R;
import com.candymanager.cliente.ClienteController;
import com.candymanager.util.BitmapUtil;
import com.candymanager.util.Mensagem;

import java.util.LinkedHashMap;


public class PedidoAdicionaController extends Fragment {



    private View view;

    public PedidoAdicionaController() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        inicializaRecursos(inflater, container);

        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_pedido_adiciona, container, false);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.check).setVisible(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.check:

                Toast.makeText(getContext(), "teste", Toast.LENGTH_LONG).show();
                return true;
            default:
                break;
        }

        return false;
    }

}
