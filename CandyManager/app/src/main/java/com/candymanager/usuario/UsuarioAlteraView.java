package com.candymanager.usuario;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.candymanager.R;
import com.github.pinball83.maskededittext.MaskedEditText;

public class UsuarioAlteraView {

        private ImageButton fotoImageButton;
        private EditText nomeEditText;
        private EditText emailEditText;
        private EditText cepEditText;
        private EditText enderecoEditText;
        private EditText bairroEditText;
        private EditText numeroEditText;


        public UsuarioAlteraView(View view) {

            fotoImageButton = (ImageButton) view.findViewById(R.id.fotoUsuarioImageButton);
            nomeEditText = (EditText) view.findViewById(R.id.nomeUsuarioEditText);
            emailEditText = (EditText) view.findViewById(R.id.emailUsuarioEditText);
            cepEditText = (EditText) view.findViewById(R.id.cepUsuarioEditText2);
            enderecoEditText = (EditText) view.findViewById(R.id.enderecoUsuarioEditText);
            bairroEditText = (EditText) view.findViewById(R.id.bairroUsuarioEditText2);
            numeroEditText = (EditText) view.findViewById(R.id.numeroUsuarioEditText);


        }

        public ImageButton getFotoImageButton() {return fotoImageButton;}


        public EditText getNomeEditText() { return nomeEditText;}



        public EditText getEmailEditText() {return emailEditText;}



        public EditText getCepEditText() {return cepEditText;}



        public EditText getEnderecoEditText() {return enderecoEditText;}



        public EditText getBairroEditText() {return bairroEditText; }



        public EditText getNumeroEditText() {return numeroEditText;}


}
