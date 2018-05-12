package com.candymanager.social;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.candymanager.social.publicar.ListarPublicacoesView;
import com.candymanager.social.publicar.PublicarView;

public class RedesSociaisView {

    private PublicarView publicarView;
    private ListarPublicacoesView listarPublicacoesView;
    private Fragment fragment;

    public RedesSociaisView(AppCompatActivity view){

    }

    public Fragment postar(){
        return fragment;
    }

    public Fragment listar(){
        return fragment;
    }
}
