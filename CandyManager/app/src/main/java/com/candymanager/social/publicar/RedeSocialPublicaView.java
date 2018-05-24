package com.candymanager.social.publicar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.candymanager.R;

public class RedeSocialPublicaView {

    private ImageButton imageDoceImageButton;
    private EditText legendaEditText;
    private Switch publicarFacebookSwitch;
    private Switch publicarInstagramSwitch;
    private Switch publicarTwitterSwitch;

    public RedeSocialPublicaView(View view){

        imageDoceImageButton = (ImageButton) view.findViewById(R.id.imagePreviewProdutoImageButton);
        legendaEditText = (EditText) view.findViewById(R.id.legendaEditText);
       // publicarFacebookSwitch = (Switch) view.findViewById(R.id.publicarFacebookSwitch);
       // publicarInstagramSwitch = (Switch) view.findViewById(R.id.publicarInstagramSwitch);
       // publicarTwitterSwitch = (Switch) view.findViewById(R.id.publicarTwitterSwitch);

    }

    public ImageButton getImageDoceImageButton() {
        return imageDoceImageButton;
    }

    public EditText getLegendaEditText() {
        return legendaEditText;
    }

    public Switch getPublicarFacebookSwitch() {
        return publicarFacebookSwitch;
    }

    public Switch getPublicarInstagramSwitch() {
        return publicarInstagramSwitch;
    }

    public Switch getPublicarTwitterSwitch() {
        return publicarTwitterSwitch;
    }
}
