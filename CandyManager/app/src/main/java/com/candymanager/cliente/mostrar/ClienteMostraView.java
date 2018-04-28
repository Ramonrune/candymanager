package com.candymanager.cliente.mostrar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.candymanager.R;
import com.github.pinball83.maskededittext.MaskedEditText;

/**
 * Created by Usuario on 22/04/2018.
 */

public class ClienteMostraView {

    private ImageButton fotoImageButton;
    private EditText nomeEditText;
    private EditText emailEditText;
    private MaskedEditText telefoneEditText;


    public ClienteMostraView(View view) {
        fotoImageButton = (ImageButton) view.findViewById(R.id.fotoClienteImageButton);
        nomeEditText = (EditText) view.findViewById(R.id.nomeClienteEditText);
        emailEditText = (EditText) view.findViewById(R.id.emailClienteEditText);
        telefoneEditText = (MaskedEditText) view.findViewById(R.id.telefoneClienteEditText);
    }

    public ImageButton getFotoImageButton() {
        return fotoImageButton;
    }

    public EditText getNomeEditText() {
        return nomeEditText;
    }

    public EditText getEmailEditText() {
        return emailEditText;
    }

    public MaskedEditText getTelefoneEditText() {
        return telefoneEditText;
    }
}
