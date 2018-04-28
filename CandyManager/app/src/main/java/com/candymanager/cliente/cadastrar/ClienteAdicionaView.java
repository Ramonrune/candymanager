package com.candymanager.cliente.cadastrar;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.candymanager.R;
import com.candymanager.cliente.recycler.ClienteAdapter;
import com.github.pinball83.maskededittext.MaskedEditText;

/**
 * Created by Usuario on 21/04/2018.
 */

public class ClienteAdicionaView {

    private ImageButton fotoImageButton;
    private EditText nomeEditText;
    private EditText emailEditText;
    private MaskedEditText telefoneEditText;


    public ClienteAdicionaView(View view) {

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
