package com.candymanager.cliente.mostrar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;
import com.candymanager.cliente.ClienteDAO;
import com.candymanager.cliente.ClienteModel;
import com.candymanager.cliente.cadastrar.ClienteAdicionaView;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.util.BitmapUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClienteMostraController extends Fragment {

    private ClienteMostraView clienteMostraView;
    private ClienteModel model;
    private View view;

    public ClienteMostraController() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inicializaRecursos(inflater, container);
        clienteMostraView.getEmailEditText().setText(model.getEmail());
        clienteMostraView.getNomeEditText().setText(model.getNome());
        clienteMostraView.getTelefoneEditText().setMaskedText(model.getTelefone().replace("(", "").replace(")", "").replace("-", ""));
        clienteMostraView.getFotoImageButton().setImageBitmap(BitmapUtil.getImage(model.getImagem()));
        ((MenuPrincipal) getActivity())
                .setActionBarTitle(model.getNome());
        return view;
    }


    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_cliente_mostra, container, false);;
        clienteMostraView = new ClienteMostraView(view);


    }

    public void setClienteModel(ClienteModel model){
        this.model = model;

    }
}
