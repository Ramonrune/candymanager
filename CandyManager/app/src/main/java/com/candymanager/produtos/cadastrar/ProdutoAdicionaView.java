package com.candymanager.produtos.cadastrar;

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


public class ProdutoAdicionaView {

    private ImageButton fotoImageButton;
    private EditText nomeEditText;
    private EditText descricaoEditText;


    public ProdutoAdicionaView(View view) {

        fotoImageButton = (ImageButton) view.findViewById(R.id.fotoProdutoImageButton);
        nomeEditText = (EditText) view.findViewById(R.id.nomeProdutoEditText);
        descricaoEditText = (EditText) view.findViewById(R.id.descricaoProdutoEditText);


    }

    public ImageButton getFotoImageButton() {
        return fotoImageButton;
    }

    public EditText getNomeEditText() {
        return nomeEditText;
    }

    public EditText getDescricaoEditText() {
        return descricaoEditText;
    }
}
