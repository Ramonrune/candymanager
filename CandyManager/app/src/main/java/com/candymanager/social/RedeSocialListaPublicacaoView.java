package com.candymanager.social;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.social.publicar.RedeSocialPublicaController;
import com.candymanager.social.publicar.RedeSocialPublicaView;

public class RedeSocialListaPublicacaoView extends Fragment{

    private RedeSocialPublicaView publicarView;
    private RedeSocialPublicaController redeSocialPublicaController;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
