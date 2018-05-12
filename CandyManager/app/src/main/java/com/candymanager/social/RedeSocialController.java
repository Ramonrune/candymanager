package com.candymanager.social;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RedeSocialController extends Fragment {

    private RedeSocialDAO redeSocialDAO;
    private RedeSocialModel redesSociaisModel;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void inicializaListeners() {

    }

    private void inicializaRecursos() {
        redeSocialDAO = new RedeSocialDAO();
        redesSociaisModel = new RedeSocialModel();
    }




}
