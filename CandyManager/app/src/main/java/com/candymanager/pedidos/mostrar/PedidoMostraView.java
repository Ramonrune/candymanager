package com.candymanager.pedidos.mostrar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.candymanager.R;

public class PedidoMostraView {

    private ImageButton calendarioImageButton;
    private EditText clienteEditText;
    private EditText dataEditText;
    private EditText pedidoEditText;

    public PedidoMostraView(View view) {

        clienteEditText = (EditText) view.findViewById(R.id.nomeDoClienteEditText);
        dataEditText = (EditText) view.findViewById(R.id.dataEditText);

    }

    public ImageButton getCalendarioImageButton() {
        return calendarioImageButton;
    }

    public EditText getClienteEditText() {
        return clienteEditText;
    }

    public EditText getDataEditText() {
        return dataEditText;
    }
}
