package com.candymanager.social.publicar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

public class RedeSocialPublicaView {

    private ImageButton imageDoce;
    private String legenda;
    private Switch publicarFacebook;
    private Switch publicarInstagram;
    private Switch publicarTwitter;
    private Button publicarButton;

    public RedeSocialPublicaView(View view){

    }

    public Switch getPublicarTwitter() {
        return publicarTwitter;
    }

    public Switch getPublicarInstagram() {
        return publicarInstagram;
    }

    public Switch getPublicarFacebook() {
        return publicarFacebook;
    }

    public String getLegenda() {
        return legenda;
    }

    public ImageButton getImageDoce() {
        return imageDoce;
    }

    public void setImageDoce(ImageButton imageDoce) {
        this.imageDoce = imageDoce;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public void setPublicarButton(Button publicarButton) {
        this.publicarButton = publicarButton;
    }

    public void setPublicarInstagram(Switch publicarInstagram) {
        this.publicarInstagram = publicarInstagram;
    }

    public void setPublicarFacebook(Switch publicarFacebook) {
        this.publicarFacebook = publicarFacebook;
    }

    public void setPublicarTwitter(Switch publicarTwitter) {
        this.publicarTwitter = publicarTwitter;
    }
}
