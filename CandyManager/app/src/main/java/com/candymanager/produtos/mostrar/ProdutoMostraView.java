package com.candymanager.produtos.mostrar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.candymanager.R;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.candymanager.R;
import com.github.pinball83.maskededittext.MaskedEditText;



public class ProdutoMostraView {

    private ImageButton fotoImageButton;
    private EditText nomeEditText;
    private EditText descricaoEditText;

    public ProdutoMostraView(View view) {

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
